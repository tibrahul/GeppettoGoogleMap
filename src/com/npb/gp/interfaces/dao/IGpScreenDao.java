package com.npb.gp.interfaces.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.npb.gp.domain.core.GpMenuDetail;
import com.npb.gp.domain.core.GpScreen;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUiWidgetX;

/**
 * 
 * @author Dan Castillo</br> Date Created: 03/11/2014</br>
 * @since .35</p>
 *
 *        The purpose of this interface is to declare the standard db operations
 *        required</br> for the screen functions</p>
 *
 *        please note that a form of this class has been in use since version
 *        .10 of the</br> Geppetto system. The .10 version is also known as
 *        "Cancun"</p>
 *
 *
 *        Modified Date: 10/22/2014</br> Modified By: Dan Castillo</p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex
 * 
 *
 *        Modified Date: 10/04/2014</br> Modified By: Dan Castillo</p>
 *
 *        added find_all_base_by_projectid(long projectid) method to handle
 *        searches where you only want to obtain the parent (base) noun by none
 *        of its children
 *
 *        Modified Date: 19/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Modified the parameter object class GpScreenX and return type to
 *        insert method
 * 
 *        Modified the parameter object class GpUiWidgetX and return type to
 *        insert widgets method
 * 
 *        And added new insert method for temporarily
 *
 *        Modified Date: 26/03/2015</br> Modified By: Suresh Palanisamy</p>
 *
 *        Created a new method as update_screen and modified the parameter
 *        object class GpScreenX and return type
 *
 *        Created a new method as delete screen and modified the parameter as id
 * 
 *        Created a new methods as update_a_widget and delete_a_widget
 *        
 *        Created a new methods as find_all_widgets_by_screen
 * 
 */

public interface IGpScreenDao {

	public GpScreenX insert(GpScreenX ascreen);

	public GpScreenX update(GpScreenX ascreen);

	public void delete(long id);

	public GpUiWidgetX insert_a_widget(GpUiWidgetX awidget, long screen_id,
			long user_id);

	public GpUiWidgetX update_a_widget(GpUiWidgetX awidget, long screen_id,
			long user_id);

	public void delete_a_widget(long id, long screen_id);

	public Map<String, GpUiWidgetX> find_all_widgets_by_screen(long screen_id);

	public void insert(GpScreen acreen);

	public void insert_screen_widgets(GpScreen ascreen);

	public void update(GpScreen ascreen);

	public void delete(GpScreen ascreen);

	public GpScreen find_by_id(long screen_id) throws Exception;

	public ArrayList<GpScreen> find_by_activity_id(long activity_id)
			throws Exception;

	public ArrayList<GpScreen> find_all_base_by_activity_id(long activity_id)
			throws Exception;

	public ArrayList<GpScreen> find_by_project_id(long project_id)
			throws Exception;

	public List<GpScreen> find_all_base_by_projectid(long projectid)
			throws Exception;

	public void update_screen_through_menubuilder(ArrayList<GpMenuDetail> menudetail);

}
