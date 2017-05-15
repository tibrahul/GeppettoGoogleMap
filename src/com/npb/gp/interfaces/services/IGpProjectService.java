package com.npb.gp.interfaces.services;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpUser;

/**
 * @author Dan Castillo</br>
 *         Creation Date: 04/14/2014</br>
 * @since .35
 *        </p>
 * 
 *        this interface will be implemented by a service that handles deals
 *        with projects
 *        </p>
 * 
 *        <b>Modified Date: 16/04/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Changed the return type to "update_project" method
 *        </p>
 * 
 *        <b>Modified Date: 15/04/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Written new method as get_widget_count
 *        </p>
 * 
 *        Modified Date: 10/22/2014</br>
 *        Modified By: Dan Castillo
 *        </p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex
 * 
 *        <b>Modified Date: 02/04/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Changed the return type to "create_project" method
 *        </p>
 * 
 * 
 */

public interface IGpProjectService {
	public GpProject create_project(GpProject aproject, GpUser user) throws Exception;

	public GpProject update_project(GpProject aproject, GpUser user) throws Exception;

	public void delete_project(GpProject aproject) throws Exception;

	public GpProject search_for_update(long project_id, GpUser user) throws Exception;

	public GpProject search_for_project(long project_id, GpUser user) throws Exception;

	public ArrayList<GpProject> search_project_by_user_id(GpUser user) throws Exception;

	public long get_widget_count() throws Exception;

	public boolean search_for_exist_project(String project_name) throws Exception;
}