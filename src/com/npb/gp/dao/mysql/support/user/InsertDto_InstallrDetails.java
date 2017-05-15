package com.npb.gp.dao.mysql.support.user;

/**
 * 
 * @author SureshAnand This InsertDto_InstallrDetails Is used to insert the some
 *         attrbutes in to the installr_user_config table
 */
public class InsertDto_InstallrDetails {

	private long id;
	private String installr_user_name;
	private String installr_password;
	private String installrToken;

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

	public String getInstallrToken() {
		return installrToken;
	}

	public void setInstallrToken(String installrToken) {
		this.installrToken = installrToken;
	}

	@Override
	public String toString() {
		return "InsertDto_InstallrDetails [id=" + id + ", installr_user_name=" + installr_user_name
				+ ", installr_password=" + installr_password + ", installrToken=" + installrToken + "]";
	}

}
