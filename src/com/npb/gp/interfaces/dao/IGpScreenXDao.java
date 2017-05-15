package com.npb.gp.interfaces.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUiWidgetX;

/**
 * 
 * @author Suresh Palanisamy</br> Date Created: 07/04/2015</br>
 * @since .75</p>
 * 
 *        The purpose of this interface is to declare the standard db</br>
 *        operations required for the screenx functions</p>
 * 
 *        <b>Modified Date: 06/05/2015<br>
 *        Modified By: Suresh Palanisamy<br>
 * 
 *        <p>
 *        Removed the User ID parameter in the insert_a_widget method
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
public interface IGpScreenXDao {
	public GpScreenX insert(GpScreenX ascreen) throws Exception;

	public GpScreenX update(GpScreenX ascreen) throws Exception;

	public void delete(long id) throws Exception;

	public GpUiWidgetX insert_a_widget(GpUiWidgetX awidget, long screen_id);

	public GpUiWidgetX update_a_widget(GpUiWidgetX awidget, long screen_id);

	public void delete_a_widget(long id, long screen_id);

	public Map<String, GpUiWidgetX> find_all_widgets_by_screen(long screen_id);

	public GpScreenX find_by_id(long screen_id) throws Exception;

	public ArrayList<GpScreenX> find_by_activity_id(long activity_id)
			throws Exception;

	public ArrayList<GpScreenX> find_all_base_by_activity_id(long activity_id)
			throws Exception;

	public ArrayList<GpScreenX> find_by_project_id(long project_id)
			throws Exception;

	public List<GpScreenX> find_all_base_by_projectid(long projectid)
			throws Exception;

	public long get_widget_count() throws Exception;
}
