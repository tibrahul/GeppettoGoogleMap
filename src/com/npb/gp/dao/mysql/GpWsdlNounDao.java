package com.npb.gp.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gb.utils.GpUserUtils;
import com.npb.gp.dao.mysql.support.noun.GpWSDLNounMapper;
import com.npb.gp.dao.mysql.support.noun.GpWsdlClassMapper;
import com.npb.gp.dao.mysql.support.noun.GpWsdlOperationMapper;
import com.npb.gp.dao.mysql.support.noun.GpWsdl_attributeMapper;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpWSDLNoun;
import com.npb.gp.domain.core.GpWsdlAttribute;
import com.npb.gp.domain.core.GpWsdlOperation;
import com.npb.gp.domain.core.GpWsdl_With_Attribute;
import com.npb.gp.interfaces.dao.IGpWSDLNounDoa;
import com.npb.gp.services.GpNounService;

/**
 * @author khalisaran </br>
 *         Creation Date: 09/04/2017</br>
 * 
 * 
 */

@Repository("GpWsdlNounDao")
public class GpWsdlNounDao implements IGpWSDLNounDoa {

	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Value("${findAllWSDLNounByProject.sql}")
	private String findAllWSDLNounByProject;
	@Value("${findclssfromwsdl.sql}")
	private String findclassfromwsdl;
	@Value("${findclassandattribute.sql}")
	private String findclassandattribute;

	@Value("${get_operation_by_wsdl.sql}")
	private String findwsdlOperation;
	@Override
	public List<GpWSDLNoun> find_WSDL_by_project_id(long project_id) throws Exception {
		
		
		MapSqlParameterSource parameters;
		
		System.err.println("dao find wsdl by project id "+project_id);
		parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", project_id);

		GpWSDLNounMapper wsdlnoun = new GpWSDLNounMapper();
		List<GpWSDLNoun> wsdlname = this.namedParameterJdbcTemplate.query(findAllWSDLNounByProject, parameters,
				wsdlnoun);
		if (wsdlname.size() < 1) {
			throw new Exception("wsdl not found for project id:  " + project_id, new Throwable("99"));
		}
		return wsdlname;

	}

	public List<GpWSDLNoun> find_class_from_wsdl(Long wsdl,long project_id) throws Exception {
		MapSqlParameterSource parameters;
		
		System.err.println("dao find wsdl by project id "+wsdl);
		parameters = new MapSqlParameterSource();
		parameters.addValue("wsdlId", wsdl);
		parameters.addValue("project_id", project_id);
		
		GpWsdlClassMapper wsdlnoun = new GpWsdlClassMapper();
		List<GpWSDLNoun> wsdlname = this.namedParameterJdbcTemplate.query(findclassfromwsdl, parameters,
				wsdlnoun);
		System.out.println("list of wsdl----->"+wsdlname.toString());
		if (wsdlname.size() < 1) {
			throw new Exception("wsdl not found for project id:  " + wsdl, new Throwable("99"));
		}
		return wsdlname;
	}

	public List<GpWsdl_With_Attribute> get_class_and_attribute(long id) throws Exception {
		MapSqlParameterSource parameters;
		System.err.println("dao find wsdl by  id "+id);
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		
		GpWsdl_attributeMapper wsdlnoun = new GpWsdl_attributeMapper();
		List<GpWsdl_With_Attribute> wsdlname = this.namedParameterJdbcTemplate.query(findclassandattribute, parameters,
				wsdlnoun);
		System.out.println("list of wsdl----->"+wsdlname.toString());
		if (wsdlname.size() < 1) {
			throw new Exception("wsdl not found for project id:  " + id, new Throwable("99"));
		}
		return wsdlname;
	}

	public List<GpWsdlOperation> get_operations_by_wsdl_id(long wsdlId) throws Exception {

		MapSqlParameterSource parameters;
		System.err.println("-------------------------- > dao find wsdlOperation by  id "+wsdlId);
		parameters = new MapSqlParameterSource();
		parameters.addValue("wsdl_id", wsdlId);
		
		GpWsdlOperationMapper wsdlOperation = new GpWsdlOperationMapper();
		List<GpWsdlOperation> listOfWsdlOperation = this.namedParameterJdbcTemplate.query(findwsdlOperation, parameters,
				wsdlOperation);
		if (listOfWsdlOperation.isEmpty()) {
			throw new Exception("wsdl Operation not found for wsdl ID  " + wsdlId, new Throwable("99"));
		}
		return listOfWsdlOperation;
	}

	

}
