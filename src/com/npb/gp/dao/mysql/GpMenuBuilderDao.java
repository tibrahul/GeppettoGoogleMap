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
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.npb.gp.dao.mysql.support.menubuilder.InsertMenuDetail;
import com.npb.gp.dao.mysql.support.menubuilder.MenuDetailMapper;
import com.npb.gp.dao.mysql.support.menubuilder.RenewDetail;
import com.npb.gp.dao.mysql.support.menubuilder.ScreenDetailMapper;
import com.npb.gp.dao.mysql.support.menubuilder.UpdateMenuDetail;
import com.npb.gp.dao.mysql.support.menubuilder.UpdateMenuTree;
import com.npb.gp.domain.core.GpMenuDetail;
import com.npb.gp.domain.core.GpMenuScreenDetail;
import com.npb.gp.interfaces.dao.IGpMenuBuilderDao;

/**
 * 
 * @author KUMARESAN
 * @since version .85
 *
 *        The purpose of this class is to interact with the DB for the basic
 *        search</br>
 *        and CRUD operations for a menu builder
 *        </p>
 *
 */

@Repository("GpMenuBuilderDao")
public class GpMenuBuilderDao implements IGpMenuBuilderDao {

	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private InsertMenuDetail insertMenuDetail;
	private UpdateMenuDetail updateMenuDetail;
	private RenewDetail renewDetail;
	private UpdateMenuTree updateMenuTree;

	@Value("${insert_menu_detail_activity.sql}")
	private String insert_menu_detail_activity;

	@Value("${find_menu_detail.sql}")
	private String find_menu_detail;

	@Value("${find_menu_detail_activity.sql}")
	private String find_menu_detail_activity;

	@Value("${update_menu_screen_detail.sql}")
	private String update_menu_screen_detail;

	@Value("${delete_menu_detail_activity.sql}")
	private String delete_menu_detail_activity;

	@Value("${renew_menu_detail.sql}")
	private String renew_menu_detail;

	@Value("${update_menu_tree.sql}")
	private String update_menu_tree;

	@Value("${find_screens_details_from_screens_table.sql}")
	private String find_screens_details_from_screens_table;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Override
	public GpMenuDetail create_menudetail_activity(GpMenuDetail menudetail) {
		System.out.print("We are in menu builder dao");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("menu_master_id", menudetail.getMenu_master_id());
		paramMap.put("name", menudetail.getName());
		paramMap.put("label", menudetail.getLabel());
		paramMap.put("description", menudetail.getDescription());
		paramMap.put("auth_id", menudetail.getAuth_id());
		paramMap.put("project_id", menudetail.getProject_id());
		paramMap.put("activity_id", menudetail.getActivity_id());
		// paramMap.put("screen_id", menudetail.getScreen_id());
		paramMap.put("menu_tree", "[]");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertMenuDetail.SQL_INSERT_MENU = insert_menu_detail_activity;
		this.insertMenuDetail = new InsertMenuDetail(this.dataSource);
		this.insertMenuDetail.updateByNamedParam(paramMap, keyHolder);
		menudetail.setId(keyHolder.getKey().longValue());
		return menudetail;
	}

	@Override
	public GpMenuScreenDetail update_menu_screen_detail(GpMenuScreenDetail detail) throws Exception { //
		System.out.println(" *** Update screen dao ****");
		GpMenuDetail dto_list = find_menu_detail_activity(detail.getActivity_id());

		if (dto_list == null) {
			System.out.println(
					"FIRST CREATE A NEW ACTIVITY, THIS IS EXISTING ACTIVITY BEFORE THIS MENU BUILDER IMPLEMENTED");
		} else {

			System.out.println("we are in else part update detail");
			String menu_tree = dto_list.getMenu_tree();
			System.out.println("Read from DB value " + menu_tree);
			ObjectMapper objectMapper = new ObjectMapper();
			List<GpMenuScreenDetail> list = objectMapper.readValue(menu_tree,
					TypeFactory.defaultInstance().constructCollectionType(List.class, GpMenuScreenDetail.class));
			
			if(detail.getLabel() == null){	
		    	detail.setLabel(detail.getName());
		    }
			
		    list.add(detail);
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(list);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("activity_id", detail.getActivity_id());
			paramMap.put("menu_tree", json);
			UpdateMenuDetail.SQL_UPDATE_MENU_DETAIL = update_menu_screen_detail;
			this.updateMenuDetail = new UpdateMenuDetail(this.dataSource);
			this.updateMenuDetail.updateByNamedParam(paramMap);
		}
		return detail;
	}
	
