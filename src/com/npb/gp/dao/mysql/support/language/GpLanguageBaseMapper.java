package com.npb.gp.dao.mysql.support.language;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.npb.gp.domain.core.GpLanguage;

public class GpLanguageBaseMapper implements RowMapper<GpLanguage> {
	

	public GpLanguage mapRow(ResultSet rs, int rowNum)
			throws SQLException {

		GpLanguage language_object = new GpLanguage();
		language_object.setId(rs.getLong("ID"));
		language_object.setIso_id(rs.getString("ISO_ID"));
		language_object.setPart_2b(rs.getString("PART_2B"));
		language_object.setPart_2t(rs.getString("PART_2T"));
		language_object.setPart_1(rs.getString("PART_1"));
		language_object.setScope(rs.getString("SCOPE"));
		language_object.setLanguage_type(rs.getString("LANGUAGE_TYPE"));
		language_object.setRef_name(rs.getString("REF_NAME"));
		language_object.setComment(rs.getString("COMMENT"));
		
		return language_object;
	}

}
