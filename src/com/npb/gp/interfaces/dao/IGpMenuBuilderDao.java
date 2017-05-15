package com.npb.gp.interfaces.dao;

import java.util.ArrayList;

import org.json.JSONArray;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.npb.gp.domain.core.GpMenuDetail;
import com.npb.gp.domain.core.GpMenuScreenDetail;

public interface IGpMenuBuilderDao {

	public GpMenuDetail update_menudetail_screen(GpMenuScreenDetail detail) throws Exception;

	public GpMenuScreenDetail update_menu_screen_detail(GpMenuScreenDetail detail) throws Exception;

	public GpMenuDetail find_menu_detail_activity(long activity_id) throws Exception;

	public GpMenuDetail delete_menudetail_activity(GpMenuDetail menudetail);

	public GpMenuDetail create_menudetail_activity(GpMenuDetail menudetail);

	public GpMenuDetail delete_menu_detail_screen(long id) throws Exception;

	public ArrayList<GpMenuDetail> find_menu_detail(long project_id) throws Exception;

	public ArrayList<GpMenuDetail> renew_menu_details(ArrayList<GpMenuDetail> menu_details)
			throws JsonProcessingException;

	public GpMenuScreenDetail find_screens_details_from_screens_table(long screen_id);

	public void update_menu_tree(long activity_id, JSONArray menutree) throws JsonProcessingException;

	void menu_builder_for_update_screen(GpMenuScreenDetail detail)
			throws Exception;
}