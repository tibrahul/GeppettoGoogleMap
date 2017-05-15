package com.npb.gp.dao.mysql.support.noun;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttributeType;

public class Noun_base_attr_type_mapper implements RowMapper<GpNounAttributeType>{
	@Override
	public  GpNounAttributeType mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GpNounAttributeType attr_type = new GpNounAttributeType();
		attr_type.setId(rs.getLong("id"));
		attr_type.setType(rs.getString("type"));
		attr_type.setSubtype(rs.getString("sub_type"));
		attr_type.setSub_type_modifier(rs.getString("sub_type_modifier"));
		return attr_type;
	}
}
