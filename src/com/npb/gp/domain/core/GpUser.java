package com.npb.gp.domain.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Dan Castillo
 * @since Januaray 2010</p>
 * 
 * 
 *        The purpose of this class serve as the OO representation for a user in
 *        the system</p>
 *
 *        Date Updated: 02/02/2011</br> Updated By: Daniel Castillo</p>
 * 
 *        added the "languagepreferencename" field to hold the long name for a
 *        language this is a bit of a hack as did not want to do looks up in the
 *        language table when I needed the long name for a language </p>
 *
 *        Modified Date: 09/07/2012</br> Modified By: Dan Castillo </p>
 * 
 *        started to use it in version .35 switched to using annotations and JPA
 *        </p>
 *
 *        Modified Date: 09/14/2012</br> Modified By: Dan Castillo</p>
 * 
 *        added newuser boolean </p>
 *
 *        Gpuser class modified.
 *
 *        Profile class created to hold user profile details
 */
/*
 * @Entity
 * 
 * @Table(name = "users")
 * 
 * @NamedQuery(name="Users.findAll", query="select c from User c")
 * 
 * @SqlResultSetMapping( name="userResult",
 * entities=@EntityResult(entityClass=GpUser.class) )
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GpUser implements Serializable, UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1160102300273815295L;

	public GpUser() {

	}

	private Long id = 0L;
	private String username;
	private String password;
	private String languagepreference;
	private String screenname; // used for display only
	private Date startdate;
	private String licenseid;
	private Date lastaccess;
	private String mustresetpassword;
	private String accesstype; // used to disable access
	private Set<GpAuthority> roles;
	private boolean newuser; // typically used for registration
	private String docker_json;
	private String docker_json_status;
	
	private Long mac_config_id; 
	private Long installr_user_config_id;

	//Role set 
   private String role;
   
   public String getRole() {
    return role;
   }

   public void setRole(String role) {
    this.role = role;
   }


	public Long getMac_config_id() {
		return mac_config_id;
	}

	public void setMac_config_id(Long mac_config_id) {
		this.mac_config_id = mac_config_id;
	}

	public Long getInstallr_user_config_id() {
		return installr_user_config_id;
	}

	public void setInstallr_user_config_id(Long installr_user_config_id) {
		this.installr_user_config_id = installr_user_config_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<GpAuthority> getRoles() {
		return roles;
	}

	public void setRoles(Set<GpAuthority> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLanguagepreference() {
		return languagepreference;
	}

	public void setLanguagepreference(String languagepreference) {
		this.languagepreference = languagepreference;
	}

	public String getScreenname() {
		return screenname;
	}

	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getLicenseid() {
		return licenseid;
	}

	public void setLicenseid(String licenseid) {
		this.licenseid = licenseid;
	}

	public Date getLastaccess() {
		return lastaccess;
	}

	public void setLastaccess(Date lastaccess) {
		this.lastaccess = lastaccess;
	}

	public String getMustresetpassword() {
		return mustresetpassword;
	}

	public void setMustresetpassword(String mustresetpassword) {
		this.mustresetpassword = mustresetpassword;
	}

	public String getAccesstype() {
		return accesstype;
	}

	public void setAccesstype(String accestype) {
		this.accesstype = accestype;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		for (GpAuthority role : roles) {
			System.out.println("The roles is: " + role.getAuthority());
			list.add(new SimpleGrantedAuthority(role.getAuthority()));
		}

		return list;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isNewuser() {
		return newuser;
	}

	public void setNewuser(boolean newuser) {
		this.newuser = newuser;
	}
	
	public String getDocker_json() {
		return docker_json;
	}

	public void setDocker_json(String docker_json) {
		this.docker_json = docker_json;
	}
	
	public String getDocker_json_status() {
		return docker_json_status;
	}
	public void setDocker_json_status(String docker_json_status) {
		this.docker_json_status = docker_json_status;
	}

}
