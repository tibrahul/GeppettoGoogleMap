package com.npb.gp.dao.mysql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.base_verbs.GpBaseVerbMapper;
import com.npb.gp.dao.mysql.support.verbs.DeleteVerb;
import com.npb.gp.dao.mysql.support.verbs.InsertVerb;
import com.npb.gp.dao.mysql.support.verbs.UpdateVerbScreen;
import com.npb.gp.dao.mysql.support.verbs.UpdateWSDLOperation;
import com.npb.gp.dao.mysql.support.verbs.VerbsMapper;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.interfaces.dao.IGpVerbsDao;

/**
 * 
 * @author Dan Castillo</br> Date Created: 11/28/2014</br>
 * @since .75</p>
 *
 *        The purpose of this is to provide standard CRUD/search of the verbs
 *        table</p>
 * 
 *        Modified Date: 16/10/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Added new variable as actual information
 * 
 *        Modified Date: 15/10/2015</br> Modified By: Reinaldo Lopez</p>
 * 
 *        added the delete_verbs and get_verbs_by_screen_id methods
 * 
 *        Modified Date: 07/04/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Changed the authorization_id to authorizations
 *
 *
 *        Modified Date: 02/24/2015</br> Modified By: Dan Castillo</p>
 * 
 *        added the get_verbs_by_activity_id method
 *
 *        Modified Date: 30/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        added the insert_a_verb method
 * 
 *        Modified Date: 31/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Wrote the new method as get_all_base_verbs for retrieve the all base
 *        verbs from the database
 *
 *        Wrote the new method as find_base_verbs_by_id method to find the base
 *        verb based on its id
 * 
 */

@Repository("GpVerbsDao")
public class GpVerbsDao implements IGpVerbsDao {
	// private Log log = LogFactory.getLog(getClass());

	private DataSource dataSource;

	@Value("${get_all_verbs.sql}")
	private String get_all_verbs_sql;

	@Value("${find_by_verb_id.sql}")
	private String find_by_verb_id_sql;

	@Value("${find_by_activity_id.sql}")
	private String find_by_activity_id_sql;

	@Value("${find_verb_by_activity_id.sql}")
	private String find_verb_by_activity_id_sql;

	@Value("${insert_verbs.sql}")
	private String insert_verbs_sql;

	@Value("${update_verb_screen.sql}")
	private String update_verb_screen_sql;

	@Value("${update_wsdl_operation.sql}")
	private String update_wsdl_operation;

	@Value("${get_all_base_verbs.sql}")
	private String get_all_base_verbs_sql;

	@Value("${get_base_verb_by_id.sql}")
	private String get_base_verb_by_id_sql;

	@Value("${delete_verbs.sql}")
	private String delete_verbs_sql;

	@Value("${get_verbs_by_screen_id.sql}")
	private String get_verbs_by_screen_id_sql;
	
	@Value("${get_verbs_by_base_verb_id.sql}")
	private String get_verbs_by_base_verb_id_sql;
	
	@Value("${find_verb_by_project_id.sql}")
	private String find_verb_by_project_id_sql;

	public InsertVerb insert_verb;
	public UpdateVerbScreen update_verb_screen;
	public DeleteVerb delete_verb;
	public UpdateWSDLOperation update_wsdl_id;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;

		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public ArrayList<GpVerb> get_verbs_by_activity_id(long activity_id)
			throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("activity_id", activity_id);

		VerbsMapper verb_mapper = new VerbsMapper();

		List<GpVerb> verb_list = this.namedParameterJdbcTemplate.query(
				this.find_by_activity_id_sql, parameters, verb_mapper);
		if (verb_list.size() < 1) {
			throw new Exception("no verbs found");
		}

