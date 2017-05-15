package com.npb.gp.interfaces.services;

import java.util.List;

import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpWSDLNoun;
import com.npb.gp.domain.core.GpWsdl_With_Attribute;

public interface IGpWSDLNounService {
	
	public List<GpWSDLNoun> search_for_wsdl_by_project_id(long project_id, GpUser user) throws Exception;
	public List<GpWSDLNoun> search_for_class_by_wsdl(Long wsdl,long project_id, GpUser user) throws Exception;
	public List<GpWsdl_With_Attribute> get_class_and_attribute(long id, Object object) throws Exception;

}
