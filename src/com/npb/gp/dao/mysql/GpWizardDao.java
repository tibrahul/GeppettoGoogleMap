package com.npb.gp.dao.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.npb.gp.constants.GpBaseConstants;
import com.npb.gp.dao.mysql.support.activity.GpWizardMapper;
import com.npb.gp.dao.mysql.support.activity.InsertWizard;
import com.npb.gp.dao.mysql.support.activity.UpdateWizardScreenId;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpWizard;
import com.npb.gp.domain.core.techtypes.GpClientDeviceType;
import com.npb.gp.interfaces.dao.IGpWizardDao;
import com.npb.gp.services.GpActivityService;
import com.npb.gp.services.GpAndroidScreenService;

@Repository("GpWizardDao")
public class GpWizardDao implements IGpWizardDao {
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private InsertWizard insert_wizard;
	private UpdateWizardScreenId update_wizard;
	private GpScreenXDao screen_dao;
	private GpActivityService gpActivityService;
	private GpAndroidScreenService android_screen_service;

	@Value("${insert_wizard.sql}")
	private String insertWizard;
	
	@Value("${update_wizard_screenids.sql}")
	private String updateWizardScreenIds;

	@Value("${findAllByActivityId.sql}")
	private String findAllByActivityId;


	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	public DataSource getDataSource() {
		return dataSource;
	}
	
	public GpScreenXDao getScreen_dao() {
		return screen_dao;
	}

	@Resource(name = "GpScreenXDao")
	public void setScreen_dao(GpScreenXDao screen_dao) {
		this.screen_dao = screen_dao;
	}
	
	public GpActivityService getGpActivityService() {
		return gpActivityService;
	}

	@Resource(name = "GpActivityService")
	public void setGpActivityService(GpActivityService gpActivityService) {
		this.gpActivityService = gpActivityService;
	}
	
	public GpAndroidScreenService getAndroid_screen_service() {
		return android_screen_service;
	}

	@Resource(name = "GpAndroidScreenService")
	public void setAndroid_screen_service(GpAndroidScreenService android_screen_service) {
		this.android_screen_service = android_screen_service;
	}

	@Override
	public List<GpWizard> find_all_by_activityid(long activityid,String wizard_type)
			throws Exception {
		System.out.println("In find_all_by_activityid -1");
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("activityId", activityid);
		parameters.addValue("wizard_type", wizard_type);
		GpWizardMapper the_mapper = new GpWizardMapper();
		List<GpWizard> dto_list = this.namedParameterJdbcTemplate.query(
				findAllByActivityId, parameters, the_mapper);
		System.out.println("In findAllByActivityId - dto_list is: "
				+ dto_list.size());

		return dto_list;
	}


