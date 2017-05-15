package com.npb.gp.controllers;

import java.util.ArrayList;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gp.domain.core.GpLanguage;
import com.npb.gp.services.GpLanguageService;

/**
 * 
 * @author KUMARESAN
 * since geppetto_server_.75
 *        
 *        We have a lot of languages on the database. retrieve the languages.
 *        it depends upon the user requirements.
 *           
 *        <b>Modified Date: 21/05/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        I have written three methods on the class. they are
 *        i) get_all_languages method to get all the languages from the data base.
 *        ii)get_by_id method to retrieve the data by id
 *        iii) get by iso_id method uses to get the data by iso_id 
 *        </p>
 *
 */


@Controller("GpLanguageController")
@RequestMapping("/language/")
public class GpLanguageController {
	
	 GpLanguageService language_service;

	public GpLanguageService getLanguage_service() {
		return language_service;
	}

	@Resource(name = "GpLanguageService")
	public void setLanguage_service(GpLanguageService language_service) {
		this.language_service = language_service;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_all_languages/", headers = "Accept=application/json")
	@ResponseBody
	
	public ArrayList<GpLanguage> get_all_languages() {
		ArrayList<GpLanguage> the_all_langauage=this.language_service.get_all_languages();
		return the_all_langauage;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_language_by_id/", headers = "Accept=application/json")
	@ResponseBody

	public  GpLanguage get_language_by_id(@RequestParam("id") long id) {
		System.out.println("We have got controller");
		System.out.println("Lanaguage id= "+id);
	   GpLanguage the_langauage_by_id=this.language_service.get_language_by_id(id);
	return (GpLanguage)the_langauage_by_id;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_language_by_iso_id/", headers = "Accept=application/json")
	@ResponseBody

	public GpLanguage get_language_by_iso_id(@RequestParam("iso_id") String iso_id) {
	GpLanguage the_langauage_by_iso_id=this.language_service.get_language_by_iso_id(iso_id);
		return the_langauage_by_iso_id;
	}

}
