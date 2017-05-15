package com.npb.gp.dao.mysql.support.organization;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpOrganization;

public class OrganizationMapper  implements RowMapper<GpOrganization>  {

	@Override
	public GpOrganization mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		GpOrganization the_Organization = new GpOrganization();
		the_Organization.setId(rs.getLong("base_organization_id"));
		the_Organization.setOrganization_name(rs.getString("organization_name")); 
		the_Organization.setContact_phone(rs.getString("contact_phone"));
		the_Organization.setAbout(rs.getString("about"));
		the_Organization.setCity(rs.getString("city"));
		the_Organization.setCountry(rs.getString("country"));
		
		return the_Organization;
	}

}
