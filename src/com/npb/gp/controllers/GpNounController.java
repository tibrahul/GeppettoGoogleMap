package com.npb.gp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gb.utils.GpUserUtils;
import com.npb.gp.dao.mysql.support.user.RestEndpointDto;
import com.npb.gp.dao.mysql.support.user.WsdlEndpointDto;
import com.npb.gp.domain.core.GpCouchBasedomain;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpNounAttributeType;
import com.npb.gp.domain.core.GpOtherNoun;
import com.npb.gp.services.GpNounService;

/**
 * @author Dan Castillo</br> Creation Date: 11/08/2014</br>
 * @since .75</p>
 * 
 *        The purpose of this class is to be the main interaction point with
 *        applications</br> requiring noun information</p>
 *
 *        NOTE: this class is the latest incarnation of the noun function in
 *        Geppetto</br> previous versions go back to version .2 of the product
 *        the immediate</br> predecessor of this class is listed below</p>
 * 
 *        <li>GpNounServiceController - Geppetto version .35 - Create Date:
 *        10/29/2012</li>
 * 
 *        <b>Modified Date: 13/03/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        wrote the "create_noun" service for create a noun using the noun json
 *        and added the "get_all_noun_type" service for fetch the records of
 *        noun from the database.
 * 
 *        Wrote the "update_noun" service for update the noun and noun
 *        attributes
 *        </p>
 * 
 *        <b>Modified Date: 18/03/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        modified "create_noun" to show the noun attributes size
 * 
 *        modified "update_noun" to show the noun attributes size
 *        </p>
 * 
 *        <b>Modified Date: 26/03/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Wrote the "delete_noun" to delete the nouns and its attributes
 *        </p>
 * 
 */

@Controller("GpNounController")
@RequestMapping("/noun")
public class GpNounController {

	private GpNounService noun_service;

	public GpNounService getNoun_service() {
		return noun_service;
	}