	@Override
	public void menu_builder_for_update_screen(GpMenuScreenDetail detail) throws Exception  {
		
		
		System.out.println(" *** Update screen dao for UPDATING MENU BUILDER===================== ****");
		GpMenuDetail dto_list = find_menu_detail_activity(detail.getActivity_id());
		String menu_tree = dto_list.getMenu_tree();
		System.out.println("Read from DB value " + menu_tree);
		ObjectMapper objectMapper = new ObjectMapper();
		List<GpMenuScreenDetail> list = objectMapper.readValue(menu_tree,
				TypeFactory.defaultInstance().constructCollectionType(List.class, GpMenuScreenDetail.class));
		
		for (GpMenuScreenDetail temp : list) {
			if(temp.getId() == detail.getId()){
			   temp.setName(detail.getName());
			   temp.setLabel(detail.getLabel());
			   temp.setDescription(detail.getDescription());
			}
		}
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(list);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("activity_id", detail.getActivity_id());
		paramMap.put("menu_tree", json);
		UpdateMenuDetail.SQL_UPDATE_MENU_DETAIL = update_menu_screen_detail;
		this.updateMenuDetail = new UpdateMenuDetail(this.dataSource);
		this.updateMenuDetail.updateByNamedParam(paramMap);
	    
	}
	
	
	

	@Override
	public GpMenuDetail find_menu_detail_activity(long activity_id) throws Exception {
		System.out.print("We are in menu builder dao");
		System.out.println("The project_id is: " + activity_id);
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("activity_id", activity_id);
		MenuDetailMapper menu_builder = new MenuDetailMapper();
		List<GpMenuDetail> dto_list = this.namedParameterJdbcTemplate.query(find_menu_detail_activity, parameters,
				menu_builder);
		if (dto_list.isEmpty()) {
			return null;
		} else {
			return (GpMenuDetail) dto_list.get(0);
		}
	}

	@Override
	public ArrayList<GpMenuDetail> find_menu_detail(long project_id) throws Exception {
		System.out.print("We are in menu builder dao");
		System.out.println("The project_id is: " + project_id);
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", project_id);
		MenuDetailMapper menu_builder = new MenuDetailMapper();
		List<GpMenuDetail> dto_list = this.namedParameterJdbcTemplate.query(find_menu_detail, parameters, menu_builder);
		return (ArrayList<GpMenuDetail>) dto_list;
	}

