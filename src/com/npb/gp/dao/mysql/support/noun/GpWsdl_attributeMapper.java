package com.npb.gp.dao.mysql.support.noun;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpWsdl_With_Attribute;

public class GpWsdl_attributeMapper implements RowMapper<GpWsdl_With_Attribute>{

	@Override
	public GpWsdl_With_Attribute mapRow(ResultSet rs, int rowNum) throws SQLException {
		GpWsdl_With_Attribute dto = new GpWsdl_With_Attribute();
		dto.setWsdlclassid(rs.getLong("classid"));
		dto.setWsdl_name(rs.getString("wsdl_name"));
		dto.setClass_name(rs.getString("class_name"));
		dto.setProject_id(rs.getLong("project_id"));
		dto.setUser_id(rs.getLong("user_id"));
		dto.setAttribute_name(rs.getString("attribute_name"));
		dto.setAttribute_type(rs.getString("attribute_type"));
		dto.setWsdlattributeid(rs.getLong("attributeid"));
		dto.setWsdl_id(rs.getLong("wsdl_id")); 
		return dto;
	}

}
