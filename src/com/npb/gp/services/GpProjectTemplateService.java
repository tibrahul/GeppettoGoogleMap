package com.npb.gp.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.domain.core.GpProjectTemplate;
import com.npb.gp.interfaces.dao.IGpProjectTemplateDao;
import com.npb.gp.interfaces.services.IGpProjectTemplateComponentService;
import com.npb.gp.interfaces.services.IGpProjectTemplateService;

/**
 * @author Dheeraj Singh</br> Creation Date: 12/31/2015</br>
 * @since 1.0 </p>
 * 
 *        this interface will be implemented by a service that handles deals
 *        with project template information </p>
 * 
 */
@Service("GpProjectTemplateService")
public class GpProjectTemplateService extends GpBaseService implements
		IGpProjectTemplateService {

	private IGpProjectTemplateDao projectTemplateDao;
	private IGpProjectTemplateComponentService projectTemplateComponentService;

	public IGpProjectTemplateDao getProjectTemplateDao() {
		return projectTemplateDao;
	}

	@Resource(name = "GpProjectTemplateDao")
	public void setProjectTemplateDao(IGpProjectTemplateDao projectTemplateDao) {
		this.projectTemplateDao = projectTemplateDao;
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
	public void create(GpProjectTemplate wrapper) throws Exception {
		// create project template
		getProjectTemplateDao().insert(wrapper);

		// create project template components
		if (wrapper.getTemplateComponents() != null
				&& !wrapper.getTemplateComponents().isEmpty()) {
			getProjectTemplateComponentService().createOrUpdate(
					wrapper.getProjectId(), wrapper.getProjectTemplateId(),
					wrapper.getTemplateComponents());
		}

	}

	@Override
	public void update(GpProjectTemplate wrapper) throws Exception {
		// update project template
		getProjectTemplateDao().update(wrapper);

		// update project template components
		if (wrapper.getTemplateComponents() != null
				&& !wrapper.getTemplateComponents().isEmpty()) {
			getProjectTemplateComponentService().createOrUpdate(
					wrapper.getProjectId(), wrapper.getProjectTemplateId(),
					wrapper.getTemplateComponents());
		}
	}

	@Override
	public void delete(long id) throws Exception {
		// check if template component exist
		find(id);

		// delete project template
		getProjectTemplateComponentService().deleteByProjectTemplate(id);

		// delete
		getProjectTemplateDao().delete(id);
	}

	@Override
	public void deleteByProject(long projectId) throws Exception {
		// delete project template components
		getProjectTemplateComponentService().deleteByProject(projectId);

		// delete project template
		getProjectTemplateDao().deleteByProject(projectId);
	}

	@Override
	public GpProjectTemplate find(long id) throws Exception {
		return getProjectTemplateDao().find(id);
	}

	@Override
	public List<GpProjectTemplate> findByProject(long projectId)
			throws Exception {
		return getProjectTemplateDao().findByProject(projectId);
	}

	@Override
	public List<GpProjectTemplate> findByTemplate(long templateId)
			throws Exception {
		return getProjectTemplateDao().findByTemplate(templateId);
	}
}
