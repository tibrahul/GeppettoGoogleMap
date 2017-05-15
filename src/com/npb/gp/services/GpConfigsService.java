package com.npb.gp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpConfigsDao;
import com.npb.gp.domain.core.GpConfig;

@Service("GpConfigsService")
public class GpConfigsService {
	@Autowired
	private GpConfigsDao the_Dao;

	public GpConfig get_by_name(String name) {
		// TODO Auto-generated method stub
		return the_Dao.get_by_name(name);
	}

}
