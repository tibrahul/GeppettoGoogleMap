package com.npb.gp.interfaces.dao;

import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpOtherNoun;
import com.npb.gp.domain.core.GpUser;


public interface IMongonounDao {
	
	public void importnoun(long project_id, GpUser loggedUser) throws Exception;



}
