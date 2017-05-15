package com.npb.gp.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.npb.gp.dao.mysql.GpActivityDao;
import com.npb.gp.dao.mysql.GpMicroServiceDao;
import com.npb.gp.dao.mysql.GpProjectDao;
import com.npb.gp.dao.mysql.GpScreenXDao;
import com.npb.gp.dao.mysql.GpWizardDao;
import com.npb.gp.dao.mysql.support.microservice.GpMicroServiceMapper;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroService;
import com.npb.gp.interfaces.services.IGpActivityService;

/**
 * Creation Date: 02/06/2014
 * 
 * @since version .35 </p>
 * 
 * 
 *        The purpose of this class is to provide the entry point for any
 *        functions having to do with Activities </p>
 * 
 *        <b>Modified Date: 07/08/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        Added menu builder codes in insert and delete methods
 *        </p>
 * 
 *        <b>Modified Date: 06/05/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        Changed the parameter in get_module_default_id method
 *        </p>
 * 
 *        Modified Date: 15/04/2015</br> Modified By: Suresh Palanisamy </p>
 * 
 *        Changed the GpScreen to GpScreenX in all methods and declarations
 * 
 *        Modified Date: 10/22/2014</br> Modified By: Dan Castillo </p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex
 * 
 *        <b>Modified Date: 01/04/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        Added new method as delete to delete the activities
 *        </p>
 * 
 * 
 * 
 *        <b>Modified Date: 05/31/2016<br>
 *        Modified By: Kumaresan Perumal<b><br>
 *        <p>
 *        I wrote some code create_activity method to check up the validation.
 *        here we find activity name exist or not
 *        </p>
 */
@Service("GpMicroActivityService")
public class GpMicroActivityService {
	GpMicroServiceDao gpMicroserviceDao;

	public GpMicroServiceDao getGpMicroserviceDao() {
		return gpMicroserviceDao;
	}

	@Resource(name = "GpMicroServiceDao")
	public void setGpMicroserviceDao(GpMicroServiceDao gpMicroserviceDao) {
		this.gpMicroserviceDao = gpMicroserviceDao;
	}

	GpActivityDao gpActivityDao;

	public GpActivityDao getGpActivityDao() {
		return gpActivityDao;
	}

	@Resource(name = "GpActivityDao")
	public void setGpActivityDao(GpActivityDao gpActivityDao) {
		this.gpActivityDao = gpActivityDao;
	}

	public GpMicroService create_microservice_wizard(GpMicroService gpWizard)
			throws Exception {
		System.out.println("gpWizard" + gpWizard.getMicroservice_name());

		return this.gpMicroserviceDao.insert(gpWizard);

	}

	public void updateWizard_screen(GpMicroService wizarddata) throws Exception {
		this.gpMicroserviceDao.updateMicroService(wizarddata);
	}

	public List<GpMicroService> get_all_micro_service(Long projectId) throws Exception {

		JSONArray jsArray = null;
		Long activityId;
		List<GpMicroService> serviceList = new ArrayList<GpMicroService>();
		List<GpMicroService> microServiceList =this.gpMicroserviceDao.getMicroservicebyId(projectId); 
		List<JSONArray> jsonObjectList=new ArrayList<JSONArray>();
		GpMicroService gpnewmicroservice = null;
		for (GpMicroService gpMicroService : microServiceList) {
			if(!gpMicroService.getActivities_json().equals("")){
				jsonObjectList.add(new JSONArray(gpMicroService.getActivities_json()));
					if(gpMicroService.getActivities_json() !=null || !gpMicroService.getActivities_json().equals("")){
						String [] array = gpMicroService.getActivities_json().split(",");
						if (array.length > 0) {
							jsArray = new JSONArray();
							for (int i = 0; i < array.length; i++) {
								String ar2[] = array[i].split(":");
								activityId = Long.parseLong(ar2[1].replaceAll("\\}", "").replaceAll("\\]", ""));
								GpActivity gpActivity = this.gpActivityDao.find_by_id(activityId);
								//activityList.add(gpActivity.getId());
								gpnewmicroservice = new GpMicroService();
								gpnewmicroservice.setId(gpMicroService.getId());
								gpnewmicroservice.setMicroservice_name(gpMicroService.getMicroservice_name());
								gpnewmicroservice.setProject_id(gpMicroService.getProject_id());
								gpnewmicroservice.setActivity_id(gpActivity.getId());	
								gpnewmicroservice.setActivity_name(gpActivity.getName());
								serviceList.add(gpnewmicroservice);
							}
					}
						
				}
			}else{
				gpnewmicroservice = gpMicroService;
				serviceList.add(gpnewmicroservice);
			}
					
		}
		return serviceList;
	}

	public void deleteWizardFromActivity(GpMicroService wizarddata) {
		this.gpMicroserviceDao.deleteActivityFromWizard(wizarddata);
	}


	public List<GpMicroService> getMicroserviceByServiceId(Long serviceId) throws Exception {
		GpMicroService microService= this.gpMicroserviceDao.getMicroserviceByServiceId(serviceId);
		List<JSONArray> jsonObjectList=new ArrayList<JSONArray>();
		List<GpMicroService> serviceList = new ArrayList<GpMicroService>();
		JSONArray jsArray = null;
		Long activityId;
		GpMicroService gpnewmicroservice = null;
			if(!microService.getActivities_json().equals("")){
				jsonObjectList.add(new JSONArray(microService.getActivities_json()));
					if(microService.getActivities_json() !=null || !microService.getActivities_json().equals("")){
						String [] array = microService.getActivities_json().split(",");
						if (array.length > 0) {
							jsArray = new JSONArray();
							for (int i = 0; i < array.length; i++) {
								String ar2[] = array[i].split(":");
								activityId = Long.parseLong(ar2[1].replaceAll("\\}", "").replaceAll("\\]", ""));
								GpActivity gpActivity = this.gpActivityDao.find_by_id(activityId);
								//activityList.add(gpActivity.getId());
								gpnewmicroservice = new GpMicroService();
								gpnewmicroservice.setId(microService.getId());
								gpnewmicroservice.setMicroservice_name(microService.getMicroservice_name());
								gpnewmicroservice.setProject_id(microService.getProject_id());
								gpnewmicroservice.setActivity_id(gpActivity.getId());	
								gpnewmicroservice.setActivity_name(gpActivity.getName());
								serviceList.add(gpnewmicroservice);
							}
					}
						
				}
			}else{
				gpnewmicroservice = microService;
				serviceList.add(gpnewmicroservice);
		}
			return serviceList;
	}


	public void updateMicroserviceWizard(GpMicroService wizarddata) {
		this.gpMicroserviceDao.updateMicroserviceWizard(wizarddata);
	}

	public GpMicroService getDataForMicroservice(Long serviceId) {
		return this.gpMicroserviceDao.getMicroserviceByServiceId(serviceId);
	}

}