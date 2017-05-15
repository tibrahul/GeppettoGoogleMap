package com.npb.gp.dao.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoIterable;
import com.npb.gp.dao.mysql.support.noun.Insert_Mongo_Schema;
import com.npb.gp.dao.mysql.support.noun.OtherNounMapper;
import com.npb.gp.dao.mysql.support.project.GpProject_Mapper;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpOtherNoun;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.MongoNoun;
import com.npb.gp.interfaces.dao.IGpNounDao;
import com.npb.gp.interfaces.dao.IMongonounDao;

@Repository("GpMongoNounDao")
public class GpMongoNounDao implements IMongonounDao {

	private int count = 0;
	private Log LOG = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static Set<String> collections;
	private static String collection;
	private static String db_name;
	private static String keys;

	private Insert_Mongo_Schema mongo_schema;

	@Value("${insertMongoSchema.sql}")
	private String insertMongoSchema;

	@Value("${insertMongodb.sql}")
	private String insertMongodb;

	@Value("${findMongoProjectid.sql}")
	private String listOfTableForOtheNoun;

	@Value("${deleteMongoNounAttributes.sql}")
	private String deleteMongoNounAttributes;
	

	@Value("${findProjectDetailsByProjectId.sql}")
	private String findProjectDetailsByProjectId;
	

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
/*
	@SuppressWarnings("unchecked")
	public ArrayList<GpTechProperties> get_tech_properties(){
		ArrayList<GpTechProperties> tech_props;
		
		HashMap<String, GpArchitypeConfigurations> base_configs 
		= gen_manager.getBase_configs();

		GpArchitypeConfigurations tech_props_configs = base_configs.get(GpGenConstants.TECH_PROPERTY_LIST);
		tech_props = (ArrayList<GpTechProperties>)tech_props_configs.getObject_value();
		
		
		return  tech_props;
	}*/
	@Override
	public void importnoun(long project_id, GpUser loggedUser) throws Exception {
		System.err.println("in calling mongo noun dao");

		MongoClient mongoClient = null;
		MapSqlParameterSource parameters_project;
		parameters_project = new MapSqlParameterSource();
		parameters_project.addValue("project_id", project_id);
		GpProject_Mapper project_mapper = new GpProject_Mapper();
		List<GpProject> dto_list = this.namedParameterJdbcTemplate.query(
				findProjectDetailsByProjectId, parameters_project, project_mapper);
		if (dto_list.size() < 1) {
			throw new Exception("project_id number " + project_id
					+ " was not found");
		}
		GpProject p = (GpProject) dto_list.get(0);
		String url = "";
		System.out.println("project got-- > "+p.getLotus_notes_enabled());
		if (p.getLotus_notes_enabled().equals("Y")) {
			System.err.println("lotuds enabled got-- > "+p.getExtra_project_info());
			if (p.getExtra_project_info() != null) {

				System.err.println("extra no tnull got-- > ");
				try {
					JSONObject extra_project_info = new JSONObject(p.getExtra_project_info());
					JSONObject json_mongo = extra_project_info.getJSONObject("mongo");
					String mongo_url = json_mongo.getString("url");
					String mongo_port = json_mongo.getString("port");
					String mongo_db_name = json_mongo.getString("db_name");
					url = mongo_url;
					System.err.println(mongo_url);
					System.err.println(mongo_port);
					System.err.println(mongo_db_name);
					
					 mongoClient = new MongoClient(url.substring(10), 27017);
						System.err.println("------------------------------------lotus notes exists------------------------- > "+url.substring(10));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} 
		}else{
			System.out.println("Connected IP-- >  52.207.24.87");
			 mongoClient = new MongoClient(" 52.207.24.87", 27017);
		}

		MongoIterable<String> allDatabases = mongoClient.listDatabaseNames();
		System.out.println("Database connected successfully");

		// searching for project id

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", project_id);
		OtherNounMapper other_noun = new OtherNounMapper();
		List<GpOtherNoun> list_of_other_nouns = this.namedParameterJdbcTemplate.query(listOfTableForOtheNoun,
				parameters, other_noun);
		if (list_of_other_nouns.size() > 0) {

			System.out.println("inside if");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			// paramMap.put("id", id);
			paramMap.put("project_id", project_id);
			this.namedParameterJdbcTemplate.execute(deleteMongoNounAttributes, paramMap,
					new PreparedStatementCallback<Object>() {
						@Override
						public Object doInPreparedStatement(PreparedStatement ps)
								throws SQLException, DataAccessException {
							return ps.executeUpdate();
						}
					});
			System.out.println("Exiting db deleted  successfully");
		}

		for (String dbName : allDatabases) {
			DB db = mongoClient.getDB(dbName);
			Set<String> collections = db.getCollectionNames();

			System.out.println("List of  dbs");
			
			for (String collectionName : collections) {
				String db_name = db.getName();
				System.err.println("database name :" + db_name);
				System.out.println("collection name :" + collectionName);
				collection = collectionName;
				@SuppressWarnings("unused")
				DBCollection coll = db.getCollection(collectionName);

				// Querying collection(table)
				DBObject doc = new BasicDBObject();
				doc.put("eval", "function() { return db." + collectionName + ".findOne(); }");
				CommandResult result = db.command(doc);

				// System.out.println(result);
				JSONObject jObject = new JSONObject(result);
				System.out.println("---- > "+jObject.toString());
				if(jObject.toString().indexOf("retval")>0){
					JSONObject menu = jObject.getJSONObject("retval");
					System.out.println("--- > menu Object  - > "+menu.toString());
					@SuppressWarnings("rawtypes")
					Iterator keysToCopyIterator = menu.keys();
					// iterating column name from collection (table)
					List<String> keysList = new ArrayList<String>();
					while (keysToCopyIterator.hasNext()) {
						String key = (String) keysToCopyIterator.next();
						//if(menu.getString("@unid")!=null)
						System.out.println("  "+key+" - > key.substring(0) - "+key.substring(0,1));
						if(key.substring(0,1).equals("@"))
						{
							System.out.println(" Key with @ - >  "+key);	
							keysList.add(key.substring(1));
						}else{
							System.out.println("Do nothing -");
							keysList.add(key);
						}
						
					}
					System.out.println("column available :" + keysList);
					String keys = keysList.toString();

					// inserting the collecton in db
					Map<String, Object> paramMap = new HashMap<String, Object>();

					paramMap.put("db_name", db_name);
					paramMap.put("collection_name", collection);
					paramMap.put("attribute", keys);
					paramMap.put("project_id", project_id);
					paramMap.put("user_id", loggedUser.getId());
					System.err.println(paramMap.toString());
					Insert_Mongo_Schema.SQL_INSERT_MONGO_SCHEMA = insertMongoSchema;
					this.mongo_schema = new Insert_Mongo_Schema(this.dataSource);
					KeyHolder keyHolder = new GeneratedKeyHolder();
					this.mongo_schema.updateByNamedParam(paramMap, keyHolder);
				}
				

			}
		}

	}
}
