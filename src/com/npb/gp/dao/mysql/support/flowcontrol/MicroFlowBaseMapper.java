package com.npb.gp.dao.mysql.support.flowcontrol;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpMicroFlowBase;

/**
 * @author Rashmi
 */
public class MicroFlowBaseMapper implements RowMapper<GpMicroFlowBase> {

	public GpMicroFlowBase mapRow(ResultSet rs, int rowNum)
			throws SQLException {

		GpMicroFlowBase micro_flow_base_objects = new GpMicroFlowBase();
		micro_flow_base_objects.setBase_verb_id(rs.getInt("BASE_VERB_ID"));
		micro_flow_base_objects.setComponent_name(rs
				.getString("COMPONENT_NAME"));
		micro_flow_base_objects.setMicro_flow_step_name(rs.getString("MICRO_FLOW_STEP_NAME"));		
		micro_flow_base_objects.setSequence_id(rs.getInt("SEQUENCE_ID"));
		micro_flow_base_objects.setBase_verb_name(rs.getString("BASE_VERB_NAME"));
		
		return micro_flow_base_objects;
	}

}
