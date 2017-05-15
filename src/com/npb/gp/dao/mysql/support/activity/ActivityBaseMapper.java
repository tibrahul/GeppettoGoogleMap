package com.npb.gp.dao.mysql.support.activity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gb.utils.GpGenericRecordParserBuilder;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpNoun;

/*
 *		  <b>Modified Date: 01/04/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Hided the if condition for the temp noun_id
 *        </p> 
 * 
 */

public class ActivityBaseMapper implements RowMapper<GpActivity> {

	@Override
	public GpActivity mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		GpActivity dto = new GpActivity();

		dto.setId(rs.getLong("ACTIVITY_ID"));
		dto.setName(rs.getString("ACTIVITY_NAME"));
		dto.setLabel(rs.getString("ACTIVITY_LABEL"));
		dto.setDescription(rs.getString("ACTIVITY_DESCRIPTION"));
		dto.setProjectid(rs.getLong("PROJECT_ID"));
		long temp_noun_id = rs.getLong("PRIMARY_NOUN_ID");

		// if(temp_noun_id > 0){
		GpNoun temp_noun = new GpNoun();
		temp_noun.setId(temp_noun_id);
		dto.setPrimary_noun(temp_noun);
		// }

		dto.setModuleid(rs.getLong("MODULE_ID"));
		dto.setModule_type(rs.getString("MODULE_TYPE"));
		dto.setNotes(rs.getString("NOTES"));

		ArrayList<String> types_list = new ArrayList<String>();
		if (rs.getString("ACTIVITY_TYPES") != null) {
			if (rs.getString("ACTIVITY_TYPES").trim().length() > 0) {
				String[] temp = GpGenericRecordParserBuilder
						.parseDelimitedString(rs.getString("ACTIVITY_TYPES"));
				for (String str : temp) {
					types_list.add(str);
				}
			}

		}
		dto.setActivity_types(types_list);

		ArrayList<GpNoun> values = new ArrayList<GpNoun>();
		dto.setSecondary_nouns(values);
		if (rs.getString("ACTIVITY_SECONDARY_NOUNS") != null) {
			if (rs.getString("ACTIVITY_SECONDARY_NOUNS").trim().length() > 0) {
				String[] temp = GpGenericRecordParserBuilder
						.parseDelimitedString(rs
								.getString("ACTIVITY_SECONDARY_NOUNS"));
				for (String str : temp) {
					GpNoun a_noun = new GpNoun();
					a_noun.setId(new Long(str).longValue());
					values.add(a_noun);
				}
			}
		}

		dto.setPredefined_activity_id(rs.getLong("PREDEFINED_ACTIVITY_ID"));
		dto.setCreatedate(rs.getTimestamp("CREATE_DATE"));
		dto.setCreatedby(rs.getLong("CREATED_BY"));
		dto.setLastmodifieddate(rs.getTimestamp("LAST_MODIFIED_DATE"));

		dto.setLastmodifiedby(rs.getLong("LAST_MODIFIED_BY"));
		return dto;

	}

}
