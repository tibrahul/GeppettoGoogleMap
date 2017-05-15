package com.npb.gp.interfaces.dao;

import java.util.List;

import com.npb.gp.domain.core.GpWSDLNoun;

public interface IGpWSDLNounDoa {
	
	public List<GpWSDLNoun> find_WSDL_by_project_id(long project_id) throws Exception;

}
