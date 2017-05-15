package com.npb.gp.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.noun.CouchMapper;
import com.npb.gp.dao.mysql.support.noun.GpDto_noun_and_attributes;
import com.npb.gp.dao.mysql.support.noun.InsertNoun;
import com.npb.gp.dao.mysql.support.noun.InsertNounAttribute;
import com.npb.gp.dao.mysql.support.noun.InsertRest_Link;
import com.npb.gp.dao.mysql.support.noun.InsertWSDL_Link;
import com.npb.gp.dao.mysql.support.noun.NounAttributeBaseMapper;
import com.npb.gp.dao.mysql.support.noun.NounBaseMapper;
import com.npb.gp.dao.mysql.support.noun.Noun_base_attr_type_mapper;
import com.npb.gp.dao.mysql.support.noun.Noun_with_attributes_mapper;
import com.npb.gp.dao.mysql.support.noun.OtherNounMapper;
import com.npb.gp.dao.mysql.support.noun.UpdateNoun;
import com.npb.gp.dao.mysql.support.noun.UpdateNounAttribute;
import com.npb.gp.dao.mysql.support.noun.UpdateNounDefaultActivity;
import com.npb.gp.domain.core.GpCouchBasedomain;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpNounAttributeType;
import com.npb.gp.domain.core.GpOtherNoun;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.interfaces.dao.IGpNounDao;

/**
 * 
 * @author Dan Castillo</br> Date Created: 04/08/2014</br>
 * @since .35</p>
 *
 *        The purpose of this class is to interact with the db for the basic
 *        search</br> and CRUD operations for a noun</p>
 *
 *        please note that a form of this class has been in use since version
 *        .10 of the</br> Geppetto system. The .10 version is also known as
 *        "Cancun"</p>
 *
 *
 *        Modified Date: 10/22/2014</br> Modified By: Dan Castillo</p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex
 * 
 *        <b>Modified Date: 13/03/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        modified codes in insert noun method and insert noun attributes method
 *        to re-initialize the datasource.
 * 
 *        wrote new methods as update noun and update noun attributes to update
 *        the noun values and attributes
 *        </p>
 * 
 *        <b>Modified Date: 18/03/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Added new method as findAllNounAttribute to fetch all the noun
 *        attributes based on the noun id
 *        </p>
 * 
 *        <b>Modified Date: 23/03/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Added new method as deleteNounAttribute to delete the noun attributes
 *        based on the noun id and its id
 *        </p>
 * 
 * 
 *        <b>Modified Date: 26/03/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Wrote the "delete_noun" to delete the nouns and its attributes
 *        </p>
 *
 */
@Repository("GpNounDao")
public class GpNounDao implements IGpNounDao {
	

	private Log LOG = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private InsertNoun insert_noun;
	private InsertNounAttribute insert_noun_attribute;
	
	private InsertWSDL_Link  insertWSDL_link;
 
	private InsertRest_Link insertRest_Link;
	
	private UpdateNoun update_noun;
	private UpdateNounAttribute update_noun_attribute;
	private UpdateNounDefaultActivity update_noun_default_activity;

	@Value("${insertNoun.sql}")
	private String insertNoun;

	@Value("${insertNounAttribute.sql}")
	private String insertNounAttribute;
	
	@Value("${insertWsdlLink.sql}")
	private String insertWsdlLink;
	
	@Value("${insertRestLink.sql}")
	private String insertRestLink;
	
	@Value("${insertothernounmaster.sql}")
	private String insertothernounmaster;
	
	@Value("${get_mongo_noun.sql}")
	private String get_mongo_nouns;

	@Value("${findNounByNounId.sql}")
	private String findNounByNounId;

	@Value("${findAllBaseNounByProject.sql}")
	private String findAllBaseNounByProject;

	@Value("${findAllOtherNouns.sql}")
	private String findAllOtherNouns;
	
	@Value("${findallbucket.sql}")
	private String findallbucket;
	
	@Value("${findalldesign.sql}")
	private String findalldesign;
	
	@Value("${findallviews.sql}")
	private String findallviews;
	
	@Value("${listOfTableForOtheNoun.sql}")
	private String listOfTableForOtheNoun;
	
	@Value("${listOfTableForCouchNoun.sql}")
	private String listOfTableForCouchNoun;
	
	@Value("${findAllNounByProjectId.sql}")
	private String findAllNounByProject;

	@Value("${updateNoun.sql}")
	private String updateNoun;

	@Value("${updateNounDefaultactivity.sql}")
	private String updateNounDefaultactivity;

