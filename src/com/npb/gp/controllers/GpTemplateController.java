package com.npb.gp.controllers;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gp.domain.core.GpProjectTemplate;
import com.npb.gp.domain.core.GpProjectTemplateComponent;
import com.npb.gp.services.GpTemplateComponentService;
import com.npb.gp.services.GpTemplateService;

/**
 * @author Dheeraj Singh</br> Creation Date: 01/01/2016</br>
 * @since 1.0 </p>
 * 
 *        The purpose of this class is to be the main interaction point with
 *        applications</br> requiring template information </p>
 * 
 */

@Controller("GpTemplateController")
@RequestMapping("/template/")
public class GpTemplateController {
	private Log log = LogFactory.getLog(getClass());

	private GpTemplateService templateService;
	private GpTemplateComponentService templateComponentService;

	public GpTemplateService getTemplateService() {
		return templateService;
	}

	@Resource(name = "GpTemplateService")
	public void setTemplateService(GpTemplateService templateService) {
		this.templateService = templateService;
	}

	public GpTemplateComponentService getTemplateComponentService() {
		return templateComponentService;
	}

	@Resource(name = "GpTemplateComponentService")
	public void setTemplateComponentService(
			GpTemplateComponentService templateComponentService) {
		this.templateComponentService = templateComponentService;
	}

	/* TEMPLATE START */
	@RequestMapping(method = RequestMethod.POST, value = "/create_template/", headers = "Accept=application/json")
	@ResponseBody
	public GpProjectTemplate createTemplate(
			@RequestBody GpProjectTemplate wrapper) throws Exception {
		log.info("Inside createTemplate method");

		getTemplateService().create(wrapper);

		return wrapper;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update_template/", headers = "Accept=application/json")
	@ResponseBody
	public GpProjectTemplate updateTemplate(
			@RequestBody GpProjectTemplate wrapper) throws Exception {
		log.info("Inside updateTemplate method");

		getTemplateService().update(wrapper);

		return wrapper;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete_template/", headers = "Accept=application/json")
	@ResponseBody
	public void deleteTemplate(@RequestParam long id) throws Exception {
		log.info("Inside deleteTemplate method");

		getTemplateService().delete(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_template/", headers = "Accept=application/json")
	@ResponseBody
	public GpProjectTemplate getTemplate(@RequestParam long id)
			throws Exception {
		log.info("Inside getTemplate method");

		return getTemplateService().find(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_all_template/", headers = "Accept=application/json")
	@ResponseBody
	public List<GpProjectTemplate> getAllTemplate(@RequestParam long organization_id) throws Exception {
		log.info("Inside getAllTemplate method");
		return  getTemplateService().findAll(organization_id);
	}

	/* TEMPLATE COMPONENT START */

	@RequestMapping(method = RequestMethod.POST, value = "/create_template_component/", headers = "Accept=application/json")
	@ResponseBody
	public GpProjectTemplateComponent createTemplateComponent(
			@RequestBody GpProjectTemplateComponent wrapper) throws Exception {
		log.info("Inside createTemplateComponent method");

		getTemplateComponentService().create(wrapper);

		return wrapper;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update_template_component/", headers = "Accept=application/json")
	@ResponseBody
	public GpProjectTemplateComponent updateTemplateComponent(
			@RequestBody GpProjectTemplateComponent wrapper) throws Exception {
		log.info("Inside updateTemplateComponent method");

		getTemplateComponentService().update(wrapper);

		return wrapper;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete_template_component/", headers = "Accept=application/json")
	@ResponseBody
	public void deleteTemplateComponent(@RequestParam long id) throws Exception {
		log.info("Inside deleteTemplateComponent method");

		getTemplateComponentService().delete(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_template_component/", headers = "Accept=application/json")
	@ResponseBody
	public GpProjectTemplateComponent getTemplateComponent(@RequestParam long id)
			throws Exception {
		log.info("Inside getTemplateComponent method");

		return getTemplateComponentService().find(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_template_component_by_template/", headers = "Accept=application/json")
	@ResponseBody
	public List<GpProjectTemplateComponent> getTemplateComponentByTemplate(
			@RequestParam long templateId) throws Exception {
		log.info("Inside getTemplateComponentByTemplate method");

		return getTemplateComponentService().findByTemplate(templateId);
	}
}