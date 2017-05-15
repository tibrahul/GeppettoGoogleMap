package com.npb.gp.dao.mysql.support.flowcontrol;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.npb.gp.domain.core.GpFlowControlBase;

public class FlowControlBaseMapper implements RowMapper<GpFlowControlBase> {

	public GpFlowControlBase mapRow(ResultSet rs, int rowNum)
			throws SQLException {

		GpFlowControlBase flow_control_base_objects = new GpFlowControlBase();
		flow_control_base_objects.setComponent_type(rs
				.getString("COMPONENT_TYPE"));
		flow_control_base_objects.setLabel(rs.getString("LABEL"));
		flow_control_base_objects.setDescription(rs.getString("DESCRIPTION"));
		flow_control_base_objects.setType(rs.getString("TYPE"));
		flow_control_base_objects.setSequence_id(rs.getInt("SEQUENCE_ID"));
		flow_control_base_objects.setSub_sequence_id(rs
				.getInt("SUB_SEQUENCE_ID"));
		return flow_control_base_objects;
	}

}
