package com.npb.gp.interfaces.dao;

import java.util.List;
import java.util.Set;

import com.npb.gp.domain.core.GpAuthority;
import com.npb.gp.domain.core.GpInstallrConfig;
import com.npb.gp.domain.core.GpMacConfig;
import com.npb.gp.domain.core.GpUser;

public interface IGpNewUsersDao {

	public abstract GpUser insert(GpUser gpUser) throws Exception;		

	public List<GpMacConfig> getMacList() throws Exception;
	
	//public List<GpInstallrConfig> getInstallrList(long id) throws Exception;
	public boolean getInstallrList(GpUser gpUser,GpMacConfig gpconfig) throws Exception ;
	
}
