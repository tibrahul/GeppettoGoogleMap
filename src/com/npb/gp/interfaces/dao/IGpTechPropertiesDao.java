package com.npb.gp.interfaces.dao;

/**
 * 
 *   Modified Date: 15/04/2015</br> Modified By: Kumaresan Perumal</p>
 * 
 *   Written new method as get_default_properties
 * 
 */

import java.util.ArrayList;

import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.domain.core.GpUser;

public interface IGpTechPropertiesDao {

	public GpTechProperties insert(GpTechProperties property);

	public GpTechProperties update(GpTechProperties property);

	public void delete(long property_id);

	public GpTechProperties find_by_id(long property_id) throws Exception;

	public ArrayList<GpTechProperties> find_by_property_type(long property_type)
			throws Exception;

	public ArrayList<GpTechProperties> get_all_properties() throws Exception;

	public ArrayList<GpTechProperties> get_default_properties(
			GpTechProperties aproperty, GpUser user);
}
