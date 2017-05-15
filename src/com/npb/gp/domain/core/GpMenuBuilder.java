package com.npb.gp.domain.core;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GpMenuBuilder {

	private long id;
	private String name;
	private String label;
	private String description;
	private long auth_id;
	private long project_id;
	// private long activity_id;
	// private long verb_id;
	private List<GpMenuDetail> menu_details;

	public List<GpMenuDetail> getMenu_details() {
		return menu_details;
	}

	public void setMenu_details(List<GpMenuDetail> menu_details) {
		this.menu_details = menu_details;
	}

	public long getId() {
		return id;
	}

	public long getProject_id() {
		return project_id;
	}

	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getAuth_id() {
		return auth_id;
	}

	public void setAuth_id(long auth_id) {
		this.auth_id = auth_id;
	}
}