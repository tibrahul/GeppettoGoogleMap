package com.npb.gp.dao.mysql.support.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectGpTemplateByOrganization_dto {

	private Long id;
	private  Long base_organization_id;
	private Long template_id ;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBase_organization_id() {
		return base_organization_id;
	}
	public void setBase_organization_id(Long base_organization_id) {
		this.base_organization_id = base_organization_id;
	}
	public Long getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(Long template_id) {
		this.template_id = template_id;
	}
	@Override
	public String toString() {
		return "SelectGpTemplateByOrganization [id=" + id + ", base_organization_id=" + base_organization_id
				+ ", template_id=" + template_id + "]";
	}
	
}
