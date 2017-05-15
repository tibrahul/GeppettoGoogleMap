package com.npb.gp.dao.mysql.support.organization;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpOrganization;

public class Base_organization_user_dtoMapper implements RowMapper<Base_organization_user_dto> {

	@Override
	public Base_organization_user_dto mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Base_organization_user_dto the_Organization = new Base_organization_user_dto();
		
		
		the_Organization.setId(rs.getLong("id"));
		the_Organization.setUser_id(rs.getLong("user_id"));
		the_Organization.setBase_organization_id(rs.getLong("base_organization_id")); 
		return the_Organization;
	}

}
