package com.npb.gp.interfaces.services;


import java.util.List;

import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpVerb;

public interface IGpUserService {
	public void Update_docker_json(GpUser user) throws Exception;
	public abstract List<GpUser> findAllUsers () throws Exception;

}
