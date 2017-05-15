package com.npb.gp.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.couchbase.client.core.config.CouchbaseBucketConfig;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseAsyncBucket;
import com.couchbase.client.java.CouchbaseBucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.cluster.BucketSettings;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.view.DesignDocument;
import com.couchbase.client.java.view.Stale;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.npb.gp.dao.mysql.support.noun.CouchMapper;
import com.npb.gp.dao.mysql.support.noun.Insert_Couch_Schema;
import com.npb.gp.dao.mysql.support.noun.Insert_Mongo_Schema;
import com.npb.gp.dao.mysql.support.noun.OtherNounMapper;
import com.npb.gp.domain.core.GpCouchBasedomain;
import com.npb.gp.domain.core.GpOtherNoun;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.interfaces.dao.IGpCouchNounDao;

@Repository("GpCouchNoundao")
public class GpCouchNoundao implements IGpCouchNounDao {

	private DataSource dataSource;
	private Insert_Couch_Schema couch_schema;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${insertcouchattribute.sql}")
	private String insertcouchattribute;

	@Value("${getbyprojectid.sql}")
	private String getbyprojectid;

	@Value("${deleteCouchNounAttributes.sql}")
	private String deleteCouchNounAttributes;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void importnoun(long project_id, GpUser loggedUser) {

		// searching for project id

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", project_id);
		CouchMapper other_noun = new CouchMapper();
		List<GpCouchBasedomain> list_of_other_nouns = this.namedParameterJdbcTemplate.query(getbyprojectid, parameters,
				other_noun);
		if (list_of_other_nouns.size() > 0) {

			System.out.println("inside if");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			// paramMap.put("id", id);
			paramMap.put("project_id", project_id);
			this.namedParameterJdbcTemplate.execute(deleteCouchNounAttributes, paramMap,
					new PreparedStatementCallback<Object>() {
						@Override
						public Object doInPreparedStatement(PreparedStatement ps)
								throws SQLException, DataAccessException {
							return ps.executeUpdate();
						}
					});
			System.out.println("Exiting db deleted  successfully");
		}
		// TODO Auto-generated method stub
		System.setProperty("viewmode", "development");
		System.out.println("inside couch dao");
		System.err.println("Connection openning..");
		Cluster cluster = CouchbaseCluster.create("54.89.127.249");
		List<BucketSettings> bucket = cluster.clusterManager("Administrator", "211218").getBuckets();
		System.err.println("CouchBase opened");
		System.out.println(bucket.toString());
		for (BucketSettings bu : bucket) {
			System.out.println("bucket name : " + bu.name());
			System.out.println("bucket password : " + bu.password());
			Bucket db = cluster.openBucket(bu.name(), bu.password());
			System.err.println("Couchbase connected successfully -------->" + bu.name());
			// System.setProperty("viewmode", "development");
			List<DesignDocument> desighDoc = db.bucketManager().getDesignDocuments();
			System.out.println(desighDoc.toString());
			List<String> views = new ArrayList<String>();
			for (DesignDocument dd : desighDoc) {
				System.err.println("design : " + dd.name());
				System.err.println("****** VIEWS ------> " + dd.toJsonObject().get("views").toString());
				JSONObject jObject = new JSONObject(dd.toJsonObject().get("views"));
				System.err.println("key " + jObject.getJSONArray("names"));
				for (Object ar : jObject.getJSONArray("names")) {
					System.out.println("array names " + ar.toString());
					String name = ar.toString();

					// quering by view
					ViewResult result = db.query(ViewQuery.from(dd.name(), name).limit(1));

					// Iterate the returned ViewRows
					ArrayList<Integer> length = new ArrayList<Integer>();
					for (ViewRow row : result) {
						System.out.println("row :" + row);
						// keys
						if (row.document().content().isEmpty()) {
							System.err.println("skipped due to id is null");
						} else {
							JSONObject jObject1 = new JSONObject(row.document().content());
							JSONArray menu = jObject1.getJSONArray("names");
							length.add(menu.length());
							System.out.println("column available :" + menu);

							Map<String, Object> paramMap = new HashMap<String, Object>();

							paramMap.put("project_id", project_id);
							paramMap.put("user_id", loggedUser.getId());
							paramMap.put("bucket", bu.name());
							paramMap.put("password", bu.password());
							paramMap.put("design", dd.name());
							paramMap.put("views", name);
							paramMap.put("attribute", menu);
							System.err.println(paramMap.toString());
							Insert_Couch_Schema.SQL_INSERT_COUCH_SCHEMA = insertcouchattribute;
							this.couch_schema = new Insert_Couch_Schema(this.dataSource);
							KeyHolder keyHolder = new GeneratedKeyHolder();
							this.couch_schema.updateByNamedParam(paramMap, keyHolder);
						}
					}

				}
			}

		}
	}
}
