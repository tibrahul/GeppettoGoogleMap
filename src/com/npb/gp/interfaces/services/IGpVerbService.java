package com.npb.gp.interfaces.services;

import java.util.ArrayList;
import java.util.List;

import com.npb.gp.domain.core.GpVerb;

/**
 * 
 * @author Suresh Palanisamy</br> Date Created: 16/10/2015</br>
 * @since 1.0 </p>
 * 
 *        <p>
 *        This class handles the CRUD operations for Verb in service layer
 *        </p>
 * 
 */

public interface IGpVerbService {
	public GpVerb insert_a_verb(GpVerb averb) throws Exception;

	public GpVerb find_by_id(long verb_id) throws Exception;

	public List<GpVerb> get_all_verbs_by_activity_id(long activity_id)
			throws Exception;

	public ArrayList<GpVerb> get_all_verbs() throws Exception;

	public List<GpVerb> get_all_base_verbs() throws Exception;

	public GpVerb find_base_verbs_by_id(long id) throws Exception;
}