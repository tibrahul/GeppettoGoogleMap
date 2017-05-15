package com.npb.gp.domain.core;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author KUMARESAN
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GpMenuDetail {

	private long id;
	// private long menu_master_id;
	private String name;
	private String label;
	private String description;
	private long auth_id;
	private long project_id;
	private long activity_id;
	// private long screen_id;
	private String menu_tree;
	private List<GpMenuScreenDetail> screen_detail;

	public List<GpMenuScreenDetail> getScreen_detail() {
		return screen_detail;
	}

	public void setScreen_detail(List<GpMenuScreenDetail> screen_detail) {
		this.screen_detail = screen_detail;
	}

	public long getProject_id() {
		return project_id;
	}

	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}

	/**
	 * public long getScreen_id() { return screen_id; } public void
	 * setScreen_id(long screen_id) { this.screen_id = screen_id; }
	 */
	public String getMenu_tree() {
		return menu_tree;
	}

	public void setMenu_tree(String menu_tree) {
		this.menu_tree = menu_tree;
	}

	public long getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(long activity_id) {
		this.activity_id = activity_id;
	}

	public long getAuth_id() {
		return auth_id;
	}

	public void setAuth_id(long auth_id) {
		this.auth_id = auth_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * public long getMenu_master_id() { return menu_master_id; } public void
	 * setMenu_master_id(long menu_master_id) { this.menu_master_id =
	 * menu_master_id; }
	 */
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
}