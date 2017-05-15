package com.npb.gp.domain.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GpNewUser {

	private int id;
	private int user_id;
	private String username;
	private String processed;
	private String lockorunlock;
	private String isEditable;
	private int adminid;
	private String type;
	private String installr_name;
	
	public String getInstallr_name() {
		return installr_name;
	}
	public void setInstallr_name(String installr_name) {
		this.installr_name = installr_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProcessed() {
		return processed;
	}
	public void setProcessed(String processed) {
		this.processed = processed;
	}
	public String getLockorunlock() {
		return lockorunlock;
	}
	public void setLockorunlock(String lockorunlock) {
		this.lockorunlock = lockorunlock;
	}
	public String getIsEditable() {
		return isEditable;
	}
	public void setIsEditable(String isEditable) {
		this.isEditable = isEditable;
	}
	public int getAdminid() {
		return adminid;
	}
	public void setAdminid(int adminid) {
		this.adminid = adminid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
