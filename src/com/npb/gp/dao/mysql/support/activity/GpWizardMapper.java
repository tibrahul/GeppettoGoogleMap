package com.npb.gp.dao.mysql.support.activity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gb.utils.GpGenericRecordParserBuilder;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpWizard;

public class GpWizardMapper implements RowMapper<GpWizard> {

	public GpWizard mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpWizard the_project = new GpWizard();

		the_project.setId(rs.getLong("WIZARD_ID"));
		the_project.setName(rs.getString("WIZARD_NAME"));
		the_project.setDescription(rs.getString("WIZARD_DESCRIPTION"));
		the_project.setActivity_id(rs
				.getLong("WIZARD_ACTIVITYID"));
		the_project.setScreenIds(rs.getString("WIZARD_SCREENIDS"));
		the_project.setWizard_type(rs.getString("WIZARD_TYPE"));

		return the_project;
	}

}
