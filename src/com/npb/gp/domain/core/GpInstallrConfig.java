package com.npb.gp.domain.core;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GpInstallrConfig {

	private long id ;
	private String installr_user_name;
	private String installr_password;
	private long user_id;
	private long mac_id;
	private String mac_ip_address;
	private Date createdDate;
	private Date updatedDate;
	private String installrToken;
	


	public String getInstallrToken() {
		return installrToken;
	}
	public void setInstallrToken(String installrToken) {
		this.installrToken = installrToken;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getInstallr_user_name() {
		return installr_user_name;
	}
	public void setInstallr_user_name(String installr_user_name) {
		this.installr_user_name = installr_user_name;
	}
	public String getInstallr_password() {
		return installr_password;
	}
	public void setInstallr_password(String installr_password) {
		this.installr_password = installr_password;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public long getMac_id() {
		return mac_id;
	}
	public void setMac_id(long mac_id) {
		this.mac_id = mac_id;
	}
	public String getMac_ip_address() {
		return mac_ip_address;
	}
	public void setMac_ip_address(String mac_ip_address) {
		this.mac_ip_address = mac_ip_address;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	@Override
	public String toString() {
		return "GpInstallrConfig [id=" + id + ", installr_user_name=" + installr_user_name + ", installr_password="
				+ installr_password + ", user_id=" + user_id + ", mac_id=" + mac_id + ", mac_ip_address="
				+ mac_ip_address + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", installrToken="
				+ installrToken + "]";
	}
}
