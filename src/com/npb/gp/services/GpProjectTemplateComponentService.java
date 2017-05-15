package com.npb.gp.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.domain.core.GpProjectTemplateComponent;
import com.npb.gp.interfaces.dao.IGpProjectTemplateComponentDao;
import com.npb.gp.interfaces.services.IGpProjectTemplateComponentService;
import com.npb.gp.interfaces.services.IGpTemplateComponentService;

/**
 * @author Dheeraj Singh</br> Creation Date: 12/31/2015</br>
 * @since 1.0 </p>
 * 
 *        this interface will be implemented by a service that handles deals
 *        with project template component information </p>
 * 
 */
@Service("GpProjectTemplateComponentService")
public class GpProjectTemplateComponentService implements
		IGpProjectTemplateComponentService {

	private IGpProjectTemplateComponentDao projectTemplateComponentDao;
	private IGpTemplateComponentService templateComponentService;

	public IGpProjectTemplateComponentDao getProjectTemplateComponentDao() {
		return projectTemplateComponentDao;
	}

	@Resource(name = "GpProjectTemplateComponentDao")
	public void setProjectTemplateComponentDao(
			IGpProjectTemplateComponentDao projectTemplateComponentDao) {
		this.projectTemplateComponentDao = projectTemplateComponentDao;
	}

	public IGpTemplateComponentService getTemplateComponentService() {
		return templateComponentService;
	}

	@Resource(name = "GpTemplateComponentService")
	public void setTemplateComponentService(
			IGpTemplateComponentService templateComponentService) {
		this.templateComponentService = templateComponentService;
	}

	@Override
	public void create(GpProjectTemplateComponent wrapper) throws Exception {
		getProjectTemplateComponentDao().insert(wrapper);
	}

	@Override
	public void update(GpProjectTemplateComponent wrapper) throws Exception {
		getProjectTemplateComponentDao().update(wrapper);
	}

	@Override
	public void createOrUpdate(long projectId, long projectTemplateId,
			List<GpProjectTemplateComponent> templateComponents)
			throws Exception {
		if (templateComponents != null && !templateComponents.isEmpty()) {
			for (GpProjectTemplateComponent gpTemplateComponent : templateComponents) {
				gpTemplateComponent.setProjectId(projectId);
				gpTemplateComponent.setProjectTemplateId(projectTemplateId);
				if (gpTemplateComponent.getProjectTemplateComponentId() == 0) {
					create(gpTemplateComponent);
				} else {
					update(gpTemplateComponent);
				}
			}
		}
	}

	@Override
	public void delete(long id) throws Exception {
		// check if template component exist
		find(id);

		// delete
		getProjectTemplateComponentDao().delete(id);
	}

	@Override
	public void deleteByProject(long projectId) throws Exception {
		getProjectTemplateComponentDao().deleteByProject(projectId);
	}

	@Override
	public void deleteByProjectTemplate(long projectTemplateId)
			throws Exception {
		getProjectTemplateComponentDao().deleteByProjectTemplate(
				projectTemplateId);
	}

	@Override
	public GpProjectTemplateComponent find(long id) throws Exception {
		return getProjectTemplateComponentDao().find(id);
	}

	@Override
	public List<GpProjectTemplateComponent> findByProject(long projectId)
			throws Exception {
		return getProjectTemplateComponentDao().findByProject(projectId);
	}

	@Override
	public List<GpProjectTemplateComponent> findByTemplateComponent(
			long templateComponentId) throws Exception {
		return getProjectTemplateComponentDao().findByTemplateComponent(
				templateComponentId);
	}

	@Override
	public List<GpProjectTemplateComponent> findByProjectTemplateAndTemplateId(
			long templateId, long projectTemplateId) throws Exception {

		// fetch template components for template id
		List<GpProjectTemplateComponent> list = getTemplateComponentService()
				.findByTemplate(templateId);

		for (GpProjectTemplateComponent gpProjectTemplateComponent : list) {
			// fetch project template component for project template and
			// template component id
			GpProjectTemplateComponent projectTemplateComponent = getProjectTemplateComponentDao()
					.findByTemplateComponentAndProjectTemplate(
							projectTemplateId,
							gpProjectTemplateComponent.getTemplateComponentId());
			if (projectTemplateComponent != null) {
				gpProjectTemplateComponent
						.setTemplateComponentValue(projectTemplateComponent
								.getTemplateComponentValue());
				gpProjectTemplateComponent
						.setProjectTemplateComponentId(projectTemplateComponent
								.getProjectTemplateComponentId());
			}
		}
		return list;
	}
}
