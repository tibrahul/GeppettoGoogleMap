package com.npb.gp.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gp.interfaces.dao.IGpKeyGenDao;

/**
 * 
 * @author Suresh Palanisamy
 * @since version.95<br>
 *        <p>
 *        Creation Date: 25/09/2015
 *        </p>
 *
 */

@Repository("GpKeyGenDao")
public class GpKeyGenDao implements IGpKeyGenDao {

	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${get_max_widget_count.sql}")
	private String get_max_widget_count_sql;

	@Value("${get_max_project_id.sql}")
	private String get_max_project_id_sql;

	@Value("${update_widget_count.sql}")
	private String update_widget_count_sql;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Override
	public long get_max_widget_count() throws Exception {

		long widget_count = this.jdbcTemplate.queryForObject(get_max_widget_count_sql, Long.class);

		if (widget_count == 0) {
			System.out.println("Widget count was not found");
			return 0;
		}
		return widget_count;
	}

	@Override
	public long update_widget_count(long widget_count) throws Exception {

		long project_id = this.jdbcTemplate.queryForObject(get_max_project_id_sql, Long.class);

		if (project_id != 0) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", project_id);
			paramMap.put("widget_count", widget_count);

			this.namedParameterJdbcTemplate.execute(this.update_widget_count_sql, paramMap,
					new PreparedStatementCallback<Object>() {
						@Override
						public Object doInPreparedStatement(PreparedStatement ps)
								throws SQLException, DataAccessException {
							return ps.executeUpdate();
						}
					});
		}
		return 0;
	}
}