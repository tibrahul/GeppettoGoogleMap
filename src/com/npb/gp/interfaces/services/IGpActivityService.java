package com.npb.gp.interfaces.services;

import java.util.ArrayList;
import java.util.List;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpButtonGroup;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpWizard;

/**
 * @author Dan Castillo</br> Creation Date: 11/09/2014</br>
 * @since .35</p>
 * 
 *        this interface will be implemented by a service that handles deals
 *        with Activities</br> see GpActiviy note for an explanation of the
 *        concept of an activity</p>
 * 
 *        Note - the creation date is our of synch with the implementing
 *        classes, because </br> I simply forgot to do this and instead went
 *        straight for the implementing class - Dan castillo</p>
 * 
 *        <b>Modified Date: 06/05/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        Changed the parameter in get_module_default_id method
 *        </p>
 * 
 *        <b>Modified Date: 28/04/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        Added new method as get_module_default_id
 *        </p>
 * 
 *        <b>Modified Date: 01/04/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        Return type changed for the create_activity method and new method as
 *        delete
 *        </p>
 * 
 */

public interface IGpActivityService {

	public GpActivity create_activity(GpActivity activity, GpUser the_user)
			throws Exception;

	public GpActivity search_for_update(long activity_id) throws Exception;

	public void update_activity(GpActivity activity, GpUser the_user);

	public ArrayList<GpActivity> get_all_activities_for_project(
			long project_id, GpUser the_user) throws Exception;

	public void delete(GpActivity activity) throws Exception;

	public long get_module_default_id(long project_id) throws Exception;
	
	public ArrayList<GpActivity> get_all_predefined_activities() throws Exception;
	
	public List<GpActivity> get_activities_by_name(String name) throws Exception;

	GpWizard create_wizard(GpWizard gpWizard) throws Exception;

	List<GpWizard> get_wizards_by_id(long activity_id,String wizard_type) throws Exception;

	void update_wizard(long activityid, long wizardid, long screenid,String wizard_type) throws Exception;

	void deleteWizard_screen(GpScreenX ascreen) throws Exception;

	void updateWizard_screen(GpScreenX ascreen) throws Exception;

	void addWizard_screen(GpScreenX ascreen) throws Exception;

}
