package com.npb.gp.dao.mysql.support.noun;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpOtherNoun;

public class OtherNounMapper implements RowMapper<GpOtherNoun>{

	@Override
	public GpOtherNoun mapRow(ResultSet rs, int rowNum) throws SQLException {
		GpOtherNoun othernoun = new GpOtherNoun();
		othernoun.setId(rs.getLong("id"));
		othernoun.setDb_name(rs.getString("db_name"));
        othernoun.setCollection_name(rs.getString("collection_name"));
        othernoun.setAttribute(rs.getString("attribute"));
		return othernoun;
	}

}
