package com.npb.gp.services;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpMongoNounDao;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpOtherNoun;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.MongoNoun;
import com.npb.gp.interfaces.dao.IMongonounDao;
import com.npb.gp.interfaces.services.IMongoNoun;

@Service("GpMongoNounService")
public class GpMongoNounService implements IMongoNoun {

	private IMongonounDao mongo_noun_dao;
	
	@Override
	public void create_noun(long project_id,GpUser loggedUser)
			throws Exception {
		this.mongo_noun_dao.importnoun(project_id, loggedUser);
		
	}

	public IMongonounDao getMongo_noun_dao() {
		return mongo_noun_dao;
	}
	@Resource(name = "GpMongoNounDao")
	public void setIMongonounDao(IMongonounDao mongo_noun_dao) {
		this.mongo_noun_dao = mongo_noun_dao;
	}

	
	

}
