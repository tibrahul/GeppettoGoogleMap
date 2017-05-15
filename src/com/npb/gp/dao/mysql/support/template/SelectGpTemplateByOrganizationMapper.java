package com.npb.gp.dao.mysql.support.template;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpProjectTemplate;

public class SelectGpTemplateByOrganizationMapper implements RowMapper<SelectGpTemplateByOrganization_dto> {

	@Override
	public SelectGpTemplateByOrganization_dto mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		SelectGpTemplateByOrganization_dto tempeletbyOrganization = new SelectGpTemplateByOrganization_dto();
		tempeletbyOrganization.setId(rs.getLong("id"));
		tempeletbyOrganization.setBase_organization_id(rs.getLong("base_organization_id"));
		tempeletbyOrganization.setTemplate_id(rs.getLong("template_id"));
		return tempeletbyOrganization;
	}
}
