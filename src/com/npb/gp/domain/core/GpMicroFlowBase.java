package com.npb.gp.domain.core;

/***
 * @author Rashmi Pandey<br>
 *         
 *
 */
public class GpMicroFlowBase {

	private long base_verb_id;
	private String component_name;
	private String micro_flow_step_name;
	//private String component_description_id;
	private long sequence_id;
	private String base_verb_name;

	public long getBase_verb_id() {
		return base_verb_id;
	}
	public void setBase_verb_id(long base_verb_id) {
		this.base_verb_id = base_verb_id;
	}
	public String getComponent_name() {
		return component_name;
	}
	public void setComponent_name(String component_name) {
		this.component_name = component_name;
	}
	public String getMicro_flow_step_name() {
		return micro_flow_step_name;
	}
	public void setMicro_flow_step_name(String micro_flow_step_name) {
		this.micro_flow_step_name = micro_flow_step_name;
	}
	public long getSequence_id() {
		return sequence_id;
	}
	public void setSequence_id(long sequence_id) {
		this.sequence_id = sequence_id;
	}
	public String getBase_verb_name() {
		return base_verb_name;
	}
	public void setBase_verb_name(String base_verb_name) {
		this.base_verb_name = base_verb_name;
	}

}
