package com.npb.gp.domain.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GpUserDetailWithAdmin {

	long id;
	
	private Integer adminid;
	
	private Integer newuserid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getAdminid() {
		return adminid;
	}

	public void setAdminid(Integer adminid) {
		this.adminid = adminid;
	}

	public Integer getNewuserid() {
		return newuserid;
	}

	public void setNewuserid(Integer newuserid) {
		this.newuserid = newuserid;
	}

	@Override
	public String toString() {
		return "GpUserDetailWithAdmin [id=" + id + ", adminid=" + adminid + ", newuserid=" + newuserid + "]";
	}
	
}
