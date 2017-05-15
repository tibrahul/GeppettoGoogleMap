package com.npb.gp.dao.mysql.support.organization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Base_organization_user_dto {

	private Long id;
	
	private Long user_id;
	
	private Long base_organization_id;

	public Long getBase_organization_id() {
		return base_organization_id;
	}

	public void setBase_organization_id(Long base_organization_id) {
		this.base_organization_id = base_organization_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "Base_organization_user_dto [id=" + id + ", user_id=" + user_id + "]";
	}
	
}