	@Value("${updateNounAttribute.sql}")
	private String updateNounAttribute;

	@Value("${findAllNounAttributes.sql}")
	private String findAllNounAttributes;

	@Value("${deleteNounAttributes.sql}")
	private String deleteNounAttributes;

	@Value("${deleteNoun.sql}")
	private String deleteNoun;
	
	@Value("${get_all_attr_base_types.sql}")
	private String get_all_attr_base_types_sql;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public void insert(GpNoun anoun) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", anoun.getName());
		paramMap.put("label", anoun.getLabel());
		paramMap.put("description", anoun.getDescription());
		paramMap.put("projectid", anoun.getProjectid());
		paramMap.put("moduleid", anoun.getProjectid()); // add this to the model
														// class
		paramMap.put("cache_enabled", "N"); // add this to the model class
		paramMap.put("notes", anoun.getNotes());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", anoun.getCreatedby());
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", anoun.getLastmodifiedby());
		paramMap.put("default_activity_id", anoun.getDefault_activity_id());
		InsertNoun.SQL_INSERT_NOUN = insertNoun;
		this.insert_noun = new InsertNoun(this.dataSource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.insert_noun.updateByNamedParam(paramMap, keyHolder);
		anoun.setId(keyHolder.getKey().longValue());

	}

	@Override
	public void update(GpNoun anoun) {
		// TODO Auto-generated method stub

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", anoun.getId());
		paramMap.put("name", anoun.getName());
		paramMap.put("label", anoun.getLabel());
		paramMap.put("description", anoun.getDescription());
		paramMap.put("projectid", anoun.getProjectid());
		paramMap.put("moduleid", anoun.getProjectid()); // add this to the model
														// class
		paramMap.put("cache_enabled", "N"); // add this to the model class
		paramMap.put("notes", anoun.getNotes());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", anoun.getCreatedby());
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", anoun.getLastmodifiedby());
		UpdateNoun.SQL_UPDATE_NOUN = updateNoun;
		this.update_noun = new UpdateNoun(this.dataSource);
		this.update_noun.updateByNamedParam(paramMap);

	}

	@Override
	public void update_default_activity_id(GpNoun anoun) {
		// TODO Auto-generated method stub

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", anoun.getId());
		paramMap.put("default_activity_id", anoun.getDefault_activity_id());
		UpdateNounDefaultActivity.SQL_UPDATE_NOUN_DEFAULT_ACTIVITY = updateNounDefaultactivity;
		this.update_noun_default_activity = new UpdateNounDefaultActivity(
				this.dataSource);
		this.update_noun_default_activity.updateByNamedParam(paramMap);

	}
	
	

