package com.npb.gp.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.flowcontrol.FlowControlBaseMapper;
import com.npb.gp.dao.mysql.support.flowcontrol.InsertFlowControl;
import com.npb.gp.dao.mysql.support.flowcontrol.UpdateMasterFlowId;
import com.npb.gp.dao.mysql.support.microflow.FlowControlMapper;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpFlowControlBase;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.interfaces.dao.IGpFlowControlDao;

/***
 * <b>Created Date: 29/04/2015<br>
 * Modified By: Kumaresan Perumal<b><br>
 * 
 * <p>
 * Created the following methods get_records_from_flow_controlbase,
 * insert_flow_control, update_master_flow_id and
 * update_masterflow_id_to_activity.
 * </p>
 * 
 * Modified By: Reinaldo Lopez Date:04/09/2105<b><br>
 * <p>
 * Added method get_flow_control_by_activity
 * </p>
 * */

@Repository("GpFlowControlDao")
public class GpFlowControlDao implements IGpFlowControlDao {
	private Log log = LogFactory.getLog(getClass());

	ArrayList<GpFlowControlBase> flow_control_base_objects;

	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${get_records_from_flow_controlbase_sql.sql}")
	private String get_records_from_flow_controlbase_sql;

	@Value("${get_flow_control_base_by_dev_framework.sql}")
	private String get_flow_control_base_by_dev_framework_sql;
	
	@Value("${get_flow_control_base_by_language_and_framework.sql}")
	private String get_flow_control_base_by_language_and_framework_sql;
	

	@Value("${insert_flow_control.sql}")
	private String insert_flow_control_sql;

	@Value("${update_master_flow_id.sql}")
	private String update_master_flow_id_sql;

	@Value("${update_master_id.sql}")
	private String update_master_id;

	@Value("${get_flow_control_by_activity.sql}")
	private String get_flow_control_by_activity_sql;

	@Value("${get_flow_control_by_activity_and_component.sql}")
	private String get_flow_control_by_activity_and_component_sql;
	
	@Value("${delete_flow_by_activity_id.sql}")
	private String delete_flow_by_activity_id_sql;

	private UpdateMasterFlowId update_master_flow_id;
	private UpdateMasterFlowId update_master;
	private InsertFlowControl insert_flow_control;

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
	public GpFlowControl insert_flow_control(GpFlowControl base) {

		Map<String, Object> insert_paramMap = new HashMap<String, Object>();
		insert_paramMap.put("master_flow_id", base.getMaster_flow_id());
		insert_paramMap.put("component_type", base.getComponent_type());
		insert_paramMap.put("label", base.getLabel());
		insert_paramMap.put("description", base.getDescription());
		insert_paramMap.put("type", base.getType());
		insert_paramMap.put("sequence_id", base.getSequence_id());
		insert_paramMap.put("sub_sequence_id", base.getSub_sequence_id());
		insert_paramMap.put("activity_id", base.getActivity_id());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertFlowControl.SQL_INSERT_FLOW_CONTROL = insert_flow_control_sql;

		this.insert_flow_control = new InsertFlowControl(this.dataSource);
		this.insert_flow_control.updateByNamedParam(insert_paramMap, keyHolder);
		base.setId(keyHolder.getKey().longValue());

		return base;
	}

	@Override
	public void update_masterflow_id(long master_flow_id) {

		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("master_flow_id", master_flow_id);
		updateMap.put("id", master_flow_id);

		UpdateMasterFlowId.SQL_UPDATE_MASTER = update_master_id;
		this.update_master = new UpdateMasterFlowId(this.dataSource);
		this.update_master.updateByNamedParam(updateMap);
	}

	@Override
	public void update_masterflow_id_to_activity(long master_flow_id,
			long activity_id) {

		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("master_flow_id", master_flow_id);
		updateMap.put("id", activity_id);

		UpdateMasterFlowId.SQL_UPDATE_MASTER = update_master_flow_id_sql;
		this.update_master_flow_id = new UpdateMasterFlowId(this.dataSource);
		this.update_master_flow_id.updateByNamedParam(updateMap);
	}

	@Override
	public List<GpFlowControlBase> get_records_from_flowcontrol_base() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		FlowControlBaseMapper flow_control_mapper = new FlowControlBaseMapper();
		return this.namedParameterJdbcTemplate.query(
				this.get_records_from_flow_controlbase_sql, parameters,
				flow_control_mapper);
	}

	@Override
	public List<GpFlowControlBase> get_records_from_flowcontrol_base(
			String devFramework) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("dev_framework", devFramework);

		FlowControlBaseMapper flow_control_mapper = new FlowControlBaseMapper();
		return this.namedParameterJdbcTemplate.query(
				this.get_flow_control_base_by_dev_framework_sql, parameters,
				flow_control_mapper);
	}
	
	public List<GpFlowControlBase> get_records_from_flowcontrol_base(GpTechProperties language, GpTechProperties framework) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("dev_framework", framework.getName());
		parameters.addValue("dev_language", language.getName());
		FlowControlBaseMapper flow_control_mapper = new FlowControlBaseMapper();
		return this.namedParameterJdbcTemplate.query(
				this.get_flow_control_base_by_dev_framework_sql, parameters,
				flow_control_mapper);
	}

	@Override
	public List<GpFlowControl> get_flow_control_by_activity(long activity_id)
			throws Exception {

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("activity_id", activity_id);
		FlowControlMapper flow_control_mapper = new FlowControlMapper();
		return this.namedParameterJdbcTemplate.query(
				this.get_flow_control_by_activity_sql, parameters,
				flow_control_mapper);
	}

	@Override
	public List<GpFlowControl> get_flow_control_by_activity_and_component(long activity_id,String component_type)
			throws Exception {

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("activity_id", activity_id);
		parameters.addValue("component_type", component_type);
		FlowControlMapper flow_control_mapper = new FlowControlMapper();
		return this.namedParameterJdbcTemplate.query(
				this.get_flow_control_by_activity_and_component_sql, parameters,
				flow_control_mapper);
	}
	
	public void delete_flows_by_activity_id(long activity_id){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("activity_id", activity_id);
		this.namedParameterJdbcTemplate.execute(this.delete_flow_by_activity_id_sql, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
	}

}
