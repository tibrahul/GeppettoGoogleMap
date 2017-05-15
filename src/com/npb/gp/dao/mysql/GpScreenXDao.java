package com.npb.gp.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.npb.gb.utils.GpGenericRecordParserBuilder;
import com.npb.gp.constants.GpUiTypeConstants;
import com.npb.gp.dao.mysql.support.screen.GpDto_screen_and_widgets;
import com.npb.gp.dao.mysql.support.screen.InsertScreen;
import com.npb.gp.dao.mysql.support.screen.InsertScreenWidget;
import com.npb.gp.dao.mysql.support.screen.ScreenBaseMapper;
import com.npb.gp.dao.mysql.support.screen.Screen_No_Children_Mapper;
import com.npb.gp.dao.mysql.support.screen.Screen_with_widget_mapper;
import com.npb.gp.dao.mysql.support.screen.UpdateScreen;
import com.npb.gp.dao.mysql.support.screen.UpdateScreenWidget;
import com.npb.gp.dao.mysql.support.screen.WidgetBaseMapper;
import com.npb.gp.domain.core.GpMenuScreenDetail;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUiWidgetX;
import com.npb.gp.interfaces.dao.IGpScreenXDao;

/**
 * 
 * @author Suresh Palanisamy</br>
 *         Date Created: 07/04/2015</br>
 * @since .75
 *        </p>
 * 
 *        Modified Date: 09/10/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variables as image_src to save the image sources
 * 
 *        Modified Date: 14/09/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new type under the handle_data_grid based on the New changes
 * 
 *        Modified Date: 10/09/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variables as header and footer for the component as card in
 *        under the widgets
 * 
 *        Modified Date: 04/09/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variable as target_url for the component as link in under
 *        the widgets
 * 
 * 
 *        The purpose of this class is to interact with the db for the basic
 *        search</br>
 *        and CRUD operations for a screen
 *        </p>
 * 
 * 
 *        Modified Date: 31/08/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new type under the single_section_container based on the
 *        Henrikh's changes
 * 
 *        Modified Date: 07/08/2015</br>
 *        Modified By: Kumaresan
 *        </p>
 * 
 *        Added menu builder codes in create and delete screen methods
 * 
 *        Modified Date: 05/08/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Removed the variable as columns and modified the insert and update
 *        methods for the widgets and reterival methods also.
 * 
 *        Modified Date: 01/07/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added the new variables as rows and columns
 * 
 *        Modified Date: 19/06/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Removed the variables X and Y
 * 
 *        Modified Date: 10/06/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variables as "deleted_widgets" in build screen method
 * 
 *        Modified Date: 04/06/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variable as css_class while inserting and updating widgets
 *        method
 * 
 *        Modified Date: 25/05/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Changed the is_container to Boolean and removed parent variable
 * 
 *        Added new variables as portraitOffsetX, portraitOffsetY,
 *        landscapeOffsetX and landscapeOffsetY
 * 
 *        Modified Date: 18/05/2015</br>
 *        Modified By: Kumaresan
 *        </p>
 * 
 *        The variable key was removed. Instead of the key, the children's ID
 *        was used to store the children's into the Map
 * 
 *        Modified Date: 12/05/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Changed the parent to String and Added new variable as parent_id
 * 
 *        Modified Date: 11/05/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variable as while retrieving the screens with children's in
 *        the build screen method
 * 
 *        <b>Modified Date: 06/05/2015<br>
 *        Modified By: Suresh Palanisamy<br>
 * 
 *        <p>
 *        Removed the User ID parameter in the insert_a_widget method
 *        </p>
 * 
 *        Modified Date: 04/05/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        <p>
 *        Modified the children's to HashMap. Solved the problems in the
 *        find_by_id method and the all sub methods
 *        </p>
 * 
 *        Modified Date: 16/04/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        <p>
 *        Modified the sec_noun_ids to array list
 *        </p>
 * 
 *        <b>Modified Date: 08/04/2015<br>
 *        Modified By: Suresh Palanisamy<br>
 * 
 *        <p>
 *        Added new method as get_widget_count to retrieve the last widget count
 *        </p>
 *
 */

@Repository("GpScreenXDao")
public class GpScreenXDao implements IGpScreenXDao {

	private Log LOG = LogFactory.getLog(getClass());

	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;

	private InsertScreen insert_screen;
	private InsertScreenWidget insert_widget;

	private UpdateScreen update_screen;
	private UpdateScreenWidget update_widget;

	@Value("${insert_screen.sql}")
	private String insertScreen;

	@Value("${insert_widget.sql}")
	private String insertWidget;

	@Value("${update_screen.sql}")
	private String updateScreen;

	@Value("${update_widget.sql}")
	private String updateWidget;

	@Value("${delete_screen.sql}")
	private String deleteScreen;

	@Value("${delete_widget.sql}")
	private String deleteWidget;

	@Value("${find_all_widgets_by_screen_id.sql}")
	private String find_all_widgets_by_screen_id_sql;

	@Value("${find_by_id.sql}")
	private String find_by_id;

