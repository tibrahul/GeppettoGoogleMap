package com.npb.gp.interfaces.dao;

import java.util.List;

import com.npb.gp.domain.core.GpButtonGroup;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpWizard;


public interface IGpWizardDao {

	public GpWizard insert(GpWizard wizard);

	public List<GpWizard> find_all_by_activityid(long activityid,String wizard_type)
			throws Exception;

	void update(long activityid, long wizardid, long screenid,String wizard_type) throws Exception;

	void deleteWizard_screen(GpScreenX ascreen) throws Exception;

	void updateWizard_screen(GpScreenX ascreen) throws Exception;

	void addWizard_screen(GpScreenX ascreen) throws Exception;

}
