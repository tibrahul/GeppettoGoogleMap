package com.npb.gp.domain.core;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Dan Castillo</br>
 *         Date Created: 03/05/2015</br>
 * @since .75
 *        </p>
 * 
 * 
 *        This class is being used to take the place of the GpUiWidget class
 *        that has been used in Geppetto since version .1. The reason for this
 *        class is that Geppetto changed front end technology from Flex/Blaze to
 *        HTML5/REST/Angular and I did not want to restrict the developers of
 *        the Screen Designer component with an old data model.
 * 
 *        Modified Date: 16/10/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variables as extra_verb_info to save the verb information
 * 
 *        Modified Date: 09/10/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variables as image_src to save the image sources
 * 
 *        Modified Date: 10/09/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variables as header, footer and items for the component as
 *        list and card
 * 
 *        Modified Date: 04/09/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variable as target_url for the component as link
 * 
 *        Modified Date: 05/08/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Modified the columns type to array list
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
 *        Modified Date: 04/06/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Added new variable as css_class
 * 
 *        Modified Date: 25/05/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Changed the is_container to Boolean and modified the getter and setter
 *        methods and removed parent variable
 * 
 *        Added new variables as portraitOffsetX, portraitOffsetY,
 *        landscapeOffsetX and landscapeOffsetY
 * 
 *        Modified Date: 12/05/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        Changed the parent to String and added new variable as parent_id
 * 
 *        Modified Date: 19/03/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        added few variables and removed parent
 * 
 *        Modified Date: 24/03/2015</br>
 *        Modified By: Suresh Palanisamy
 *        </p>
 * 
 *        removed the font_size for temporarily
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GpUiWidgetX {

	private long id;
	private String name;
	private String description;
	private String label;
	private String notes;
	private long parent_id;
	private String parent_name; // why do we need this?
	private long screen_id;
	private long number_of_children;
	private String type;
	private String supports_label; // why do we need this?
	private Boolean is_container;
	private String ui_technology;
	private String data_binding_context; // = "notbound"; notbound, primarynoun,
											// secondarynoun
	private String verb_binding_context; // = "notbound"; notbound, bound
	private long noun_id;
	private long noun_attribute_id;
	private String extended_attributes; // if this is populated then there are
										// more attributes for the UIWidget
	// private HashMap<String, Object> extended_attribs; // why do we need this
	// private ArrayList<GpUiWidgetX> children;
	// private ArrayList<GpEvent> events;
	// public long x;
	// public long y;
	private String events;
	private String event_verb_combo;
	private String verb_target;
	private Map<String, GpUiWidgetX> children;
	public long width;
	public long height;
	public long portraitX; // = -1;
	public long portraitY; // = -1;
	public long landscapeX;// = -1;
	public long landscapeY; // = -1;
	private Date createdate;
	private long createdby;
	private Date lastmodifieddate;
	private long lastmodifiedby;
	private long portraitOffsetX;
	private long portraitOffsetY;
	private long landscapeOffsetX;
	private long landscapeOffsetY;
	private long portrait_height;
	private long portrait_width;
	private long landscape_height;
	private long landscape_width;
	private String css_class;
	private long rows;
	private ArrayList<GpUiWidgetX> columns;
	private String target_url;
	private String header;
	private String footer;
	private String image_src;
	private String extra_verb_info;
	private long data_binding_target_noun_id;
	private long data_binding_target_noun_attr_id;
	private long wizard_id;
	private long screen_wizard_sequence_id;
	private long section_position;
	private String group_values;
	private String custom_verb_info;
	
	public String getCustom_verb_info() {
		return custom_verb_info;
	}

	public void setCustom_verb_info(String custom_verb_info) {
		this.custom_verb_info = custom_verb_info;
	}

	private boolean isChecked;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public long getScreen_id() {
		return screen_id;
	}

	public void setScreen_id(long screen_id) {
		this.screen_id = screen_id;
	}

	public long getNumber_of_children() {
		return number_of_children;
	}

	public void setNumber_of_children(long number_of_children) {
		this.number_of_children = number_of_children;
	}

	public String getSupports_label() {
		return supports_label;
	}

	public void setSupports_label(String supports_label) {
		this.supports_label = supports_label;
	}

	public Boolean getIs_container() {
		return is_container;
	}

	public void setIs_container(Boolean is_container) {
		this.is_container = is_container;
	}

	public String getUi_technology() {
		return ui_technology;
	}

	public void setUi_technology(String ui_technology) {
		this.ui_technology = ui_technology;
	}

	public String getData_binding_context() {
		return data_binding_context;
	}

	public void setData_binding_context(String data_binding_context) {
		this.data_binding_context = data_binding_context;
	}

	public String getVerb_binding_context() {
		return verb_binding_context;
	}

	public void setVerb_binding_context(String verb_binding_context) {
		this.verb_binding_context = verb_binding_context;
	}

	public long getNoun_attribute_id() {
		return noun_attribute_id;
	}

	public void setNoun_attribute_id(long noun_attribute_id) {
		this.noun_attribute_id = noun_attribute_id;
	}

	public long getNoun_id() {
		return noun_id;
	}

	public void setNoun_id(long noun_id) {
		this.noun_id = noun_id;
	}

	public String getExtended_attributes() {
		return extended_attributes;
	}

	public void setExtended_attributes(String extended_attributes) {
		this.extended_attributes = extended_attributes;
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}

	public String getEvent_verb_combo() {
		return event_verb_combo;
	}

	public void setEvent_verb_combo(String event_verb_combo) {
		this.event_verb_combo = event_verb_combo;
	}

	public String getVerb_target() {
		return verb_target;
	}

	public void setVerb_target(String verb_target) {
		this.verb_target = verb_target;
	}

	/*
	 * public ArrayList<GpEvent> getEvents() { return events; }
	 * 
	 * public void setEvents(ArrayList<GpEvent> events) { this.events = events;
	 * }
	 * 
	 * public long getX() { return x; }
	 * 
	 * public void setX(long x) { this.x = x; }
	 * 
	 * public long getY() { return y; }
	 * 
	 * public void setY(long y) { this.y = y; }
	 */

	public long getPortraitX() {
		return portraitX;
	}

	public void setPortraitX(long portraitX) {
		this.portraitX = portraitX;
	}

	public long getPortraitY() {
		return portraitY;
	}

	public void setPortraitY(long portraitY) {
		this.portraitY = portraitY;
	}

	public long getLandscapeX() {
		return landscapeX;
	}

	public void setLandscapeX(long landscapeX) {
		this.landscapeX = landscapeX;
	}

	public long getLandscapeY() {
		return landscapeY;
	}

	public void setLandscapeY(long landscapeY) {
		this.landscapeY = landscapeY;
	}

	public long getWidth() {
		return width;
	}

	public void setWidth(long width) {
		this.width = width;
	}

	public long getHeight() {
		return height;
	}

	public void setHeight(long height) {
		this.height = height;
	}

	public Map<String, GpUiWidgetX> getChildren() {
		return children;
	}

	public void setChildren(Map<String, GpUiWidgetX> children) {
		this.children = children;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public long getCreatedby() {
		return createdby;
	}

	public void setCreatedby(long createdby) {
		this.createdby = createdby;
	}

	public Date getLastmodifieddate() {
		return lastmodifieddate;
	}

	public void setLastmodifieddate(Date lastmodifieddate) {
		this.lastmodifieddate = lastmodifieddate;
	}

	public long getLastmodifiedby() {
		return lastmodifiedby;
	}

	public void setLastmodifiedby(long lastmodifiedby) {
		this.lastmodifiedby = lastmodifiedby;
	}

	public long getParent_id() {
		return parent_id;
	}

	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}

	public long getPortraitOffsetX() {
		return portraitOffsetX;
	}

	public void setPortraitOffsetX(long portraitOffsetX) {
		this.portraitOffsetX = portraitOffsetX;
	}

	public long getPortraitOffsetY() {
		return portraitOffsetY;
	}

	public void setPortraitOffsetY(long portraitOffsetY) {
		this.portraitOffsetY = portraitOffsetY;
	}

	public long getLandscapeOffsetX() {
		return landscapeOffsetX;
	}

	public void setLandscapeOffsetX(long landscapeOffsetX) {
		this.landscapeOffsetX = landscapeOffsetX;
	}

	public long getLandscapeOffsetY() {
		return landscapeOffsetY;
	}

	public void setLandscapeOffsetY(long landscapeOffsetY) {
		this.landscapeOffsetY = landscapeOffsetY;
	}

	public long getPortrait_height() {
		return portrait_height;
	}

	public void setPortrait_height(long portrait_height) {
		this.portrait_height = portrait_height;
	}

	public long getPortrait_width() {
		return portrait_width;
	}

	public void setPortrait_width(long portrait_width) {
		this.portrait_width = portrait_width;
	}

	public long getLandscape_height() {
		return landscape_height;
	}

	public void setLandscape_height(long landscape_height) {
		this.landscape_height = landscape_height;
	}

	public long getLandscape_width() {
		return landscape_width;
	}

	public void setLandscape_width(long landscape_width) {
		this.landscape_width = landscape_width;
	}

	public String getCss_class() {
		return css_class;
	}

	public void setCss_class(String css_class) {
		this.css_class = css_class;
	}

	public long getRows() {
		return rows;
	}

	public void setRows(long rows) {
		this.rows = rows;
	}

	public ArrayList<GpUiWidgetX> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<GpUiWidgetX> columns) {
		this.columns = columns;
	}

	public String getTarget_url() {
		return target_url;
	}

	public void setTarget_url(String target_url) {
		this.target_url = target_url;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getImage_src() {
		return image_src;
	}

	public void setImage_src(String image_src) {
		this.image_src = image_src;
	}

	public String getExtra_verb_info() {
		return extra_verb_info;
	}

	public void setExtra_verb_info(String extra_verb_info) {
		this.extra_verb_info = extra_verb_info;
	}

	public long getWizard_id() {
		return wizard_id;
	}

	public void setWizard_id(long wizard_id) {
		this.wizard_id = wizard_id;
	}

	public long getScreen_wizard_sequence_id() {
		return screen_wizard_sequence_id;
	}

	public void setScreen_wizard_sequence_id(long screen_wizard_sequence_id) {
		this.screen_wizard_sequence_id = screen_wizard_sequence_id;
	}

	public long getData_binding_target_noun_id() {
		return data_binding_target_noun_id;
	}

	public void setData_binding_target_noun_id(long data_binding_target_noun_id) {
		this.data_binding_target_noun_id = data_binding_target_noun_id;
	}

	public long getData_binding_target_noun_attr_id() {
		return data_binding_target_noun_attr_id;
	}

	public void setData_binding_target_noun_attr_id(
			long data_binding_target_noun_attr_id) {
		this.data_binding_target_noun_attr_id = data_binding_target_noun_attr_id;
	}

	public long getSection_position() {
		return section_position;
	}

	public void setSection_position(long section_position) {
		this.section_position = section_position;
	}

	public String getGroup_values() {
		return group_values;
	}

	public void setGroup_values(String group_values) {
		this.group_values = group_values;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	@Override
	public String toString() {
		return "GpUiWidgetX [id=" + id + ", name=" + name + ", description=" + description + ", label=" + label
				+ ", notes=" + notes + ", parent_id=" + parent_id + ", parent_name=" + parent_name + ", screen_id="
				+ screen_id + ", number_of_children=" + number_of_children + ", type=" + type + ", supports_label="
				+ supports_label + ", is_container=" + is_container + ", ui_technology=" + ui_technology
				+ ", data_binding_context=" + data_binding_context + ", verb_binding_context=" + verb_binding_context
				+ ", noun_id=" + noun_id + ", noun_attribute_id=" + noun_attribute_id + ", extended_attributes="
				+ extended_attributes + ", events=" + events + ", event_verb_combo=" + event_verb_combo
				+ ", verb_target=" + verb_target + ", children=" + children + ", width=" + width + ", height=" + height
				+ ", portraitX=" + portraitX + ", portraitY=" + portraitY + ", landscapeX=" + landscapeX
				+ ", landscapeY=" + landscapeY + ", createdate=" + createdate + ", createdby=" + createdby
				+ ", lastmodifieddate=" + lastmodifieddate + ", lastmodifiedby=" + lastmodifiedby + ", portraitOffsetX="
				+ portraitOffsetX + ", portraitOffsetY=" + portraitOffsetY + ", landscapeOffsetX=" + landscapeOffsetX
				+ ", landscapeOffsetY=" + landscapeOffsetY + ", portrait_height=" + portrait_height
				+ ", portrait_width=" + portrait_width + ", landscape_height=" + landscape_height + ", landscape_width="
				+ landscape_width + ", css_class=" + css_class + ", rows=" + rows + ", columns=" + columns
				+ ", target_url=" + target_url + ", header=" + header + ", footer=" + footer + ", image_src="
				+ image_src + ", extra_verb_info=" + extra_verb_info + ", data_binding_target_noun_id="
				+ data_binding_target_noun_id + ", data_binding_target_noun_attr_id=" + data_binding_target_noun_attr_id
				+ ", wizard_id=" + wizard_id + ", screen_wizard_sequence_id=" + screen_wizard_sequence_id
				+ ", section_position=" + section_position + ", group_values=" + group_values + ", custom_verb_info="
				+ custom_verb_info + ", isChecked=" + isChecked + "]";
	}

	
}