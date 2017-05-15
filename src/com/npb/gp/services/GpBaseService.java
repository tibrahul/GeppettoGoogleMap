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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class GpBaseService implements ApplicationContextAware {
	protected final Log logger = LogFactory.getLog(getClass());
	protected ApplicationContext ctx;



	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub

	}

}
