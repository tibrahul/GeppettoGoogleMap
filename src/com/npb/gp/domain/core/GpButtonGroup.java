package com.npb.gp.domain.core;

public class GpButtonGroup {

	long id;
	String name;
	long screenId;
	String type;
	String data_binding_context;
	long noun_id;
	long noun_attribute_id;
	
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

	public long getScreenId() {
		return screenId;
	}

	public void setScreenId(long l) {
		this.screenId = l;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData_binding_context() {
		return data_binding_context;
	}

	public void setData_binding_context(String data_binding_context) {
		this.data_binding_context = data_binding_context;
	}

	public long getNoun_id() {
		return noun_id;
	}

	public void setNoun_id(long noun_id) {
		this.noun_id = noun_id;
	}

	public long getNoun_attribute_id() {
		return noun_attribute_id;
	}

	public void setNoun_attribute_id(long noun_attribute_id) {
		this.noun_attribute_id = noun_attribute_id;
	}
	
}