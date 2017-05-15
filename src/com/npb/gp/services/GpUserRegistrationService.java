package com.npb.gp.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpAuthorityDao;
import com.npb.gp.dao.mysql.GpNewUsersDao;
import com.npb.gp.dao.mysql.GpUserDao;
import com.npb.gp.domain.core.GpAuthority;
import com.npb.gp.domain.core.GpInstallrConfig;
import com.npb.gp.domain.core.GpMacConfig;
import com.npb.gp.domain.core.GpUser;

/**
 * Date Created: September 22 2012
 * 
 * @author Dan Castillo
 * 
 *         The purpose of this class is to provide the business logic needed to
 *         register a new user to the system
 * 
 * 
 * 
 */
@Service("GpUserRegistrationService")
public class GpUserRegistrationService extends GpBaseService{

	GpUserDao user_dao;

	
	public GpUserDao getUser_dao() {
		return user_dao;
	}

	@Resource(name = "GpUserDao")
	public void setUser_dao(GpUserDao user_dao) {
		this.user_dao = user_dao;
	}
	
	GpNewUsersDao newuser_dao;
	
	public GpNewUsersDao getNewuser_dao() {
		return newuser_dao;
	}
	
	@Resource(name = "GpNewUsersDao")
	public void setNewuser_dao(GpNewUsersDao newuser_dao) {
		this.newuser_dao = newuser_dao;
	}

	/**
	 * Registers a new user to the system - the method will enforce any required
	 * fields on the User class
	 * 
	 * @param newuser
	 *            {@link com.npb.gp.domain.core.GpUser}
	 * @return com.npb.gp.domain.core.GpUser - with ID populated
	 * @throws Exception
	 * 
	 * 
	 */

	public GpUser registerUser(GpUser newuser) throws Exception {
		try {
			System.out
					.println("In  GpUserRegistrationService - registerUser()");

		} catch (Exception e) {
			logger.error("error occured in GpUserRegistrationService"
					+ " registerUser method()");
			logger.error("the user id is: " + newuser.getId()
					+ " the error is:");

			e.printStackTrace();
			Locale lc = new Locale(newuser.getLanguagepreference());
			throw new Exception(ctx.getMessage(
					"GpUserRegistrationService.error.unknown", null, lc));
		}

		return null;

	}

	/**
	 * Registers a new user to the system - the method only requires that a user
	 * supply an email and password
	 * 
	 * @param newuser
	 *            - type: com.npb.gp.domain.core.GpUser
	 * @return com.npb.gp.domain.core.GpUser - with ID populated
	 * @throws Exception
	 */
	public GpUser lightRegisterUser(GpUser newuser, GpUserDao obj,GpAuthorityDao authorityDao) throws Exception {
		GpUser newgpuser=null;
		
		try {
			System.out
					.println("In  GpUserRegistrationService - lightRegisterUser()");

			Date saltdate = new Date();
			String thesalt = new Long(saltdate.getTime()).toString();// save
																		// this
																		// to
																		// the
																		// DB

			System.out
					.println("In  GpUserRegistrationService - lightRegisterUser()"
							+ " thesalt is: " + thesalt);

			ShaPasswordEncoder enc = new ShaPasswordEncoder(256); // 256 s/b
																	// configurable
			/*String encrypted = enc.encodePassword(newuser.getPassword(),
					thesalt); // save this to DB

			System.out
					.println("In  GpUserRegistrationService - lightRegisterUser()"

							+ " the encrypted password is: " + encrypted);*/

			newuser.setPassword(newuser.getPassword());
			if(user_dao == null){
				user_dao = obj;
			}
			//GpUser olduser = this.user_dao.findUser(newuser.getUsername());
			//if(olduser==null){
				//newgpuser = this.user_dao.insert(newuser);
			//}else{
				//return new GpUser();
			//}
			//this.newuser_dao.insert(newgpuser);
			
			//Keep this As Off Now
			List<GpMacConfig> listOfMac=this.newuser_dao.getMacList();
			if (listOfMac.size() <= 0) {
				System.err.println("No Mac is Present for allocation \n No Mac machine Found For Allocation..!!!Please contact your Service Provider..!");
			}else{
				newgpuser = this.user_dao.insert(newuser);
				this.newuser_dao.insert(newgpuser);
				
				for(int i=0;i<listOfMac.size();i++){
					boolean testNow=this.newuser_dao.getInstallrList(newgpuser,listOfMac.get(i));
					if(testNow == true){
						System.out.println("Mac machine Allocated for the New User");
						break;
					}else{
						System.out.println("No Mac machine Found For Allocation..!!!Please contact your Service Provider");
						break;
					}
				}
			//}
		
			
			// need to check whether mac is avail SLECT * FROM MAC TABLE WHERE status="Not_Full"
			// list[1]--> select * from installr table where mac_id=1
			// if length >= 10 ?then move to next :just allocate the mac ti user
			
			
			Set<GpAuthority> authorities = new HashSet<GpAuthority>();
			if(newgpuser != null && newgpuser.getId() != null && newgpuser.getId() > 0){
				GpAuthority authority = new GpAuthority();
				authority.setUserId(newgpuser.getId());
				authority.setAuthority("ROLE_USER");
				authorityDao.insert(authority);
				authorities.add(authority);
				newgpuser.setRoles(authorities);
			}
			// authorities.add(new SimpleGrantedAuthority("ROLE_USER"));		
			// Create a UsernamePasswordAuthenticationToken
			Authentication authentication = new UsernamePasswordAuthenticationToken(newgpuser, null);
			//Add the Authentication to the SecurityContext
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			}

		} catch (Exception e) {
			logger.error("error occured in GpUserRegistrationService"
					+ " lightRegisterUser method()");
			logger.error("the user id is: " + newuser.getId()
					+ " the error is:");
			e.printStackTrace();
			Locale lc = new Locale(newuser.getLanguagepreference());
			throw new Exception(ctx.getMessage(
					"GpUserRegistrationService.error.unknown", null, lc));
		}
		// save in DB
		return newgpuser;
		
	}

}
