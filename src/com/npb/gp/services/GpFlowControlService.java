package com.npb.gp.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.constants.GpBaseConstants;
import com.npb.gp.dao.mysql.GpFlowControlDao;
import com.npb.gp.dao.mysql.GpProjectDao;
import com.npb.gp.dao.mysql.GpTechPropertyDao;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpFlowControlBase;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.interfaces.services.IGpFlowControlService;

/***
 * <b>Created Date: 29/04/2015<br>
 * Modified By: Kumaresan Perumal<b><br>
 * 
 * <p>
 * Created the following methods as get_records_from_flowcontrol_base
 * </p>
 * 
 * Modified By: Reinaldo Lopez <b><br>
 * Date:04/09/2015
 * <p>
 * Added the method get_flow_control_by_activity
 * </p>
 * */
@Service("GpFlowControlService")
public class GpFlowControlService extends GpBaseService implements
		IGpFlowControlService {

	private GpFlowControlDao flowcontrol_dao;
	private GpProjectDao project_dao;
	private GpTechPropertyDao tech_prop_dao;

	public GpTechPropertyDao getTech_prop_dao() {
		return tech_prop_dao;
	}

	@Resource(name = "GpTechPropertyDao")
	public void setTech_prop_dao(GpTechPropertyDao tech_prop_dao) {
		this.tech_prop_dao = tech_prop_dao;
	}

	public GpFlowControlDao getFlowcontrol_dao() {
		return flowcontrol_dao;
	}

	@Resource(name = "GpFlowControlDao")
	public void setFlowcontrol_dao(GpFlowControlDao flowcontrol_dao) {
		this.flowcontrol_dao = flowcontrol_dao;
	}

	public GpProjectDao getProject_dao() {
		return project_dao;
	}

	@Resource(name = "GpProjectDao")
	public void setProject_dao(GpProjectDao project_dao) {
		this.project_dao = project_dao;
	}

	@Override
	public void get_records_from_flowcontrol_base(long project, long activity)
			throws Exception {

		Boolean first_flow_control = true;
		long master_flow_id = 0;

		GpFlowControl a_flow_control = new GpFlowControl();

		// TODO: fetch only for selected frameworks
		GpProject the_project = this.project_dao.find_by_id(project);

		List<String> tech_properties_types = new ArrayList<String>();

		// GpServerDevFramework
		long server_language = the_project.getServer_dev_lang();
		long server_dev_framework = the_project.getServer_dev_framework();
		long client_language = the_project.getClient_dev_language();
		long client_framework = the_project.getClient_dev_framework();
		
		GpTechProperties techProperties_server_language = tech_prop_dao
				.find_by_id(server_language);
		GpTechProperties techProperties_server_framework = tech_prop_dao
				.find_by_id(server_dev_framework);
		GpTechProperties techProperties_client_language = tech_prop_dao
				.find_by_id(client_language);
		GpTechProperties techProperties_client_framework = tech_prop_dao
				.find_by_id(client_framework);

		List<GpFlowControlBase> flow_control_base_objects = new ArrayList<GpFlowControlBase>();
		// get GpFlow Master
		flow_control_base_objects.addAll(this.flowcontrol_dao
				.get_records_from_flowcontrol_base("na"));
		//get Flow for server 
		flow_control_base_objects.addAll(this.flowcontrol_dao
				.get_records_from_flowcontrol_base(techProperties_server_language,techProperties_server_framework));
		//get flow for client
		flow_control_base_objects.addAll(this.flowcontrol_dao
				.get_records_from_flowcontrol_base(techProperties_client_language,techProperties_client_framework));

		for (GpFlowControlBase myFlowControlBase : flow_control_base_objects) {

			GpFlowControl master_flow_record = new GpFlowControl();
			master_flow_record.setComponent_type(myFlowControlBase
					.getComponent_type());
			master_flow_record.setLabel(myFlowControlBase.getLabel());
			master_flow_record.setDescription(myFlowControlBase
					.getDescription());
			master_flow_record.setType(myFlowControlBase.getType());
			master_flow_record.setSequence_id(myFlowControlBase
					.getSequence_id());
			master_flow_record.setSub_sequence_id(myFlowControlBase
					.getSub_sequence_id());
			master_flow_record.setActivity_id(activity);

			String type = myFlowControlBase.getType().toString().trim();
			String key = GpBaseConstants.GpFlow_Master;

			if (first_flow_control && type.equals(key)) {
				System.out.println("Condition is worked");

				master_flow_record.setMaster_flow_id(master_flow_id);
				a_flow_control = this.flowcontrol_dao
						.insert_flow_control(master_flow_record);

				master_flow_id = a_flow_control.getId();
				this.flowcontrol_dao.update_masterflow_id(master_flow_id);

				first_flow_control = false;
			} else {
				master_flow_record.setMaster_flow_id(master_flow_id);
				this.flowcontrol_dao.insert_flow_control(master_flow_record);
			}
		}
		this.flowcontrol_dao.update_masterflow_id_to_activity(master_flow_id,
				activity);
	}

	@Override
	public List<GpFlowControl> get_flow_control_by_activity(long activity_id)
			throws Exception {
		return this.flowcontrol_dao.get_flow_control_by_activity(activity_id);
	}
	
	@Override
	public List<GpFlowControl> get_flow_control_by_activity_and_component(long activity_id,String componentType)
			throws Exception {
		return this.flowcontrol_dao.get_flow_control_by_activity_and_component(activity_id, componentType);
	}
	
	public void delete_flows_by_activity_id(long activity_id){
		this.flowcontrol_dao.delete_flows_by_activity_id(activity_id);
	}
}