	@Override
	public void delete(long noun_id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", noun_id);

		this.namedParameterJdbcTemplate.execute(deleteNoun, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
	}

	@Override
	public GpNoun find_by_id(long noun_id) throws Exception {
		// TODO Auto-generated method stub
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", noun_id);
		Noun_with_attributes_mapper noun_and_attribute_mapper = new Noun_with_attributes_mapper();
		List<GpDto_noun_and_attributes> dto_list = this.namedParameterJdbcTemplate
				.query(findNounByNounId, parameters, noun_and_attribute_mapper);
		if (dto_list.size() < 1) {
			throw new Exception("noun_id number " + noun_id + " was not found");
		}
		GpDto_noun_and_attributes test = new GpDto_noun_and_attributes();

		return test.get_noun_from_result_set(dto_list);
	}

	@Override
	public List<GpNoun> find_all_base_by_projectid(long projectid)
			throws Exception {
		System.out.println("In GpNounDao find_all_base_by_projectid -1 ");
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("projectid", projectid);
		// NounBaseMapper
		NounBaseMapper noun_base_mapper = new NounBaseMapper();
		List<GpNoun> the_noun_list = this.namedParameterJdbcTemplate.query(
				findAllBaseNounByProject, parameters, noun_base_mapper);
		if (the_noun_list.size() < 1) {
			return new ArrayList<GpNoun>();
		}

		return the_noun_list;
	}

	@Override
	public ArrayList<GpNoun> find_by_project_id(long project_id)
			throws Exception {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", project_id);
		/*
		 * sql =
		 * " select nouns.id as NOUN_ID, nouns.name as NOUN_NAME, nouns.label as NOUN_LABEL,"
		 * + " nouns.description as NOUN_DESCRIPTION  " +
		 * " from geppetto.nouns " + " where  nouns.projectid =  :id";
		 */
		Noun_with_attributes_mapper noun_and_attribute_mapper = new Noun_with_attributes_mapper();
		List<GpDto_noun_and_attributes> the_noun_attrib_list = this.namedParameterJdbcTemplate
				.query(findAllNounByProject, parameters,
						noun_and_attribute_mapper);
		if (the_noun_attrib_list.size() < 1) {
			throw new Exception("nouns not found for project id:  "
					+ project_id, new Throwable("99"));
		}

		GpDto_noun_and_attributes test = new GpDto_noun_and_attributes();

		return test.get_noun_list_from_resultset(the_noun_attrib_list);
	}

	@Override
	public void insert_a_noun_attribute(GpNounAttribute anttribute,
			long noun_id, long user_id) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", anttribute.getName());
		paramMap.put("label", anttribute.getLabel());
		paramMap.put("description", anttribute.getDescription());
		paramMap.put("nounid", anttribute.getNounid());
		paramMap.put("base_attr_type_id", anttribute.getBase_attr_type_id());
		paramMap.put("part_of_unique_key", anttribute.isIspart_of_unique_key());
		paramMap.put("data_length", anttribute.getDatalength());
		paramMap.put("relationships", anttribute.getRelationships()); // add this to the class
		paramMap.put("notes", anttribute.getNotes());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", anttribute.getCreatedby());
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", anttribute.getLastmodifiedby());
		paramMap.put("modifier_name", anttribute.getModifierName());
		paramMap.put("modifier_id", anttribute.getModifierId());
		paramMap.put("is_secondary_noun", anttribute.isIs_secondary_noun());
		
		InsertNounAttribute.SQL_INSERT_ATTRIBUTE = insertNounAttribute;
		this.insert_noun_attribute = new InsertNounAttribute(this.dataSource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.insert_noun_attribute.updateByNamedParam(paramMap, keyHolder);
		anttribute.setId(keyHolder.getKey().longValue());

	}

	@Override
	public void insert_wsdl_link(String link , int project_id , long user_id) {
		System.err.println("link");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("wsdl_endpoint",link);
		paramMap.put("project_id",project_id);
		paramMap.put("user_id",user_id);
		InsertWSDL_Link.SQL_InsertWSDL_Link = insertWsdlLink;
		this.insertWSDL_link =  new InsertWSDL_Link(this.dataSource);
		this.insertWSDL_link.updateByNamedParam(paramMap);
	}
	
	@Override
	public void insert_rest_link(String link , int project_id , long user_id) {
		System.err.println("link");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("rest_endpoint",link);
		paramMap.put("project_id",project_id);
		paramMap.put("user_id",user_id);
		InsertRest_Link.SQL_InsertREST_LINK = insertRestLink;
		this.insertRest_Link =  new InsertRest_Link(this.dataSource);
		this.insertRest_Link.updateByNamedParam(paramMap);
	}
	
	@Override
	public void update_a_noun_attribute(GpNounAttribute anttribute,
			long noun_id, long user_id) {
		// TODO Auto-generated method stub

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", anttribute.getId());
		paramMap.put("name", anttribute.getName());
		paramMap.put("label", anttribute.getLabel());
		paramMap.put("description", anttribute.getDescription());
		paramMap.put("nounid", anttribute.getNounid());
		paramMap.put("base_attr_type_id", anttribute.getBase_attr_type_id());
		paramMap.put("part_of_unique_key", anttribute.isIspart_of_unique_key());
		paramMap.put("data_length", anttribute.getDatalength());
		paramMap.put("relationships", anttribute.getRelationships()); // add this to the class
		paramMap.put("notes", anttribute.getNotes());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", anttribute.getCreatedby());
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", anttribute.getLastmodifiedby());
		paramMap.put("modifier_name", anttribute.getModifierName());
		paramMap.put("modifier_id", anttribute.getModifierId());
		paramMap.put("is_secondary_noun", anttribute.isIs_secondary_noun() ? 1:0);
		UpdateNounAttribute.SQL_UPDATE_ATTRIBUTE = updateNounAttribute;
		this.update_noun_attribute = new UpdateNounAttribute(this.dataSource);
		this.update_noun_attribute.updateByNamedParam(paramMap);

	}
	
	public List<GpNounAttributeType> get_all_base_attr_type(){
		Noun_base_attr_type_mapper noun_attr_type_mapper = new Noun_base_attr_type_mapper();
		List<GpNounAttributeType> the_noun_attrib_list = this.namedParameterJdbcTemplate
				.query(get_all_attr_base_types_sql, noun_attr_type_mapper);
		
		return the_noun_attrib_list;
	}

	@Override
	public List<GpNounAttribute> find_All_Noun_Attributes(long noun_id)
			throws Exception {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("nounid", noun_id);

		NounAttributeBaseMapper noun_attribute_base_mapper = new NounAttributeBaseMapper();
		List<GpNounAttribute> the_noun_attrib_list = this.namedParameterJdbcTemplate
				.query(findAllNounAttributes, parameters,
						noun_attribute_base_mapper);
		/*
		 * if (the_noun_attrib_list.size() < 1) { throw new
		 * Exception("nouns not found for noun id: " + noun_id, new
		 * Throwable("99")); }
		 */
		return the_noun_attrib_list;
	}

	@Override
	public void delete_a_noun_attribute(long id, long noun_id) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("nounid", noun_id);
		this.namedParameterJdbcTemplate.execute(deleteNounAttributes, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
	}


	public List<GpOtherNoun> get_mongo_noun(GpOtherNoun onoun, GpUser gpUser) {
		System.err.println("===========>+getimg_mongo_noun");
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("db_name", onoun.getDb_name());
		parameters.addValue("project_id", onoun.getProjectid());
		
		System.out.println("&&&&&&&&&"+listOfTableForOtheNoun);
		
        OtherNounMapper other_noun = new OtherNounMapper();  
		List<GpOtherNoun> list_of_other_nouns = this.namedParameterJdbcTemplate
		    .query(listOfTableForOtheNoun,parameters,other_noun);
		 
		System.err.println("list of other noun"+list_of_other_nouns.size());
		return list_of_other_nouns;
		
	}
	
	 public List<GpOtherNoun> get_all_predefined_nouns() throws Exception {
		    
		    System.out.println("Getting all predefined nouns dao");
		    
		  OtherNounMapper other_noun = new OtherNounMapper();
		  
		  List<GpOtherNoun> list_of_other_nouns = this.namedParameterJdbcTemplate
		    .query(findAllOtherNouns, other_noun);
		  
		  System.err.println("list of other noun==>"+list_of_other_nouns.size());
		  
		  return list_of_other_nouns;
		  
		 }

	public List<GpCouchBasedomain> get_couch_noun(GpCouchBasedomain conoun, GpUser gpUser) {
		// TODO Auto-generated method stub
		System.err.println("===========>+getimg_couch_noun");
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("bucket", conoun.getbucket());
		parameters.addValue("design", conoun.getDesign());
		parameters.addValue("views", conoun.getViews());
		parameters.addValue("project_id", conoun.getProject_id());
		
		System.out.println("&&&&&&&&&"+listOfTableForOtheNoun);
		
		CouchMapper couch_noun = new CouchMapper();  
		List<GpCouchBasedomain> list_of_couch_bucket = this.namedParameterJdbcTemplate
		    .query(listOfTableForCouchNoun,parameters,couch_noun);
		 
		System.err.println("list of other noun"+list_of_couch_bucket.toString());
		return list_of_couch_bucket;
	}

	public List<GpCouchBasedomain> get_all_couch_buckets() {
		// TODO Auto-generated method stub
		 System.out.println("Getting all predefined nouns dao");
		    
		 CouchMapper couch = new CouchMapper();
		  
		  List<GpCouchBasedomain> list_of_couch_buckets = this.namedParameterJdbcTemplate
		    .query(findallbucket,couch);
		  
		  System.err.println("list of other noun==>"+list_of_couch_buckets.toString());
		  
		  return list_of_couch_buckets;
	}

	public List<GpCouchBasedomain> get_all_couch_design(String bucket) {
		// TODO Auto-generated method stubS
		System.out.println("Getting all design");
		
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("bucket", bucket);
	    
	 CouchMapper couch = new CouchMapper();
	  
	  List<GpCouchBasedomain> list_of_couch_design = this.namedParameterJdbcTemplate
	    .query(findalldesign,parameters,couch);
	  
	  System.err.println("list of other noun==>"+list_of_couch_design.toString());
	  
	  return list_of_couch_design;
	}

	public List<GpCouchBasedomain> get_all_couch_views(String design) {
		// TODO Auto-generated method stub
		System.out.println("Getting all views");
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("design", design);
	    
		 CouchMapper couch = new CouchMapper();
		  
		  List<GpCouchBasedomain> list_of_couch_views = this.namedParameterJdbcTemplate
		    .query(findallviews,parameters,couch);
		  
		  System.err.println("list of other noun==>"+list_of_couch_views.toString());
		  
		  return list_of_couch_views;
	}

	

}
