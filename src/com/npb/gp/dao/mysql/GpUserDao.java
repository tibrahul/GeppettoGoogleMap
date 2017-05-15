package com.npb.gp.dao.mysql;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.npb.gp.dao.mysql.support.activity.GpAuthorityRowMapper;
import com.npb.gp.dao.mysql.support.projecttemplate.SelectUserDetailWithAdmin;
import com.npb.gp.dao.mysql.support.user.DeleteInstallrNewUserByUserId;
import com.npb.gp.dao.mysql.support.user.DeleteUserDetailFrCorrespondingAdminId;
import com.npb.gp.dao.mysql.support.user.DeleteUserDetailWithAdminData;
import com.npb.gp.dao.mysql.support.user.GpUserIdRowMapper;
import com.npb.gp.dao.mysql.support.user.GpUserRowMapper;
import com.npb.gp.dao.mysql.support.user.InsertDto_InstallrDetails;
import com.npb.gp.dao.mysql.support.user.InsertNewUserWithAdmin;
import com.npb.gp.dao.mysql.support.user.InsertUser;
import com.npb.gp.dao.mysql.support.user.InstallrInsertUserInfo;
import com.npb.gp.dao.mysql.support.user.SelectStatusOfNewUserDetails;
import com.npb.gp.dao.mysql.support.user.SelectStatusOfNewUserDetailsWithInstallrName;
import com.npb.gp.dao.mysql.support.user.UpdateInstallrNameInNewUserTbl;
import com.npb.gp.dao.mysql.support.user.UpdateNewUserStatus;
import com.npb.gp.dao.mysql.support.user.UpdateNewUserStatusWithIseditable;
import com.npb.gp.dao.mysql.support.user.UpdateNew_Users_Status;
import com.npb.gp.dao.mysql.support.user.UpdateUser;
import com.npb.gp.dao.mysql.support.user.UpdateUserDockerStatus;
import com.npb.gp.domain.core.GpAuthority;
import com.npb.gp.domain.core.GpNewUser;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpUserDetailWithAdmin;
import com.npb.gp.interfaces.dao.IGpUserDao;

@Repository("GpUserDao")
public class GpUserDao implements IGpUserDao {

	private Log log = LogFactory.getLog(getClass());
	private InsertUser insert_user;
	
	// add the InsertNewUserWithAdmin 
	private InsertNewUserWithAdmin insertNewUserWithAdmin;
	
	private InstallrInsertUserInfo installrInsertUserInfo;
	
	// update the column of lockorunlock in new_users table
	private UpdateNewUserStatus updateNewUserStatus;
	
	// update the column of new-user tble isEditable and lockorunlock based on id
	private UpdateNewUserStatusWithIseditable updateNewUserStatusWithIseditable;
 	
	// delete the useridWithAdmindata
	private DeleteUserDetailWithAdminData deleteUserDetailWithAdminData;
	
	//delete userid cordspnd adminid 
	private DeleteUserDetailFrCorrespondingAdminId deleteUserDetailFrCorrespondingAdminId;
	
	// Only Chnage the status of newusers table type OPEN to COMPLETE
	private UpdateNew_Users_Status updateNew_Users_Status;
	
	// Delete the override admin record in installr table using user_id
	private DeleteInstallrNewUserByUserId deleteInstallrNewUserByUserId;
	
	// for adding the installr name in to the new user table...,
	private UpdateInstallrNameInNewUserTbl updateInstallrNameInNewUserTbl;
	
	
	private UpdateUser update_user;
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	// find By Id 
	  //findUserById.sql
	 @Value("${findUserById.sql}")
	 private String findUserById;

	@Value("${findUser.sql}")
	private String findUser;
	
	@Value("${update_new_user_json_created.sql}")
	private String update_new_user_json_created_sql;

	@Value("${findRole.sql}")
	private String findRole;
	
	// InstallrDetails data insert query for instll username, passw, tokens
	@Value("${update.installruserinfo.sql}")
	private String insertInstllrUserInfo;
	
	//findAdminandnewuserId.sql
	@Value("${findAdminandnewuserId.sql}")
	private String findAdminandnewuserId;
	
	@Value("${findNewUserDetailsByUserId.sql}")
	private String findNewUserDetailsByUserId;
	
	//delete the userdetailwithadmin
	@Value("${delete.userdetailwithadmin.sql}")
	private String delete_userdetailwithadmin;
	
//	delete.userIdCrspndadminId.sql
	@Value("${delete.userIdCrspndadminId.sql}")
	private String delete_userIdCrspdadminId;
	
