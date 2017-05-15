package com.npb.gp.services;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.domain.core.GpProjectTemplate;
import com.npb.gp.interfaces.dao.IGpTemplateDao;
import com.npb.gp.interfaces.services.IGpProjectTemplateService;
import com.npb.gp.interfaces.services.IGpTemplateComponentService;
import com.npb.gp.interfaces.services.IGpTemplateService;

/**
 * @author Dheeraj Singh</br> Creation Date: 12/30/2015</br>
 * @since 1.0 </p>
 * 
 *        this interface will be implemented by a service that handles deals
 *        with template information </p>
 * 
 */
@Service("GpTemplateService")
public class GpTemplateService extends GpBaseService implements
		IGpTemplateService {

	private IGpTemplateDao templateDao;

	private IGpTemplateComponentService templateComponentService;

	private IGpProjectTemplateService projectTemplateService;

	public IGpTemplateDao getTemplateDao() {
		return templateDao;
	}

	@Resource(name = "GpTemplateDao")
	public void setTemplateDao(IGpTemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public IGpTemplateComponentService getTemplateComponentService() {
		return templateComponentService;
	}

	@Resource(name = "GpTemplateComponentService")
	public void setTemplateComponentService(
			IGpTemplateComponentService templateComponentService) {
		this.templateComponentService = templateComponentService;
	}

	public IGpProjectTemplateService getProjectTemplateService() {
		return projectTemplateService;
	}

	@Resource(name = "GpProjectTemplateService")
	public void setProjectTemplateService(
			IGpProjectTemplateService projectTemplateService) {
		this.projectTemplateService = projectTemplateService;
	}

	@Override
	public void create(GpProjectTemplate wrapper) throws Exception {
		getTemplateDao().insert(wrapper);
	}

	@Override
	public void update(GpProjectTemplate wrapper) throws Exception {
		getTemplateDao().update(wrapper);
	}

	@Override
	public void delete(long id) throws Exception {
		// check if template exist
		find(id);

		// check if the template component has been used
		List<GpProjectTemplate> list = getProjectTemplateService()
				.findByTemplate(id);
		if (list != null && !list.isEmpty()) {
			throw new Exception(
					"Template (ID="
							+ id
							+ ") can't be deleted. It has been used inside a Project Template.");
		}

		// delete template components
		getTemplateComponentService().deleteTemplateComponent(id);

		// delete template
		getTemplateDao().delete(id);
	}

	@Override
	public GpProjectTemplate find(long id) throws Exception {
		return getTemplateDao().find(id);
	}

	@Override
	public List<GpProjectTemplate> findAll(long organization_id) throws Exception {
		return getTemplateDao().findAll(organization_id);
	}
}
