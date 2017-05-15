package com.npb.gp.dao.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.flowcontrol.MicroFlowBaseMapper;
import com.npb.gp.dao.mysql.support.microflow.DeleteMicroFlow;
import com.npb.gp.dao.mysql.support.microflow.InsertMicroFlow;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpMicroFlowBase;
import com.npb.gp.interfaces.dao.IGpMicroFlowDao;

/***
 * <b>Created Date: 04/09/2015<br>
 * Modified By: Reinaldo Lopez<b><br>
 * 
 * <p>
 * Created the following methods
 * insert_micro_flow
 * </p>
 * 
 * <b>Modified Date: 18/10/2015<br>
 * Modified By: Reinaldo Lopez<b><br>
 * 
 * <p>
 * Added delete_micro_flow method
 * </p>
 * 
 * <p>
 *  Modified By : Rashmi
 *  Date: 29/03/2016
 *  Added get_records_from_micro_flow_base 
 * </p>
 * 
 * */
@Repository("GpMicroFlowDao")
public class GpMicroFlowDao implements IGpMicroFlowDao {

	private DataSource dataSource;
	private Log log = LogFactory.getLog(getClass());
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${insert_micro_flow.sql}")
	private String insert_micro_flow_sql;
	
	@Value("${delete_micro_flow.sql}")
	private String delete_micro_flow_sql;

	@Value("${get_records_from_micro_flow_base.sql}")
	private String get_records_from_micro_flow_base_sql;
	
	@Value("${get_micro_flow_base_by_action_and_component.sql}")
	private String get_micro_flow_base_by_action_and_component_sql;

	private InsertMicroFlow insert_micro_flow;
	private DeleteMicroFlow delete_micro_flow;

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
	public GpMicroFlow insert_micro_flow(GpMicroFlow base) {

		Map<String, Object> insert_paramMap = new HashMap<String, Object>();
		insert_paramMap.put("flow_control_id", base.getFlow_control_id());
		insert_paramMap.put("component_type", base.getComponent_type());
		insert_paramMap.put("description", base.getDescription());		
		insert_paramMap.put("master_flow_id", base.getMaster_flow_id());
		insert_paramMap.put("description", base.getDescription());
		insert_paramMap.put("code_id", base.getCode_id());
		insert_paramMap.put("sequence_id", base.getSequence_id());
		insert_paramMap.put("verb_id", base.getVerb_id());
		insert_paramMap.put("action", base.getAction());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertMicroFlow.SQL_INSERT_MICRO_FLOW = insert_micro_flow_sql;		

		this.insert_micro_flow = new InsertMicroFlow(this.dataSource);
		this.insert_micro_flow.updateByNamedParam(insert_paramMap, keyHolder);
		base.setId(keyHolder.getKey().longValue());

		return base;
	}

	@Override
	public void delete_micro_flow(long verb_id) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("verb_id", verb_id);
		
		DeleteMicroFlow.SQL_DELETE_MICROFLOW = delete_micro_flow_sql;
		this.delete_micro_flow = new DeleteMicroFlow(dataSource);
		this.delete_micro_flow.updateByNamedParam(paramMap);
				
	}

	@Override
	public ArrayList<GpMicroFlowBase> get_records_from_micro_flow_base(String base_verb_name) {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("base_verb_name", base_verb_name);
		MicroFlowBaseMapper microFlowBaseMapper = new MicroFlowBaseMapper();
		
		List<GpMicroFlowBase> listOfMicroFlowBase = this.namedParameterJdbcTemplate
				.query(this.get_records_from_micro_flow_base_sql, parameters,
						microFlowBaseMapper);
		return (ArrayList<GpMicroFlowBase>) listOfMicroFlowBase;
	}
	
	public ArrayList<GpMicroFlowBase> get_records_from_micro_flow_base(String base_verb_name, String componen_name) {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("base_verb_name", base_verb_name);
		parameters.addValue("component_name", componen_name);
		MicroFlowBaseMapper microFlowBaseMapper = new MicroFlowBaseMapper();
		
		List<GpMicroFlowBase> listOfMicroFlowBase = this.namedParameterJdbcTemplate
				.query(this.get_micro_flow_base_by_action_and_component_sql, parameters,
						microFlowBaseMapper);
		return (ArrayList<GpMicroFlowBase>) listOfMicroFlowBase;
	}
}

