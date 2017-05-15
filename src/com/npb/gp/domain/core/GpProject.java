package com.npb.gp.domain.core;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dan Castillo<br>
 *         Date Created: 03/07/2013<br>
 * @since .35</p>
 * 
 *        The purpose this class is to encapsulate the concept of a
 *        project.</br> A project is composed of all the artifacts needed to
 *        produce a working</br> software system.</p>
 * 
 *        Please note that this class was originally part of the POC for
 *        Geppetto</br> The POC spanned multiple iterations and was known as
 *        versions .1 (Cancun),.2(Caracas)</br> and.3(Vijayawada) of Geppetto -
 *        Dan Castillo</p>
 *        
 *          Modified Date: 27/05/2015</br> Modified By: kumaresan perumal</p>
 * 
 *        i added a new field called desktop_css_framework. 
 *        and changed client_css_frameworks into mobile_css_framework.
 *        
 *
 *        Modified Date: 22/05/2015</br> Modified By: kumaresan perumal</p>
 * 
 *        i removed the client css frame works because it was an array list to
 *        select multiple options. our requirement is single selection. i
 *        changed it as single selection string
 * 
 *        Modified Date: 01/21/2015</br> Modified By: Dan Castillo</p> changed
 *        "authorizations" field from a simple string to and ArrayList of
 *        Strings</p>
 * 
 *        Modified Date: 11/27/2014</br> Modified By: Dan Castillo</p>
 * 
 *        added "authorization_type" and "authorizations" field - the former is
 *        used</br> to indicate if group based permissioning is used, for now
 *        the default</br> is group based . The "authorizations" field is used
 *        to store the supported </br> groups used to perform authorization -
 *        for now there are only two default groups</br GpAdmin and GpUser</p>
 *
 *
 *        Modified Date: 15/04/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Changed the GpScreen to GpScreenX in all methods and declarations
 * 
 * 
 * 
 *        Modified Date: 11/20/2014</br> Modified By: Dan Castillo</p>
 *
 *        added generation_type field - the value in this field indicates</br>
 *        if the application will have code on a server,client or mixed
 *        (both)</br> this allows the code generation logic to make decisions
 *        about how to set up</br> the project while generating code</p>
 * 
 * 
 *        Modified Date: 10/22/2014</br> Modified By: Dan Castillo</p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex
 * 
 * 
 *        Modified Date: 06/15/2014</br> Modified By: Dan Castillo</p>
 *
 *        Major re-write in order to match the needs of the code generation
 * 
 * 
 */

public class GpProject {
	private long id; // x
	private String name; // x
	private String description;// x
	private String label; // x

	private long default_module_id; // x
	private String default_module_label; // x
	private String notes; // x

	private Date createdate; // x
	private long createdby; // x
	private Date lastmodifieddate; // x
	private long lastmodifiedby; // x

	private ArrayList<String> client_os_types; // x Android, windows, windows
												// mobile, IOS, Tizen, etc
	private ArrayList<String> client_device_types; // x desktop, phone, tablet,
													// etc
	private long client_dev_language; // x java, js, objective-c,
													// c#, c++
	private long client_dev_framework; // x -its by device type -
														// AngularJS, Ember,
														// MVC-Android - if gen
														// is native, MVC-IOS,
														// if gen is native
	private ArrayList<String> client_widget_frameworks; // x

	// private ArrayList<String> client_css_frameworks; // x // we have to
	// remove this before we stored multiple options.
	// now we store a single value so we do not need array list.

	private String mobile_css_framework;
	private String desktop_css_framework;
	private boolean communication_protocal;
	//private String default_module_label;
	private boolean stand_alone_app; 
	
	private ArrayList<String> app_ui_template; // x its by device type
	private long client_code_pattern; // x
	private long server_code_pattern; // x
	private long server_dev_lang; // x
	private long server_dev_framework; // x
	private long server_run_time; // x
	private long server_os; // x
	private long server_dbms; // x
	private long server_deployment_environment; // x
	private ArrayList<String> global_extensions; // x this are pointers to
													// custom code
	
	private ArrayList<String> ui_navigation_styles; // x
	private ArrayList<String> supported_browsers; // x muti - OK

	private long default_human_language; // x
	private ArrayList<String> other_human_languages; // x
	private String entity; // x this will be used for root of the package I.E:
							// com.newportbay.*
	private boolean enforce_documentation; // x
	private long widget_count; // x used to autoname widgets in a project
	private String generation_type;
	private String authorization_type;
	private ArrayList<String> authorizations;

