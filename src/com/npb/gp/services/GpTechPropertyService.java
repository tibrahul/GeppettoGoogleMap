package com.npb.gp.services;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpTechPropertyDao;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.interfaces.services.IGpTechPropertiesService;

/**
 * 
 * @author Dan Castillo Date Created: 06/18/2014</br>
 * @since .35</p>
 * 
 *        Handles any logic dealing with the managing of technical
 *        properties</p>
 *
 *        <b>Modified Date: 14/04/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 *        <p>
 *        Wrote the following methods as insert_techproperties,
 *        update_techproperties, delete_techproperties and
 *        get_default_properties.
 *        </p>
 *
 */
@Service("GpTechPropertyService")
public class GpTechPropertyService implements IGpTechPropertiesService {

	private GpTechPropertyDao tech_prop_dao;

	public GpTechPropertyDao getTech_prop_dao() {
		return tech_prop_dao;
	}

	@Resource(name = "GpTechPropertyDao")
	public void setTech_prop_dao(GpTechPropertyDao tech_prop_dao) {
		this.tech_prop_dao = tech_prop_dao;
	}

	@Override
	public GpTechProperties create_tech_property(GpTechProperties aproperty)
			throws Exception {
		return this.tech_prop_dao.insert(aproperty);
	}

	@Override
	public GpTechProperties update_tech_property(GpTechProperties aproperty)
			throws Exception {
		return this.tech_prop_dao.update(aproperty);
	}

	@Override
	public void delete_tech_property(long property_id) throws Exception {
		this.tech_prop_dao.delete(property_id);
	}

	@Override
	public GpTechProperties search_for_property_by_id(long property_id,
			GpUser user) throws Exception {
		return null;
	}

	@Override
	public ArrayList<GpTechProperties> get_all_tech_properties(GpUser user)
			throws Exception {
		ArrayList<GpTechProperties> props;

		try {
			props = this.tech_prop_dao.get_all_properties();

			return props;

		} catch (Exception e) {
			throw new Exception("Unforseen error in GpTechPropertyService "
					+ "- in get_all_tech_properties()");
		}

	}

	@Override
	public ArrayList<GpTechProperties> search_for_property_by_type(
			String property_type, GpUser user) throws Exception {
		return null;
	}

	@Override
	public ArrayList<GpTechProperties> get_default_properties(
			GpTechProperties aproperty, GpUser user) {
		ArrayList<GpTechProperties> the_default_props = this.tech_prop_dao
				.get_default_properties(aproperty, user);
		return the_default_props;
	}

}
