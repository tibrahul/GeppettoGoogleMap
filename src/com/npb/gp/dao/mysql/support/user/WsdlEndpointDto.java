package com.npb.gp.dao.mysql.support.user;

public class WsdlEndpointDto {

	private String wsdl_endpoint;
	private int project_id;
	private long user_id;
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getWsdl_endpoint() {
		return wsdl_endpoint;
	}
	public void setWsdl_endpoint(String wsdl_endpoint) {
		this.wsdl_endpoint = wsdl_endpoint;
	}
	
}
