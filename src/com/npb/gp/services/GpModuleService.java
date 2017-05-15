package com.npb.gp.services;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.npb.gb.utils.GpUserUtils;
import com.npb.gp.dao.mysql.GpModuleDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpModule;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.interfaces.services.IGpModuleService;

/**
 * 
 * @author Reinaldo </br> 
 * Date Created: 20/09/2015</br>
 * 
 * 
 *        The purpose of this class is to provide the entry point for any
 *        functions having to do with modules</br>
 * 
 */
@Service("GpModuleService")
public class GpModuleService extends GpBaseService implements IGpModuleService{
	
	GpModuleDao module_dao;
	GpActivityService activity_service;
	GpVerbService verb_service;
	
	public GpVerbService getVerb_service() {
		return verb_service;
	}
	
	@Resource(name = "GpVerbService")
	public void setVerb_service(GpVerbService verb_service) {
		this.verb_service = verb_service;
	}

	public GpActivityService getActivity_service() {
		return activity_service;
	}

	@Resource(name = "GpActivityService")
	public void setActivity_service(GpActivityService activity_service) {
		this.activity_service = activity_service;
	}
		
	public GpModuleDao getModule_dao() {
		return module_dao;
	}

	@Resource(name = "GpModuleDao")
	public void setModule_dao(GpModuleDao module_dao) {
		this.module_dao = module_dao;
	}

	@Override
	public GpModule insert_module(GpModule module, GpUser user) throws Exception {
		System.out.println("GpModuleService - insert_module");						
				
		module.setCreatedby(user.getId());
		module.setLastmodifiedby(user.getId());	
		
		GpModule m_added = this.module_dao.insert(module);
		
		GpActivity anActivity = new GpActivity();
		anActivity.setName(m_added.getName() + " Activity");
		anActivity.setLabel(m_added.getLabel());
		anActivity.setDescription(m_added.getDescription());
		anActivity.setProjectid(m_added.getProjectid());
		anActivity.setModuleid(m_added.getId());
		anActivity.setCreatedby(user.getId());
		anActivity.setLastmodifiedby(user.getId());
		anActivity.setModule_type("not_default");
		anActivity.setPredefined_activity_id(m_added.getPredefined_activity_id());
		anActivity.setActivity_types(new ArrayList<String>());
		anActivity.setSecondary_nouns(new ArrayList<GpNoun>());
		anActivity.setPrimary_noun(new GpNoun());
		
		
		anActivity = this.activity_service.create_activity(anActivity,
				GpUserUtils.getLoggedUser());
		
		String gcd_json_string = this.module_dao.get_gcd_json(module.getPredefined_activity_id());
		System.out.println("json " + gcd_json_string);
		JSONObject gcd_json = new JSONObject(gcd_json_string);
		if(gcd_json.has("verbs") && !gcd_json.isNull("verbs")){
			System.out.println("reading json");
			JSONArray json_array_verbs = gcd_json.getJSONArray("verbs");
			for(int i=0;i<json_array_verbs.length();i++){
				JSONObject json_verb = json_array_verbs.getJSONObject(i);
				String verb_name = json_verb.getString("verb_name");
				String verb_label = json_verb.getString("verb_label");
				String description = json_verb.getString("description");
				long activity_id = anActivity.getId();
				GpVerb the_verb = new GpVerb();
				the_verb.setActivity_id(activity_id);
				the_verb.setDescription(description);
				the_verb.setLabel(verb_label);
				the_verb.setName(verb_name);
				the_verb.setAction_on_data("GpComponentVerb");
				the_verb.setActual_information(json_verb.toString());
				//the_verb.setNotes("{\"type\":\"component_verb\"}");
				the_verb = this.verb_service.insert_a_verb(the_verb);
			}
		}
		return m_added;
	}

	public String get_gcd_json(long predefined_activity_id) throws Exception {
		// TODO Auto-generated method stub
		return this.module_dao.get_gcd_json(predefined_activity_id);
	}

}
