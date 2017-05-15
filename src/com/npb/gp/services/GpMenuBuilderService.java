package com.npb.gp.services;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.npb.gp.dao.mysql.GpMenuBuilderDao;
import com.npb.gp.dao.mysql.GpScreenDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMenuDetail;
import com.npb.gp.domain.core.GpMenuScreenDetail;
import com.npb.gp.domain.core.GpScreen;
import com.npb.gp.interfaces.services.IGpMenuBuilderService;

/**
 * 
 * @author KUMARESAN Creation Date: 10/07/2015
 * @since version .85
 *        </p>
 * 
 *        The menu builder service which makes us create the menu bar, sub menus
 *        and sub menus of sub menu.Here the methods are called from screenXDao
 *        and activity service.When we create an activity and a screen then
 *        delete them.
 */

@Service("GpMenuBuilderService")
public class GpMenuBuilderService implements IGpMenuBuilderService {

	private GpMenuBuilderDao menubuilder_dao;
	private GpScreenDao gpScreenDao;
	
	public GpMenuBuilderDao getMenubuilder_dao() {
		return menubuilder_dao;
	}

	@Resource(name = "GpMenuBuilderDao")
	public void setMenubuilder_dao(GpMenuBuilderDao menubuilder_dao) {
		this.menubuilder_dao = menubuilder_dao;
	}

	
	public GpScreenDao getGpScreenDao() {
		return gpScreenDao;
	}

	@Resource(name = "GpScreenDao")
	public void setGpScreenDao(GpScreenDao gpScreenDao) {
		this.gpScreenDao = gpScreenDao;
	}

	@Override
	public GpMenuDetail create_menudetail_activity(GpMenuDetail menudetail) throws Exception {
		System.out.println("showed object is " + menudetail);
		return menubuilder_dao.create_menudetail_activity(menudetail);
	}

	@Override
	public GpMenuScreenDetail update_screen_menudetail(GpMenuScreenDetail detail) throws Exception {
		return menubuilder_dao.update_menu_screen_detail(detail);
	}

	@Override
	public ArrayList<GpMenuDetail> find_menu_detail(long project_id) throws Exception {
		return menubuilder_dao.find_menu_detail(project_id);
	}

	@Override
	public void delete_menudetail_activity(GpActivity activity) throws Exception {
		GpMenuDetail menudetail = new GpMenuDetail();
		menudetail.setActivity_id(activity.getId());
		menudetail.setProject_id(activity.getProjectid());
		menubuilder_dao.delete_menudetail_activity(menudetail);
	}

	public ArrayList<GpMenuDetail> renew_menu_details(ArrayList<GpMenuDetail> menu_detail)
			throws JsonProcessingException {
		/* Update screen name/label and description in Screen table too*/
		//gpScreenDao.update_screen_through_menubuilder(menu_detail);
		return menubuilder_dao.renew_menu_details(menu_detail);
	}
}