package com.npb.gp.interfaces.services;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestBody;

import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUser;

/**
 * @author Dan Castillo</br> Creation Date: 03/11/2014</br>
 * @since .35</p>
 * 
 *        this interface will be implemented by a service that handles screens
 *        using</br> the Android OS</p> The reason for created OS specifics
 *        Interfaces and implementations is purely</br> for flexibility in the
 *        future</p>
 * 
 *        Modified Date: 13/04/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        <p>
 *        Modified the parameter and the return type to GpScreenX in all methods
 *        </p>
 * 
 * 
 *        Modified Date: 10/22/2014</br> Modified By: Dan Castillo</p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex
 * 
 */

public interface IGpAndroidScreenService {
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
}
