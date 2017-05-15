package com.npb.gp.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.domain.core.GpProjectTemplateComponent;
import com.npb.gp.interfaces.dao.IGpTemplateComponentDao;
import com.npb.gp.interfaces.services.IGpProjectTemplateComponentService;
import com.npb.gp.interfaces.services.IGpTemplateComponentService;

/**
 * @author Dheeraj Singh</br> Creation Date: 12/30/2015</br>
 * @since 1.0 </p>
 * 
 *        this interface will be implemented by a service that handles deals
 *        with template component information </p>
 * 
 */
@Service("GpTemplateComponentService")
public class GpTemplateComponentService extends GpBaseService implements
		IGpTemplateComponentService {

	private IGpTemplateComponentDao templateComponentDao;
	private IGpProjectTemplateComponentService projectTemplateComponentService;

	public IGpTemplateComponentDao getTemplateComponentDao() {
		return templateComponentDao;
	}

	@Resource(name = "GpTemplateComponentDao")
	public void setTemplateComponentDao(
			IGpTemplateComponentDao templateComponentDao) {
		this.templateComponentDao = templateComponentDao;
	}

	public IGpProjectTemplateComponentService getProjectTemplateComponentService() {
		return projectTemplateComponentService;
	}

	@Resource(name = "GpProjectTemplateComponentService")
	public void setProjectTemplateComponentService(
			IGpProjectTemplateComponentService projectTemplateComponentService) {
		this.projectTemplateComponentService = projectTemplateComponentService;
	}

	@Override
	public void create(GpProjectTemplateComponent wrapper) throws Exception {
		getTemplateComponentDao().insert(wrapper);
	}

	@Override
	public void update(GpProjectTemplateComponent wrapper) throws Exception {
		getTemplateComponentDao().update(wrapper);
	}

	@Override
	public void delete(long id) throws Exception {
		// check if template component exist
		find(id);

		// check if the template component has been used
		List<GpProjectTemplateComponent> list = getProjectTemplateComponentService()
				.findByTemplateComponent(id);
		if (list != null && !list.isEmpty()) {
			throw new Exception("Template Component (ID=" + id
					+ ") can't be deleted. It has been used inside a project.");
		}
		// delete
		getTemplateComponentDao().delete(id);
	}

	@Override
	public void deleteTemplateComponent(long templateId) throws Exception {
		getTemplateComponentDao().deleteTemplateComponent(templateId);
	}

	@Override
	public GpProjectTemplateComponent find(long id) throws Exception {
		return getTemplateComponentDao().find(id);
	}

	@Override
	public List<GpProjectTemplateComponent> findByTemplate(long templateId)
			throws Exception {
		return getTemplateComponentDao().findByTemplate(templateId);
	}
}
