package com.npb.gp.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gb.utils.GpUserUtils;
import com.npb.gp.domain.core.GpCouchBasedomain;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpWSDLNoun;
import com.npb.gp.domain.core.GpWsdlAttribute;
import com.npb.gp.domain.core.GpWsdlOperation;
import com.npb.gp.domain.core.GpWsdl_With_Attribute;
import com.npb.gp.services.GpNounService;
import com.npb.gp.services.GpWsdlNounService;

/**
 * @author khalisaran
 * </br> Creation Date: 09/04/2017</br>
 * 
 * @modified Dhinakar on   : 19/04/2017
 */

@Controller("GpWSDLNounController")
@RequestMapping("/wsdlNoun")
public class GpWSDLNounController {
	
	private GpWsdlNounService noun_service;
	private GpNounService service;

	
	public GpNounService getService() {
		return service;
	}
	@Resource(name="GpNounService")
	public void setService(GpNounService service) {
		this.service = service;
	}

	public GpWsdlNounService getNoun_service() {
		return noun_service;
	}

	@Resource(name="GpWsdlNounService")
	public void setNoun_service(GpWsdlNounService noun_service) {
		this.noun_service = noun_service;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_all_wsdl_by_project_id/", headers = "Accept=application/json")
	@ResponseBody
	public List<GpWSDLNoun> get_all_nouns_by_project_id(
			@RequestParam("project_id") long project_id) throws Exception {

		System.out
				.println("GpNounController - get_all_wsdl_by_project_id - 1"+project_id);

		List<GpWSDLNoun> the_nouns = this.noun_service
				.search_for_wsdl_by_project_id(project_id, null);

		return the_nouns;

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/get_all_class_from_wsdl/", headers = "Accept=application/json")
	@ResponseBody
	public List<GpWSDLNoun> get_all_class_from_wsdl(
			@RequestParam("wsdlId") Long wsdlId,@RequestParam("project_id") long project_id) throws Exception {

		System.out
				.println("GpNounController - get_all_class_from_wsdl - "+wsdlId);

		List<GpWSDLNoun> the_nouns = this.noun_service
				.search_for_class_by_wsdl(wsdlId, project_id,null);

		return the_nouns;

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/get_class_and_attribute/", headers = "Accept=application/json")
	@ResponseBody
	public List<GpWsdl_With_Attribute> get_class_and_attribute(
			@RequestParam("id") long id) throws Exception {

		System.out
				.println("GpNounController - get_all_class_from_wsdl - "+id);

		List<GpWsdl_With_Attribute> the_nouns = this.noun_service
				.get_class_and_attribute(id,null);

		return the_nouns;

	}
	
	
	 @RequestMapping(method = RequestMethod.POST, value = "/selected_other_noun/{class_name}/{project_id}", headers = "Accept=application/json")
	 @ResponseBody
	 public GpNoun selected_other_noun(@RequestBody List<GpWsdl_With_Attribute> wsdlWithAttribute,@PathVariable String class_name, @PathVariable long project_id ) throws Exception {
			System.out.println("insdie dao of add wsdl noun to noun table");
			  GpNoun noun_to_insert = new GpNoun();
			  GpNoun createdNoun =new  GpNoun();
			 
			  System.out.println("size----- of noun"+wsdlWithAttribute.size());
			 
				  noun_to_insert.setName(class_name);
				  noun_to_insert.setLabel(class_name);
				  noun_to_insert.setDescription(class_name);
				  noun_to_insert.setProjectid(project_id);
				  noun_to_insert.setNounattributes(new ArrayList<GpNounAttribute>());
				  
				   createdNoun = service.create_wsdl_noun(noun_to_insert,GpUserUtils.getLoggedUser());
				  System.err.println("created noun------>"+createdNoun.toString());
				  ArrayList<GpNounAttribute> list_to_be_add = new ArrayList<GpNounAttribute>();
				  for(int i=0 ; i<wsdlWithAttribute.size() ; i++){
					  GpNounAttribute attribute = new GpNounAttribute();
					  if(i==0){
						  noun_to_insert.setWsdl_id(wsdlWithAttribute.get(i).getWsdl_id()); 
					  }
					System.err.println("attribute name ----->"+wsdlWithAttribute.get(i).getAttribute_name());
				    attribute.setNounid(createdNoun.getId());
				    attribute.setName(wsdlWithAttribute.get(i).getAttribute_name());
				    attribute.setLabel(wsdlWithAttribute.get(i).getAttribute_name());
				    attribute.setDefault_activity_id(0);
				    attribute.setDescription("Description");
				    attribute.setBase_attr_type_id(4);
				    attribute.setIspart_of_unique_key(false);
				    attribute.setDatalength("");
				    attribute.setModifierId(0);
				    attribute.setModifierName(wsdlWithAttribute.get(i).getAttribute_type());
				    list_to_be_add.add(attribute);
				    
			  }
				    noun_to_insert.setNounattributes(list_to_be_add);
				    service.update_wsdl_noun(noun_to_insert, GpUserUtils.getLoggedUser());
				    System.out.println("update successfully");
			
			return null;
		}
		@RequestMapping(method = RequestMethod.GET, value = "/get_operations_by_wsdl_id/", headers = "Accept=application/json")
		@ResponseBody
		public List<GpWsdlOperation> get_operations_by_wsdl_id(@RequestParam("wsdl_id") long id) throws Exception {

			List<GpWsdlOperation> the_nouns = this.noun_service.get_operations_by_wsdl_id(id);
			return the_nouns;
		}
}
