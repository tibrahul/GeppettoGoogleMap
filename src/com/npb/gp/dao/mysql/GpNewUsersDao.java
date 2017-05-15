package com.npb.gp.dao.mysql;

import java.util.Date;


import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.user.GpInstallruserMapper;
import com.npb.gp.dao.mysql.support.user.GpMacUserMapper;
import com.npb.gp.dao.mysql.support.user.InsertInstallrUser;
import com.npb.gp.dao.mysql.support.user.InsertNewUser;
import com.npb.gp.dao.mysql.support.user.UpdateMacUser;
import com.npb.gp.dao.mysql.support.user.UpdateUser;
import com.npb.gp.dao.mysql.support.user.UpdateUserMacInstallr;
import com.npb.gp.domain.core.GpInstallrConfig;
import com.npb.gp.domain.core.GpMacConfig;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.interfaces.dao.IGpNewUsersDao;

@Repository("GpNewUsersDao")
public class GpNewUsersDao implements IGpNewUsersDao {

	private Log log = LogFactory.getLog(getClass());
	private InsertNewUser insert_user;
	
	private UpdateMacUser updateMacUser;
	private InsertInstallrUser insertInstallrDetails;
	private UpdateUserMacInstallr updateUserMacInstallr;
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	@Value("${insert.newuser.sql}")
	private String insertNewUser;
	
	@Value("${findMac.sql}")
	private String insertMacForUser;
	
	@Value("${findInstallr.sql}")
	private String findInstallruser;
	
	@Value("${insertInstallrUser.sql}")
	private String insertInstallrUser;
	
	@Value("${update.user.sql}")
	private String updateUser_Mac_Installr;
	
	@Value("${update.mac_allocation_status.sql}")
	private String mac_allocation_status;
	
	
	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	
	@Override
	public GpUser insert(GpUser gpUser) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", gpUser.getId());
		paramMap.put("username", gpUser.getUsername());		
		paramMap.put("createdon", new Date());
		//Added "requested" for new user coming in database 
		//using this "requested" token we send the email
		paramMap.put("processed","requested");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertNewUser.SQL_INSERT_NEW_USER = insertNewUser;
		this.insert_user = new InsertNewUser(dataSource);
		this.insert_user.updateByNamedParam(paramMap, keyHolder);
		return gpUser;
	}


	@Override
	public List<GpMacConfig> getMacList() throws Exception {
		System.err.println("In getting mac list");
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("status", "Not_Full");
		GpMacUserMapper project_mapper = new GpMacUserMapper();
		List<GpMacConfig> dto_list = this.namedParameterJdbcTemplate.query(
				insertMacForUser, parameters, project_mapper);
		if (dto_list.size() < 0) {
			System.err.println("No Mac is Present for allocation");
			throw new Exception("No Mac is Present for allocation"
					+ " was not found");
		}
		return  dto_list;
	}
	@Override
	public boolean getInstallrList(GpUser newgpuser,GpMacConfig gpMacConfig) throws Exception {
		System.err.println("In getting  Installr List");
		GpInstallrConfig installrUser = new GpInstallrConfig();
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("mac_config_id",gpMacConfig.getId() );
		GpInstallruserMapper project_mapper = new GpInstallruserMapper();
		List<GpInstallrConfig> dto_list = this.namedParameterJdbcTemplate.query(
				findInstallruser, parameters, project_mapper);
		if (dto_list.size() <20) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("user_id", newgpuser.getId());
			paramMap.put("mac_config_id", gpMacConfig.getId());
			paramMap.put("createdDate", new Date());
			paramMap.put("updatedDate", new Date());
			KeyHolder keyHolder = new GeneratedKeyHolder();
			InsertInstallrUser.SQL_INSERT_INSTALLR_USER = insertInstallrUser;
			this.insertInstallrDetails = new InsertInstallrUser(dataSource);
			this.insertInstallrDetails.updateByNamedParam(paramMap, keyHolder);
			installrUser.setId(keyHolder.getKey().longValue());
			
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("id", newgpuser.getId());
			userMap.put("mac_config_id", gpMacConfig.getId());
			userMap.put("installr_user_config_id", installrUser.getId());
			
			KeyHolder userkeyHolder = new GeneratedKeyHolder();
			UpdateUserMacInstallr.SQL_UPDATE_USER_MAC_INSTALLR = updateUser_Mac_Installr;
			this.updateUserMacInstallr = new UpdateUserMacInstallr(dataSource);
			this.updateUserMacInstallr.updateByNamedParam(userMap, userkeyHolder);
			System.err.println("<<<<-----Mac Allocation DONE---->>>>>");
			return true;
		}else{
			//when installr / mac machine full -- mac table will update as staus Full
			Map<String, Object> macMap = new HashMap<String, Object>();
			System.err.println("<<<<----1--  MAC ALLOCATION STARTS---->>>>>\n"+newgpuser.toString());
			macMap.put("id", gpMacConfig.getId());
			macMap.put("status", "Full");
			macMap.put("updatedDate", new Date());
			
			KeyHolder mackeyHolder = new GeneratedKeyHolder();
			UpdateMacUser.SQL_UPADATE_MAC_USER = mac_allocation_status;
			this.updateMacUser = new UpdateMacUser(dataSource);
			this.updateMacUser.updateByNamedParam(macMap, mackeyHolder);
			System.err.println("<<<<-------Mac Machine are Full------->>>>>");
			return  false;
		}
	}
}
