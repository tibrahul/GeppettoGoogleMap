package com.npb.gp.dao.mysql.support.microflow;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpFlowControl;

/***
 * <b>Created Date: 04/09/2015<br>
 * Modified By: Reinaldo Lopez<b><br>
 * 
 * 
 * */
public class FlowControlMapper implements RowMapper<GpFlowControl> {

	public GpFlowControl mapRow(ResultSet rs, int rowNum)
			throws SQLException {

		GpFlowControl flow_control_object = new GpFlowControl();
		flow_control_object.setId(rs.getInt("ID"));
		flow_control_object.setMaster_flow_id(rs.getInt("MASTER_FLOW_ID"));
		flow_control_object.setComponent_type(rs.getString("COMPONENT_TYPE"));
		flow_control_object.setLabel(rs.getString("LABEL"));
		flow_control_object.setDescription(rs.getString("DESCRIPTION"));
		flow_control_object.setType(rs.getString("TYPE"));
		flow_control_object.setSequence_id(rs.getInt("SEQUENCE_ID"));
		flow_control_object.setSub_sequence_id(rs.getInt("SUB_SEQUENCE_ID"));
		flow_control_object.setActivity_id(rs.getInt("ACTIVITY_ID"));			
		return flow_control_object;
	}

}
