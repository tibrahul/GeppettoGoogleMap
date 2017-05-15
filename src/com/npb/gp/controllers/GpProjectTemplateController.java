package com.npb.gp.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gp.domain.core.GpProjectTemplate;
import com.npb.gp.domain.core.GpProjectTemplateComponent;
import com.npb.gp.services.GpProjectTemplateComponentService;
import com.npb.gp.services.GpProjectTemplateService;

/**
 * @author Dheeraj Singh</br> Creation Date: 01/05/2016</br>
 * @since 1.0 </p>
 * 
 *        The purpose of this class is to be the main interaction point with
 *        applications</br> requiring project template information </p>
 * 
 */

@Controller("GpProjectTemplateController")
@RequestMapping("/projecttemplate")
public class GpProjectTemplateController {
	private Log log = LogFactory.getLog(getClass());

	private GpProjectTemplateService projectTemplateService;
	private GpProjectTemplateComponentService projectTemplateComponentService;

	public GpProjectTemplateService getProjectTemplateService() {
		return projectTemplateService;
	}

	@Resource(name = "GpProjectTemplateService")
	public void setProjectTemplateService(
			GpProjectTemplateService projectTemplateService) {
		this.projectTemplateService = projectTemplateService;
	}

	public GpProjectTemplateComponentService getProjectTemplateComponentService() {
		return projectTemplateComponentService;
	}

	@Resource(name = "GpProjectTemplateComponentService")
	public void setProjectTemplateComponentService(
			GpProjectTemplateComponentService projectTemplateComponentService) {
		this.projectTemplateComponentService = projectTemplateComponentService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create/", headers = "Accept=application/json")
	@ResponseBody
	public GpProjectTemplate createProjectTemplate(
			@RequestBody GpProjectTemplate wrapper) throws Exception {
		log.info("Inside createProjectTemplate method");

		getProjectTemplateService().create(wrapper);

		return wrapper;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update/", headers = "Accept=application/json")
	@ResponseBody
	public GpProjectTemplate updateProjectTemplate(
			@RequestBody GpProjectTemplate wrapper) throws Exception {
		log.info("Inside updateProjectTemplate method");

		getProjectTemplateService().update(wrapper);

		return wrapper;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/", headers = "Accept=application/json")
	@ResponseBody
	public void deleteProjectTemplate(@RequestParam long id) throws Exception {
		log.info("Inside deleteProjectTemplate method");

		getProjectTemplateService().delete(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete_by_project/", headers = "Accept=application/json")
	@ResponseBody
	public void deleteProjectTemplateByProject(@RequestParam long projectId)
			throws Exception {
		log.info("Inside deleteProjectTemplateByProject method");

		getProjectTemplateService().deleteByProject(projectId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get/", headers = "Accept=application/json")
	@ResponseBody
	public GpProjectTemplate getProjectTemplate(@RequestParam long id)
			throws Exception {
		log.info("Inside getProjectTemplate method");

		return getProjectTemplateService().find(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_by_project/", headers = "Accept=application/json")
	@ResponseBody
	public List<GpProjectTemplate> getProjectTemplateByProject(
			@RequestParam long projectId) throws Exception {
		log.info("Inside getProjectTemplateByProject method");

		return getProjectTemplateService().findByProject(projectId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_by_template/", headers = "Accept=application/json")
	@ResponseBody
	public List<GpProjectTemplate> getProjectTemplateByTemplate(
			@RequestParam long templateId) throws Exception {
		log.info("Inside getProjectTemplateByTemplate method");

		return getProjectTemplateService().findByTemplate(templateId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create_component/", headers = "Accept=application/json")
	@ResponseBody
	public void createComponent(@RequestBody GpProjectTemplateComponent wrapper)
			throws Exception {
		log.info("Inside createComponent method");

		getProjectTemplateComponentService().create(wrapper);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update_component/", headers = "Accept=application/json")
	@ResponseBody
	public void updateComponent(@RequestBody GpProjectTemplateComponent wrapper)
			throws Exception {
		log.info("Inside updateComponent method");

		getProjectTemplateComponentService().update(wrapper);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete_component/", headers = "Accept=application/json")
	@ResponseBody
	public void deleteComponent(@RequestParam long id) throws Exception {
		log.info("Inside deleteComponent method");

		getProjectTemplateComponentService().delete(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete_component_by_project/", headers = "Accept=application/json")
	@ResponseBody
	public void deleteComponentByProject(@RequestParam long projectId)
			throws Exception {
		log.info("Inside deleteComponentByProject method");

		getProjectTemplateComponentService().deleteByProject(projectId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_component/", headers = "Accept=application/json")
	@ResponseBody
	public GpProjectTemplateComponent getComponent(@RequestParam long id)
			throws Exception {
		log.info("Inside getComponent method");

		return getProjectTemplateComponentService().find(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_component_by_project/", headers = "Accept=application/json")
	@ResponseBody
	public List<GpProjectTemplateComponent> getComponentByProject(
			@RequestParam long projectId) throws Exception {
		log.info("Inside getComponentByProject method");

		return getProjectTemplateComponentService().findByProject(projectId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_component_by_template/", headers = "Accept=application/json")
	@ResponseBody
	public List<GpProjectTemplateComponent> getComponentByTemplete(
			@RequestParam long templateComponentId) throws Exception {
		log.info("Inside getComponentByTemplete method");

		return getProjectTemplateComponentService().findByTemplateComponent(
				templateComponentId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_component_by_project_template_and_template/", headers = "Accept=application/json")
	@ResponseBody
	public List<GpProjectTemplateComponent> getComponentsByProjectTemplateAndTemplate(
			@RequestParam long templateId, @RequestParam long projectTemplateId)
			throws Exception {
		log.info("Inside getComponentByProjectAndTemplateComp method");

		return getProjectTemplateComponentService()
				.findByProjectTemplateAndTemplateId(templateId,
						projectTemplateId);
	}

}