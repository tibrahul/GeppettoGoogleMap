package com.npb.gp.interfaces.services;

import java.util.ArrayList;
import java.util.List;

import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpVerb;

/**
 * @author Dan Castillo</br> Creation Date: 03/11/2014</br>
 * @since .35</p>
 * 
 *        this interface will be implemented by a service that handles screens
 *        using</br> the IOS OS</p> The reason for created OS specifics
 *        Interfaces and implementations is purely</br> for flexibility in the
 *        future</p>
 * 
 *        Modified Date: 07/04/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Changed the return type to GpScreenX for the
 *        "search_for_screens_by_project_id" method
 * 
 *        Modified Date: 06/04/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Changed the return list type to GpScreenX for the
 *        "search_for_screens_by_activity_id" method
 * 
 * 
 *        Modified Date: 10/22/2014</br> Modified By: Dan Castillo</p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex
 * 
 *        Modified Date: 19/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Modified the parameter object class GpScreenX and return type to
 *        create_screen method
 * 
 *        Modified Date: 26/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Modified the parameter object class GpScreenX and return type to
 *        update_screen and delete screen method
 * 
 *        Modified Date: 31/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Wrote the new method as get_all_base_verbs for retrieve the all base
 *        verbs from the database
 * 
 */

public interface IGpIosScreenService {
	public GpScreenX create_screen(GpScreenX ascreen, GpUser user)
			throws Exception;

	public GpScreenX update_screen(GpScreenX ascreen, GpUser user)
			throws Exception;

	public void delete_screen(GpScreenX ascreen) throws Exception;

	public GpScreenX search_for_screen_by_screen_id(long screen_id)
			throws Exception;

	public ArrayList<GpScreenX> search_for_screens_by_project_id(long project_id)
			throws Exception;

	public ArrayList<GpScreenX> search_for_screens_by_activity_id(
			long activity_id) throws Exception;

	public List<GpVerb> get_all_base_verbs() throws Exception;
}
