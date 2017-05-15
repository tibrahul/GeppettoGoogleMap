package com.npb.gp.interfaces.services;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMenuDetail;
import com.npb.gp.domain.core.GpMenuScreenDetail;

public interface IGpMenuBuilderService {

	public GpMenuDetail create_menudetail_activity(GpMenuDetail menudetail) throws Exception;

	public GpMenuScreenDetail update_screen_menudetail(GpMenuScreenDetail detail)
			throws JsonProcessingException, Exception;

	public ArrayList<GpMenuDetail> find_menu_detail(long activity_id) throws Exception;

	// public GpMenu_detail delete_screen(long activity_id) throws Exception;
	// public GpMenuDetail update_menudetail_activity(GpMenuDetail
	// menudetail)throws Exception;
	void delete_menudetail_activity(GpActivity activity) throws Exception;
}