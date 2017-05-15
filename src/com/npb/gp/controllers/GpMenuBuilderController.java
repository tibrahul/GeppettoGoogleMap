package com.npb.gp.controllers;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gp.domain.core.GpMenuDetail;
import com.npb.gp.domain.core.GpMenuScreenDetail;
import com.npb.gp.services.GpMenuBuilderService;
/**
 * 
 * @author KUMARESAN
 * 
 * Creation Date: 10/07/2015
 * @since version .85</p>
 *
 *The menu builder controller which makes us create the menu bar,
 *sub menus and sub menus of sub menu. The concepts of the menu builder
 *is when we create an activity or a screen. It is updated on the table.
 *In this controller we can do two operations called updating and finding the menus.
 *
 */
@Controller("GpMenuBuilderController")
@RequestMapping("/menubuilder/")
public class GpMenuBuilderController {
	
	private GpMenuBuilderService menubuilder_service;
	
	public GpMenuBuilderService getMenubuilder_service() {
		return menubuilder_service;
	}
	
	@Resource(name="GpMenuBuilderService")
	public void setMenubuilder_service(GpMenuBuilderService menubuilder_service) {
		this.menubuilder_service = menubuilder_service;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/create_menudetail/", headers = "Accept=application/json")
	@ResponseBody
	public GpMenuDetail create_menu_detail(@RequestBody GpMenuDetail menudetail) throws Exception{
		System.out.print("We are in menu builder controller");
		return menubuilder_service.create_menudetail_activity(menudetail);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/update_screen_menudetail/", headers = "Accept=application/json")
	@ResponseBody
	public GpMenuScreenDetail update_screen_menu_detail(@RequestBody GpMenuScreenDetail menudetail) throws Exception{
		System.out.print("We are in menu builder controller");
		return menubuilder_service.update_screen_menudetail(menudetail);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/find_screendetail/", headers = "Accept=application/json")
	@ResponseBody
	public ArrayList<GpMenuDetail> find_screen_detail(@RequestParam("project_id") long project_id) throws Exception{
		System.out.print("We are in menu builder controller");
		return menubuilder_service.find_menu_detail(project_id);
	}
		
	@RequestMapping(method = RequestMethod.POST, value = "/update_menudetail/", headers = "Accept=application/json")
	@ResponseBody
	public ArrayList<GpMenuDetail> renew_menu_details(@RequestBody ArrayList<GpMenuDetail> menudetail) throws Exception{
		System.out.print("We are in menu builder controller");
		return menubuilder_service.renew_menu_details(menudetail);
	}
	
	
}
