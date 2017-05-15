package com.npb.gp.domain.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GpWSDLNoun {
	
	private long id;
	private long project_id;
	private long user_id;
	private String class_name;
	private String wsdl_name;
	private Long wsdlId;
	
	
	
	public Long getWsdlId() {
		return wsdlId;
	}
	public void setWsdlId(Long wsdlId) {
		this.wsdlId = wsdlId;
	}
	public long getProject_id() {
		return project_id;
	}
	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getWsdl_name() {
		return wsdl_name;
	}
	public void setWsdl_name(String wsdl_name) {
		this.wsdl_name = wsdl_name;
	}
	@Override
	public String toString() {
		return "GpWSDLNoun [id=" + id + ", project_id=" + project_id + ", user_id=" + user_id + ", class_name="
				+ class_name + ", wsdl_name=" + wsdl_name + ", wsdlId=" + wsdlId + "]";
	}
}
