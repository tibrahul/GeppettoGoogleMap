package com.npb.gp.domain.core;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GpMacConfig {

	private long id ;
	private String  ipa_mac_ip_address;
	private String ipa_mac_user_name;
	private String ipa_mac_password;
	private String status;
	private String apple_dev_account;
	private Date creatdedDate;
	private Date updatedDate;
	
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Date getCreatdedDate() {
		return creatdedDate;
	}
	public void setCreatdedDate(Date creatdedDate) {
		this.creatdedDate = creatdedDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIpa_mac_ip_address() {
		return ipa_mac_ip_address;
	}
	public void setIpa_mac_ip_address(String ipa_mac_ip_address) {
		this.ipa_mac_ip_address = ipa_mac_ip_address;
	}
	public String getIpa_mac_user_name() {
		return ipa_mac_user_name;
	}
	public void setIpa_mac_user_name(String ipa_mac_user_name) {
		this.ipa_mac_user_name = ipa_mac_user_name;
	}
	public String getIpa_mac_password() {
		return ipa_mac_password;
	}
	public void setIpa_mac_password(String ipa_mac_password) {
		this.ipa_mac_password = ipa_mac_password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApple_dev_account() {
		return apple_dev_account;
	}
	public void setApple_dev_account(String apple_dev_account) {
		this.apple_dev_account = apple_dev_account;
	}
	
	
}
