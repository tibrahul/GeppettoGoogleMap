package com.npb.gp.services;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.domain.core.GpUser;
import com.npb.gp.interfaces.dao.IGpCouchNounDao;
import com.npb.gp.interfaces.services.IGpCouchNounService;
@Service("GpCouchService")
public class GpCouchService implements IGpCouchNounService {
	
	private IGpCouchNounDao couch_noun_dao;

	public void create_noun(long project_id, GpUser loggedUser) {
		// TODO Auto-generated method stub
		this.couch_noun_dao.importnoun(project_id, loggedUser);
		
	}

	public IGpCouchNounDao getCouch_noun_dao() {
		return couch_noun_dao;
	}
	
	@Resource(name = "GpCouchNoundao")
	public void setCouch_noun_dao(IGpCouchNounDao couch_noun_dao) {
		this.couch_noun_dao = couch_noun_dao;
	}
	
	

}