	// these two project_nouns and project_sceens are pulled
	// for their respective tables and are not stored in the
	// project table
	private ArrayList<GpNoun> project_nouns;
	private ArrayList<GpScreenX> project_screens;

	// these are not stored in the project table at this time
	private ArrayList<String> processing_mode_ids; // human or system

	private boolean mobile_standalone;
	private boolean cert_required;

	private ArrayList<GpModule> external_module_list;
	private GpModule default_module;

	private String multi_user_status;
	private String multi_user_info;
	private int application_type;
	private String lotus_notes_enabled;
	private String lotus_notes_cred_enabled;
	private String extra_project_info;

	private List<GpMicroService> gpMicroService;
	
	
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

	public long getDefault_module_id() {
		return default_module_id;
	}

	public void setDefault_module_id(long default_module_id) {
		this.default_module_id = default_module_id;
	}

	public String getDefault_module_label() {
		return default_module_label;
	}

	public void setDefault_module_label(String default_module_label) {
		this.default_module_label = default_module_label;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public ArrayList<String> getClient_os_types() {
		return client_os_types;
	}

	public void setClient_os_types(ArrayList<String> client_os_types) {
		this.client_os_types = client_os_types;
	}

	public ArrayList<String> getClient_device_types() {
		return client_device_types;
	}

	public void setClient_device_types(ArrayList<String> client_device_types) {
		this.client_device_types = client_device_types;
	}
	

	public long getClient_dev_language() {
		return client_dev_language;
	}

	public void setClient_dev_language(long client_dev_language) {
		this.client_dev_language = client_dev_language;
	}

	public long getClient_dev_framework() {
		return client_dev_framework;
	}

	public void setClient_dev_framework(long client_dev_framework) {
		this.client_dev_framework = client_dev_framework;
	}

	public ArrayList<String> getClient_widget_frameworks() {
		return client_widget_frameworks;
	}

	public void setClient_widget_frameworks(
			ArrayList<String> client_widget_frameworks) {
		this.client_widget_frameworks = client_widget_frameworks;
	}

	
	public ArrayList<String> getApp_ui_template() {
		return app_ui_template;
	}

	public void setApp_ui_template(ArrayList<String> app_ui_template) {
		this.app_ui_template = app_ui_template;
	}

	public long getClient_code_pattern() {
		return client_code_pattern;
	}

	public void setClient_code_pattern(long client_code_pattern) {
		this.client_code_pattern = client_code_pattern;
	}

	public long getServer_code_pattern() {
		return server_code_pattern;
	}

	public void setServer_code_pattern(long server_code_pattern) {
		this.server_code_pattern = server_code_pattern;
	}

	public long getServer_dev_lang() {
		return server_dev_lang;
	}

	public void setServer_dev_lang(long server_dev_lang) {
		this.server_dev_lang = server_dev_lang;
	}

	public long getServer_dev_framework() {
		return server_dev_framework;
	}

	public void setServer_dev_framework(long server_dev_framework) {
		this.server_dev_framework = server_dev_framework;
	}

	public long getServer_run_time() {
		return server_run_time;
	}

	public void setServer_run_time(long server_run_time) {
		this.server_run_time = server_run_time;
	}

	public long getServer_os() {
		return server_os;
	}

	public void setServer_os(long server_os) {
		this.server_os = server_os;
	}

	public long getServer_dbms() {
		return server_dbms;
	}

	public void setServer_dbms(long server_dbms) {
		this.server_dbms = server_dbms;
	}

	public long getServer_deployment_environment() {
		return server_deployment_environment;
	}

	public void setServer_deployment_environment(
			long server_deployment_environment) {
		this.server_deployment_environment = server_deployment_environment;
	}

	public ArrayList<String> getGlobal_extensions() {
		return global_extensions;
	}

	public void setGlobal_extensions(ArrayList<String> global_extensions) {
		this.global_extensions = global_extensions;
	}

	public ArrayList<String> getUi_navigation_styles() {
		return ui_navigation_styles;
	}

	public void setUi_navigation_styles(ArrayList<String> ui_navigation_styles) {
		this.ui_navigation_styles = ui_navigation_styles;
	}

	public ArrayList<String> getSupported_browsers() {
		return supported_browsers;
	}

	public void setSupported_browsers(ArrayList<String> supported_browsers) {
		this.supported_browsers = supported_browsers;
	}

	public long getDefault_human_language() {
		return default_human_language;
	}

	public void setDefault_human_language(long default_human_language) {
		this.default_human_language = default_human_language;
	}

	public ArrayList<String> getOther_human_languages() {
		return other_human_languages;
	}

	public void setOther_human_languages(ArrayList<String> other_human_languages) {
		this.other_human_languages = other_human_languages;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public boolean isEnforce_documentation() {
		return enforce_documentation;
	}

	public void setEnforce_documentation(boolean enforce_documentation) {
		this.enforce_documentation = enforce_documentation;
	}

	public long getWidget_count() {
		return widget_count;
	}

	public void setWidget_count(long widget_count) {
		this.widget_count = widget_count;
	}

	public String getGeneration_type() {
		return generation_type;
	}

	public void setGeneration_type(String generation_type) {
		this.generation_type = generation_type;
	}

	public String getAuthorization_type() {
		return authorization_type;
	}

	public void setAuthorization_type(String authorization_type) {
		this.authorization_type = authorization_type;
	}

	public ArrayList<String> getAuthorizations() {
		return authorizations;
	}

	public void setAuthorizations(ArrayList<String> authorizations) {
		this.authorizations = authorizations;
	}

	public ArrayList<GpNoun> getProject_nouns() {
		return project_nouns;
	}

	public void setProject_nouns(ArrayList<GpNoun> project_nouns) {
		this.project_nouns = project_nouns;
	}

	public ArrayList<GpScreenX> getProject_screens() {
		return project_screens;
	}

	public void setProject_screens(ArrayList<GpScreenX> project_screens) {
		this.project_screens = project_screens;
	}

	public ArrayList<String> getProcessing_mode_ids() {
		return processing_mode_ids;
	}

	public void setProcessing_mode_ids(ArrayList<String> processing_mode_ids) {
		this.processing_mode_ids = processing_mode_ids;
	}

	public boolean isMobile_standalone() {
		return mobile_standalone;
	}

	public void setMobile_standalone(boolean mobile_standalone) {
		this.mobile_standalone = mobile_standalone;
	}

	public boolean isCert_required() {
		return cert_required;
	}

	public void setCert_required(boolean cert_required) {
		this.cert_required = cert_required;
	}

	public ArrayList<GpModule> getExternal_module_list() {
		return external_module_list;
	}

	public void setExternal_module_list(ArrayList<GpModule> external_module_list) {
		this.external_module_list = external_module_list;
	}

	public GpModule getDefault_module() {
		return default_module;
	}

	public void setDefault_module(GpModule default_module) {
		this.default_module = default_module;
	}

	public String getMulti_user_status() {
		return multi_user_status;
	}

	public void setMulti_user_status(String multi_user_status) {
		this.multi_user_status = multi_user_status;
	}

	public String getMulti_user_info() {
		return multi_user_info;
	}

	public void setMulti_user_info(String multi_user_info) {
		this.multi_user_info = multi_user_info;
	}
	public String getMobile_css_framework() {
		return mobile_css_framework;
	}

	public void setMobile_css_framework(String mobile_css_frameworks) {
		this.mobile_css_framework = mobile_css_frameworks;
	}

	public String getDesktop_css_framework() {
		return desktop_css_framework;
	}

	public void setDesktop_css_framework(String desktop_css_framework) {
		this.desktop_css_framework = desktop_css_framework;
	}

	public boolean isCommunication_protocal() {
		return communication_protocal;
	}

	public void setCommunication_protocal(boolean communication_protocal) {
		this.communication_protocal = communication_protocal;
	}

	public boolean isStand_alone_app() {
		return stand_alone_app;
	}

	public void setStand_alone_app(boolean stand_alone_app) {
		this.stand_alone_app = stand_alone_app;
	}

	public int getApplication_type() {
		return application_type;
	}
	
	public void setApplication_type(int application_type) {
		this.application_type = application_type;
	}

	public List<GpMicroService> getGpMicroService() {
		return gpMicroService;
	}

	public void setGpMicroService(List<GpMicroService> gpMicroService) {
		this.gpMicroService = gpMicroService;
	}

	public String getLotus_notes_enabled() {
		return lotus_notes_enabled;
	}

	public void setLotus_notes_enabled(String lotus_notes_enabled) {
		this.lotus_notes_enabled = lotus_notes_enabled;
	}

	public String getExtra_project_info() {
		return extra_project_info;
	}

	public void setExtra_project_info(String extra_project_info) {
		this.extra_project_info = extra_project_info;
	}
	public String getLotus_notes_cred_enabled() {
		return lotus_notes_cred_enabled;
	}

	public void setLotus_notes_cred_enabled(String lotus_notes_cred_enabled) {
		this.lotus_notes_cred_enabled = lotus_notes_cred_enabled;
	}
	
	
}
