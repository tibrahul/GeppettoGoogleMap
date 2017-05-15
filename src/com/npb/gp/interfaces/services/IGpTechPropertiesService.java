package com.npb.gp.interfaces.services;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpTechProperties;

/**
 * @author Dan Castillo</br> Creation Date: 06/18/2014</br>
 * @since .35</p>
 * 
 *        this interface will be implemented by a service that handles deals
 *        with tech properties</p>
 * 
 *        Modified Date: 14/04/2015</br> Modified By: Kumaresan Perumal</p>
 * 
 *        Written new method as get_default_properties
 * 
 */
public interface IGpTechPropertiesService {
	public GpTechProperties create_tech_property(GpTechProperties aproperty)
			throws Exception;

	public GpTechProperties update_tech_property(GpTechProperties aproperty)
			throws Exception;

	public void delete_tech_property(long property_id) throws Exception;

	public GpTechProperties search_for_property_by_id(long property_id,
			GpUser user) throws Exception;

	public ArrayList<GpTechProperties> search_for_property_by_type(
			String property_type, GpUser user) throws Exception;

	public ArrayList<GpTechProperties> get_all_tech_properties(GpUser user)
			throws Exception;

	public ArrayList<GpTechProperties> get_default_properties(
			GpTechProperties aproperty, GpUser user) throws Exception;
}
