package com.npb.gp.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

import com.npb.gp.constants.GpUiTypeConstants;
import com.npb.gp.dao.mysql.support.screen.GpDto_screen_and_widgets;
import com.npb.gp.dao.mysql.support.screen.InsertScreen;
import com.npb.gp.dao.mysql.support.screen.InsertScreenWidget;
import com.npb.gp.dao.mysql.support.screen.ScreenBaseMapper;
import com.npb.gp.dao.mysql.support.screen.Screen_No_Children_Mapper;
import com.npb.gp.dao.mysql.support.screen.Screen_with_widget_mapper;
import com.npb.gp.dao.mysql.support.screen.UpdateScreen;
import com.npb.gp.dao.mysql.support.screen.UpdateScreenFewParams;
import com.npb.gp.dao.mysql.support.screen.UpdateScreenWidget;
import com.npb.gp.dao.mysql.support.screen.WidgetBaseMapper;
import com.npb.gp.domain.core.GpMenuDetail;
import com.npb.gp.domain.core.GpMenuScreenDetail;
import com.npb.gp.domain.core.GpScreen;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUiWidget;
import com.npb.gp.domain.core.GpUiWidgetX;
import com.npb.gp.interfaces.dao.IGpScreenDao;

/**
 * 
 * @author Dan Castillo</br> Date Created: 03/14/2014</br>
 * @since .35</p>
 *
 *        The purpose of this class is to interact with the db for the basic
 *        search</br> and CRUD operations for a screeny</p>
 *
 *        please note that a form of this class has been in use since version
 *        .10 of the</br> Geppetto system. The .10 version is also known as
 *        "Cancun"</p>
 *
 *        Modified Date: 07/04/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        <p>
 *        Modified the some methods and hidden the some codes in that methods
 *        and added comment text on top the commented codes
 *        </p>
 *
 *
 *        Modified Date: 10/22/2014</br> Modified By: Dan Castillo</p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex **
 *
 *        Modified Date: 19/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        modified the insert_screen and insert_a_widget methods to insert the
 *        screen and its children values
 *
 *        Modified Date: 24/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        removed the font_size variable and removed from the query for
 *        temporarily
 *
 *        Modified Date: 26/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Wrote the new method as update_screen, delete screen, update_widget
 *        and delete_widget methods for update and delete the screens
 * 
 *        Wrote the new method to fetch the widgets by using the screen_id
 * 
 *        Modified Date: 02/04/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Temporarily hidden the if condition in find_all_base_by_activity_id
 *        method
 *
 */
@Repository("GpScreenDao")
public class GpScreenDao implements IGpScreenDao {

	private Log LOG = LogFactory.getLog(getClass());

	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${insert_screen.sql}")
	private String insertScreen;

	@Value("${insert_widget.sql}")
	private String insertWidget;

	@Value("${update_screen.sql}")
	private String updateScreen;

	@Value("${update_screen_through_menu.sql}")
	private String update_screen_through_menu;
	
	@Value("${update_widget.sql}")
	private String updateWidget;

	@Value("${delete_screen.sql}")
	private String deleteScreen;

	@Value("${delete_widget.sql}")
	private String deleteWidget;

	@Value("${find_all_widgets_by_screen.sql}")
	private String findAllWidgetsByScreen;

	@Value("${get_base_by_activity_id.sql}")
	private String get_base_by_activity_id_sql;

	private InsertScreen insert_screen;
	private InsertScreenWidget insert_widget;

	private UpdateScreen update_screen;
	private UpdateScreenFewParams updateScreenFewParams;
	private UpdateScreenWidget update_widget;

	private GpUiWidget immediate_parent;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public GpScreenX insert(GpScreenX ascreen) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", ascreen.getName());
		paramMap.put("description", ascreen.getDescription());
		paramMap.put("label", ascreen.getLabel());
		paramMap.put("type", ascreen.getType());
		paramMap.put("role",ascreen.getRole());
		paramMap.put("notes", ascreen.getNotes());
		paramMap.put("height", ascreen.getHeight());
		paramMap.put("width", ascreen.getWidth());
		paramMap.put("client_device_type_id",
				ascreen.getClient_device_type_id());
		paramMap.put("client_device_type", ascreen.getClient_device_type());
		paramMap.put("client_device_type_name",
				ascreen.getClient_device_type_name());
		paramMap.put("client_device_type_label",
				ascreen.getClient_device_type_label());
		paramMap.put("client_device_type_description",
				ascreen.getClient_device_type_description());
		paramMap.put("client_device_screen_size",
				ascreen.getClient_device_screen_size());
		paramMap.put("client_device_resolution",
				ascreen.getClient_device_resolution());
		paramMap.put("client_device_ppcm", ascreen.getClient_device_ppcm());
		paramMap.put("client_device_type_os_name",
				ascreen.getClient_device_type_os_name());
		paramMap.put("client_device_type_os_version",
				ascreen.getClient_device_type_os_version());
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

