package com.npb.gp.dao.mysql.support.project;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpProject;

/**
 * 
 * @author Dan Castillo</br>
 *         Date Created: 04/08/2014</br>
 * @since .35
 *        </p>
 *
 *        The purpose of this class is to support the GpProjectDao by provding
 *        mapping
 *        </p>
 * 
 *        Modified Date: 10/22/2014</br>
 *        Modified By: Dan Castillo
 *        </p>
 * 
 *        removed all references to the ""backing"" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex
 * 
 *        Modified Date: 22/05/2015</br>
 *        Modified By: kumaresan perumal
 *        </p>
 * 
 *        i removed the client css frame works because it was an array list to
 *        select multiple options. our requirement is single selection. i
 *        changed it as single selection string
 *
 *
 */
public class GpExisting_ProjectName_Mapper implements RowMapper<GpProject> {

	public GpProject mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpProject the_project = new GpProject();
		the_project.setName(rs.getString("PROJECT_NAME"));
		return the_project;
	}
}