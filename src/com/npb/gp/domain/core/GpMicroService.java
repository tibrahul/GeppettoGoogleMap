package com.npb.gp.domain.core;
import java.util.ArrayList;

public class GpMicroService {
	private Long id;
	private String microservice_name;
	private Long project_id;
	private String activities_json;
	private ArrayList<GpActivity> activities = new ArrayList<>();
	private String notes;
	private String description;
	private Long activity_id;
	private String activity_name;
	
	public String getMicroservice_name() {
		return microservice_name;
	}
	public void setMicroservice_name(String microservice_name) {
		this.microservice_name = microservice_name;
	}
	
	public String getActivities_json() {
		return activities_json;
	}
	public void setActivities_json(String activities_json) {
		this.activities_json = activities_json;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProject_id() {
		return project_id;
	}
	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}
	public ArrayList<GpActivity> getActivities() {
		return activities;
	}
	public void setActivities(ArrayList<GpActivity> activities) {
		this.activities = activities;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(Long activity_id) {
		this.activity_id = activity_id;
	}
	public String getActivity_name() {
		return activity_name;
	}
	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
}
