package com.npb.gp.domain.core;

/***
 * @author Kumaresan Perumal<br>
 *         Created Date: 29/04/2015<br>
 *         Modified By: Kumaresan Perumal<b><br>
 * 
 *         This class is used to support the data from flow_control
 *
 */
public class GpFlowControlBase {

	private String component_type;
	private String label;
	private String description;
	private String type;
	private long sequence_id;
	private long sub_sequence_id;

	public String getComponent_type() {
		return component_type;
	}

	public void setComponent_type(String component_type) {
		this.component_type = component_type;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSequence_id() {
		return sequence_id;
	}

	public void setSequence_id(long sequence_id) {
		this.sequence_id = sequence_id;
	}

	public long getSub_sequence_id() {
		return sub_sequence_id;
	}

	public void setSub_sequence_id(long sub_sequence_id) {
		this.sub_sequence_id = sub_sequence_id;
	}
}