		// String sec_noun_str =
		// GpGenericRecordParserBuilder.buildDelimitedString(ascreen.getSecondary_noun_ids());
		// paramMap.put("secondary_noun_ids", sec_noun_str);

		/* Temp */
		paramMap.put("secondary_noun_ids", ";");

		paramMap.put("human_language_id", ascreen.getHuman_language_id());

		paramMap.put("wizard_id", ascreen.getWizard_id());
		
		paramMap.put("screen_wizard_sequence_id", ascreen.getScreen_wizard_sequence_id());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertScreen.SQL_INSERT_SCREEN = insertScreen;
		this.insert_screen = new InsertScreen(this.dataSource);
		this.insert_screen.updateByNamedParam(paramMap, keyHolder);
		ascreen.setId(keyHolder.getKey().longValue());
		return ascreen;
	}

	@Override
	public GpUiWidgetX insert_a_widget(GpUiWidgetX awidget, long screen_id,
			long user_id) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", awidget.getName());
		paramMap.put("label", awidget.getLabel());
		paramMap.put("description", awidget.getDescription());
		paramMap.put("notes", awidget.getNotes());
		// paramMap.put("parentid", awidget.getParent());
		// paramMap.put("parent", awidget.getParent());
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
		// paramMap.put("font_size", awidget.getFontSize());
		// paramMap.put("x", awidget.getX());
		// paramMap.put("y", awidget.getY());
		paramMap.put("portrait_x", awidget.getPortraitX());
		paramMap.put("portrait_y", awidget.getPortraitY());
		paramMap.put("portrait_width", new Long(awidget.getHeight()).toString());
		paramMap.put("portrait_height",
				new Long(awidget.getHeight()).toString());
		paramMap.put("landscape_x", awidget.getLandscapeX());
		paramMap.put("landscape_y", awidget.getLandscapeY());
		paramMap.put("landscape_width",
				new Long(awidget.getLandscapeX()).toString());
		paramMap.put("landscape_height",
				new Long(awidget.getLandscapeY()).toString());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", user_id);
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", user_id);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertScreenWidget.SQL_INSERT_WIDGET = insertWidget;
		this.insert_widget = new InsertScreenWidget(this.dataSource);
		this.insert_widget.updateByNamedParam(paramMap, keyHolder);
		awidget.setId(keyHolder.getKey().longValue());
		return awidget;
	}

	@Override
	public GpScreenX update(GpScreenX ascreen) {
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
		paramMap.put("client_device_type_id",
				ascreen.getClient_device_type_id());
		paramMap.put("client_device_type", ascreen.getClient_device_type());
		paramMap.put("client_device_type_name",
				ascreen.getClient_device_type_name());
		paramMap.put("client_device_type_label",
				ascreen.getClient_device_type_label());
		paramMap.put("client_device_type_description",
				ascreen.getClient_device_type_description());
		paramMap.put("client_device_screen_size",
				ascreen.getClient_device_screen_size());
		paramMap.put("client_device_resolution",
				ascreen.getClient_device_resolution());
		paramMap.put("client_device_ppcm", ascreen.getClient_device_ppcm());
		paramMap.put("client_device_type_os_name",
				ascreen.getClient_device_type_os_name());
		paramMap.put("client_device_type_os_version",
				ascreen.getClient_device_type_os_version());
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

		// String sec_noun_str =
		// GpGenericRecordParserBuilder.buildDelimitedString(ascreen.getSecondary_noun_ids());
		// paramMap.put("secondary_noun_ids", sec_noun_str);

		/* Temp */
		paramMap.put("secondary_noun_ids", ";");

		paramMap.put("human_language_id", ascreen.getHuman_language_id());
		paramMap.put("wizard_id", ascreen.getWizard_id());
		paramMap.put("screen_wizard_sequence_id", ascreen.getScreen_wizard_sequence_id());

		UpdateScreen.SQL_UPDATE_SCREEN = updateScreen;
		this.update_screen = new UpdateScreen(this.dataSource);
		this.update_screen.updateByNamedParam(paramMap);
		return ascreen;
	}

	@Override
	public void update_screen_through_menubuilder(ArrayList<GpMenuDetail> menu_details) {
		for (GpMenuDetail gpMenu_detail : menu_details) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			List<GpMenuScreenDetail> screendetails = gpMenu_detail.getScreen_detail();
			for (GpMenuScreenDetail menunewdetails : screendetails) {
			paramMap.put("id", menunewdetails.getId());
			paramMap.put("name", menunewdetails.getName());
			paramMap.put("description", menunewdetails.getDescription());
			paramMap.put("label", menunewdetails.getLabel());
			paramMap.put("activityid", menunewdetails.getActivity_id());

			UpdateScreenFewParams.SQL_UPDATE_SCREEN = update_screen_through_menu;
			this.updateScreenFewParams = new UpdateScreenFewParams(this.dataSource);
			this.updateScreenFewParams.updateByNamedParam(paramMap);
			}
		}
	}

	@Override
	public GpUiWidgetX update_a_widget(GpUiWidgetX awidget, long screen_id,
			long user_id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", awidget.getId());
		paramMap.put("name", awidget.getName());
		paramMap.put("label", awidget.getLabel());
		paramMap.put("description", awidget.getDescription());
		paramMap.put("notes", awidget.getNotes());
		// paramMap.put("parentid", awidget.getParent());
		// paramMap.put("parent", awidget.getParent());
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
		// paramMap.put("font_size", awidget.getFontSize());
		// paramMap.put("x", awidget.getX());
		// paramMap.put("y", awidget.getY());
		paramMap.put("portrait_x", awidget.getPortraitX());
		paramMap.put("portrait_y", awidget.getPortraitY());
		paramMap.put("portrait_width", new Long(awidget.getHeight()).toString());
		paramMap.put("portrait_height",
				new Long(awidget.getHeight()).toString());
		paramMap.put("landscape_x", awidget.getLandscapeX());
		paramMap.put("landscape_y", awidget.getLandscapeY());
		paramMap.put("landscape_width",
				new Long(awidget.getLandscapeX()).toString());
		paramMap.put("landscape_height",
				new Long(awidget.getLandscapeY()).toString());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", user_id);
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", user_id);

		UpdateScreenWidget.SQL_UPDATE_WIDGET = updateWidget;
		this.update_widget = new UpdateScreenWidget(this.dataSource);
		this.update_widget.updateByNamedParam(paramMap);

		return awidget;
	}

	@Override
	public void delete(long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);

		this.namedParameterJdbcTemplate.execute(deleteScreen, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
	}

	@Override
	public void delete_a_widget(long id, long screen_id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("screen_id", screen_id);

		this.namedParameterJdbcTemplate.execute(deleteWidget, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
	}

	@Override
	public Map<String, GpUiWidgetX> find_all_widgets_by_screen(long screen_id) {
		Integer key = 0;
		WidgetBaseMapper widget_base_mapper = new WidgetBaseMapper();
		Map<String, GpUiWidgetX> the_widgets = new HashMap<String, GpUiWidgetX>();

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("screen_id", screen_id);

		List<GpUiWidgetX> widgets_list = this.namedParameterJdbcTemplate.query(
				findAllWidgetsByScreen, parameters, widget_base_mapper);

		if (widgets_list.size() > 0) {
			for (GpUiWidgetX gpUiWidgetX : widgets_list) {
				the_widgets.put((++key).toString(), gpUiWidgetX);
			}
		}

		return the_widgets;
	}

	@Override
	public void insert_screen_widgets(GpScreen ascreen) {

		System.out.println("In the DAO insert_screen_widgets ");
		ArrayList<GpUiWidget> widget_list = ascreen.getComponents();
		System.out
				.println("In the DAO insert_screen_widgets - widget_list.size() is: "
						+ widget_list.size());
		for (GpUiWidget a_widget : widget_list) {

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("name", a_widget.getName());
			paramMap.put("label", a_widget.getLabel());
			paramMap.put("description", a_widget.getDescription());
			paramMap.put("notes", a_widget.getNotes());
			paramMap.put("parentid", a_widget.getParent_id());
			paramMap.put("screen_id", a_widget.getScreen_id());
			paramMap.put("is_container", a_widget.getIs_container());
			paramMap.put("supports_label", a_widget.getSupports_label());
			paramMap.put("ui_technology", a_widget.getUitechnology());
			paramMap.put("width", a_widget.getWidth());
			paramMap.put("height", a_widget.getHeight());
			paramMap.put("x", a_widget.getX());
			paramMap.put("y", a_widget.getY());
			paramMap.put("data_binding_context",
					a_widget.getData_binding_context());
			paramMap.put("verb_binding_context",
					a_widget.getVerb_binding_context());
			paramMap.put("noun_id", a_widget.getNoun_id());
			paramMap.put("noun_attribute_id", a_widget.getNoun_attribute_id());
			paramMap.put("type", a_widget.getType());
			paramMap.put("parent_name", a_widget.getParent_name());
			paramMap.put("number_of_children", a_widget.getNumber_of_children());
			paramMap.put("extended_attributes",
					a_widget.getExtended_attributes());
			paramMap.put("event_verb_combo", a_widget.getEvent_verb_combo());
			paramMap.put("verb_target", a_widget.getVerb_target());
			paramMap.put("portrait_x", a_widget.getPortrait_x());
			paramMap.put("portrait_y", a_widget.getPortrait_y());
			paramMap.put("portrait_width",
					new Long(a_widget.getPortrait_width()).toString());
			paramMap.put("portrait_height",
					new Long(a_widget.getPortrait_height()).toString());
			paramMap.put("landscape_x", a_widget.getLandscape_y());
			paramMap.put("landscape_y", a_widget.getLandscape_y());
			paramMap.put("landscape_width",
					new Long(a_widget.getLandscape_width()).toString());
			paramMap.put("landscape_height",
					new Long(a_widget.getLandscape_height()).toString());

			paramMap.put("created_date", new Date());
			paramMap.put("created_by", ascreen.getCreatedby());
			paramMap.put("last_modified_date", new Date());
			paramMap.put("last_modified_by", ascreen.getLastmodifiedby());

			KeyHolder keyHolder = new GeneratedKeyHolder();
			this.insert_widget.updateByNamedParam(paramMap, keyHolder);
			a_widget.setId(keyHolder.getKey().longValue());

		}
	}

	@Override
	public void insert(GpScreen acreen) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GpScreen ascreen) {

	}

	@Override
	public void delete(GpScreen ascreen) {

	}

	@Override
	public GpScreen find_by_id(long screen_id) throws Exception {
		// TODO Auto-generated method stub

		String sql;
		MapSqlParameterSource parameters;
		/** handle secondary nouns - screens */
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", screen_id);
		sql = "select screens.id SCREEN_ID, screens.name as SCREEN_NAME, screens.label as SCREEEN_LABEL, screens.role as SCREEN_ROLE, screens.description as SCREEN_DESCRIPTION,"
				+ " screens.projectid as SCREEN_PROJECTID, screens.activityid as SCREEN_ACTIVITY_ID, screens.type as SCREEN_TYPE, "
				+ " screens.client_device_type_id as SCREEN_CLIENT_DEVICE_TYPE_ID, screens.client_device_type_label as SCREEN_CLIENT_DEVICE_TYPE_LABEL,"
				+ " screens.client_device_type as SCREEN_CLIENT_DEVICE_TYPE, screens.client_device_type_os_name as SCREEN_CLIENT_DEVICE_TYPE_OS_NAME,"
				+ " screens.human_language_id as SCREEN_HUMAN_LANGUAGE_ID, "
				+ " screens.current_orientation as SCREEN_CURRENT_ORIENTATION, screens.is_orientation_locked as SCREEN_IS_ORIENTATION_LOCKED,"
				+ " screens.primary_noun_id as SCREEN_PRIMARY_NOUN_ID, screens.secondary_noun_ids as SCREEN_SECONDARY_IDS,"
				+ " screens.landscape_image_name as SCREEN_LANDSCAPE_IMAGE_NAME, screens.portrait_image_name as SCREEN_PORTRAIT_IMAGE_NAME, "
				+ " screens.created_by SCREEN_CREATED_BY, screens.created_date as SCREEN_CREATED_DATE,"
				+ " screens.last_modified_by as SCREEN_LAST_MODIFIED_BY, screens.last_modified_date as SCREEN_LAST_MODIFIED_DATE,"

				+ " widgets.id as WIDGET_ID, widgets.name as WIDGET_NAME, widgets.label as WIDGET_LABEL,"
				+ " widgets.description as WIDGET_DESCRIPTION, widgets.supports_label as WIDGET_SUPPORTS_LABEL,"
				+ " widgets.parent_name as WIDGET_PARENT_NAME, widgets.is_container as WIDGET_IS_CONTAINER,"
				+ " widgets.parentid as WIDGET_PARENT_ID, widgets.type as WIDGET_TYPE, widgets.height as WIDGET_HEIGHT,"
				+ " widgets.width as WIDGET_WIDTH, widgets.x as WIDGET_X, widgets.y as WIDGET_Y, widgets.landscape_height as WIDGET_LANDSCAPE_HEIGHT,"
				+ " widgets.landscape_width as WIDGET_LANDSCAPE_WIDTH, widgets.landscape_x as WIDGET_LANDSCAPE_X,"
				+ " widgets.landscape_Y as WIDGET_LANDSCAPE_Y, widgets.portrait_height as WIDGET_PORTRAIT_HEIGHT,"
				+ " widgets.portrait_width as WIDGET_PORTRAIT_WIDTH, widgets.portrait_x as WIDGET_PORTRAIT_X,"
				+ " widgets.portrait_y as WIDGET_PORTRAIT_Y, widgets.data_binding_context as WIDGET_DATA_BINDING_CONTEXT,"
				+ " widgets.noun_id as WIDGET_NOUN_ID, widgets.noun_attribute_id as WIDGET_NOUN_ATTRIBUTE_ID,"
				+ " widgets.verb_binding_context as WIDGET_VERB_BINDING_CONTEXT, widgets.verb_target as WIDGET_VERB_TARGET,"
				+ " widgets.number_of_children as WIDGET_NUMBER_OF_CHILDREN, widgets.extended_attributes as WIDGET_EXTENDED_ATTRIBUTES,"
				+ " widgets.event_verb_combo as WIDGET_EVENT_VERB_COMBO, widgets.notes as WIDGET_NOTES,"
				+ " widgets.created_by WIDGET_CREATED_BY, widgets.created_date as WIDGET_CREATED_DATE,"
				+ " widgets.last_modified_by as WIDGET_LAST_MODIFIED_BY, widgets.last_modified_date as WIDGET_LAST_MODIFIED_DATE"
				+ " from geppetto.screens"
				+ " left join geppetto.widgets on geppetto.screens.id = geppetto.widgets.screen_id"
				+ " where geppetto.screens.id in (select id from geppetto.screens where id = :id)";

		Screen_with_widget_mapper screen_and_widget_mapper = new Screen_with_widget_mapper();
		List<GpDto_screen_and_widgets> dto_list = this.namedParameterJdbcTemplate
				.query(sql, parameters, screen_and_widget_mapper);
		if (dto_list.size() < 1) {
			throw new Exception("screenid number " + screen_id
					+ " was not found");
		}

		System.out.println("In find_by_id dto_list.size() is: "
				+ dto_list.size());

		return this.build_screen(dto_list);
	}

	public ArrayList<GpScreen> find_all_base_by_activity_id(long activity_id)
			throws Exception {
		String sql;
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("activity_id", activity_id);

		ScreenBaseMapper screen_mapper = new ScreenBaseMapper();

		// Temporarily Hided because of the GpScreen will changed to GpScreenX

		/*
		 * List<GpScreen> screen_list = this.namedParameterJdbcTemplate.query(
		 * this.get_base_by_activity_id_sql, parameters, screen_mapper);
		 * 
		 * if (screen_list.size() < 1) { throw new
		 * Exception("no screens found for activity_id : " + activity_id); }
		 * 
		 * System.out.println("######### - In GpScreenDao -" +
		 * "  find_all_base_by_activity_id is: " + screen_list.size() +
		 * " #######################");
		 * 
		 * return (ArrayList<GpScreen>) screen_list;
		 */

		return null;
	}

	@Override
	public ArrayList<GpScreen> find_by_activity_id(long activity_id)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GpScreen> find_all_base_by_projectid(long projectid)
			throws Exception {
		String sql;
		MapSqlParameterSource parameters;

		parameters = new MapSqlParameterSource();
		parameters.addValue("projectid", projectid);
		sql = "select screens.id as SCREEN_ID, screens.name as SCREEN_NAME,"
				+ " screens.label as SCREEEN_LABEL, screens.role as SCREEN_ROLE, screens.description as SCREEN_DESCRIPTION,"
				+ " screens.projectid as SCREEN_PROJECTID, screens.activityid as SCREEN_ACTIVITY_ID,"
				+ " screens.type as SCREEN_TYPE, screens.client_device_type_id as SCREEN_CLIENT_DEVICE_TYPE_ID,"
				+ " screens.client_device_type_label as SCREEN_CLIENT_DEVICE_TYPE_LABEL,"
				+ " screens.client_device_type as SCREEN_CLIENT_DEVICE_TYPE, "
				+ "screens.client_device_type_os_name as SCREEN_CLIENT_DEVICE_TYPE_OS_NAME, "
				+ " screens.human_language_id as SCREEN_HUMAN_LANGUAGE_ID,"
				+ " screens.current_orientation as SCREEN_CURRENT_ORIENTATION,"
				+ " screens.is_orientation_locked as SCREEN_IS_ORIENTATION_LOCKED,"
				+ " screens.primary_noun_id as SCREEN_PRIMARY_NOUN_ID, "
				+ "screens.secondary_noun_ids as SCREEN_SECONDARY_IDS,"
				+ " screens.landscape_image_name as SCREEN_LANDSCAPE_IMAGE_NAME,"
				+ " screens.portrait_image_name as SCREEN_PORTRAIT_IMAGE_NAME, "
				+ " screens.created_by SCREEN_CREATED_BY, screens.created_date as SCREEN_CREATED_DATE,"
				+ " screens.last_modified_by as SCREEN_LAST_MODIFIED_BY,"
				+ " screens.last_modified_date as SCREEN_LAST_MODIFIED_DATE,"
				+ " screens.wizard_id as SCREEN_WIZARD_ID"
				+ " screens.screen_wizard_sequence_id as SCREEN_WIZARD_SEQUENCE_ID"
				+ " from geppetto.screens "
				+ " where screens.projectid=:projectid";

		ScreenBaseMapper screen_base_mapper = new ScreenBaseMapper();

		// Temporarily Hided because of the GpScreen will changed to GpScreenX

		/*
		 * List<GpScreen> screen_list =
		 * this.namedParameterJdbcTemplate.query(sql, parameters,
		 * screen_base_mapper); if (screen_list.size() < 1) { return new
		 * ArrayList<GpScreen>();
		 * 
		 * } return screen_list;
		 */

		return null;
	}

	@Override
	public ArrayList<GpScreen> find_by_project_id(long project_id)
			throws Exception {
		System.out.println("In the DAO - find_by_project_id - 1");
		String sql;
		MapSqlParameterSource parameters;

		parameters = new MapSqlParameterSource();
		parameters.addValue("id", project_id);
		sql = "select screens.id SCREEN_ID, screens.name as SCREEN_NAME, screens.label as SCREEEN_LABEL, screens.description as SCREEN_DESCRIPTION,"
				+ " screens.projectid as SCREEN_PROJECTID, screens.activityid as SCREEN_ACTIVITY_ID, screens.type as SCREEN_TYPE, "
				+ " screens.client_device_type_id as SCREEN_CLIENT_DEVICE_TYPE_ID, screens.client_device_type_label as SCREEN_CLIENT_DEVICE_TYPE_LABEL,"
				+ " screens.client_device_type as SCREEN_CLIENT_DEVICE_TYPE, screens.client_device_type_os_name as SCREEN_CLIENT_DEVICE_TYPE_OS_NAME, "
				+ " screens.human_language_id as SCREEN_HUMAN_LANGUAGE_ID,"
				+ " screens.current_orientation as SCREEN_CURRENT_ORIENTATION, screens.is_orientation_locked as SCREEN_IS_ORIENTATION_LOCKED,"
				+ " screens.primary_noun_id as SCREEN_PRIMARY_NOUN_ID, screens.secondary_noun_ids as SCREEN_SECONDARY_IDS,"
				+ " screens.landscape_image_name as SCREEN_LANDSCAPE_IMAGE_NAME, screens.portrait_image_name as SCREEN_PORTRAIT_IMAGE_NAME, "
				+ " screens.created_by SCREEN_CREATED_BY, screens.created_date as SCREEN_CREATED_DATE,"
				+ " screens.last_modified_by as SCREEN_LAST_MODIFIED_BY, screens.last_modified_date as SCREEN_LAST_MODIFIED_DATE,screens.wizard_id as SCREEN_WIZARD_ID,screens.screen_wizard_sequence_id as SCREEN_WIZARD_SEQUENCE_ID"
				+ " from geppetto.screens " + " where screens.projectid=:id";

		Screen_No_Children_Mapper screen_and_widget_mapper = new Screen_No_Children_Mapper();

		// Temporarily Hided because of the GpScreen will changed to GpScreenX

		/*
		 * List<GpScreen> screen_list =
		 * this.namedParameterJdbcTemplate.query(sql, parameters,
		 * screen_and_widget_mapper); System.out
		 * .println("In the DAO - find_by_project_id - 2 - screen_list.size() IS: "
		 * + screen_list.size()); if (screen_list.size() < 1) { throw new
		 * Exception("no screens where found for project id:  " + project_id,
		 * new Throwable("99"));
		 * 
		 * }
		 * 
		 * System.out.println("In find_by_id screen_list.size() is: " +
		 * screen_list.size());
		 * 
		 * return (ArrayList<GpScreen>) screen_list;
		 */

		return null;
	}

	private GpScreen build_screen(List<GpDto_screen_and_widgets> dto_list) {

		GpScreen the_screen = new GpScreen();
		GpDto_screen_and_widgets a_dto = dto_list.get(0);
		the_screen.setId(a_dto.getScreen_id());
		the_screen.setName(a_dto.getScreen_name());
		the_screen.setLandscape_image_name(a_dto
				.getScreen_landscape_image_name());
		the_screen
				.setPortrait_image_name(a_dto.getScreen_portrait_image_name());
		// the_screen
		// .setOrientation_locked(a_dto.isScreen_is_orientation_locked());
		the_screen.setOrientation(a_dto.getScreen_current_orientation());
		the_screen.setHuman_language_id(a_dto.getScreen_human_language_id());
		the_screen.setLabel(a_dto.getScreen_label());
		the_screen.setDescription(a_dto.getScreen_description());

		the_screen.setClient_device_type_label(a_dto
				.getScreen_client_device_type_label());
		the_screen.setClient_device_type_os_name(a_dto
				.getScreen_client_device_type_os_name());
		the_screen.setClient_device_type(a_dto.getScreen_client_device_type());
		the_screen.setClient_device_type_id(a_dto
				.getScreen_client_device_type_id());

		the_screen.setComponents(new ArrayList<GpUiWidget>());

		the_screen.setWizard_id(a_dto.getScreen_wizard_id());
		
		the_screen.setScreen_wizard_sequence_id(a_dto.getScreen_wizard_sequence_id());
		// this.handle_widgets(the_screen, dto_list, i);
		this.handle_screen_children(the_screen, dto_list);
		for (GpUiWidget a_widget : the_screen.getComponents()) {
			if (a_widget.getIs_container()) {
				this.handle_widget_children(the_screen, dto_list, a_widget);
			}
		}
		return the_screen;
	}

	// this adds all the widgets that are direct children of the screen
	private void handle_screen_children(GpScreen the_screen,
			List<GpDto_screen_and_widgets> dto_list) {

		for (GpDto_screen_and_widgets the_dto : dto_list) {
			if (the_dto.getWidget_parent_id() == the_screen.getId()) {
				GpUiWidget the_widget = new GpUiWidget();
				this.handle_common_widget_properties(the_dto, the_widget);
				the_screen.getComponents().add(the_widget);
			}
		}
	}

	private void handle_widget_children(GpScreen the_screen,
			List<GpDto_screen_and_widgets> dto_list, GpUiWidget the_container) {

		for (GpUiWidget the_widget : the_screen.getComponents()) {

			the_widget.setChildren(new ArrayList<GpUiWidget>());
			if (the_widget.getType()
					.equals(GpUiTypeConstants.GpTabBarContainer)
					|| the_widget.getType().equals(
							GpUiTypeConstants.GpAccordionContainer)) {

				this.handle_multi_section_container(the_widget, dto_list);

			} else if (the_widget.getType().equals(
					GpUiTypeConstants.GpBorderContainer)) {
				this.handle_single_section_container(the_widget, dto_list);

			} else if (the_widget.getType()
					.equals(GpUiTypeConstants.GpDataGrid)) {
				this.handle_data_grid(the_widget, dto_list);
			}
		}

	}

	/**
	 * 
	 * @param the_screen
	 * @param the_container
	 * @param dto_list
	 *            </p> I realize that this can be combined with the
	 *            single_section_container method but</br> I want to be able to
	 *            have flexibility to add specific functionality by
	 *            container</p>
	 */

	private void handle_multi_section_container(GpUiWidget the_container,
			List<GpDto_screen_and_widgets> dto_list) {

		// first find sections to the main parent control
		for (GpDto_screen_and_widgets a_dto : dto_list) {

			if (a_dto.getWidget_parent_id() == the_container.getId()) {
				GpUiWidget a_section = new GpUiWidget();
				this.handle_common_widget_properties(a_dto, a_section);
				a_section.setChildren(new ArrayList<GpUiWidget>());
				the_container.getChildren().add(a_section);
			}

		}

		// second for each section find its children and add to section
		for (GpUiWidget a_section : the_container.getChildren()) {

			for (GpDto_screen_and_widgets a_dto : dto_list) {

				if (a_dto.getWidget_parent_id() == a_section.getId()) {
					System.out.println("@@@@@@@  FOUND SECTION CHILD @@@@@@"
							+ "\n section id is: " + a_section.getId()
							+ "\n section name is: " + a_section.getName());
					GpUiWidget a_widget = new GpUiWidget();
					this.handle_common_widget_properties(a_dto, a_widget);
					if (a_dto.getWidget_type().equals(
							GpUiTypeConstants.GpDataGrid)) {
						a_widget.setChildren(new ArrayList<GpUiWidget>());
						this.handle_data_grid(a_widget, dto_list);
					}
					System.out.println("Adding found child to section "
							+ "\n a_widget.getName() is: " + a_widget.getName()
							+ "\n a_widget.getType()" + a_widget.getType());
					a_section.getChildren().add(a_widget);
				}
			}
		}

	}

	/**
	 * 
	 * @param the_screen
	 * @param the_container
	 * @param dto_list
	 *            </p> I realize that this can be combined with the
	 *            handle_multi_section_container method but</br> I want to be
	 *            able to have flexibility to add specific functionality by
	 *            container</p>
	 */
	private void handle_single_section_container(GpUiWidget the_container,
			List<GpDto_screen_and_widgets> dto_list) {

		for (GpDto_screen_and_widgets a_dto : dto_list) {

			if (a_dto.getWidget_parent_id() == the_container.getId()) {

				GpUiWidget a_widget = new GpUiWidget();
				this.handle_common_widget_properties(a_dto, a_widget);

				if (a_dto.getWidget_type().equals(GpUiTypeConstants.GpDataGrid)) {
					a_widget.setChildren(new ArrayList<GpUiWidget>());
					this.handle_data_grid(a_widget, dto_list); // add all the
																// grids columns
				}
				the_container.getChildren().add(a_widget);
			}

		}
	}

	/**
	 * 
	 * @param the_screen
	 * @param the_container
	 * @param dto_list
	 *            </p> I realize that this can be combined with the
	 *            single_section_container method but</br> I think that Grids
	 *            will have more specific processing in the future
	 *            <p>
	 */
	private void handle_data_grid(GpUiWidget the_container,
			List<GpDto_screen_and_widgets> dto_list) {

		for (GpDto_screen_and_widgets a_dto : dto_list) {
			if (a_dto.getWidget_parent_id() == the_container.getId()) {
				GpUiWidget a_widget = new GpUiWidget();
				this.handle_common_widget_properties(a_dto, a_widget);
				the_container.getChildren().add(a_widget);
			}

		}

	}

	private void handle_common_widget_properties(
			GpDto_screen_and_widgets dto_source, GpUiWidget widget_target) {

		widget_target.setId(dto_source.getWidget_id());
		widget_target.setDescription(dto_source.getWidget_description());
		widget_target.setLabel(dto_source.getWidget_label());
		widget_target.setName(dto_source.getWidget_name());
		widget_target.setNotes(dto_source.getWidget_notes());
		widget_target.setType(dto_source.getWidget_type());
		// widget_target.setIs_container(dto_source.isWidget_is_container());

		widget_target.setData_binding_context(dto_source
				.getWidget_data_binding_context());
		widget_target.setExtended_attributes(dto_source
				.getWidget_extended_attributes());
		widget_target.setVerb_binding_context(dto_source
				.getWidget_verb_binding_context());
		widget_target.setEvent_verb_combo(dto_source
				.getWidget_event_verb_combo());
		widget_target.setVerb_target(dto_source.getWidget_verb_target());

		widget_target.setNoun_attribute_id(dto_source
				.getWidget_noun_attribute_id());
		widget_target.setNoun_id(dto_source.getWidget_noun_id());

		// widget_target.setNumber_of_children(0); //maybe we dont need this

		widget_target.setParent_id(dto_source.getWidget_parent_id());
		widget_target.setParent_name(dto_source.getWidget_parent_name());
		widget_target.setScreen_id(dto_source.getScreen_id());
		widget_target.setSupports_label(new Boolean(dto_source
				.getWidget_supports_label()));
		// widget_target.setUitechnology(dto_source);

		// widget_target.setWidth(dto_source.getWidget_width());
		// widget_target.setHeight(dto_source.getWidget_height());
		//widget_target.setX(dto_source.getWidget_x());
		//widget_target.setY(dto_source.getWidget_y());

		widget_target.setPortrait_height(new Long(dto_source
				.getWidget_portrait_height()).longValue()); // do this until you
															// change the type
															// on the front end
		widget_target.setPortrait_width(new Long(dto_source
				.getWidget_portrait_width()).longValue());
		widget_target.setPortrait_x(dto_source.getWidget_portrait_x());
		widget_target.setPortrait_y(dto_source.getWidget_portrait_y());

		widget_target.setLandscape_height(new Long(dto_source
				.getWidget_landscape_height()).longValue());
		widget_target.setLandscape_width(new Long(dto_source
				.getWidget_landscape_width()).longValue());
		widget_target.setLandscape_x(dto_source.getWidget_landscape_x());
		widget_target.setLandscape_y(dto_source.getWidget_landscape_y());

		widget_target.setCreatedate(dto_source.getWidget_createdate());
		widget_target.setCreatedby(dto_source.getWidget_createdby());
		widget_target.setLastmodifiedby(dto_source.getWidget_lastmodifiedby());
		widget_target.setLastmodifieddate(dto_source
				.getWidget_lastmodifieddate());

	}

}