	@Value("${insert.user.sql}")
	private String insertUser;
	
	// Need to add the sql for adding the new user Info with admin details to the table
	@Value("${insert.userwithadmin.sql}")
	private String insertUserwithAdminDet;
	
	@Value("${update.userwithadmin.sql}")
	private String updateUserwithAdminDet;
	
	// change the status OPEN to COMPLETE
	@Value("${update.statusChangetoNewUsr.sql}")
	private String changeStatusOpentoComFrNewUser;
	
	// insert the installr name in to the newuser table
	@Value("${update.insertinstallrname.sql}")
	private String insertInstallrNameIntoNewUsertble;
	
	// delete the installrs record by user_id
	@Value("${delete.installrbyuserId.sql}")
	private String deleteinstallrDtabyUserId;
	
	
	@Value("${update.uptStatusNewUser.sql}")
	private String updateNewUSerStatus;
	
	@Value("${update.user_docker_json.sql}")
	private String update_user_docker_json;
	
	@Value("${update.user_docker_json_status.sql}")
	private String update_user_docker_json_status;
	
	@Value("${findAllUsers.sql}")
	private String findAllUsers;

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}
	
	// Findbyuser id 
	   public GpUser findByUserId(long userid){
	    GpUser gpuser =null;
	    System.out.println("userid is" + userid);
	    MapSqlParameterSource sqlParameter = new MapSqlParameterSource();
	    sqlParameter.addValue("userid", userid);
	    GpUserIdRowMapper user_row_Id_Mapper = new GpUserIdRowMapper();
	    List<GpUser> user = this.namedParameterJdbcTemplate.query(findUserById,
	      sqlParameter, user_row_Id_Mapper);
	    System.out.println("Size of user" + user.size());
	    if (user.size() != 0) {
	     System.out.println("User found" + user.get(0).getUsername());
	     System.err.println("User id "+user.get(0).getId());
	     System.err.println("User trole "+user.get(0).getRole());
	     Set<GpAuthority> authorties = getRolesForGpUser(user.get(0));
	     user.get(0).setRoles(authorties);
	     gpuser = user.get(0);
	    } else {
	     log.debug("No roles found for this user" + user);
	     // returning set as null
	     //user.get(0).setRoles(null);
	    }
	    return gpuser;
	   }

	// Adding the Installr details -> installr_user_name , installr_password ,installrToken
		// Using InsertDto_InstallrDetails dto save the datas in to installr_user_config table
		public int inserttheInstallrInfo(InsertDto_InstallrDetails insertDto_InstallrDetails){
			System.out.println("data of installer "+insertDto_InstallrDetails.toString());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", insertDto_InstallrDetails.getId());
			paramMap.put("installr_user_name", insertDto_InstallrDetails.getInstallr_user_name());
			paramMap.put("installr_password", insertDto_InstallrDetails.getInstallr_password());		
			paramMap.put("installrToken", insertDto_InstallrDetails.getInstallrToken());
			KeyHolder keyHolder = new GeneratedKeyHolder();
			InstallrInsertUserInfo.SQL_INSERT_INSTALLR_USER_INFO = insertInstllrUserInfo;
			this.installrInsertUserInfo = new InstallrInsertUserInfo(dataSource);
			int entrornt = this.installrInsertUserInfo.updateByNamedParam(paramMap, keyHolder);
			return entrornt;
		}   
	   
	   
	   
	@Override
	public GpUser findUser(String userName) {
		GpUser gpuser =null;
		
		System.out.println("UserName is" + userName);
		MapSqlParameterSource sqlParameter = new MapSqlParameterSource();
		sqlParameter.addValue("userName", userName);
		GpUserRowMapper user_row_Mapper = new GpUserRowMapper();
		List<GpUser> user = this.namedParameterJdbcTemplate.query(findUser,
				sqlParameter, user_row_Mapper);
		System.out.println("Size of user" + user.size());
		if (user.size() != 0) {
			System.out.println("User found" + user.get(0).getUsername());
			Set<GpAuthority> authorties = getRolesForGpUser(user.get(0));
			user.get(0).setRoles(authorties);
			gpuser = user.get(0);
		} else {
			log.debug("No roles found for this user" + user);
			// returning set as null
			//user.get(0).setRoles(null);
		}
		return gpuser;
	}

	@Override
	public Set<GpAuthority> getRolesForGpUser(GpUser user) {
		MapSqlParameterSource sqlParameter = new MapSqlParameterSource();
		sqlParameter.addValue("userId", user.getId());
		GpAuthorityRowMapper authority_row_Mapper = new GpAuthorityRowMapper();
		List<GpAuthority> authorities = this.namedParameterJdbcTemplate.query(
				findRole, sqlParameter, authority_row_Mapper);
		HashSet<GpAuthority> auth = new HashSet<GpAuthority>(authorities);
		System.out.println("Role" + auth.size());
		return auth;
	}

	
	
	@Override
	public List<GpUser> findAllUsers(){
		MapSqlParameterSource sqlParameter = new MapSqlParameterSource();
		GpUserRowMapper authority_row_Mapper = new GpUserRowMapper();
		List<GpUser> users = this.namedParameterJdbcTemplate.query(
				findAllUsers, sqlParameter, authority_row_Mapper);
		
		for(int i=0;i<users.size();i++)
		{
			 Set<GpAuthority> authorties = getRolesForGpUser(users.get(i));
		     users.get(i).setRoles(authorties);
			System.err.println("-- > users - > "+users.get(i).toString().toString());
		}
		return users;
	}
	@Override
	public GpUser insert(GpUser gpUser) throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", gpUser.getUsername());
		paramMap.put("password", gpUser.getPassword());
		paramMap.put("newuser", true);
		paramMap.put("startdate", new Date());
		paramMap.put("languagepreference", gpUser.getLanguagepreference());
		paramMap.put("licenseid", gpUser.getLicenseid());
		paramMap.put("lastaccess", gpUser.getLastaccess());
		paramMap.put("mustresetpassword", gpUser.getMustresetpassword());
		paramMap.put("accesstype", gpUser.getAccesstype());
		
		paramMap.put("mac_config_id", gpUser.getMac_config_id());
		paramMap.put("installr_user_config_id", gpUser.getInstallr_user_config_id());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertUser.SQL_INSERT_USER = insertUser;
		this.insert_user = new InsertUser(dataSource);
		this.insert_user.updateByNamedParam(paramMap, keyHolder);
		gpUser.setId(keyHolder.getKey().longValue());
		return gpUser;

	}
	
	// New Insert Admin User
	// This method used to Store the Information of allocate the new user i for particular admin
	public void storeIfo_Under_Admin(int loggedAdminId, int selectedUserIdIngrid){
		System.err.println("coming in storeIfo_Under_Admin");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("adminid", loggedAdminId);
		paramMap.put("newuserid", selectedUserIdIngrid);
		paramMap.put("created_on", new Date());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertNewUserWithAdmin.SQL_INSERT_NEW_USER_WITH_ADMIN = insertUserwithAdminDet;
		this.insertNewUserWithAdmin = new InsertNewUserWithAdmin(dataSource);
		this.insertNewUserWithAdmin.updateByNamedParam(paramMap, keyHolder);
		
	}
	
	// Delete the Installrs data of the selected userId by Admin
	public void delete_the_Installr_forselected_UserId(int selectedUserIdByAdminDltDtaInstllr){
		System.out.println("Comin Insidde the delete_the_Installr_forselected_UserId()");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", selectedUserIdByAdminDltDtaInstllr);
		DeleteInstallrNewUserByUserId.SQL_DELETE_NEW_USER_DATA_BYUSER_ID = deleteinstallrDtabyUserId;
		this.deleteInstallrNewUserByUserId = new DeleteInstallrNewUserByUserId(dataSource);
		this.deleteInstallrNewUserByUserId.updateByNamedParam(paramMap);
	}
	
	
	
	// method to change the status onlu in new_users table 
	public int new_users_table_change_status(int selectedId, int currentLoggedAdminId){
		System.out.println("Change the status for only in new_users table"+selectedId);
		String locked = "unlock";
		String isEditable = "false";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", selectedId);
		paramMap.put("lockorunlock",locked);
		paramMap.put("isEditable",isEditable);
		paramMap.put("adminid",currentLoggedAdminId);
		UpdateNewUserStatusWithIseditable.SQL_UPDATE_NEW_USER_STATUS_LOCK_UNLOCK = updateNewUSerStatus;
		this.updateNewUserStatusWithIseditable = new UpdateNewUserStatusWithIseditable(this.dataSource);
		int i = this.updateNewUserStatusWithIseditable.updateByNamedParam(paramMap);
		return i;
	}
	
	// Delete the column  data because the unlock condition 
	public void delete_The_records_while_unlock_Status(List<GpUserDetailWithAdmin> retrievedByAdmId){
		System.out.println("Inside the delete_The_records_while_unlock_Status()");
		for(int a=0;a<retrievedByAdmId.size();a++){
//			retrievedByAdmId.get(a).getId();
			System.out.println("retrievedByAdmId ID"+retrievedByAdmId.get(a).getId());
			System.out.println("retrievedByAdmId.get(a).getAdminId()"+retrievedByAdmId.get(a).getAdminid());
			System.out.println("retrievedByAdmId.get(a).getNewUserId()"+retrievedByAdmId.get(a).getNewuserid());
			
//			System.out.println("deleting verbs for screen_id = " + screen_id);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", retrievedByAdmId.get(a).getId());

			DeleteUserDetailWithAdminData.SQL_DELETE_USERDETAILWITHADMIN_DATA = delete_userdetailwithadmin;
			this.deleteUserDetailWithAdminData = new DeleteUserDetailWithAdminData(dataSource);
			this.deleteUserDetailWithAdminData.updateByNamedParam(paramMap);

		}
	}
	
	// For deleting the admin and new user id while overrride happen
	public int  delete_The_records_while_override_happen(int newUserId , int adminUserid){
		System.out.println("Inside the delete_The_records_while_unlock_Status()");
//		for(int a=0;a<retrievedByAdmId.size();a++){
//			retrievedByAdmId.get(a).getId();
			System.out.println("newUserId ID"+newUserId);
			System.out.println("adminUserid"+adminUserid);
//			System.out.println("retrievedByAdmId.get(a).getNewUserId()"+retrievedByAdmId.get(a).getNewuserid());
			
//			System.out.println("deleting verbs for screen_id = " + screen_id);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("newuserid", newUserId);
			paramMap.put("adminid", adminUserid);
			DeleteUserDetailFrCorrespondingAdminId.SQL_DELETE_USERDETAILWITH_CORSPOND_ADMIN_ID = delete_userIdCrspdadminId;
			this.deleteUserDetailFrCorrespondingAdminId = new DeleteUserDetailFrCorrespondingAdminId(dataSource);
			int dltrnt = this.deleteUserDetailFrCorrespondingAdminId.updateByNamedParam(paramMap);
			System.err.println("delete comes from the delete_The_records_while_override_happen"+dltrnt);
			return dltrnt;
//		}
	}
	
	
	public int insert_installr_name_into_newuser_tabl(long userId , String installrname){
		System.err.println("Feed the installr data in to new user table");
		System.out.println("user id of installr details "+userId);
		System.out.println("installr name"+installrname);
		SelectStatusOfNewUserDetailsWithInstallrName selectdatabyuserid = new SelectStatusOfNewUserDetailsWithInstallrName();
//		InsertNewUser insertinstallr = new InsertNewUser();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("installr_name", installrname);
		paramMap.put("user_id", userId);
	/*	List<GpNewUser> data = this.namedParameterJdbcTemplate.query(insertInstallrNameIntoNewUsertble,
				paramMap , selectdatabyuserid);*/
		UpdateInstallrNameInNewUserTbl.SQL_UPDATE_INSTALLR_NAME_IN_USER_TABLE = insertInstallrNameIntoNewUsertble;
		this.updateInstallrNameInNewUserTbl = new UpdateInstallrNameInNewUserTbl(this.dataSource);
		int i = this.updateInstallrNameInNewUserTbl.updateByNamedParam(paramMap);
		return i;
	}
	
	public int change_status_OPEN_to_COMPLT(long statusChangeUserId){
		System.out.println("Change the status of OPEN in to COMPELETE"+statusChangeUserId);
		
		SelectStatusOfNewUserDetails selectdatabyuserid = new SelectStatusOfNewUserDetails();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", statusChangeUserId);
		
		List<GpNewUser> data = this.namedParameterJdbcTemplate.query(findNewUserDetailsByUserId,
				paramMap , selectdatabyuserid);
		
		if(data.size() != 0){
			
		} else {
			System.err.println("Not inside any value may error happened");
		}
		
		String typebyUserId = data.get(0).getType();
		String lockorunlock = data.get(0).getLockorunlock();
		System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!lock or unlock data  =>"+lockorunlock);
		System.err.println("*****************typebyUserId please show Me********* "+typebyUserId);
		if(typebyUserId.equals("COMPLETED")){
			System.err.println("completed comes");
			paramMap.put("type", "OPEN");
		}else{
			System.err.println("open coming dat");
			paramMap.put("type", "COMPLETED");
		}
		
		UpdateNew_Users_Status.SQL_UPDATE_NEW_USER_STATUS = changeStatusOpentoComFrNewUser;
		this.updateNew_Users_Status = new UpdateNew_Users_Status(this.dataSource);
		int i = this.updateNew_Users_Status.updateByNamedParam(paramMap);
		return i;
		
	}
	
	// Only mark AS OPEN what ever status 
	public int change_status_OPEN_WHAT_EVER(long statusChangeUserId){
		System.out.println("Change the status of OPEN in to COMPELETE"+statusChangeUserId);
		
		SelectStatusOfNewUserDetails selectdatabyuserid = new SelectStatusOfNewUserDetails();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", statusChangeUserId);
		
		List<GpNewUser> data = this.namedParameterJdbcTemplate.query(findNewUserDetailsByUserId,
				paramMap , selectdatabyuserid);
		
		if(data.size() != 0){
			
		} else {
			System.err.println("Not inside any value may error happened");
		}
		
		String typebyUserId = data.get(0).getType();
		System.err.println("*****************typebyUserId please show Me********* "+typebyUserId);
		if(typebyUserId.equals("COMPLETED") || typebyUserId.equals("OPEN")){
			System.err.println("completed comes what comes change to OPEN");
			paramMap.put("type", "OPEN");
		}
		
		UpdateNew_Users_Status.SQL_UPDATE_NEW_USER_STATUS = changeStatusOpentoComFrNewUser;
		this.updateNew_Users_Status = new UpdateNew_Users_Status(this.dataSource);
		int i = this.updateNew_Users_Status.updateByNamedParam(paramMap);
		return i;
		
	}
	
	
	
	
	// Update the status of new user is lock or unlock
	public int  change_status_lock_or_unlock_newUser(int selectedUserIdIngrid, String adminName, int adminId){
		String locked = "Locked by "+adminName;
		System.err.println("Locked By admin user name for every locked "+locked);
		String isEditable = "true";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", selectedUserIdIngrid);
		paramMap.put("lockorunlock",locked);
		paramMap.put("isEditable",isEditable);
		paramMap.put("adminid",adminId);
		/*UpdateNewUserStatus.SQL_UPDATE_NEW_USER_STATUS_LOCK_UNLOCK = updateUserwithAdminDet;
		this.updateNewUserStatus = new UpdateNewUserStatus(this.dataSource);
		int i = this.updateNewUserStatus.updateByNamedParam(paramMap);
		System.out.println(" i : " + i);
		return i;*/
		UpdateNewUserStatusWithIseditable.SQL_UPDATE_NEW_USER_STATUS_LOCK_UNLOCK = updateNewUSerStatus;
		this.updateNewUserStatusWithIseditable = new UpdateNewUserStatusWithIseditable(this.dataSource);
		int i = this.updateNewUserStatusWithIseditable.updateByNamedParam(paramMap);
		return i;
	}

	// Select the rows using admin and newuserId 
	public List<GpUserDetailWithAdmin> select_rows_using_Adm_NewUsrId(int adminId ,int newUserId){
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("adminid", adminId);
		parameters.addValue("newuserid", newUserId);
		SelectUserDetailWithAdmin selectMapper = new SelectUserDetailWithAdmin();

		return this.namedParameterJdbcTemplate.query(findAdminandnewuserId,
				parameters , selectMapper);
	}
	
	@Override
	public void Update_docker_json(GpUser gpUser) throws Exception {	

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", gpUser.getId());
		paramMap.put("docker_json", gpUser.getDocker_json());		
		UpdateUser.SQL_UPDATE_USER = update_user_docker_json;
		this.update_user = new UpdateUser(this.dataSource);				
		this.update_user.updateByNamedParam(paramMap);		
		MapSqlParameterSource parameters = new MapSqlParameterSource(); 
		parameters.addValue("user_id", gpUser.getId());
		this.namedParameterJdbcTemplate.update(update_new_user_json_created_sql, parameters);
	}

	public void Update_docker_json_status(GpUser gpUser) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", gpUser.getId());
		paramMap.put("docker_json_status", gpUser.getDocker_json_status());		
		UpdateUserDockerStatus.SQL_UPDATE_USER = update_user_docker_json_status;
		UpdateUserDockerStatus update_user = new UpdateUserDockerStatus(this.dataSource);				
		update_user.updateByNamedParam(paramMap);	
	}

}
