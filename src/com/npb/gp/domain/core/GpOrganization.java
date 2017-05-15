package com.npb.gp.domain.core;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.npb.gp.dao.mysql.support.organization.Base_organization_user_dto;


/**
 * @author Dhinakar Paneer Selvam<br>
 *         Date Created: 03/22/2017<br>
 * @since alpha</p>
 * 
 *        The purpose this class is to encapsulate the concept of a
 *        Organization.</br> A Organization is associated with a user in geppetto </br> software system.</p>
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GpOrganization implements Serializable, UserDetails {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1097288366346795370L;

	/**
	 * 
	 */

	private Long id;
	
	private String organization_name;
	
	private Long user_id;
	
	private Date createdate;
	private long createdby; 
	private Date lastmodifieddate; 
	private long lastmodifiedby; 
	private String about;
	private String contact_phone;
	//dhina
	private String city;
	private String country;
	private long template;
	
	private List<Base_organization_user_dto> members_in_organization;
	
	 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
 

	public String getOrganization_name() {
		return organization_name;
	}

	public void setOrganization_name(String organization_name) {
		this.organization_name = organization_name;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public long getCreatedby() {
		return createdby;
	}

	public void setCreatedby(long createdby) {
		this.createdby = createdby;
	}

	public Date getLastmodifieddate() {
		return lastmodifieddate;
	}

	public void setLastmodifieddate(Date lastmodifieddate) {
		this.lastmodifieddate = lastmodifieddate;
	}

	public long getLastmodifiedby() {
		return lastmodifiedby;
	}

	public void setLastmodifiedby(long lastmodifiedby) {
		this.lastmodifiedby = lastmodifiedby;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
 
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public List<Base_organization_user_dto> getMembers_in_organization() {
		return members_in_organization;
	}

	public void setMembers_in_organization(List<Base_organization_user_dto> members_in_organization) {
		this.members_in_organization = members_in_organization;
	}

	public long getTemplate() {
		return template;
	}

	public void setTemplate(long template) {
		this.template = template;
	}

	@Override
	public String toString() {
		return "GpOrganization [id=" + id + ", organization_name=" + organization_name + ", user_id=" + user_id
				+ ", createdate=" + createdate + ", createdby=" + createdby + ", lastmodifieddate=" + lastmodifieddate
				+ ", lastmodifiedby=" + lastmodifiedby + ", about=" + about + ", contact_phone=" + contact_phone
				+ ", city=" + city + ", country=" + country + ", template=" + template + ", members_in_organization="
				+ members_in_organization + "]";
	}
	
 

 
}
