package com.npb.gp.dao.mysql.support.noun;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpWsdlOperation;

public class GpWsdlOperationMapper  implements RowMapper<GpWsdlOperation>  {

	@Override
	public GpWsdlOperation mapRow(ResultSet rs, int rowNum) throws SQLException {
		GpWsdlOperation wsdlOperation = new GpWsdlOperation();
		wsdlOperation.setId(rs.getLong("id"));
		wsdlOperation.setClass_id(rs.getLong("class_id"));
		wsdlOperation.setWsdl_id(rs.getLong("wsdl_id"));
		wsdlOperation.setOperation(rs.getString("operation"));
		wsdlOperation.setOperation_parameters(rs.getString("operation_parameters"));
		wsdlOperation.setReturnType(rs.getString("returnType"));
		
		return wsdlOperation;
	}

}
