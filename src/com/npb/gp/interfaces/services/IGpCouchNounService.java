package com.npb.gp.interfaces.services;

import com.npb.gp.domain.core.GpUser;

public interface IGpCouchNounService {

	public void create_noun(long project_id, GpUser loggedUser) throws Exception;
}
