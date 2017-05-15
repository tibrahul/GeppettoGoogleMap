package com.npb.gp.interfaces.dao;

import java.util.ArrayList;
import java.util.List;

import com.npb.gp.domain.core.GpVerb;

/**
 * 
 * @author Dan Castillo</br> Date Created: 06/13/2014</br>
 * @since .75</p>
 *
 *        The purpose of this interface is to declare the standard db operations
 *        required</br> for the functions that handle verbs -see GpVerb class
 *        for description of verbs</p>
 *
 *        Modified Date: 02/24/2015</br> Modified By: Dan Castillo</p>
 * 
 *        added the get_verbs_by_activity_id method
 *
 *        Modified Date: 30/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        added the insert_a_verb and the get_all_base_verbs method
 * 
 *        Modified Date: 31/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        added the find_base_verbs_by_id method to find the base verb based on
 *        its id
 * 
 *        Modified Date: 15/10/2015</br> Modified By: Reinaldo Lopez</p>
 * 
 *        added the delete_verbs and get_verbs_by_screen_id methods
 *
 */

public interface IGpVerbsDao {

	public GpVerb insert_a_verb(GpVerb averb) throws Exception;

	public GpVerb update_a_verb_screen(GpVerb averb) throws Exception;

	public GpVerb find_by_id(long verb_id) throws Exception;

	public ArrayList<GpVerb> get_verbs_by_activity_id(long activity_id)
			throws Exception;

	public ArrayList<GpVerb> get_all_verbs() throws Exception;

	public List<GpVerb> get_all_base_verbs() throws Exception;

	public List<GpVerb> find_all_verbs_by_activity_id(long activity_id)
			throws Exception;

	public GpVerb find_base_verbs_by_id(long id) throws Exception;

	public void delete_verbs(long screen_id) throws Exception;

	public ArrayList<GpVerb> get_verbs_by_screen_id(long screen_id)
			throws Exception;

	public GpVerb update_wsdl_operation_id(GpVerb averb) throws Exception;
}