	@Override
	public GpMenuDetail delete_menudetail_activity(GpMenuDetail menudetail) {
		System.out.print("We are in menu builder dao");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("project_id", menudetail.getProject_id());
		paramMap.put("activity_id", menudetail.getActivity_id());
		this.namedParameterJdbcTemplate.execute(delete_menu_detail_activity, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
		return menudetail;
	}

	@Override
	public GpMenuDetail delete_menu_detail_screen(long id) throws Exception {

		System.out.println("We are in menu builder dao");
		/**
		 * This method is used to get activity_id,project_id from screens table
		 * on the DB
		 */
		GpMenuScreenDetail Obj_dto = this.find_screens_details_from_screens_table(id);

		long activity_id = Obj_dto.getActivity_id();
		long screen_id = id;
		/** This method is used to get an activity of menu details */
		GpMenuDetail dto_list = this.find_menu_detail_activity(activity_id);
		System.out.println("the object is : " + dto_list.getMenu_tree());
		System.out.println("the object is : " + dto_list.getId());

		String menu_tree = dto_list.getMenu_tree();
		System.out.println("Read from DB value " + menu_tree);
		ObjectMapper objectMapper = new ObjectMapper();
		List<GpMenuScreenDetail> list = objectMapper.readValue(menu_tree,
				TypeFactory.defaultInstance().constructCollectionType(List.class, GpMenuScreenDetail.class));
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == screen_id) {
				System.out.println("deleted id is =" + list.get(i).getId());
				System.out.println("object is removed from there = ");
				list.remove(i);
			}
		}

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(list);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("activity_id", activity_id);
		paramMap.put("menu_tree", json);
		UpdateMenuDetail.SQL_UPDATE_MENU_DETAIL = update_menu_screen_detail;
		this.updateMenuDetail = new UpdateMenuDetail(this.dataSource);
		this.updateMenuDetail.updateByNamedParam(paramMap);
		return null;
	}

	@Override
	public GpMenuDetail update_menudetail_screen(GpMenuScreenDetail detail) throws Exception {
		System.out.print("We are in menu builder dao");
		long screen_id = detail.getId();
		GpMenuDetail dto_list = this.find_menu_detail_activity(detail.getActivity_id());
		System.out.println("the object is : " + dto_list.getMenu_tree());
		System.out.println("the object is : " + dto_list.getId());

		String menu_tree = dto_list.getMenu_tree();
		System.out.println("Read from DB value " + menu_tree);
		ObjectMapper objectMapper = new ObjectMapper();
		List<GpMenuScreenDetail> list = objectMapper.readValue(menu_tree,
				TypeFactory.defaultInstance().constructCollectionType(List.class, GpMenuScreenDetail.class));
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == screen_id) {
				System.out.println("if condition is worked by me");
				list.get(i).setName(detail.getName());
				list.get(i).setLabel(detail.getLabel());
				list.get(i).setDescription(detail.getDescription());
			}
		}
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(list);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("activity_id", detail.getActivity_id());
		paramMap.put("menu_tree", json);
		UpdateMenuDetail.SQL_UPDATE_MENU_DETAIL = update_menu_screen_detail;
		this.updateMenuDetail = new UpdateMenuDetail(this.dataSource);
		this.updateMenuDetail.updateByNamedParam(paramMap);
		return null;
	}

	@Override
	public ArrayList<GpMenuDetail> renew_menu_details(ArrayList<GpMenuDetail> menu_details)
			throws JsonProcessingException {
		for (GpMenuDetail gpMenu_detail : menu_details) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("name", gpMenu_detail.getName());
			paramMap.put("label", gpMenu_detail.getLabel());
			paramMap.put("description", gpMenu_detail.getDescription());
			// paramMap.put("auth_id", gpMenu_detail.getAuth_id());
			paramMap.put("activity_id", gpMenu_detail.getActivity_id());
			// paramMap.put("project_id", gpMenu_detail.getProject_id());
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(gpMenu_detail.getScreen_detail());
			// String str=gpMenu_detail.getScreen_detail().toString();
			paramMap.put("menu_tree", json);
			RenewDetail.SQL_UPDATE_MENU_DETAIL = renew_menu_detail;
			this.renewDetail = new RenewDetail(this.dataSource);
			this.renewDetail.updateByNamedParam(paramMap);
		}
		return menu_details;
	}

	@Override
	public void update_menu_tree(long activity_id,JSONArray menutree)
			throws JsonProcessingException {
		
			Map<String, Object> paramMap = new HashMap<String, Object>();

			String json = menutree.toString();
			paramMap.put("activity_id", activity_id);
			paramMap.put("menu_tree", json);
			UpdateMenuTree.SQL_UPDATE_MENU_DETAIL = update_menu_tree;
			this.updateMenuTree = new UpdateMenuTree(this.dataSource);
			this.updateMenuTree.updateByNamedParam(paramMap);

	}

	@Override
	public GpMenuScreenDetail find_screens_details_from_screens_table(long screen_id) {
		System.out.print("We are in menu builder dao");
		System.out.println("The screen_id is: " + screen_id);
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", screen_id);
		ScreenDetailMapper menu_builder = new ScreenDetailMapper();
		List<GpMenuScreenDetail> dto_list = this.namedParameterJdbcTemplate
				.query(find_screens_details_from_screens_table, parameters, menu_builder);
		return (GpMenuScreenDetail) dto_list.get(0);

	}
	
	public void delete_menudetail_by_activityid(long activityId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("project_id", menudetail.getProject_id());
		paramMap.put("activity_id", activityId);
		this.namedParameterJdbcTemplate.execute(delete_menu_detail_activity, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
	}

}