	@Value("${find_all_base_by_projectid.sql}")
	private String find_all_base_by_projectid;

	@Value("${find_by_project_id.sql}")
	private String find_by_project_id;

	@Value("${find_base_by_activity_id.sql}")
	private String find_base_by_activity_id_sql;

	@Value("${find_by_activity_id.sql}")
	private String find_by_activity_id;

	@Value("${get_widget_count.sql}")
	private String get_widget_count;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private GpMenuBuilderDao menubuilder_dao;

	public GpMenuBuilderDao getMenubuilder_dao() {
		return menubuilder_dao;
	}

	@Resource(name = "GpMenuBuilderDao")
	public void setMenubuilder_dao(GpMenuBuilderDao menubuilder_dao) {
		this.menubuilder_dao = menubuilder_dao;
	}

	@Override
	public GpScreenX insert(GpScreenX ascreen) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", ascreen.getName());
		paramMap.put("description", ascreen.getDescription());
		paramMap.put("label", ascreen.getLabel());
		//SureshAnand Added role for INSERT role into db 
		paramMap.put("role", ascreen.getRole());
		paramMap.put("type", ascreen.getType());
		paramMap.put("notes", ascreen.getNotes());
		paramMap.put("height", ascreen.getHeight());
		paramMap.put("width", ascreen.getWidth());
		paramMap.put("client_device_type_id", ascreen.getClient_device_type_id());
		paramMap.put("client_device_type", ascreen.getClient_device_type());
		paramMap.put("client_device_type_name", ascreen.getClient_device_type_name());
		paramMap.put("client_device_type_label", ascreen.getClient_device_type_label());
		paramMap.put("client_device_type_description", ascreen.getClient_device_type_description());
		paramMap.put("client_device_screen_size", ascreen.getClient_device_screen_size());
		paramMap.put("client_device_resolution", ascreen.getClient_device_resolution());
		paramMap.put("client_device_ppcm", ascreen.getClient_device_ppcm());
		paramMap.put("client_device_type_os_name", ascreen.getClient_device_type_os_name());
		paramMap.put("client_device_type_os_version", ascreen.getClient_device_type_os_version());
		paramMap.put("landscape_image_name", ascreen.getLandscape_image_name());
		paramMap.put("portrait_image_name", ascreen.getPortrait_image_name());
		paramMap.put("current_orientation", ascreen.getOrientation());
		paramMap.put("is_orientation_locked", ascreen.getOrientation_locked());
		paramMap.put("activityid", ascreen.getActivity_id());
		paramMap.put("projectid", ascreen.getProjectid());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", ascreen.getCreatedby());
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", ascreen.getLastmodifiedby());
		paramMap.put("primary_noun_id", ascreen.getPrimary_noun_id());
		paramMap.put("client_language_type", ascreen.getClient_language_type());
		paramMap.put("client_library_type", ascreen.getClient_library_type());
		paramMap.put("event_verb_combo", ascreen.getEvent_verb_combo());
		String sec_noun_str = GpGenericRecordParserBuilder.buildDelimitedString(ascreen.getSecondary_noun_ids());
		paramMap.put("secondary_noun_ids", sec_noun_str);

		paramMap.put("human_language_id", ascreen.getHuman_language_id());
		paramMap.put("wizard_id", ascreen.getWizard_id());
		paramMap.put("screen_wizard_sequence_id", ascreen.getScreen_wizard_sequence_id());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertScreen.SQL_INSERT_SCREEN = insertScreen;
		this.insert_screen = new InsertScreen(this.dataSource);
		this.insert_screen.updateByNamedParam(paramMap, keyHolder);
		ascreen.setId(keyHolder.getKey().longValue());

		// Code for insert the creating screen to menu builder
		GpMenuScreenDetail screen = new GpMenuScreenDetail();
		screen.setId(ascreen.getId());
		screen.setName(ascreen.getName());
		screen.setLabel(ascreen.getLabel());
		screen.setRole(ascreen.getRole());
		screen.setDescription(ascreen.getDescription());
		screen.setActivity_id(ascreen.getActivity_id());
		screen.setAuth_id(0); // default value assigned
		screen.setProject_id(ascreen.getProjectid());
		this.menubuilder_dao.update_menu_screen_detail(screen);