	@Override
	public GpWizard insert(GpWizard wizard) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", wizard.getName());
		paramMap.put("description", wizard.getDescription());
		paramMap.put("activityId", wizard.getActivity_id());
		paramMap.put("screenIds", wizard.getScreenIds());
		paramMap.put("wizard_type", wizard.getWizard_type());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertWizard.SQL_INSERT_WIZARD = insertWizard;
		this.insert_wizard = new InsertWizard(this.dataSource);
		this.insert_wizard.updateByNamedParam(paramMap, keyHolder);
		wizard.setId(keyHolder.getKey().longValue());
		System.out.println("The wizard id is: " + wizard.getId());
		return wizard;
	}

	@Override
	public void update(long activityid,long wizardid,long screenid,String wizard_type) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("id", wizardid);
		
		//get screen id and then concatenate it with existing
		List<GpWizard> wizardlist = this.find_all_by_activityid(activityid,wizard_type);
		String screenids = "";
		for (Iterator iterator = wizardlist.iterator(); iterator.hasNext();) {
			GpWizard gpWizard2 = (GpWizard) iterator.next();
			screenids = gpWizard2.getScreenIds();
		}
		if(!screenids.equals("")){
			paramMap.put("screenIds", screenids+";"+screenid);
		}else{
			paramMap.put("screenIds", screenid);	
		}
		
		UpdateWizardScreenId.SQL_UPDATE_WIZARD = updateWizardScreenIds;
		this.update_wizard = new UpdateWizardScreenId(this.dataSource);

		this.update_wizard.updateByNamedParam(paramMap);

	}
	
	@Override
	public void deleteWizard_screen(GpScreenX ascreen) throws Exception {
		if(ascreen.getActivity_id()==0||ascreen.getWizard_id()==0){
			GpScreenX the_screen = this.android_screen_service.search_for_screen_by_screen_id(ascreen.getId());
			ascreen.setActivity_id(the_screen.getActivity_id());
			ascreen.setWizard_id(the_screen.getWizard_id());
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", ascreen.getWizard_id());
		String screenids = "";
		
		List<GpWizard> wizards = this.find_all_by_activityid(ascreen.getActivity_id(),ascreen.getClient_device_type());
		for (GpWizard gpWizard : wizards) {
			String str=gpWizard.getScreenIds();
		     String[] screenIdlist = str.split(";");
		     for (String screenId : screenIdlist) {
		    	 if(!screenId.isEmpty()){
			    	 if(Long.parseLong(screenId)!=ascreen.getId()){
			    		 if(!screenids.equals("")){
			    				screenids=screenids+";"+screenId;
			    			}else{	
			    				screenids=screenId;
			    			}
			    	 }
		    	 }
			}
		}
		paramMap.put("screenIds", screenids);
		
		UpdateWizardScreenId.SQL_UPDATE_WIZARD = updateWizardScreenIds;
		this.update_wizard = new UpdateWizardScreenId(this.dataSource);

		this.update_wizard.updateByNamedParam(paramMap);
		GpScreenX gpScreen=this.screen_dao.find_by_id(ascreen.getId());
		gpScreen.setWizard_id(0L);
		gpScreen.setScreen_wizard_sequence_id(0L);
		this.screen_dao.update(gpScreen);
		long sequenceId=1;
		ArrayList<GpScreenX> screenList=this.screen_dao.find_by_activity_id(ascreen.getActivity_id());
		for (GpScreenX gpScreenX : screenList) {
			if(gpScreenX.getScreen_wizard_sequence_id()!=0){
				gpScreenX.setScreen_wizard_sequence_id(sequenceId);
				this.screen_dao.update(gpScreenX);
				sequenceId=sequenceId+1;
			}
		}

	}
	
	@Override
	public void updateWizard_screen(GpScreenX ascreen) throws Exception {
		long newSequenceId=ascreen.getScreen_wizard_sequence_id();//new sequence id
		
		GpScreenX gpScreenData=this.screen_dao.find_by_id(ascreen.getId());
		long oldSequenceId=gpScreenData.getScreen_wizard_sequence_id();
		
		ArrayList<GpScreenX> screenList=this.screen_dao.find_by_activity_id(ascreen.getActivity_id());
		for (GpScreenX gpScreenX : screenList) {
			if(gpScreenX.getScreen_wizard_sequence_id()==newSequenceId){
				gpScreenX.setScreen_wizard_sequence_id(oldSequenceId);
				this.screen_dao.update(gpScreenX);
			}
		}
		GpScreenX gpScreen=this.screen_dao.find_by_id(ascreen.getId());
		gpScreen.setScreen_wizard_sequence_id(newSequenceId);
		this.screen_dao.update(gpScreen);
		
	}
	
	@Override
	public void addWizard_screen(GpScreenX ascreen) throws Exception {
		GpScreenX the_screen = this.android_screen_service.search_for_screen_by_screen_id(ascreen.getId());
		List<GpWizard> wizards = this.find_all_by_activityid(the_screen.getActivity_id(),the_screen.getClient_device_type());
		for (GpWizard gpWizard : wizards) {
			the_screen.setWizard_id(gpWizard.getId());
		}
		
		List<GpScreenX> screenList= this.screen_dao.find_all_base_by_activity_id(the_screen.getActivity_id());
		Long sequence_id = 0L;
		for (GpScreenX gpScreenX : screenList) {
			if(gpScreenX.getScreen_wizard_sequence_id()>sequence_id){
				sequence_id=gpScreenX.getScreen_wizard_sequence_id();
			}	
		}
		the_screen.setScreen_wizard_sequence_id(sequence_id+1L);
		this.screen_dao.update(the_screen);
		if(the_screen.getWizard_id()>0){
		gpActivityService.update_wizard(the_screen.getActivity_id(),the_screen.getWizard_id(),the_screen.getId(),the_screen.getClient_device_type());
		}
	}
}
