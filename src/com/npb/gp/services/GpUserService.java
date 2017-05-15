package com.npb.gp.services;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.npb.gp.dao.mysql.GpUserDao;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.interfaces.services.IGpUserService;

@Transactional
@Service("GpUserService")
public class GpUserService implements UserDetailsService, IGpUserService {

	private Log log = LogFactory.getLog(getClass());

	private GpUserDao gpuser_Dao;

	public GpUserDao getGpuser_Dao() {
		return gpuser_Dao;
	}

	@Resource(name = "GpUserDao")
	public void setGpuser_Dao(GpUserDao gpuser_Dao) {
		this.gpuser_Dao = gpuser_Dao;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		GpUser user = gpuser_Dao.findUser(userName);
		if (user == null) {
			throw new UsernameNotFoundException("No User found");
		}
		return user;
	}

	
	@Override
	@Transactional
	public void Update_docker_json(GpUser user) throws Exception {
		 gpuser_Dao.Update_docker_json(user);
	}

	public void Update_docker_json_status(GpUser user) {
		gpuser_Dao.Update_docker_json_status(user);
	}

	@Override
	public List<GpUser> findAllUsers() throws Exception {
		return gpuser_Dao.findAllUsers();
	}

}
