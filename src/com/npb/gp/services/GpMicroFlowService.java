package com.npb.gp.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpMicroFlowDao;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpMicroFlowBase;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.interfaces.services.IGpMicroFlowService;

/***
 * <b>Created Date: 04/09/2015<br>
 * Modified By: Reinaldo Lopez<b><br>
 * 
 * <p>
 * Created the following methods insert_verb_method_implementation,
 * 	handleVerb
 * 
 *        Modified Date: 15/10/2015</br>
 *        Modified By: Reinaldo Lopez
 *        </p>
 * 
 *        Added delete_verb_method_implementation method
 * 
 *  <p>
 *  Modified By : Rashmi
 *  Date: 29/03/2016
 *  Modified insert_verb_method_implementation 
 * </p>
 * 
 * */
@Service("GpMicroFlowService")
public class GpMicroFlowService extends GpBaseService implements IGpMicroFlowService{
	
	private GpMicroFlow microFlow = new GpMicroFlow();
	private String[] micro_flow_base;
	private String[] micro_flow_descriptions;	
		
	private GpMicroFlowDao micro_flow_dao;			
	
	public GpMicroFlowDao getMicro_flow_dao() {
		return micro_flow_dao;
	}

	private GpFlowControlService flow_control_service;

	public GpFlowControlService getFlow_control_service() {
		return flow_control_service;
	}

	@Resource(name = "GpFlowControlService")
	public void setFlow_control_service(
			GpFlowControlService flow_control_service) {
		this.flow_control_service = flow_control_service;
	}
	@Resource(name = "GpMicroFlowDao")
	public void setMicro_flow_dao(GpMicroFlowDao micro_flow_dao) {
		this.micro_flow_dao = micro_flow_dao;
	}


	@Override
	public void insert_verb_method_implementation(long activity_id,long verb_id, GpVerb verb) throws Exception {
		
		String action = verb.getAction_on_data();
	     
		switch (action) {
	         case "GpGetAllValues":	             
	         case "GpGetNounById":	        	 
	         case "GpSearchForUpdate":	             
	         case "GpCreate":
	         case "GpUpdate":
	         case "GpSearch":
	         case "GpSearchDetail":
	         case "GpDelete":
	         case "GpGetNounByParentId":
	         case "GpDeleteByParentId":
	         case "GpTakePhoto":
	         case "GpRecordVideo":
	         case "GpCancel" : 
	        //	 micro_flow_base = new String[]{"GpStart","GpValidate","GpServerPost","GpServerResponse","GpDisplayServerReponse","GpEnd"};
	        	// micro_flow_descriptions = new String[]{"verb start","data validation","post to server","process server response","display server response","verb end"};
	        	 this.handleVerb(activity_id,verb_id,action);	        	 	         	             	         	        	
	        	 break;
/*	        	 
	         
	        	 micro_flow_base = new String[]{"GpStart","GpConfirm","GpServerPost","GpServerResponse","GpEnd"};
	        	 micro_flow_descriptions = new String[]{"verb start","display delete confirmation","post to server","display server response","verb end"};
	        	 this.handleVerb(flow_control_id, master_flow_id, verb_id);
	             break;
*/	             	         	             
	             
	         case "GpCustom":
	        	
	         case "GpRecordSound":
	         
	         
	     }
							
		}

	@Override
	public void handleVerb(long activity_id,long verb_id, String action) throws Exception {
			
		//select data from micro_flow_base table
		List<GpFlowControl> flowControlList = flow_control_service.get_flow_control_by_activity(activity_id);
		
		for(GpFlowControl the_flow : flowControlList){
			//System.out.println("+++" + the_flow.getComponent_type());
			if(!the_flow.getType().equals("master")){
				ArrayList<GpMicroFlowBase> microFlowBases =  get_records_from_micro_flow_base(action,the_flow.getComponent_type());
				for (GpMicroFlowBase micro_flow : microFlowBases) {
					GpMicroFlow new_micro_flow = new GpMicroFlow();
					new_micro_flow.setFlow_control_id(the_flow.getId());
					new_micro_flow.setMaster_flow_id(the_flow.getMaster_flow_id());
					new_micro_flow.setComponent_type(micro_flow.getComponent_name());
					new_micro_flow.setDescription("");
					new_micro_flow.setCode_id(-1);
					new_micro_flow.setSequence_id(micro_flow.getSequence_id());
					new_micro_flow.setVerb_id(verb_id);
					new_micro_flow.setAction(micro_flow.getMicro_flow_step_name());
					this.micro_flow_dao.insert_micro_flow(new_micro_flow);
					//System.out.println(new_micro_flow.getComponent_type() + " " + new_micro_flow.getAction());
				}
			}
		}				
	}

	@Override
	public void delete_verb_method_implementation(long verb_id) throws Exception {				
		System.out.println("deleting microflow for verb = "+verb_id);
		this.micro_flow_dao.delete_micro_flow(verb_id);
		
	}

	@Override
	public ArrayList<GpMicroFlowBase> get_records_from_micro_flow_base(String base_verb_name) {

		ArrayList<GpMicroFlowBase> micro_flow_base_objects = this.micro_flow_dao
				.get_records_from_micro_flow_base(base_verb_name);
		
		return micro_flow_base_objects;
	}
	
	public ArrayList<GpMicroFlowBase> get_records_from_micro_flow_base(String base_verb_name, String component_name) {

		ArrayList<GpMicroFlowBase> micro_flow_base_objects = this.micro_flow_dao
				.get_records_from_micro_flow_base(base_verb_name, component_name);
		
		return micro_flow_base_objects;
	}

	
}
