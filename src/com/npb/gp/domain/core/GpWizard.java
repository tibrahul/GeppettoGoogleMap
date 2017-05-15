package com.npb.gp.domain.core;

public class GpWizard {

	long id;
	long activity_id;
	String name;
	String description;
	String screenIds;
	String wizard_type;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(long activity_id) {
		this.activity_id = activity_id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getScreenIds() {
		return screenIds;
	}

	public void setScreenIds(String screenIds) {
		this.screenIds = screenIds;
	}

	public String getWizard_type() {
		return wizard_type;
	}

	public void setWizard_type(String wizard_type) {
		this.wizard_type = wizard_type;
	}
	
}