	@Resource(name = "GpNounService")
	public void setNoun_service(GpNounService noun_service) {
		this.noun_service = noun_service;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search_for_update/", headers = "Accept=application/json")
	@ResponseBody
	public GpNoun search_for_update(@RequestParam("noun_id") long noun_id)
			throws Exception {

		System.out.println("GpNounController - search_for_update - 1");

		GpNoun the_noun = this.noun_service.search_for_noun_by_noun_id(noun_id,
				null);

		return the_noun;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_all_nouns_by_project_id/", headers = "Accept=application/json")
	@ResponseBody
	public ArrayList<GpNoun> get_all_nouns_by_project_id(
			@RequestParam("project_id") long project_id) throws Exception {

		System.out
				.println("GpNounController - get_all_nouns_by_project_id - 1");

		ArrayList<GpNoun> the_nouns = this.noun_service
				.search_for_nouns_by_project_id(project_id, null);

		return the_nouns;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_a_noun/", headers = "Accept=application/json")
	@ResponseBody
	public GpNoun get_a_noun(@RequestParam("noun_id") long noun_id)
			throws Exception {

		System.out.println("GpNounController - get_a_noun - 1");

		GpNoun the_noun = this.noun_service.search_for_noun_by_noun_id(
				noun_id, null);

		return the_noun;

	}

	/**
	 * This method will return all the noun Type
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get_all_noun_type/", headers = "Accept=application/json")
	@ResponseBody
	public List<GpNounAttributeType> get_all_noun_Type() throws Exception {
		System.out.println("Get all noun type Called : ");
		List<GpNounAttributeType> gp_Noun_Attribute_type = noun_service.get_all_base_attr_type();
		return gp_Noun_Attribute_type;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/create_noun/", headers = "Accept=application/json")
	@ResponseBody
	public GpNoun create_noun(@RequestBody GpNoun anoun) throws Exception {
		System.out.println("Create Noun Called!");
		System.out.println("In noun -1 ");
		System.out.println("In noun -2 " + anoun);
		System.out.println(" noun.getName() is: " + anoun.getName());
		System.out.println("noun.getNounattributes().size() is: "
				+ anoun.getNounattributes().size());
		return this.noun_service.create_noun(anoun, GpUserUtils.getLoggedUser());
		
	}
	
	/**
	 * @author SureshAnand
	 * @return doesnt return anything 
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/save_wsdl_link/", headers = "Accept=application/json")
	@ResponseBody
	public void save_wsdl_link( @RequestBody WsdlEndpointDto wsdlEndpointDto) throws Exception {
		System.out.println("Inside  save_wsdl_link!"+wsdlEndpointDto.getWsdl_endpoint());
		System.err.println("Inside the save_wsdl_link "+wsdlEndpointDto.getProject_id());
		System.err.println("Inside GpUserUtils.getLoggedUser().getId()"+GpUserUtils.getLoggedUser().getId());
		noun_service.insert_wsdl_link(wsdlEndpointDto.getWsdl_endpoint(),wsdlEndpointDto.getProject_id()
				,GpUserUtils.getLoggedUser().getId());
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/save_rest_link/", headers = "Accept=application/json")
	@ResponseBody
	public void save_rest_link( @RequestBody RestEndpointDto restEndpointDto) throws Exception {
		System.out.println("Inside  save_wsdl_link!"+restEndpointDto.getRest_endpoint());
		System.err.println("Inside the save_wsdl_link "+restEndpointDto.getProject_id());
		System.err.println("Inside GpUserUtils.getLoggedUser().getId()"+GpUserUtils.getLoggedUser().getId());
		noun_service.insert_rest_link(restEndpointDto.getRest_endpoint(),restEndpointDto.getProject_id()
				,GpUserUtils.getLoggedUser().getId());
	}
	
	//this is for other noun
	 @RequestMapping(method = RequestMethod.GET, value = "/get_all_predefined_nouns/", headers = "Accept=application/json")
	 @ResponseBody
	 public List<GpOtherNoun> get_all_predefined_nouns() throws Exception {

	  System.out.println("Getting all predefined nouns controller");
	  List<GpOtherNoun> list_of_other_nouns = noun_service.get_all_predefined_nouns();
	     return list_of_other_nouns;

	 }
	 
	 //couch bucket
	 @RequestMapping(method = RequestMethod.GET, value = "/get_all_couch_buckets/", headers = "Accept=application/json")
	 @ResponseBody
	 public List<GpCouchBasedomain> get_all_couch_buckets() throws Exception {

	  System.out.println("Getting all couch buckets");
	  List<GpCouchBasedomain> list_of_couch_buckets = noun_service.get_all_couch_buckets();
	     return list_of_couch_buckets;

	 }
	 
	//couch design
		 @RequestMapping(method = RequestMethod.GET, value = "/get_all_couch_design/{bucket}", headers = "Accept=application/json")
		 @ResponseBody
		 public List<GpCouchBasedomain> get_all_couch_design(@PathVariable("bucket")String bucket) throws Exception {

		  System.out.println("Getting couch design"+ bucket);
		  List<GpCouchBasedomain> list_of_couch_design = noun_service.get_all_couch_design(bucket);
		     return list_of_couch_design;

		 }
		 
		//couch design
		 @RequestMapping(method = RequestMethod.GET, value = "/get_all_couch_views/{design}", headers = "Accept=application/json")
		 @ResponseBody
		 public List<GpCouchBasedomain> get_all_couch_views(@PathVariable("design")String design) throws Exception {

		  System.out.println("Getting couch design");
		  List<GpCouchBasedomain> list_of_couch_views = noun_service.get_all_couch_views(design);
		     return list_of_couch_views;

		 }
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/selected_other_noun/", headers = "Accept=application/json")
	 @ResponseBody
	 public GpNoun selected_other_noun(@RequestBody GpCouchBasedomain anoun) throws Exception {
	  
	  System.out.println("selected other noun is"+anoun.getId());
	  GpNoun noun_to_insert = new GpNoun();
	  noun_to_insert.setName(anoun.getbucket());
	  noun_to_insert.setLabel(anoun.getViews());
	  noun_to_insert.setDescription(anoun.getDesign());
	  noun_to_insert.setProjectid(anoun.getProject_id());
	  noun_to_insert.setNounattributes(new ArrayList<GpNounAttribute>());
	  
	  GpNoun createdNoun = noun_service.create_noun(noun_to_insert,GpUserUtils.getLoggedUser());
	  
	  ArrayList<GpNounAttribute> list_to_be_add = new ArrayList<GpNounAttribute>();
	  System.out.println("-----<>"+anoun.getAttribute().toString());
	  String remove_braces = anoun.getAttribute().substring(1,anoun.getAttribute().length()-1);
	  System.err.println("---> remove brace == ? "+remove_braces);
	  String[] list_of_attributes = remove_braces.split(",");  
	  
	  for (String element : list_of_attributes) {
	   
	   if(!element.contains("_id")){
	    
	    GpNounAttribute attribute = new GpNounAttribute();
	    attribute.setNounid(createdNoun.getId());
	    attribute.setName(element.substring(1,element.length()-1).trim());
	    attribute.setLabel(element.substring(1,element.length()-1).trim());
	    attribute.setDefault_activity_id(0);
	    attribute.setDescription("Description");
	    attribute.setBase_attr_type_id(4);
	    attribute.setIspart_of_unique_key(false);
	    attribute.setDatalength("");
	    attribute.setModifierId(0);
	    attribute.setModifierName("Text1234");
	    list_to_be_add.add(attribute);
	    
	   }
	   
	  }
	     
	  noun_to_insert.setNounattributes(list_to_be_add);
	  noun_service.update_noun(noun_to_insert, GpUserUtils.getLoggedUser());
	  return createdNoun;
	  
	 }

	@RequestMapping(method = RequestMethod.POST, value = "/update_noun/", headers = "Accept=application/json")
	@ResponseBody
	public void update_noun(@RequestBody GpNoun anoun) throws Exception {
		System.out.println("Update Noun Called!");
		System.out.println("In noun -1 ");
		System.out.println("In noun -2 " + anoun);
		System.out.println("noun.getId() is: " + anoun.getId());
		System.out.println("noun.getName() is: " + anoun.getName());
		System.out.println("noun.getNounattributes().size() is: "
				+ anoun.getNounattributes().size());

		this.noun_service.update_noun(anoun, GpUserUtils.getLoggedUser());
	}

	@RequestMapping(method = RequestMethod.POST, value = "/delete_noun/", headers = "Accept=application/json")
	@ResponseBody
	public void delete_noun(@RequestBody GpNoun noun) throws Exception {
		System.out.println("Delete noun called!");
		System.out.println("Noun: " + noun.getId());
		this.noun_service.delete_noun(noun);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/get_mongo_noun/", headers = "Accept=application/json")
	@ResponseBody
	public List<GpOtherNoun> get_mongo_noun(@RequestBody GpOtherNoun onoun) throws Exception {
		System.out.println("mongo noun called");
		 List<GpOtherNoun> get_mnoun = this.noun_service.get_mongo_noun_service(onoun, GpUserUtils.getLoggedUser());
		return get_mnoun;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/get_couch_noun/", headers = "Accept=application/json")
	@ResponseBody
	public List<GpCouchBasedomain> get_couch_noun(@RequestBody GpCouchBasedomain conoun ) throws Exception {
		System.out.println("couch noun called"+conoun.toString());
		 List<GpCouchBasedomain> get_couch = this.noun_service.get_couch_noun_service(conoun,GpUserUtils.getLoggedUser());
		System.out.println(get_couch);
		 return get_couch;
	}

}