		return (ArrayList<GpVerb>) verb_list;

	}

	@Override
	public List<GpVerb> find_all_verbs_by_activity_id(long activity_id)
			throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("activity_id", activity_id);

		VerbsMapper verb_mapper = new VerbsMapper();

		return this.namedParameterJdbcTemplate.query(
				this.find_verb_by_activity_id_sql, parameters, verb_mapper);
	}
	
	public List<GpVerb> find_all_verbs_by_project_id(long project_id)
			throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", project_id);

		VerbsMapper verb_mapper = new VerbsMapper();

		return this.namedParameterJdbcTemplate.query(
				this.find_verb_by_project_id_sql, parameters, verb_mapper);
	}

	@Override
	public GpVerb find_by_id(long verb_id) throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", verb_id);

		VerbsMapper verb_mapper = new VerbsMapper();

		List<GpVerb> verb_list = this.namedParameterJdbcTemplate.query(
				this.find_by_verb_id_sql, parameters, verb_mapper);
		if (verb_list.size() < 1) {
			throw new Exception("no verbs found");
		}

		return verb_list.get(0);
	}

	@Override
	public ArrayList<GpVerb> get_all_verbs() throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		VerbsMapper verb_mapper = new VerbsMapper();

		List<GpVerb> verb_list = this.namedParameterJdbcTemplate.query(
				this.get_all_verbs_sql, parameters, verb_mapper);
		if (verb_list.size() < 1) {
			throw new Exception("no verbs found");
		}

		return (ArrayList<GpVerb>) verb_list;
	}

	@Override
	public GpVerb insert_a_verb(GpVerb averb) throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", averb.getName());
		paramMap.put("label", averb.getLabel());
		paramMap.put("description", averb.getDescription());
		paramMap.put("notes", averb.getNotes());
		paramMap.put("action_on_data", averb.getAction_on_data());
		paramMap.put("activity_id", averb.getActivity_id());
		paramMap.put("authorizations", averb.getAuthorizations());
		paramMap.put("screen_id", averb.getScreen_id());
		paramMap.put("base_verb_id", averb.getId());
		paramMap.put("actual_information", averb.getActual_information());
		paramMap.put("wsdl_operation_id", averb.getWsdl_operation_id());

		InsertVerb.SQL_INSERT_VERB = insert_verbs_sql;
		this.insert_verb = new InsertVerb(this.dataSource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.insert_verb.updateByNamedParam(paramMap, keyHolder);
		averb.setId(keyHolder.getKey().longValue());

		return averb;
	}

	@Override
	public GpVerb update_a_verb_screen(GpVerb averb) throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", averb.getId());
		paramMap.put("screen_id", averb.getScreen_id());

		UpdateVerbScreen.SQL_UPDATE_VERB_SCREEN = update_verb_screen_sql;
		this.update_verb_screen = new UpdateVerbScreen(this.dataSource);
		this.update_verb_screen.updateByNamedParam(paramMap);

		return averb;
	}
	
	@Override
	public GpVerb update_wsdl_operation_id(GpVerb averb) throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", averb.getId());
		paramMap.put("wsdl_operation_id", averb.getWsdl_operation_id());

		UpdateWSDLOperation.SQL_UPDATE_WSDL_OPERATION= update_wsdl_operation;
		this.update_wsdl_id = new UpdateWSDLOperation(this.dataSource);
		this.update_wsdl_id.updateByNamedParam(paramMap);

		return averb;
	}

	@Override
	public List<GpVerb> get_all_base_verbs() throws Exception {
		GpBaseVerbMapper verb_mapper = new GpBaseVerbMapper();

		List<GpVerb> verb_list = this.namedParameterJdbcTemplate.query(
				this.get_all_base_verbs_sql, verb_mapper);

		if (verb_list.size() < 1) {
			throw new Exception("no verbs found");
		}
		return (ArrayList<GpVerb>) verb_list;
	}

	@Override
	public GpVerb find_base_verbs_by_id(long id) throws Exception {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);

		GpBaseVerbMapper verb_mapper = new GpBaseVerbMapper();

		List<GpVerb> the_verb = this.namedParameterJdbcTemplate.query(
				this.get_base_verb_by_id_sql, parameters, verb_mapper);

		return the_verb.get(0);
	}

	@Override
	public void delete_verbs(long screen_id) throws Exception {
		System.out.println("deleting verbs for screen_id = " + screen_id);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("screen_id", screen_id);

		DeleteVerb.SQL_DELETE_VERBS = delete_verbs_sql;
		this.delete_verb = new DeleteVerb(dataSource);
		this.delete_verb.updateByNamedParam(paramMap);
	}

	@Override
	public ArrayList<GpVerb> get_verbs_by_screen_id(long screen_id)
			throws Exception {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("screen_id", "%" + screen_id + "%");

		VerbsMapper verb_mapper = new VerbsMapper();

		List<GpVerb> the_verbs = this.namedParameterJdbcTemplate.query(
				this.get_verbs_by_screen_id_sql, parameters, verb_mapper);

		return (ArrayList<GpVerb>) the_verbs;
	}
	
	public ArrayList<GpVerb> get_verbs_by_base_verb_id(long base_verb_id, long activity_id)
			throws Exception {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("base_verb_id", base_verb_id);
		parameters.addValue("activity_id", activity_id);

		VerbsMapper verb_mapper = new VerbsMapper();

		List<GpVerb> the_verbs = this.namedParameterJdbcTemplate.query(
				this.get_verbs_by_base_verb_id_sql, parameters, verb_mapper);

		return (ArrayList<GpVerb>) the_verbs;
	}
}