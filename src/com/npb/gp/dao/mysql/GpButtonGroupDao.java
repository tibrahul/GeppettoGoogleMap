package com.npb.gp.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.npb.gb.utils.GpGenericRecordParserBuilder;
import com.npb.gp.dao.mysql.support.activity.GpButtonGroupMapper;
import com.npb.gp.dao.mysql.support.activity.InsertGroup;
import com.npb.gp.dao.mysql.support.activity.UpdateActivity;
import com.npb.gp.dao.mysql.support.activity.UpdateButtonGroup;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpButtonGroup;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.interfaces.dao.IGpButtonGroupDao;

@Repository("GpButtonGroupDao")
public class GpButtonGroupDao implements IGpButtonGroupDao {
	
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private InsertGroup insert_group;
	private UpdateButtonGroup updateButtonGroup;

	@Value("${delete_group.sql}")
	private String deleteGrp;

	@Value("${insert_group.sql}")
	private String insert_grp;
	
	@Value("${update_group_data_binding.sql}")
	private String update_group_data_binding;
	
	@Value("${getAllGroupsByScreenId.sql}")
	private String getAllGroupsByScreenId;

	@Value("${getAllGroupsByScreenIdAndType.sql}")
	private String getAllGroupsByScreenIdAndType;
	

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	public DataSource getDataSource() {
		return dataSource;
	}
	

	@Override
	public ArrayList<GpButtonGroup> getAllGroupsForScreen(long screenId)
			throws Exception {
		System.out.println("In getAllGroupsForScreen -1");
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("screen_id", screenId);
		GpButtonGroupMapper the_mapper = new GpButtonGroupMapper();
		ArrayList<GpButtonGroup> dto_list =  (ArrayList<GpButtonGroup>) this.namedParameterJdbcTemplate.query(
				getAllGroupsByScreenId, parameters, the_mapper);

		return dto_list;
	}

	@Override
	public ArrayList<GpButtonGroup> getAllGroupsForScreenAndType(long screenId,String type)
			throws Exception {
		System.out.println("In getAllGroupsForScreen -1");
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("screen_id", screenId);
		parameters.addValue("type", type);
		GpButtonGroupMapper the_mapper = new GpButtonGroupMapper();
		ArrayList<GpButtonGroup> dto_list =  (ArrayList<GpButtonGroup>) this.namedParameterJdbcTemplate.query(
				getAllGroupsByScreenIdAndType, parameters, the_mapper);

		return dto_list;
	}



	@Override
	public GpButtonGroup insert(GpButtonGroup buttonGroup) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", buttonGroup.getName());
		paramMap.put("screenId", buttonGroup.getScreenId());
		paramMap.put("type", buttonGroup.getType());
		paramMap.put("data_binding_context", buttonGroup.getData_binding_context());
		paramMap.put("noun_attribute_id", buttonGroup.getNoun_attribute_id());
		paramMap.put("noun_id", buttonGroup.getNoun_id());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertGroup.SQL_INSERT_GROUP = insert_grp;
		this.insert_group = new InsertGroup(this.dataSource);
		this.insert_group.updateByNamedParam(paramMap, keyHolder);
		
		buttonGroup.setId(keyHolder.getKey().longValue());
		System.out.println("The grp id is: " + buttonGroup.getId());
		return buttonGroup;
	}

	@Override
	public void delete(long screenId) throws Exception {

		// Code for deleting a grp
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("screen_id", screenId);

		this.namedParameterJdbcTemplate.execute(this.deleteGrp, paramMap, new PreparedStatementCallback<Object>() {
			@Override
			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.executeUpdate();
			}
		});
	}

	@Override
	public void update(GpButtonGroup gpButtonGroup) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("data_binding_context", gpButtonGroup.getData_binding_context());
		paramMap.put("noun_id", gpButtonGroup.getNoun_id());
		paramMap.put("noun_attribute_id", gpButtonGroup.getNoun_attribute_id());
		paramMap.put("id", gpButtonGroup.getId());

		UpdateButtonGroup.SQL_UPDATE_BUTTON_GROUP = update_group_data_binding;
		this.updateButtonGroup = new UpdateButtonGroup(this.dataSource);

		this.updateButtonGroup.updateByNamedParam(paramMap);

	}
}
