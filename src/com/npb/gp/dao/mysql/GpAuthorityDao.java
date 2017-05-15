package com.npb.gp.dao.mysql;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.authority.InsertAuthority;
import com.npb.gp.domain.core.GpAuthority;
import com.npb.gp.domain.core.GpBaseAuthority;
import com.npb.gp.interfaces.dao.IGpAuthorityDao;

/**
 * @author Rashmi
 * 
 * @date : 09 Sept/15
 */

@Repository("GpAuthorityDao")
public class GpAuthorityDao implements IGpAuthorityDao {

	private DataSource dataSource;
	private InsertAuthority insert_authority;

	@Value("${insert.authority.sql}")
	private String insertAuthority;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insert(GpAuthority gpAuth) throws Exception {
		BigInteger roleid = new BigInteger(String.valueOf(GpBaseAuthority.ROLE_USER));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", gpAuth.getUserId());
		paramMap.put("role_id", roleid);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertAuthority.SQL_INSERT_AUTHORITY = insertAuthority;
		this.insert_authority = new InsertAuthority(dataSource);
		this.insert_authority.updateByNamedParam(paramMap, keyHolder);
		gpAuth.setId(keyHolder.getKey().longValue());
	}

}
