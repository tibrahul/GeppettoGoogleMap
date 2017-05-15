package com.npb.gp.services;

/**
 * Date Created: September 22 2012
 * 
 * @author Dan Castillo
 * 
 * The purpose of this class is to serve as the base class for all spring based services
 * 
 * this class serves the same basic purpose as the GpBaseManger in Geppetto .1 through .3
 * 
 */

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpApplicationTypeDao;
import com.npb.gp.domain.core.GpApplicationType;
import com.npb.gp.interfaces.dao.IGpApplicationTypeDao;

@Service("GpApplicationTypeService")
public class GpApplicationTypeService implements IGpApplicationTypeDao {
	GpApplicationTypeDao gpApplicationTypeDao;
	
	
	public GpApplicationTypeDao getGpApplicationTypeDao() {
		return gpApplicationTypeDao;
	}
	
	@Resource(name = "GpApplicationTypeDao")
	public void setGpApplicationTypeDao(GpApplicationTypeDao gpApplicationTypeDao) {
		this.gpApplicationTypeDao = gpApplicationTypeDao;
	}
	
	protected final Log logger = LogFactory.getLog(getClass());
	protected ApplicationContext ctx;
	@Override
	public List<GpApplicationType> find_all_Application_type() throws Exception {
		return this.gpApplicationTypeDao.find_all_Application_type();
	}



}
