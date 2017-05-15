package com.npb.gp.domain.core;

//import java.sql.Date;
import java.util.Date;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.domain.core.GpScreenX;

/**
 * @author Dan Castillo</p> Date Created: 01/21/2013
 * @since .35</p>
 * 
 *        This is one of the key model classes for the system, its purpose is to
 *        encapsulate information regarding an activity.</p>
 *
 *        What is an Activity? An activity DESCRIBES or encapsulates the work
 *        that</br> needs to be accomplished - see BPMN description of an
 *        activity for more information</p>
 *
 * 
 * 
 *        Please note that in previous versions of Geppetto (versions .2 and
 *        .3), the concept represented</br> by this class was known as an
 *        operation. The operation concept was replaced</br> because it was not
 *        well thought out and does not align with BPMN - it is hoped that as
 *        Geppetto expands it can use some form of BPMN</p>
 *
 *		  <b>Modified Date: 11/19/2015</b></br> <b>Modified By: Suresh
 *        Palanisamy</b></p>
 *        
 *		  Added new variable as location_path for other activities
 *
 *        <b>Modified Date: 11/15/2014</b></br> <b>Modified By: Dan
 *        Castillo</b></p>
 * 
 *        made deprecated the activies_types field - the original idea for this
 *        field </br> was to allow the code generation logic to determine if any
 *        particular piece of </br> code was to execute on the client or the
 *        server, after closer analysis it was </br> determined that the logic
 *        required for this task was more complex that could be </br>
 *        accomplished by the use of a single condition the
 *        <b>activities_types</b> field</br> is replaced by three new fields
 *        which are:</br></p>
 * 
 *        <li>location_logic</li> <li>processing_context</li> <li>master_flow_id
 *        </li></br></p> </p>
 *
 *        <b>Modified Date: 11/09/2014</b></br> <b>Modified By: Dan
 *        Castillo</b></p>
 * 
 * 
 *        Changed the name from GpBaseActivity to simply GpActivity</br> also
 *        removed all deprecated fields.</p>
 * 
 * 
 *        <b>Modified Date: 02/22/2014</b></br> <b>Modified By: Dan
 *        Castillo</b></p>
 * 
 *        <b>added the module_type field.</b></p> The module_type at this time
 *        is envisioned to have two primary values - it is either "default" or
 *        "not_default"</br> The "default" value indicates that the activity is
 *        part of the code assets that make up a project, whereas "not_default"
 *        indicates that the activity</br> is part of another project and is
 *        being pulled into the current project as a module because it provides
 *        needed functionality. For example, lets say that</br> a project needed
 *        the capability to upload/download files. Since this is a very commonly
 *        needed capability it would be created in a project that could be
 *        shared.</br> A Geppetto user when creating their project would simply
 *        need to drag and drop the icon representing file transfers onto their
 *        project to obtain such capabilities internally<br>
 *        any activity that came from the file transfer module would have the
 *        "not_default" value in the "module_type" field</p>
 * 
 *        <b>
 *        <ul>
 *        Implications/Open Issues
 *        </ul>
 *        </b> <li>module_type would have to be changed on the fly when a user
 *        started to use the capability on their project</li> <li>if the code
 *        for the capability is brought into the users project that means they
 *        can change it and break it</li> <li>perhaps a better way would be to
 *        provide call backs that can be registered with the reusable code so
 *        that the user can't modify the code but still modify its behavior if
 *        needed</li></p>
 * 
 * 
 *        Modified Date: 15/04/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Changed the GpScreen to GpScreenX in all declarations
 * 
 *        Modified Date: 12/03/2015 Modified By: Suresh Palanisamy
 * 
 *        Added "@JsonIgnoreProperties(ignoreUnknown=true)"
 *
 *			Added activity_type field for predefined_activities table
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class GpActivity {

	private long id;
	private String name;
	private String description;
	private String label;
	private String notes;
	private long projectid;
	private long moduleid;
	private String module_type;

	private GpNoun primary_noun;
	private ArrayList<GpNoun> secondary_nouns;

	/**
	 * 
	 * this is being replaced by three new properties
	 * 
	 */
	@Deprecated
	private ArrayList<String> activity_types;

	private String location_logic;
	private String processing_context;
	private long master_flow_id;

	private ArrayList<GpScreenX> tablet_screens;
	private ArrayList<GpScreenX> phone_screens;
	private ArrayList<GpScreenX> pc_screens;
	private ArrayList<GpVerb> theverbs;
	private ArrayList<GpScreenX> wizard_phone_screens;
	private ArrayList<GpScreenX> wizard_pc_screens;
	
	private String multi_user_status;
	private String multi_user_info;
	private Date createdate;
	private long createdby;
	private Date lastmodifieddate;
	private long lastmodifiedby;
	
	private String location_path;
	private long predefined_activity_id;
	private boolean activity_visibility;
	private String activity_type;
	
	private GpWizard activity_phone_wizard;
	private GpWizard activity_pc_wizard;

	
	//wsdl 
	
	private Long wsdl_id;
	
	public Long getWsdl_id() {
		return wsdl_id;
	}

	public void setWsdl_id(Long wsdl_id) {
		this.wsdl_id = wsdl_id;
	}

	public GpNoun getPrimary_noun() {
		return primary_noun;
	}

	public void setPrimary_noun(GpNoun primary_noun) {
		this.primary_noun = primary_noun;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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

	public long getProjectid() {
		return projectid;
	}

	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}

	public long getModuleid() {
		return moduleid;
	}

	public void setModuleid(long moduleid) {
		this.moduleid = moduleid;
	}

	public String getModule_type() {
		return module_type;
	}

	public void setModule_type(String module_type) {
		this.module_type = module_type;
	}

	public ArrayList<String> getActivity_types() {
		return activity_types;
	}

	public void setActivity_types(ArrayList<String> activity_types) {
		this.activity_types = activity_types;
	}

	public String getLocation_logic() {
		return location_logic;
	}

	public void setLocation_logic(String location_logic) {
		this.location_logic = location_logic;
	}

	public String getProcessing_context() {
		return processing_context;
	}

	public void setProcessing_context(String processing_context) {
		this.processing_context = processing_context;
	}

	public long getMaster_flow_id() {
		return master_flow_id;
	}

	public void setMaster_flow_id(long master_flow_id) {
		this.master_flow_id = master_flow_id;
	}

	public ArrayList<GpNoun> getSecondary_nouns() {
		return secondary_nouns;
	}

	public void setSecondary_nouns(ArrayList<GpNoun> secondary_nouns) {
		this.secondary_nouns = secondary_nouns;
	}

	public ArrayList<GpVerb> getTheverbs() {
		return theverbs;
	}

	public void setTheverbs(ArrayList<GpVerb> theverbs) {
		this.theverbs = theverbs;
	}

	public ArrayList<GpScreenX> getTablet_screens() {
		return tablet_screens;
	}

	public void setTablet_screens(ArrayList<GpScreenX> tablet_screens) {
		this.tablet_screens = tablet_screens;
	}

	public ArrayList<GpScreenX> getPhone_screens() {
		return phone_screens;
	}

	public void setPhone_screens(ArrayList<GpScreenX> phone_screens) {
		this.phone_screens = phone_screens;
	}

	public ArrayList<GpScreenX> getPc_screens() {
		return pc_screens;
	}

	public void setPc_screens(ArrayList<GpScreenX> pc_screens) {
		this.pc_screens = pc_screens;
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

	public String getLocation_path() {
		return location_path;
	}

	public void setLocation_path(String location_path) {
		this.location_path = location_path;
	}

	public long getPredefined_activity_id() {
		return predefined_activity_id;
	}

	public void setPredefined_activity_id(long predefined_activity_id) {
		this.predefined_activity_id = predefined_activity_id;
	}

	public boolean isActivity_visibility() {
		return activity_visibility;
	}

	public void setActivity_visibility(boolean activity_visibility) {
		this.activity_visibility = activity_visibility;
	}

	public String getActivity_type() {
		return activity_type;
	}

	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
	}

	public ArrayList<GpScreenX> getWizard_phone_screens() {
		return wizard_phone_screens;
	}

	public void setWizard_phone_screens(ArrayList<GpScreenX> wizard_phone_screens) {
		this.wizard_phone_screens = wizard_phone_screens;
	}

	public ArrayList<GpScreenX> getWizard_pc_screens() {
		return wizard_pc_screens;
	}

	public void setWizard_pc_screens(ArrayList<GpScreenX> wizard_pc_screens) {
		this.wizard_pc_screens = wizard_pc_screens;
	}

	public GpWizard getActivity_phone_wizard() {
		return activity_phone_wizard;
	}

	public void setActivity_phone_wizard(GpWizard activity_phone_wizard) {
		this.activity_phone_wizard = activity_phone_wizard;
	}

	public GpWizard getActivity_pc_wizard() {
		return activity_pc_wizard;
	}

	public void setActivity_pc_wizard(GpWizard activity_pc_wizard) {
		this.activity_pc_wizard = activity_pc_wizard;
	}
	
}