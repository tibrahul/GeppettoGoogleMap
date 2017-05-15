package com.npb.gp.interfaces.dao;

import java.util.List;
import java.util.Set;

import org.omg.PortableServer.ThreadPolicyOperations;

import com.npb.gp.domain.core.GpAuthority;
import com.npb.gp.domain.core.GpUser;

public interface IGpUserDao {

	public abstract GpUser findUser(String userName);

	public abstract Set<GpAuthority> getRolesForGpUser(GpUser user);

	public abstract GpUser insert(GpUser gpUser) throws Exception;
		
	public abstract void Update_docker_json(GpUser gpUser) throws Exception;
	
	public abstract List<GpUser> findAllUsers () throws Exception;

}
