package com.npb.gp.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gp.domain.core.GpMobileDeviceType;
import com.npb.gp.services.GpMobileDeviceTypeService;

/**
 * 
 * @author Suresh Palanisamy </br> Creation Date: 25/03/2015
 * @since version .75</p>
 * 
 *        The purpose of this class is to be handling of the device types
 *        representing a</br> Geppetto Device Types</br>
 * 
 * 
 */

@Controller("GpMobileDeviceTypeController")
@RequestMapping("/devicetypes")
public class GpMobileDeviceTypeController {

	private GpMobileDeviceTypeService mobile_device_service;

	public GpMobileDeviceTypeService getMobile_device_service() {
		return mobile_device_service;
	}

	@Resource(name = "GpMobileDeviceTypeService")
	public void setMobile_device_service(
			GpMobileDeviceTypeService mobile_device_service) {
		this.mobile_device_service = mobile_device_service;
	}

	@RequestMapping(value = "/create_device/", method = RequestMethod.POST, headers = "application/json")
	@ResponseBody
	public void create_mobile_device(
			@RequestBody GpMobileDeviceType a_mobile_device) throws Exception {

	}

	@RequestMapping(value = "/update_device/", method = RequestMethod.POST, headers = "application/json")
	@ResponseBody
	public void update_mobile_device(
			@RequestBody GpMobileDeviceType a_mobile_device) throws Exception {

	}

	@RequestMapping(method = RequestMethod.GET, value = "/find_all_devices/")
	@ResponseBody
	public List<GpMobileDeviceType> find_all_devices() throws Exception {
		System.out.println("find all devices called!");

		List<GpMobileDeviceType> all_devices = this.mobile_device_service
				.find_all_devices();

		System.out.println("Devices: " + all_devices);

		return all_devices;
	}

}
