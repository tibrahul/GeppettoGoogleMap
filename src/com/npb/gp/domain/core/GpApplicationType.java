package com.npb.gp.domain.core;

//import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class GpApplicationType {
	private long id;
	private String name;
	private String description;
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
	
	
}