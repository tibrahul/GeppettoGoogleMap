package com.npb.gp.domain.core;

import java.io.Serializable;

public class GpProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long profileId = 0L;
	private String firstName;
	private String lastName;
	private String middleName;
	private String emailAddress;
	private String primaryEmailAddress;
	private String phoneNumber;
	private GpUser user;

	public GpProfile() {

	}

	public GpProfile(Long profileId, String firstName, String lastName,
			String middleName, String emailAddress, String primaryEmailAddress,
			String phoneNumber, GpUser user) {
		super();
		this.profileId = profileId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.emailAddress = emailAddress;
		this.primaryEmailAddress = primaryEmailAddress;
		this.phoneNumber = phoneNumber;
		this.user = user;
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public GpUser getUser() {
		return user;
	}

	public void setUser(GpUser user) {
		this.user = user;
	}

}
