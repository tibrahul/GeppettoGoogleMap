package com.npb.gp.interfaces.services;


import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpOtherNoun;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.MongoNoun;

public interface IMongoNoun {
	public void create_noun(long project_id, GpUser loggedUser) throws Exception;






}
