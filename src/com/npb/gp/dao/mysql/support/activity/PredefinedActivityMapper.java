package com.npb.gp.dao.mysql.support.activity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpActivity;

/**
 * 
 * @author Suresh Palanisamy
 *         <p>
 *         Date Created: 11/19/2015
 *         </p>
 * @since Version 1.0
 * 
 *        <p>
 *        This class maps the other activities to the GpActivity
 *        </p>
 *
 */

public class PredefinedActivityMapper implements RowMapper<GpActivity> {

	@Override
	public GpActivity mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpActivity activity = new GpActivity();
		activity.setId(rs.getLong("ACTIVITY_ID"));
		activity.setName(rs.getString("ACTIVITY_NAME"));
		activity.setLabel(rs.getString("ACTIVITY_LABEL"));
		activity.setDescription(rs.getString("ACTIVITY_DESC"));
		activity.setLocation_path(rs.getString("ACTIVITY_LOCATION_PATH"));
		activity.setCreatedby(rs.getLong("ACTIVITY_CREATED_BY"));
		activity.setCreatedate(rs.getTimestamp("ACTIVITY_CREATE_DATE"));
		activity.setLastmodifiedby(rs.getLong("ACTIVITY_LAST_MODIFIED_BY"));
		activity.setLastmodifieddate(rs.getTimestamp("ACTIVITY_LAST_MODIFIED_DATE"));
		activity.setActivity_visibility(rs.getBoolean("ACTIVITY_VISIBILITY"));
		activity.setActivity_type(rs.getString("ACTIVITY_TYPE"));
		
		return activity;
	}
}