package com.npb.gp.dao.mysql.support.activity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpButtonGroup;

public class GpButtonGroupMapper implements RowMapper<GpButtonGroup> {

	public GpButtonGroup mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpButtonGroup the_grp = new GpButtonGroup();

		the_grp.setId(rs.getLong("ID"));
		the_grp.setName(rs.getString("NAME"));
		the_grp.setScreenId(rs.getLong("SCREEN_ID"));
		the_grp.setType(rs.getString("TYPE"));
		the_grp.setData_binding_context(rs.getString("DATA_BINDING_CONTEXT"));
		the_grp.setNoun_attribute_id(rs.getLong("NOUN_ATTRIBUTE_ID"));
		the_grp.setNoun_id(rs.getLong("NOUN_ID"));

		return the_grp;
	}

}