		return ascreen;
	}

	@Override
	public GpScreenX update(GpScreenX ascreen) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", ascreen.getId());
		paramMap.put("name", ascreen.getName());
		paramMap.put("description", ascreen.getDescription());
		paramMap.put("label", ascreen.getLabel());
		paramMap.put("role", ascreen.getRole());
		paramMap.put("type", ascreen.getType());
		paramMap.put("notes", ascreen.getNotes());
		paramMap.put("height", ascreen.getHeight());
		paramMap.put("width", ascreen.getWidth());
		paramMap.put("client_device_type_id", ascreen.getClient_device_type_id());
		paramMap.put("client_device_type", ascreen.getClient_device_type());
		paramMap.put("client_device_type_name", ascreen.getClient_device_type_name());
		paramMap.put("client_device_type_label", ascreen.getClient_device_type_label());
		paramMap.put("client_device_type_description", ascreen.getClient_device_type_description());
		paramMap.put("client_device_screen_size", ascreen.getClient_device_screen_size());
		paramMap.put("client_device_resolution", ascreen.getClient_device_resolution());
		paramMap.put("client_device_ppcm", ascreen.getClient_device_ppcm());
		paramMap.put("client_device_type_os_name", ascreen.getClient_device_type_os_name());
		paramMap.put("client_device_type_os_version", ascreen.getClient_device_type_os_version());
		paramMap.put("landscape_image_name", ascreen.getLandscape_image_name());
		paramMap.put("portrait_image_name", ascreen.getPortrait_image_name());
		paramMap.put("current_orientation", ascreen.getOrientation());
		paramMap.put("is_orientation_locked", ascreen.getOrientation_locked());
		paramMap.put("activityid", ascreen.getActivity_id());
		paramMap.put("projectid", ascreen.getProjectid());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", ascreen.getCreatedby());
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", ascreen.getLastmodifiedby());
		paramMap.put("primary_noun_id", ascreen.getPrimary_noun_id());
		paramMap.put("client_language_type", ascreen.getClient_language_type());
		paramMap.put("client_library_type", ascreen.getClient_library_type());
		paramMap.put("event_verb_combo", ascreen.getEvent_verb_combo());
		String sec_noun_str = GpGenericRecordParserBuilder.buildDelimitedString(ascreen.getSecondary_noun_ids());
		paramMap.put("secondary_noun_ids", sec_noun_str);

		paramMap.put("human_language_id", ascreen.getHuman_language_id());
		paramMap.put("wizard_id", ascreen.getWizard_id());
		paramMap.put("screen_wizard_sequence_id", ascreen.getScreen_wizard_sequence_id());
		
		UpdateScreen.SQL_UPDATE_SCREEN = updateScreen;
		this.update_screen = new UpdateScreen(this.dataSource);
		this.update_screen.updateByNamedParam(paramMap);
		
		
		GpMenuScreenDetail screen = new GpMenuScreenDetail();
		screen.setId(ascreen.getId());
		screen.setName(ascreen.getName());
		screen.setLabel(ascreen.getLabel());
		screen.setDescription(ascreen.getDescription());
		screen.setActivity_id(ascreen.getActivity_id());
		screen.setAuth_id(0); // default value assigned
		screen.setProject_id(ascreen.getProjectid());
		this.menubuilder_dao.menu_builder_for_update_screen(screen);
		
		
		
		return ascreen;
	}

	@Override
	public void delete(long id) throws Exception {

		// Code for deleting a screen in menu builder
		this.menubuilder_dao.delete_menu_detail_screen(id);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);

		this.namedParameterJdbcTemplate.execute(this.deleteScreen, paramMap, new PreparedStatementCallback<Object>() {
			@Override
			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.executeUpdate();
			}
		});
	}

	@Override
	public GpUiWidgetX insert_a_widget(GpUiWidgetX awidget, long screen_id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", awidget.getName());
		paramMap.put("label", awidget.getLabel());
		paramMap.put("description", awidget.getDescription());
		paramMap.put("notes", awidget.getNotes());
		paramMap.put("parent_id", awidget.getParent_id());
		paramMap.put("parent_name", awidget.getParent_name());
		paramMap.put("screen_id", awidget.getScreen_id());
		paramMap.put("number_of_children", awidget.getNumber_of_children());
		paramMap.put("type", awidget.getType());
		paramMap.put("supports_label", awidget.getSupports_label());
		paramMap.put("is_container", awidget.getIs_container());
		paramMap.put("ui_technology", awidget.getUi_technology());
		paramMap.put("data_binding_context", awidget.getData_binding_context());
		paramMap.put("verb_binding_context", awidget.getVerb_binding_context());
		paramMap.put("noun_id", awidget.getNoun_id());
		paramMap.put("noun_attribute_id", awidget.getNoun_attribute_id());
		paramMap.put("extended_attributes", awidget.getExtended_attributes());
		paramMap.put("events", awidget.getEvents());
		paramMap.put("event_verb_combo", awidget.getEvent_verb_combo());
		paramMap.put("verb_target", awidget.getVerb_target());
		paramMap.put("width", awidget.getWidth());
		paramMap.put("height", awidget.getHeight());
		// paramMap.put("x", awidget.getX());
		// paramMap.put("y", awidget.getY());
		paramMap.put("portrait_x", awidget.getPortraitX());
		paramMap.put("portrait_y", awidget.getPortraitY());
		paramMap.put("portrait_height", awidget.getPortrait_height());
		paramMap.put("portrait_width", awidget.getPortrait_width());
		paramMap.put("landscape_x", awidget.getLandscapeX());
		paramMap.put("landscape_y", awidget.getLandscapeY());
		paramMap.put("landscape_height", awidget.getLandscape_height());
		paramMap.put("landscape_width", awidget.getLandscape_width());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", awidget.getCreatedby());
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", awidget.getLastmodifiedby());
		paramMap.put("portrait_offset_x", awidget.getPortraitOffsetX());
		paramMap.put("portrait_offset_y", awidget.getPortraitOffsetY());
		paramMap.put("landscape_offset_x", awidget.getLandscapeOffsetX());
		paramMap.put("landscape_offset_y", awidget.getLandscapeOffsetY());
		paramMap.put("css_class", awidget.getCss_class());
		paramMap.put("rows", awidget.getRows());
		// paramMap.put("columns", awidget.getColumns());
		paramMap.put("target_url", awidget.getTarget_url());
		paramMap.put("header", awidget.getHeader());
		paramMap.put("footer", awidget.getFooter());
		paramMap.put("image_src", awidget.getImage_src());
		paramMap.put("extra_verb_info", awidget.getExtra_verb_info());
		paramMap.put("custom_verb_info", awidget.getCustom_verb_info());
		paramMap.put("wizard_id", awidget.getWizard_id());
		paramMap.put("screen_wizard_sequence_id", awidget.getScreen_wizard_sequence_id());
		paramMap.put("data_binding_target_noun_id", awidget.getData_binding_target_noun_id());
		paramMap.put("data_binding_target_noun_attr_id", awidget.getData_binding_target_noun_attr_id());
		paramMap.put("section_position", awidget.getSection_position());
		paramMap.put("group_values", awidget.getGroup_values());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertScreenWidget.SQL_INSERT_WIDGET = insertWidget;
		this.insert_widget = new InsertScreenWidget(this.dataSource);
		this.insert_widget.updateByNamedParam(paramMap, keyHolder);
		awidget.setId(keyHolder.getKey().longValue());
		return awidget;
	}

	@Override
	public GpUiWidgetX update_a_widget(GpUiWidgetX awidget, long screen_id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", awidget.getId());
		paramMap.put("name", awidget.getName());
		paramMap.put("label", awidget.getLabel());
		paramMap.put("description", awidget.getDescription());
		paramMap.put("notes", awidget.getNotes());
		paramMap.put("parent_id", awidget.getParent_id());
		paramMap.put("parent_name", awidget.getParent_name());
		paramMap.put("screen_id", awidget.getScreen_id());
		paramMap.put("number_of_children", awidget.getNumber_of_children());
		paramMap.put("type", awidget.getType());
		paramMap.put("supports_label", awidget.getSupports_label());
		paramMap.put("is_container", awidget.getIs_container());
		paramMap.put("ui_technology", awidget.getUi_technology());
		paramMap.put("data_binding_context", awidget.getData_binding_context());
		paramMap.put("verb_binding_context", awidget.getVerb_binding_context());
		paramMap.put("noun_id", awidget.getNoun_id());
		paramMap.put("noun_attribute_id", awidget.getNoun_attribute_id());
		paramMap.put("extended_attributes", awidget.getExtended_attributes());
		paramMap.put("events", awidget.getEvents());
		paramMap.put("event_verb_combo", awidget.getEvent_verb_combo());
		paramMap.put("verb_target", awidget.getVerb_target());
		paramMap.put("width", awidget.getWidth());
		paramMap.put("height", awidget.getHeight());
		// paramMap.put("x", awidget.getX());
		// paramMap.put("y", awidget.getY());
		paramMap.put("portrait_x", awidget.getPortraitX());
		paramMap.put("portrait_y", awidget.getPortraitY());
		paramMap.put("portrait_height", awidget.getPortrait_height());
		paramMap.put("portrait_width", awidget.getPortrait_width());
		paramMap.put("landscape_x", awidget.getLandscapeX());
		paramMap.put("landscape_y", awidget.getLandscapeY());
		paramMap.put("landscape_height", awidget.getLandscape_height());
		paramMap.put("landscape_width", awidget.getLandscape_width());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", awidget.getCreatedby());
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", awidget.getLastmodifiedby());
		paramMap.put("portrait_offset_x", awidget.getPortraitOffsetX());
		paramMap.put("portrait_offset_y", awidget.getPortraitOffsetY());
		paramMap.put("landscape_offset_x", awidget.getLandscapeOffsetX());
		paramMap.put("landscape_offset_y", awidget.getLandscapeOffsetY());
		paramMap.put("css_class", awidget.getCss_class());
		paramMap.put("rows", awidget.getRows());
		// paramMap.put("columns", awidget.getColumns());
		paramMap.put("target_url", awidget.getTarget_url());
		paramMap.put("header", awidget.getHeader());
		paramMap.put("footer", awidget.getFooter());
		paramMap.put("image_src", awidget.getImage_src());
		paramMap.put("extra_verb_info", awidget.getExtra_verb_info());
		paramMap.put("custom_verb_info", awidget.getCustom_verb_info());
		paramMap.put("wizard_id", awidget.getWizard_id());
		paramMap.put("screen_wizard_sequence_id", awidget.getScreen_wizard_sequence_id());
		paramMap.put("data_binding_target_noun_id", awidget.getData_binding_target_noun_id());
		paramMap.put("data_binding_target_noun_attr_id", awidget.getData_binding_target_noun_attr_id());
		paramMap.put("section_position", awidget.getSection_position());
		paramMap.put("group_values", awidget.getGroup_values());

		UpdateScreenWidget.SQL_UPDATE_WIDGET = updateWidget;
		this.update_widget = new UpdateScreenWidget(this.dataSource);
		this.update_widget.updateByNamedParam(paramMap);

		return awidget;
	}

	@Override
	public void delete_a_widget(long id, long screen_id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("screen_id", screen_id);

		this.namedParameterJdbcTemplate.execute(this.deleteWidget, paramMap, new PreparedStatementCallback<Object>() {
			@Override
			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.executeUpdate();
			}
		});
	}

	@Override
	public Map<String, GpUiWidgetX> find_all_widgets_by_screen(long screen_id) {
		WidgetBaseMapper widget_base_mapper = new WidgetBaseMapper();
		Map<String, GpUiWidgetX> the_widgets = new HashMap<String, GpUiWidgetX>();

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("screen_id", screen_id);

		List<GpUiWidgetX> widgets_list = this.namedParameterJdbcTemplate.query(this.find_all_widgets_by_screen_id_sql,
				parameters, widget_base_mapper);

		if (widgets_list.size() > 0) {
			for (GpUiWidgetX gpUiWidgetX : widgets_list) {
				the_widgets.put(Long.toString(gpUiWidgetX.getId()), gpUiWidgetX);
			}
		}

		return the_widgets;
	}

	@Override
	public GpScreenX find_by_id(long screen_id) throws Exception {

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", screen_id);

		Screen_with_widget_mapper screen_and_widget_mapper = new Screen_with_widget_mapper();
		List<GpDto_screen_and_widgets> dto_list = this.namedParameterJdbcTemplate.query(this.find_by_id, parameters,
				screen_and_widget_mapper);

		return this.build_screen(dto_list);
	}

	@Override
	public ArrayList<GpScreenX> find_by_activity_id(long activity_id) throws Exception {
		MapSqlParameterSource parameters;

		parameters = new MapSqlParameterSource();
		parameters.addValue("id", activity_id);

		Screen_No_Children_Mapper screen_and_widget_mapper = new Screen_No_Children_Mapper();
		List<GpScreenX> screen_list = this.namedParameterJdbcTemplate.query(this.find_by_activity_id, parameters,
				screen_and_widget_mapper);

		/*
		 * if (screen_list.size() < 1) { throw new Exception(
		 * "no screens where found for activity id:  " + activity_id, new
		 * Throwable("99")); }
		 */

		return (ArrayList<GpScreenX>) screen_list;
	}

	@Override
	public ArrayList<GpScreenX> find_all_base_by_activity_id(long activity_id) throws Exception {

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("activity_id", activity_id);

		ScreenBaseMapper screen_mapper = new ScreenBaseMapper();

		List<GpScreenX> screen_list = this.namedParameterJdbcTemplate.query(this.find_base_by_activity_id_sql,
				parameters, screen_mapper);

		if (screen_list.size() < 1) {
			return new ArrayList<GpScreenX>();
		}

		return (ArrayList<GpScreenX>) screen_list;
	}

	@Override
	public ArrayList<GpScreenX> find_by_project_id(long project_id) throws Exception {

		MapSqlParameterSource parameters;

		parameters = new MapSqlParameterSource();
		parameters.addValue("id", project_id);

		Screen_No_Children_Mapper screen_and_widget_mapper = new Screen_No_Children_Mapper();
		List<GpScreenX> screen_list = this.namedParameterJdbcTemplate.query(this.find_by_project_id, parameters,
				screen_and_widget_mapper);

		/*
		 * if (screen_list.size() < 1) { throw new Exception(
		 * "no screens where found for project id:  " + project_id, new
		 * Throwable("99")); }
		 */

		return (ArrayList<GpScreenX>) screen_list;
	}

	@Override
	public List<GpScreenX> find_all_base_by_projectid(long projectid) throws Exception {

		MapSqlParameterSource parameters;

		parameters = new MapSqlParameterSource();
		parameters.addValue("projectid", projectid);

		ScreenBaseMapper screen_base_mapper = new ScreenBaseMapper();
		List<GpScreenX> screen_list = this.namedParameterJdbcTemplate.query(this.find_all_base_by_projectid, parameters,
				screen_base_mapper);

		if (screen_list.size() < 1) {
			return new ArrayList<GpScreenX>();
		}

		return screen_list;
	}

	private GpScreenX build_screen(List<GpDto_screen_and_widgets> dto_list) {

		GpScreenX the_screen = new GpScreenX();
		GpDto_screen_and_widgets a_dto = dto_list.get(0);
		the_screen.setId(a_dto.getScreen_id());
		the_screen.setName(a_dto.getScreen_name());
		the_screen.setLabel(a_dto.getScreen_label());
		// SureshAnand 
		 		// Set the role detail if the user edit the screen
		the_screen.setRole(a_dto.getScreen_role());
		the_screen.setDescription(a_dto.getScreen_description());
		the_screen.setType(a_dto.getScreen_type());
		the_screen.setActivity_id(a_dto.getScreen_activity_id());
		the_screen.setProjectid(a_dto.getScreen_projectid());
		the_screen.setClient_device_type(a_dto.getScreen_client_device_type());
		the_screen.setHuman_language_id(a_dto.getScreen_human_language_id());
		the_screen.setClient_device_type_id(a_dto.getScreen_client_device_type_id());
		the_screen.setOrientation(a_dto.getScreen_current_orientation());
		the_screen.setOrientation_locked(a_dto.isScreen_is_orientation_locked());
		the_screen.setClient_device_type_label(a_dto.getScreen_client_device_type_label());
		the_screen.setNotes(a_dto.getScreen_notes());
		the_screen.setCreatedate(a_dto.getScreen_createdate());
		the_screen.setCreatedby(a_dto.getScreen_createdby());
		the_screen.setLastmodifieddate(a_dto.getScreen_lastmodifieddate());
		the_screen.setLastmodifiedby(a_dto.getScreen_lastmodifiedby());
		the_screen.setLandscape_image_name(a_dto.getScreen_landscape_image_name());
		the_screen.setPortrait_image_name(a_dto.getScreen_portrait_image_name());
		the_screen.setPrimary_noun_id(a_dto.getScreen_primary_noun_id());
		the_screen.setEvent_verb_combo(a_dto.getScreen_event_verb_combo());
		the_screen.setWizard_id(a_dto.getScreen_wizard_id());
		the_screen.setScreen_wizard_sequence_id(a_dto.getScreen_wizard_sequence_id());

		ArrayList<String> sec_noun_ids = new ArrayList<String>();
		if (a_dto.getScreen_secondary_noun_ids() != null) {
			String[] secondary_noun_ids = GpGenericRecordParserBuilder
					.parseDelimitedString(a_dto.getScreen_secondary_noun_ids());

			for (String str : secondary_noun_ids) {
				sec_noun_ids.add(str);
			}
		}
		the_screen.setSecondary_noun_ids(sec_noun_ids);

		the_screen.setClient_device_type_os_name(a_dto.getScreen_client_device_type_os_name());
		the_screen.setClient_device_type_name(a_dto.getScreen_client_device_type_name());
		the_screen.setClient_device_type_description(a_dto.getScreen_client_device_type_description());
		the_screen.setClient_device_screen_size(a_dto.getScreen_client_device_screen_size());
		the_screen.setClient_device_resolution(a_dto.getScreen_client_device_resolution());
		the_screen.setClient_device_ppcm(a_dto.getScreen_client_device_ppcm());
		the_screen.setClient_device_type_os_version(a_dto.getScreen_client_device_type_os_version());
		the_screen.setClient_language_type(a_dto.getScreen_client_language_type());
		the_screen.setClient_library_type(a_dto.getScreen_client_library_type());
		the_screen.setHeight(a_dto.getScreen_height());
		the_screen.setWidth(a_dto.getScreen_width());

		the_screen.setComponents(new ArrayList<GpUiWidgetX>());
		the_screen.setDeleted_widgets(new ArrayList<Long>());

		// this.handle_widgets(the_screen, dto_list, i);
		this.handle_screen_children(the_screen, dto_list);
		for (Entry<String, GpUiWidgetX> a_widget : the_screen.getChildren().entrySet()) {
			if (a_widget.getValue().getIs_container()) {
				this.handle_widget_children(the_screen, dto_list, a_widget.getValue());
			} else {
				a_widget.getValue().setChildren(new HashMap<String, GpUiWidgetX>());
			}
		}
		return the_screen;
	}

	// this adds all the widgets that are direct children of the screen
	private void handle_screen_children(GpScreenX the_screen, List<GpDto_screen_and_widgets> dto_list) {

		Map<String, GpUiWidgetX> a_child = new HashMap<String, GpUiWidgetX>();

		for (GpDto_screen_and_widgets the_dto : dto_list) {
			if (the_dto.getWidget_parent_id() == the_screen.getId()) {
				GpUiWidgetX the_widget = new GpUiWidgetX();
				this.handle_common_widget_properties(the_dto, the_widget);
				a_child.put(Long.toString(the_widget.getId()), the_widget);
			}
		}
		the_screen.setChildren(a_child);
	}

	private void handle_widget_children(GpScreenX the_screen, List<GpDto_screen_and_widgets> dto_list,
			GpUiWidgetX the_container) {

		for (Entry<String, GpUiWidgetX> the_widget : the_screen.getChildren().entrySet()) {

			the_widget.getValue().setChildren(new HashMap<String, GpUiWidgetX>());
			if (the_widget.getValue().getType().equals(GpUiTypeConstants.GpTabBarContainer)
					|| the_widget.getValue().getType().equals(GpUiTypeConstants.GpAccordionContainer)) {
				this.handle_multi_section_container(the_widget.getValue(), dto_list);

			} else if (the_widget.getValue().getType().equals(GpUiTypeConstants.GpBorderContainer)
					|| the_widget.getValue().getType().equals(GpUiTypeConstants.GpPanel)
					|| the_widget.getValue().getType().equals(GpUiTypeConstants.GpCard)) {
				this.handle_single_section_container(the_widget.getValue(), dto_list);

			} else if (the_widget.getValue().getType().equals(GpUiTypeConstants.GpDataGrid)
					|| the_widget.getValue().getType().equals(GpUiTypeConstants.GpList)
					|| the_widget.getValue().getType().equals(GpUiTypeConstants.GpRadioButton)) {
				this.handle_data_grid(the_widget.getValue(), dto_list);
			}
		}
	}

	/**
	 * 
	 * @param the_screen
	 * @param the_container
	 * @param dto_list
	 *            </p>
	 *            I realize that this can be combined with the
	 *            single_section_container method but</br>
	 *            I want to be able to have flexibility to add specific
	 *            functionality by container
	 *            </p>
	 */

	private void handle_multi_section_container(GpUiWidgetX the_container, List<GpDto_screen_and_widgets> dto_list) {

		// first find sections to the main parent control

		Map<String, GpUiWidgetX> a_multi_section = new HashMap<String, GpUiWidgetX>();
		
		for (GpDto_screen_and_widgets a_dto : dto_list) {

			if (a_dto.getWidget_parent_id() == the_container.getId()) {
				GpUiWidgetX a_section = new GpUiWidgetX();
				this.handle_common_widget_properties(a_dto, a_section);
				a_section.setChildren(new HashMap<String, GpUiWidgetX>());
				/*if (the_container.getType().equals(GpUiTypeConstants.GpAccordionContainer))
				{
					a_multi_section.put(Long.toString(a_section.getSection_position()), a_section);
				}else{
					a_multi_section.put(Long.toString(a_section.getId()), a_section);
				}*/
				//a_multi_section.put(Long.toString(a_section.getId()), a_section);
				a_multi_section.put(Long.toString(a_section.getSection_position()), a_section);
			}
		}
		the_container.setChildren(a_multi_section);

		// second for each section find its children and add to section

		for (Entry<String, GpUiWidgetX> a_section : the_container.getChildren().entrySet()) {

			for (GpDto_screen_and_widgets a_dto : dto_list) {

				if (a_dto.getWidget_parent_id() == a_section.getValue().getId()) {
					System.out.println("@@@@@@@  FOUND SECTION CHILD @@@@@@" + "\n section id is: "
							+ a_section.getValue().getId() + "\n section name is: " + a_section.getValue().getName());
					GpUiWidgetX a_widget = new GpUiWidgetX();
					this.handle_common_widget_properties(a_dto, a_widget);
					a_widget.setChildren(new HashMap<String, GpUiWidgetX>());
					if (a_dto.getWidget_type().equals(GpUiTypeConstants.GpDataGrid) || a_dto.getWidget_type().equals(GpUiTypeConstants.GpList)
							|| a_dto.getWidget_type().equals(GpUiTypeConstants.GpRadioButton)) {
						this.handle_data_grid(a_widget, dto_list);
					}
					System.out.println("Adding found child to section " + "\n a_widget.getName() is: "
							+ a_widget.getName() + "\n a_widget.getType()" + a_widget.getType());
					a_section.getValue().getChildren().put(Long.toString(a_widget.getId()), a_widget);
				}
			}
		}
	}

	/**
	 * 
	 * @param the_screen
	 * @param the_container
	 * @param dto_list
	 *            </p>
	 *            I realize that this can be combined with the
	 *            handle_multi_section_container method but</br>
	 *            I want to be able to have flexibility to add specific
	 *            functionality by container
	 *            </p>
	 */
	private void handle_single_section_container(GpUiWidgetX the_container, List<GpDto_screen_and_widgets> dto_list) {

		Map<String, GpUiWidgetX> a_section_container = new HashMap<String, GpUiWidgetX>();

		for (GpDto_screen_and_widgets a_dto : dto_list) {

			if (a_dto.getWidget_parent_id() == the_container.getId()) {

				GpUiWidgetX a_widget = new GpUiWidgetX();
				this.handle_common_widget_properties(a_dto, a_widget);

				if (a_dto.getWidget_type().equals(GpUiTypeConstants.GpDataGrid) || a_dto.getWidget_type().equals(GpUiTypeConstants.GpList)
				|| a_dto.getWidget_type().equals(GpUiTypeConstants.GpRadioButton)) {
					a_widget.setChildren(new HashMap<String, GpUiWidgetX>());
					this.handle_data_grid(a_widget, dto_list); // add all the
																// grids columns
				}
				a_section_container.put(Long.toString(a_widget.getId()), a_widget);
			}
		}
		the_container.setChildren(a_section_container);
	}

	/**
	 * 
	 * @param the_screen
	 * @param the_container
	 * @param dto_list
	 *            </p>
	 *            I realize that this can be combined with the
	 *            single_section_container method but</br>
	 *            I think that Grids will have more specific processing in the
	 *            future
	 *            <p>
	 */
	private void handle_data_grid(GpUiWidgetX the_container, List<GpDto_screen_and_widgets> dto_list) {

		ArrayList<GpUiWidgetX> a_data_grid = new ArrayList<GpUiWidgetX>();

		for (GpDto_screen_and_widgets a_dto : dto_list) {
			if (a_dto.getWidget_parent_id() == the_container.getId()) {
				GpUiWidgetX a_widget = new GpUiWidgetX();
				this.handle_common_widget_properties(a_dto, a_widget);
				a_data_grid.add(a_widget);
			}
		}
		the_container.setColumns(a_data_grid);
	}

	private void handle_common_widget_properties(GpDto_screen_and_widgets dto_source, GpUiWidgetX widget_target) {

		widget_target.setId(dto_source.getWidget_id());
		widget_target.setName(dto_source.getWidget_name());
		widget_target.setLabel(dto_source.getWidget_label());
		widget_target.setDescription(dto_source.getWidget_description());
		widget_target.setParent_id(dto_source.getWidget_parent_id());
		widget_target.setIs_container(dto_source.getWidget_is_container());
		widget_target.setSupports_label(dto_source.getWidget_supports_label());
		widget_target.setUi_technology(dto_source.getWidget_ui_technology());
		widget_target.setWidth(dto_source.getWidget_width());
		widget_target.setHeight(dto_source.getWidget_height());
		// widget_target.setX(dto_source.getWidget_x());
		// widget_target.setY(dto_source.getWidget_y());
		widget_target.setNotes(dto_source.getWidget_notes());
		widget_target.setData_binding_context(dto_source.getWidget_data_binding_context());
		widget_target.setVerb_binding_context(dto_source.getWidget_verb_binding_context());
		widget_target.setNoun_id(dto_source.getWidget_noun_id());
		widget_target.setNoun_attribute_id(dto_source.getWidget_noun_attribute_id());
		widget_target.setType(dto_source.getWidget_type());
		widget_target.setParent_name(dto_source.getWidget_parent_name());

		widget_target.setNumber_of_children(dto_source.getWidget_number_of_children()); // maybe
																						// we
																						// dont
																						// need
																						// this

		widget_target.setExtended_attributes(dto_source.getWidget_extended_attributes());
		widget_target.setEvent_verb_combo(dto_source.getWidget_event_verb_combo());
		widget_target.setVerb_target(dto_source.getWidget_verb_target());
		widget_target.setPortraitX(dto_source.getWidget_portrait_x());
		widget_target.setPortraitY(dto_source.getWidget_portrait_y());
		widget_target.setPortrait_height(dto_source.getWidget_portrait_height());
		widget_target.setPortrait_width(dto_source.getWidget_portrait_width());
		widget_target.setLandscapeX(dto_source.getWidget_landscape_x());
		widget_target.setLandscapeY(dto_source.getWidget_landscape_y());
		widget_target.setLandscape_height(dto_source.getWidget_landscape_height());
		widget_target.setLandscape_width(dto_source.getWidget_landscape_width());
		widget_target.setCreatedate(dto_source.getWidget_createdate());
		widget_target.setCreatedby(dto_source.getWidget_createdby());
		widget_target.setLastmodifiedby(dto_source.getWidget_lastmodifiedby());
		widget_target.setLastmodifieddate(dto_source.getWidget_lastmodifieddate());
		widget_target.setScreen_id(dto_source.getScreen_id());
		widget_target.setEvents(dto_source.getWidget_events());
		widget_target.setPortraitOffsetX(dto_source.getWidget_portraitOffsetX());
		widget_target.setPortraitOffsetY(dto_source.getWidget_portraitOffsetY());
		widget_target.setLandscapeOffsetX(dto_source.getWidget_landscapeOffsetX());
		widget_target.setLandscapeOffsetY(dto_source.getWidget_landscapeOffsetY());
		widget_target.setCss_class(dto_source.getWidget_css_class());
		widget_target.setRows(dto_source.getWidget_rows());
		// widget_target.setColumns(dto_source.getWidget_columns());
		widget_target.setTarget_url(dto_source.getWidget_target_url());
		widget_target.setHeader(dto_source.getWidget_header());
		widget_target.setFooter(dto_source.getWidget_footer());
		widget_target.setImage_src(dto_source.getWidget_image_src());
		widget_target.setExtra_verb_info(dto_source.getWidget_extra_verb_info());
		widget_target.setCustom_verb_info(dto_source.getWidget_custom_verb_info());
		widget_target.setData_binding_target_noun_id(dto_source.getWidget_data_binding_target_noun_id());
		widget_target.setData_binding_target_noun_attr_id(dto_source.getWidget_data_binding_target_noun_attr_id());
		widget_target.setSection_position(dto_source.getWidget_section_position());
		widget_target.setGroup_values(dto_source.getGroup_values());
	}

	@Override
	public long get_widget_count() throws Exception {
		return this.jdbcTemplate.queryForObject(this.get_widget_count, Long.class);
	}
}
