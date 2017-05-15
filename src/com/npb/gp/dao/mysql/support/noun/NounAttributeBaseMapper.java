package com.npb.gp.dao.mysql.support.noun;

/**
 * 
 * @author Suresh Palanisamy</br> Date Created: 18/03/2015</br>
 * @since .75</p>
 *
 *        The purpose of this class is to declare the values for the noun attributes from the result set</p>
 */

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpNounAttribute;

public class NounAttributeBaseMapper implements RowMapper<GpNounAttribute> {

	@Override
	public GpNounAttribute mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpNounAttribute nounAttrib = new GpNounAttribute();

		/* ATTRIBUTES START */
		nounAttrib.setId(rs.getLong("id"));
		nounAttrib.setNounid(rs.getLong("nounid"));
		nounAttrib.setName(rs.getString("name"));
		nounAttrib.setLabel(rs.getString("label"));
		nounAttrib.setDescription(rs.getString("description"));
		nounAttrib.setBase_attr_type_id(rs.getLong("base_attr_type_id"));
		nounAttrib.setIspart_of_unique_key(rs.getBoolean("part_of_unique_key"));
		nounAttrib.setDatalength(rs.getString("data_length"));
		nounAttrib.setTechnicalname(rs.getString("relationships"));
		nounAttrib.setNotes(rs.getString("notes"));
		nounAttrib.setCreatedby(rs.getLong("created_by"));
		nounAttrib.setCreatedate(rs.getTimestamp("created_date"));
		nounAttrib.setLastmodifiedby(rs.getLong("last_modified_by"));
		nounAttrib.setLastmodifieddate(rs.getTimestamp("last_modified_date"));

		/* ATTRIBUTES END */

		return nounAttrib;
	}
}
