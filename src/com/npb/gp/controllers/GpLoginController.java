package com.npb.gp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gb.utils.GpUserUtils;
import com.npb.gp.dao.mysql.GpUserDao;
import com.npb.gp.dao.mysql.support.user.InsertDto_InstallrDetails;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpUserDetailWithAdmin;

@Controller("GpLoginController")
@RequestMapping("/login")
public class GpLoginController {
	
	 private GpUserDao user_dao;
	 String roleByUserId = "";

	 public GpUserDao getUserDao() {
	  return user_dao;
	 }

	 @Resource(name = "GpUserDao")
	 public void setNoun_service(GpUserDao user_dao) {
	  this.user_dao = user_dao;
	 }

	@RequestMapping(value = "/loginSuccess/", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> success() {
		System.out.println("Login success controller called");
		Map<String, Object> response = new HashMap<String, Object>();
		System.out.println("Login success controller called");
		System.out.println("*********UserName*******" + GpUserUtils.getLoggedUser().getUsername());
		System.out.println("*********UserId*********" + GpUserUtils.getLoggedUser().getId());
		response.put("status", "success");
		response.put("username", GpUserUtils.getLoggedUser().getUsername());
		response.put("userid", GpUserUtils.getLoggedUser().getId());
		//	For Getting the static role from users table
	    long userId  = GpUserUtils.getLoggedUser().getId();
	    System.out.println("id for the current Logged in user Id =>"+userId);
	    String userNm = GpUserUtils.getLoggedUser().getUsername();
	    System.err.println("userNm");
	    GpUser rolePerId = user_dao.findByUserId(userId);
	    if(rolePerId != null){
	      roleByUserId = rolePerId.getRole(); 
	      System.err.println("role by user "+roleByUserId);
	    }
	    response.put("role",roleByUserId);
		return response;
	}
	@RequestMapping(value = "/loginFailure/", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> failure() {
		System.out.println("Login failure controller called");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", "failed");
		return response;
	}

	@RequestMapping(value = "/accessDenied/", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> accessDenied() {
		System.out.println("Access denied controller called");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", "Access Denied");
		return response;
	}

	@RequestMapping(value = "/noUserPrincipalFound/", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> sessionNotFound() {
		System.out.println("No user principal controller called");
		System.out.println("No principal found. Redirect the page to login");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", "No User principal Found. Redirect to login");
		return response;
	}

	@RequestMapping(value = "/logout/", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> logout() {
		System.out.println("Logged out controller called");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", "logged out");
		return response;
	}
	
	@RequestMapping(value = "/allocateNewUsrUnderAdmin/{loggedAdminId}/{selectedUserIdIngrid}/{adminName}", method = RequestMethod.GET)
	public @ResponseBody boolean allocateNewUser_under_someAdmins(@PathVariable(value="loggedAdminId") int loggedAdminId,@PathVariable(value="selectedUserIdIngrid") int selectedUserIdIngrid,@PathVariable(value="adminName") String adminName) {
		System.err.println("Coming currently Or Not In java side");
		System.err.println("User Id from java"+loggedAdminId);
		System.err.println("New User Id form Java "+selectedUserIdIngrid);
		System.err.println("adminName =>"+adminName);
		// trRfa = 0 (false) or trRfa = 1 (true)
		int trRfa = user_dao.change_status_lock_or_unlock_newUser(selectedUserIdIngrid,adminName,loggedAdminId);
		
		if(trRfa == 0){
			// Check the admin can remove the lock or not 
			// pass both id and query it to another table to view or not
			List<GpUserDetailWithAdmin> data = user_dao.select_rows_using_Adm_NewUsrId(loggedAdminId, selectedUserIdIngrid);
			System.out.println("data of List<GpUserDetailWithAdmin>"+data.toString());
			if(data.size() != 0){
				// return true bcz he has access to unlock that in UI
				return true;
			}else{
				System.out.println("Its zero or empty cant have access ");
				return false;
			}
//			return 0;
		}
		if(trRfa == 1){
			// Admin
			user_dao.storeIfo_Under_Admin(loggedAdminId,selectedUserIdIngrid);
			return true;
		}
		return false;
	}
	
	// validatepersonlockorunlock () returns true or false if the lockorunlock column has 
	// word he is lock or unlock like 
	@RequestMapping(value = "/validatepersonlockorunlock/{loggedAdminId}/{selectedUserIdIngrid}", method = RequestMethod.GET)
	public @ResponseBody boolean  validatepersonlockorunlock(@PathVariable(value="loggedAdminId") int loggedAdminId,@PathVariable(value="selectedUserIdIngrid") int selectedUserIdIngrid) {
		System.err.println("validatepersonlockorunlock Id form Java "+selectedUserIdIngrid);
		
		List<GpUserDetailWithAdmin> data = user_dao.select_rows_using_Adm_NewUsrId(loggedAdminId, selectedUserIdIngrid);
		System.out.println("data of List<GpUserDetailWithAdmin>"+data.toString());
		if(data.size() != 0){
			// return true bcz he has access to unlock that in UI
			// selected user id on grid use change the status in button and enable the update details buttn else err msgs 
			// need a method to change the status using this id selectedUserIdIngrid in new_users table
			int dt = user_dao.new_users_table_change_status(selectedUserIdIngrid,loggedAdminId);
			if(dt == 1){
				
				// Delete that ID if its there makes some trouble
				user_dao.delete_The_records_while_unlock_Status(data);
				return true;
			} else {
				return false;	
			}
		}else{
			System.out.println("Its zero or empty cant have access ");
			return false;
		}
		
	}
	
	@RequestMapping(value = "/store_the_Installr_details", method = RequestMethod.POST)
	public @ResponseBody boolean store_the_installr_details(@RequestBody InsertDto_InstallrDetails insertDto_InstallrDetails){
		System.out.println("Inside the store_the_installr_details");
		InsertDto_InstallrDetails insertVal = new InsertDto_InstallrDetails();
		insertVal.setId(insertDto_InstallrDetails.getId());
		insertVal.setInstallr_password(insertDto_InstallrDetails.getInstallr_password());
		insertVal.setInstallr_user_name(insertDto_InstallrDetails.getInstallr_user_name());
		insertVal.setInstallrToken(insertDto_InstallrDetails.getInstallrToken());
		int dst = user_dao.inserttheInstallrInfo(insertVal);
		if(dst == 0){
			return false;
		} else {
			// Change the Status in the type column  Of new_users table
			// column name type in new_users table
			long currentUserIdNeedToStatus  = insertVal.getId();
			System.err.println("currentUserIdNeedToStatus ID =>"+currentUserIdNeedToStatus);
			int statusUpdated = user_dao.change_status_OPEN_to_COMPLT(currentUserIdNeedToStatus);
			// here we need to add the installr name  in to the new_users table ...,
			// InsertNewUser
			String installrname = insertDto_InstallrDetails.getInstallr_user_name();
			long userid = insertDto_InstallrDetails.getId();
			int installr_name = user_dao.insert_installr_name_into_newuser_tabl(userid , installrname);
			System.err.println("Inserted or Not => ****************************************************************"+installr_name);
			if(statusUpdated != 0 && installr_name !=0){
				return true;
			}
			return false;
		}
	}
	
	@RequestMapping(value = "/deleteUserIdCrpndAdminId/{newUserId}/{assignedadminIdFrNwUsr}/{currntloggedAdminId}/{fullname}/{selectedUserId}", method = RequestMethod.GET)
	public @ResponseBody boolean deleteNewUserWithAdminCredinTable(@PathVariable(value="newUserId") int newUserId,@PathVariable(value="assignedadminIdFrNwUsr") int assignedadminIdFrNwUsr,
			@PathVariable(value="currntloggedAdminId") int currntloggedAdminId,@PathVariable(value="fullname") String fullname,@PathVariable(value="selectedUserId") int selectedUserId){
		System.out.println("newUserId =>"+newUserId);
		System.out.println("assignedadminIdFrNwUsr =>"+assignedadminIdFrNwUsr);
		System.out.println("currntloggedAdminId =>"+currntloggedAdminId);
		System.out.println("fullname =>"+fullname);
		System.out.println("selectedUserId =>"+selectedUserId);
		
		int dltrnot = user_dao.delete_The_records_while_override_happen(newUserId, assignedadminIdFrNwUsr);
		
		if(dltrnot == 0){
			System.err.println("damn srures not coming");
		} else {
			int trRfa = user_dao.change_status_lock_or_unlock_newUser(newUserId,fullname,currntloggedAdminId);
			
			if(trRfa == 0){
				// Check the admin can remove the lock or not 
				// pass both id and query it to another table to view or not
				List<GpUserDetailWithAdmin> data = user_dao.select_rows_using_Adm_NewUsrId(currntloggedAdminId, newUserId);
				System.out.println("data of List<GpUserDetailWithAdmin>"+data.toString());
				if(data.size() != 0){
					// return true bcz he has access to unlock that in UI
					return true;
				}else{
					System.out.println("Its zero or empty cant have access ");
					return false;
				}
//				return 0;
			}
			if(trRfa == 1){
				// For deleting the installr's data of the userId as well as the Below storeIfo_Under_Admin() can reassigned the adminfor the Selected 
				// new user 
				//	user_dao.delete_the_Installr_forselected_UserId(user_id);
				
				//THis method does a opration like when the  already completed user may occur any probs they need to be overide all the data as well
				// as we need to assign the that selected user to him ,also we need to change the status like if already has a status COMPLETED change to OPEN...its used
				user_dao.change_status_OPEN_WHAT_EVER(selectedUserId);
				
				// Reassigned the Admin for the selected user
				System.err.println("vcom ing here damnn sure");
				user_dao.storeIfo_Under_Admin(currntloggedAdminId,newUserId);
				return true;
			}
		}
		return false;
	}
	
}