package com.npb.gp.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.applicationtypes.ApplicationTypeMapper;
import com.npb.gp.domain.core.GpApplicationType;
import com.npb.gp.interfaces.dao.IGpApplicationTypeDao;

/**
 * 
 * @author Suresh Palanisamy</br> Date Created: 25/03/2015</br>
 * @since .75</p>
 *
 *        The purpose of this class is to interact with the db for the basic
 *        search</br> and CRUD operations for a device types</p>
 *
 */

@Repository("GpApplicationTypeDao")
public class GpApplicationTypeDao implements IGpApplicationTypeDao {

	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${findAllApplicationType.sql}")
	private String findAllApplicationType;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public List<GpApplicationType> find_all_Application_type() throws Exception {

		ApplicationTypeMapper applicationType = new ApplicationTypeMapper();
			List<GpApplicationType> appType = this.namedParameterJdbcTemplate
				.query(findAllApplicationType, applicationType);

		if (appType.size() < 1) {
			return new ArrayList<GpApplicationType>();
		}

		return appType;
	}

}
