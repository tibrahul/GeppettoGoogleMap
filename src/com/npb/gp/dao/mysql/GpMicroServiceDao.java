package com.npb.gp.dao.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.activity.UpdateGpMicroService;
import com.npb.gp.dao.mysql.support.activity.UpdateGpMicroServiceData;
import com.npb.gp.dao.mysql.support.microservice.GpMicroServiceMapper;
import com.npb.gp.dao.mysql.support.microservice.InsertMicroservice;
import com.npb.gp.domain.core.GpMicroService;

@Repository("GpMicroServiceDao")
public class GpMicroServiceDao {
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private UpdateGpMicroService updateGpMicroService;
	private UpdateGpMicroServiceData updateGpMicroServiceData;

	@Value("${insert_micro_service.sql}")
	private String insertMicroservice;

	@Value("${get_microservice_by_id.sql}")
	private String getMicroservicebyId;
	
	@Value("${get_all_micro_service.sql}")
	private String get_all_micro_service;
	
	@Value("${get_microservice_by_serviceid.sql}")
	private String getMicroserviceByServiceId;

	@Value("${update_micro_service.sql}")
	private String updatemicroservice;

	@Value("${update_microservice_wizard.sql}")
	private String updateMicroserviceWizard;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	public GpMicroService insert(GpMicroService micro_service) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("project_id", micro_service.getProject_id());
		paramMap.put("activities_json", micro_service.getActivities_json());
		paramMap.put("microservice_name", micro_service.getMicroservice_name());
		paramMap.put("notes", micro_service.getNotes());
		paramMap.put("description", micro_service.getDescription());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertMicroservice.SQL_INSERT_MICROSERVICE = insertMicroservice;
		InsertMicroservice insert_microservice = new InsertMicroservice(
				dataSource);
		insert_microservice.updateByNamedParam(paramMap, keyHolder);
		micro_service.setId(keyHolder.getKey().longValue());
		return micro_service;
	}

	public List<GpMicroService> getMicroservicebyId(long project_id)
			throws Exception {
		System.out.println("In find_all_by_activityid -1");
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", project_id);
		GpMicroServiceMapper the_mapper = new GpMicroServiceMapper();

		List<GpMicroService> microServiceList = this.namedParameterJdbcTemplate
				.query(getMicroservicebyId, parameters, the_mapper);
		System.out.println("In getMicroservicebyId - microservice is: "
				+ microServiceList);
		if (microServiceList.size() < 1) {
			return new ArrayList<GpMicroService>();
		}
		return microServiceList;
	}

	public void updateMicroService(GpMicroService wizarddata) {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", wizarddata.getId());
		GpMicroServiceMapper the_mapper = new GpMicroServiceMapper();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		List<GpMicroService> microServiceList = this.namedParameterJdbcTemplate
				.query(getMicroserviceByServiceId, parameters, the_mapper);
		
		if(microServiceList.get(0).getActivities_json() == null || microServiceList.get(0).getActivities_json() ==""){
			paramMap.put("activities_json", wizarddata.getActivities_json());
		}else if(microServiceList.get(0).getActivities_json().contains((wizarddata.getActivities_json().replaceAll("\\[", "").replaceAll("\\]","")))){
			paramMap.put("activities_json", microServiceList.get(0).getActivities_json());
		}else{
			String str=wizarddata.getActivities_json();
			str = str.replaceAll("\\[", "").replaceAll("\\]","");
		  StringBuffer jsondata=new StringBuffer(microServiceList.get(0).getActivities_json()).insert(microServiceList.get(0).getActivities_json().lastIndexOf('}')+1,","+str);
			paramMap.put("activities_json", new String(jsondata));
		}
		
		paramMap.put("id", wizarddata.getId());
		UpdateGpMicroService.SQL_UPDATE_WIZARD = updatemicroservice;
		this.updateGpMicroService = new UpdateGpMicroService(this.dataSource);

		this.updateGpMicroService.updateByNamedParam(paramMap);
	}
	
	public List<GpMicroService> get_all_micro_service()
			throws Exception {

		GpMicroServiceMapper the_mapper = new GpMicroServiceMapper();
		List<GpMicroService> microservice = this.namedParameterJdbcTemplate
					.query(get_all_micro_service, the_mapper);

			if (microservice.size() < 1) {
				return new ArrayList<GpMicroService>();
			}

			return microservice;
		}

	public void deleteActivityFromWizard(GpMicroService wizarddata) {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", wizarddata.getId());
		GpMicroServiceMapper the_mapper = new GpMicroServiceMapper();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		List<GpMicroService> microServiceList = this.namedParameterJdbcTemplate
				.query(getMicroserviceByServiceId, parameters, the_mapper);
		ArrayList<String> list = new ArrayList<String>(); 
		JSONArray jsArray=new JSONArray(microServiceList.get(0).getActivities_json());
		if (jsArray != null) { 
			   for (int i=0;i<jsArray.length();i++){ 
			    list.add(jsArray.get(i).toString());
			   } 
			}
		
		if(list.indexOf(wizarddata.getActivities_json().replaceAll("\\[", "").replaceAll("\\]", "")) == -1){
			//paramMap.put("activities_json","");
		}else{
		list.remove(list.indexOf(wizarddata.getActivities_json().replaceAll("\\[", "").replaceAll("\\]", "")));
		}
		
		if(list.size()<1){
			paramMap.put("activities_json","");
	    }else{
		paramMap.put("activities_json",list.toString());
	    }
		paramMap.put("id", wizarddata.getId());
		UpdateGpMicroService.SQL_UPDATE_WIZARD = updatemicroservice;
		this.updateGpMicroService = new UpdateGpMicroService(this.dataSource);

		this.updateGpMicroService.updateByNamedParam(paramMap);
	}

	public GpMicroService getMicroserviceByServiceId(Long id) {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		GpMicroServiceMapper the_mapper = new GpMicroServiceMapper();
		List<GpMicroService> microServiceList = this.namedParameterJdbcTemplate
				.query(getMicroserviceByServiceId, parameters, the_mapper);
		if (microServiceList.size() < 1) {
			return new GpMicroService();
		}
		return microServiceList.get(0);
		}

	
	public void updateMicroserviceWizard(GpMicroService wizarddata) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", wizarddata.getId());
		paramMap.put("notes", wizarddata.getNotes());
		paramMap.put("description", wizarddata.getDescription());
		paramMap.put("microservice_name", wizarddata.getMicroservice_name());
		UpdateGpMicroServiceData.SQL_UPDATE_WIZARD_DATA = updateMicroserviceWizard;
		this.updateGpMicroServiceData = new UpdateGpMicroServiceData(this.dataSource);
		this.updateGpMicroServiceData.updateByNamedParam(paramMap);

	}
	
	}
