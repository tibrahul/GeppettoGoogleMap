package com.npb.gp.domain.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Kumaresan
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GpMenuScreenDetail {
	private long id;
	private String name;
	private String label;
	private String description;
	// SureshAnand added the role in GpMenuScreenDetail
	private String role;
	private long auth_id;
	private long project_id;
	private long activity_id;

	/**
	 * private long screen_id; private String menu_tree; private ArrayList
	 * <GpMenuDetail> menudetails;
	 * 
	 * public String getMenu_tree() { return menu_tree; } public void
	 * setMenu_tree(String menu_tree) { this.menu_tree = menu_tree; }
	 * 
	 * public ArrayList<GpMenuDetail> getMenudetails() { return menudetails; }
	 * public void setMenudetails(ArrayList<GpMenuDetail> menudetails) {
	 * this.menudetails = menudetails; }
	 */

	public long getProject_id() {
		return project_id;
	}

	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}

	public long getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(long activity_id) {
		this.activity_id = activity_id;
	}

	public long getId() {
		return id;
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
	public String getRole() {
		 		return role;
		 	}
		 
		 	public void setRole(String role) {
		 		this.role = role;
		 	}
	/**
	 * public long getScreen_id() { return screen_id; } public void
	 * setScreen_id(long screen_id) { this.screen_id = screen_id; }
	 */
}