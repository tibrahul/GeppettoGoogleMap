package com.npb.gp.dao.mysql.support.verbs;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.npb.gp.domain.core.GpVerb;

public class VerbsMapper implements RowMapper<GpVerb> {
	@Override
	public GpVerb mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpVerb the_verb = new GpVerb();
		the_verb.setId(rs.getLong("VERB_ID"));
		the_verb.setName(rs.getString("VERB_NAME"));
		the_verb.setLabel(rs.getString("VERB_LABEL"));
		the_verb.setDescription(rs.getString("VERB_DESCRIPTION"));
		the_verb.setNotes(rs.getString("VERB_NOTES"));
		the_verb.setAction_on_data(rs.getString("VERB_ACTION_ON_DATA"));
		the_verb.setActual_information(rs.getString("VERB_ACTUAL_INFORMATION"));

		the_verb.setActivity_id(rs.getLong("ACTIVITY_ID"));
		the_verb.setScreen_id(rs.getString("SCREEN_ID"));
		the_verb.setBase_verb_id(rs.getLong("BASE_VERB_ID"));
		the_verb.setWsdl_operation_id(rs.getString("WSDL_OPERATION_ID"));

		return the_verb;
	}
}
