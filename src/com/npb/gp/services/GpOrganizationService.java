package com.npb.gp.services;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpOrganizationDao;
import com.npb.gp.domain.core.GpOrganization;
import com.npb.gp.domain.core.GpUser;

@Service("GpOrganizationService")
public class GpOrganizationService extends GpBaseService {

	private GpOrganizationDao gpOrganizationDao;

	
	
	
	public GpOrganization create_project(GpOrganization organization,GpUser user) throws Exception {
		organization.setCreatedby(user.getId());
		organization.setLastmodifiedby(user.getId());
		System.out.println("GpOrganizationService - create_project" +organization.toString()+"\n" );
		return this.gpOrganizationDao.insert(organization,user);
		
	}
	
	
	public GpOrganizationDao getGpOrganizationDao() {
		return gpOrganizationDao;
	}

	@Resource(name = "GpOrganizationDao")
	public void setGpOrganizationDao(GpOrganizationDao gpOrganizationDao) {
		this.gpOrganizationDao = gpOrganizationDao;
	}


	public GpOrganization getOrganization_by_user_id(Long id) throws Exception {
		
		// TODO Auto-generated method stub
		return this.gpOrganizationDao.getOrganization_by_user_id(id);
	}
	
}
