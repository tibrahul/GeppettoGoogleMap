package com.npb.gp.controllers;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gp.services.GpKeyGenService;

/**
 * 
 * @author Suresh Palanisamy
 * @since version.95<br>
 *        <p>
 *        Creation Date: 25/09/2015
 *        </p>
 *
 */

@Controller("GpKeyGenController")
@RequestMapping("/keygen/")
public class GpKeyGenController {

	private GpKeyGenService gp_key_gen_service;

	public GpKeyGenService getGp_key_gen_service() {
		return gp_key_gen_service;
	}

	@Resource(name = "GpKeyGenService")
	public void setGp_key_gen_service(GpKeyGenService gp_key_gen_service) {
		this.gp_key_gen_service = gp_key_gen_service;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_max_widget_count/")
	@ResponseBody
	public ArrayList<Long> get_max_widget_count() throws Exception {
		System.out.println("In GpKeyGenController - get_max_widget_count");

		ArrayList<Long> no_of_widgets = this.gp_key_gen_service.get_max_widget_count();
		return no_of_widgets;
	}
}