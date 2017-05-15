package com.npb.gp.interfaces.dao;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpProject;

/**
 * 
 * @author Dan Castillo</br>
 *         Date Created: 04/15/2014</br>
 * @since .35
 *        </p>
 *
 *        The purpose of this interface is to declare the standard db operations
 *        required</br>
 *        for the project functions
 *        </p>
 *
 *        please note that a form of this class has been in use since version
 *        .10 of the</br>
 *        Geppetto system. The .10 version is also known as "Cancun"
 *        </p>
 * 
 *        <b> Modified date: 06/05/2015</br>
 *        Modified by: Kumaresan perumal<b><br>
 * 
 *        <p>
 *        Written new method as get_default_module_id
 *        </p>
 *
 *        <b> Modified date: 10/04/2015</br>
 *        Modified by: Kumaresan perumal<b><br>
 * 
 *        <p>
 *        Written new method as insertion_for_deleted_projects_of_record
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
 *        Changed the return type to "insert" method
 *        </p>
 * 
 *
 */

public interface IGpProjectDao {

	public GpProject insert(GpProject aproject) throws Exception;

	public GpProject update(GpProject aproject) throws Exception;

	public void delete(GpProject aproject) throws Exception;

	public GpProject find_by_id(long project_id) throws Exception;

	public ArrayList<GpProject> find_by_user_id(long user_id) throws Exception;

	public void insertion_for_deleted_projects_of_record(GpProject aproject) throws Exception;

	public long get_default_module_id(long project_id) throws Exception;

	public boolean search_for_exist_project(String project_name) throws Exception;
}
