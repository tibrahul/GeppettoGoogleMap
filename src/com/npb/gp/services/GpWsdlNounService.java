package com.npb.gp.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpWsdlNounDao;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpWSDLNoun;
import com.npb.gp.domain.core.GpWsdlAttribute;
import com.npb.gp.domain.core.GpWsdlOperation;
import com.npb.gp.domain.core.GpWsdl_With_Attribute;
import com.npb.gp.interfaces.services.IGpWSDLNounService;

/**
 * @author khalisaran
 * </br> Creation Date: 09/04/2017</br>
 * 
 * 
 */
@Service("GpWsdlNounService")
public class GpWsdlNounService  extends GpBaseService implements IGpWSDLNounService {
	
	private GpWsdlNounDao noun_dao;
	
	public GpWsdlNounDao getNoun_dao() {
		return noun_dao;
	}

	@Resource(name="GpWsdlNounDao")
	public void setNoun_dao(GpWsdlNounDao noun_dao) {
		this.noun_dao = noun_dao;
	}



	public List<GpWSDLNoun> search_for_wsdl_by_project_id(long project_id, GpUser user) throws Exception {
		List<GpWSDLNoun> the_nouns = null;
		
		the_nouns = this.noun_dao.find_WSDL_by_project_id(project_id);


		return the_nouns;
	}

	public List<GpWSDLNoun> search_for_class_by_wsdl(Long wsdl,long project_id, GpUser user) throws Exception {
		// TODO Auto-generated method stub
		
		List<GpWSDLNoun> attribute = null;
		attribute = this.noun_dao.find_class_from_wsdl(wsdl,project_id);
		return attribute;
	}

	public List<GpWsdl_With_Attribute> get_class_and_attribute(long id, Object object) throws Exception {
		List<GpWsdl_With_Attribute> wsdl_attribute = null;
		wsdl_attribute = this.noun_dao.get_class_and_attribute(id);
		return wsdl_attribute;
	}

	public List<GpWsdlOperation> get_operations_by_wsdl_id(long wsdl_id) throws Exception {
		List<GpWsdlOperation> wsdlOperation = new ArrayList<GpWsdlOperation>(); 
		wsdlOperation = this.noun_dao.get_operations_by_wsdl_id(wsdl_id);
		return wsdlOperation;
	}


}
