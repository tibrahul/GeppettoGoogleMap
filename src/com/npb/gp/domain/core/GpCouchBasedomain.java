package com.npb.gp.domain.core;

import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GpCouchBasedomain {
	
	private long id;
	private long project_id;
	private String user_id;
	private String bucket;
	private String password;
	private String design;
	private String views;
	private String attribute;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProject_id() {
		return project_id;
	}
	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getbucket() {
		return bucket;
	}
	public void setbucket(String bucket) {
		this.bucket = bucket;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDesign() {
		return design;
	}
	public void setDesign(String design) {
		this.design = design;
	}
	public String getViews() {
		return views;
	}
	public void setViews(String views) {
		this.views = views;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	@Override
	public String toString() {
		return "GpCouchBasedomain [id=" + id + ", project_id=" + project_id
				+ ", user_id=" + user_id + ", bucket=" + bucket + ", password="
				+ password + ", design=" + design + ", views=" + views
				+ ", attribute=" + attribute + "]";
	}
	
	

}
