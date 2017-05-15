package com.npb.gp.domain.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GpWsdl_With_Attribute {
	
	private long wsdlclassid;
	private long wsdlattributeid;
	private long project_id;
	private long user_id;
	private String Wsdl_name;
	private String class_name;
	private String attribute_name;
	private String attribute_type;
	
	private Long wsdl_id;
	
	
	
	public Long getWsdl_id() {
		return wsdl_id;
	}
	public void setWsdl_id(Long wsdl_id) {
		this.wsdl_id = wsdl_id;
	}
	public long getWsdlattributeid() {
		return wsdlattributeid;
	}
	public void setWsdlattributeid(long wsdlattributeid) {
		this.wsdlattributeid = wsdlattributeid;
	}
	public long getWsdlclassid() {
		return wsdlclassid;
	}
	public void setWsdlclassid(long wsdlclassid) {
		this.wsdlclassid = wsdlclassid;
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
	public String getWsdl_name() {
		return Wsdl_name;
	}
	public void setWsdl_name(String wsdl_name) {
		Wsdl_name = wsdl_name;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getAttribute_name() {
		return attribute_name;
	}
	public void setAttribute_name(String attribute_name) {
		this.attribute_name = attribute_name;
	}
	public String getAttribute_type() {
		return attribute_type;
	}
	public void setAttribute_type(String attribute_type) {
		this.attribute_type = attribute_type;
	}
	@Override
	public String toString() {
		return "GpWsdl_With_Attribute [wsdlclassid=" + wsdlclassid + ", wsdlattributeid=" + wsdlattributeid
				+ ", project_id=" + project_id + ", user_id=" + user_id + ", Wsdl_name=" + Wsdl_name + ", class_name="
				+ class_name + ", attribute_name=" + attribute_name + ", attribute_type=" + attribute_type + "]";
	}
	
	
	
	

}
