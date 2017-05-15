package com.npb.gp.domain.core;

public class GpMicroFlow {
	
	private long id;
	private long flow_control_id;
	private String component_type;
	private String description;
	private long master_flow_id;
	private long code_id;
	private long sequence_id;
	private long verb_id;	
	private String action;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getFlow_control_id() {
		return flow_control_id;
	}
	public void setFlow_control_id(long flow_control_id) {
		this.flow_control_id = flow_control_id;
	}
	public String getComponent_type() {
		return component_type;
	}
	public void setComponent_type(String component_type) {
		this.component_type = component_type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getMaster_flow_id() {
		return master_flow_id;
	}
	public void setMaster_flow_id(long master_flow_id) {
		this.master_flow_id = master_flow_id;
	}
	public long getCode_id() {
		return code_id;
	}
	public void setCode_id(long code_id) {
		this.code_id = code_id;
	}
	public long getSequence_id() {
		return sequence_id;
	}
	public void setSequence_id(long sequence_id) {
		this.sequence_id = sequence_id;
	}
	public long getVerb_id() {
		return verb_id;
	}
	public void setVerb_id(long verb_id) {
		this.verb_id = verb_id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}	
	
}
