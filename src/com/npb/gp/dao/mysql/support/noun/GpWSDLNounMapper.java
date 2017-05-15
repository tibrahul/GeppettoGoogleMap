package com.npb.gp.dao.mysql.support.noun;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpWSDLNoun;

public class GpWSDLNounMapper  implements RowMapper<GpWSDLNoun>{

	@Override
	public GpWSDLNoun mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GpWSDLNoun noun =new GpWSDLNoun();
		/*noun.setId(rs.getLong("id"));
		
		noun.setUser_id(rs.getLong("user_id"));
		noun.setClass_name(rs.getString("class_name"));*/
		noun.setWsdl_name(rs.getString("wsdl_name"));
		noun.setProject_id(rs.getLong("project_id"));
		noun.setWsdlId(rs.getLong("wsdl_id")); 
		return noun;
		
	}

}
