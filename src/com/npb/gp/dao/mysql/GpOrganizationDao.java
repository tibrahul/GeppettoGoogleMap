package com.npb.gp.dao.mysql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.organization.Base_organization_template;
import com.npb.gp.dao.mysql.support.organization.Base_organization_user;
import com.npb.gp.dao.mysql.support.organization.Base_organization_user_dto;
import com.npb.gp.dao.mysql.support.organization.InsertOrganization;
import com.npb.gp.dao.mysql.support.organization.OrganizationMapper;
import com.npb.gp.dao.mysql.support.organization.Base_organization_user_dtoMapper;
import com.npb.gp.domain.core.GpOrganization;
import com.npb.gp.domain.core.GpUser;

@Repository("GpOrganizationDao")
public class GpOrganizationDao {

	
	
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private InsertOrganization insertOrganization;
	private Base_organization_user base_organization_user;
	private Base_organization_template base_organization_template;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}
	
	@Value("${create_organization.sql}")
	private String create_organization;
	
	@Value("${base_organization_user_sql.sql}")
	private String base_organization_user_sql;
	
	@Value("${getOrganization_by_user_id.sql}")
	private String getOrganization_by_user_id;
	
	@Value("${base_organization_template_sql.sql}")
	private String base_organization_template_sql;
	
	@Value("${get_organizationById.sql}")
	private String get_organizationById;
	public GpOrganization insert(GpOrganization organization,GpUser user) throws Exception {

		
		long Organization_id = 0;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		Map<String, Object> base_organization = new HashMap<String, Object>();
		base_organization.put("organization_name",organization.getOrganization_name());
		base_organization.put("contact_phone", organization.getContact_phone());
		
		base_organization.put("city", organization.getCity());
		base_organization.put("country", organization.getCountry());
		base_organization.put("about", organization.getAbout());
		
		
		InsertOrganization.SQL_INSERT_ORGANIZATION = create_organization;
		this.insertOrganization = new InsertOrganization(dataSource);
		this.insertOrganization.updateByNamedParam(base_organization, keyHolder);
		organization.setId(keyHolder.getKey().longValue()); 
		Organization_id = keyHolder.getKey().longValue();
		
		System.out.println("Organization_id "+ Organization_id);
		
		ArrayList<Integer> Geppetto_default_template_id = new ArrayList<Integer>(
			    Arrays.asList(15,18));
		//TO Make Geppetto ONE and Geppetto TWO as default Template
		
		for (Integer gepetto_template :Geppetto_default_template_id){
			Map<String ,Object> base_organization_template_sql_map = new HashMap<String ,Object>();
			base_organization_template_sql_map.put("base_organization_id",Organization_id);
			System.err.println("Template Id Inserted -- > "+gepetto_template.longValue());
			base_organization_template_sql_map.put("template_id",gepetto_template.longValue());
			
			
			Base_organization_template.BASE_ORGANIZATION_TEMPLATE = base_organization_template_sql;
			this.base_organization_template = new Base_organization_template(dataSource);
			this.base_organization_template.updateByNamedParam(base_organization_template_sql_map, keyHolder);
		}
		
		
		if(organization.getMembers_in_organization()!=null){
			Map<String, Object> Only_loggedin_user= new HashMap<String, Object>();
			Only_loggedin_user.put("base_organization_id",Organization_id);
			Only_loggedin_user.put("user_id",user.getId());
			
			
			Base_organization_user.BASE_ORGANIZATION_USER = base_organization_user_sql;
			this.base_organization_user = new Base_organization_user(dataSource);
			this.base_organization_user.updateByNamedParam(Only_loggedin_user, keyHolder);
			//User For Add Member
			
			for(int i =0;i<organization.getMembers_in_organization().size();i++){
			Map<String, Object> base_organization_user_map= new HashMap<String, Object>();
			base_organization_user_map.put("base_organization_id",Organization_id);
			base_organization_user_map.put("user_id",organization.getMembers_in_organization().get(i).getId());
			
			
			Base_organization_user.BASE_ORGANIZATION_USER = base_organization_user_sql;
			this.base_organization_user = new Base_organization_user(dataSource);
			this.base_organization_user.updateByNamedParam(base_organization_user_map, keyHolder);
			}
		}else{
			Map<String, Object> base_organization_user_map= new HashMap<String, Object>();
			base_organization_user_map.put("base_organization_id",Organization_id);
			base_organization_user_map.put("user_id",user.getId());
			
			
			Base_organization_user.BASE_ORGANIZATION_USER = base_organization_user_sql;
			this.base_organization_user = new Base_organization_user(dataSource);
			this.base_organization_user.updateByNamedParam(base_organization_user_map, keyHolder);
		}
		return organization;

	}

	@SuppressWarnings("null")
	public GpOrganization getOrganization_by_user_id(Long id) throws Exception {  
		// TODO Auto-generated method stub
		MapSqlParameterSource parameters;
		System.out.println("id--- > "+id);
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", id); 
		System.out.println("getOrganization_by_user_id- > "+getOrganization_by_user_id.toString());
		Base_organization_user_dtoMapper base_organization_user_dtoMapper = new Base_organization_user_dtoMapper();
		parameters.addValue("user_id", id);
		List<Base_organization_user_dto> dto_list = this.namedParameterJdbcTemplate.query(
				getOrganization_by_user_id, parameters, base_organization_user_dtoMapper);
		
		OrganizationMapper OrganizationMapper =new OrganizationMapper();
		List<GpOrganization> OrganizationList = null;
		MapSqlParameterSource parameters_organization = new MapSqlParameterSource();
		//OrganizationMapper get_organizationById.sql
		System.out.println("dto_list.size()dto_list.size()dto_list.size() >>>>> "+dto_list.size());
		
		for(int i=0;i<dto_list.size();i++){
			System.err.println("dto_list.get(i).getBase_organization_id()- > "+dto_list.get(i).getBase_organization_id());
			
			parameters_organization.addValue("base_organization_id",dto_list.get(i).getBase_organization_id());
			System.out.println("get_organizationById--- > "+get_organizationById.toString());
			OrganizationList = this.namedParameterJdbcTemplate.query(
					get_organizationById, parameters_organization, OrganizationMapper);
			
		}
		System.err.println("--OrganizationList >*************** >> - "+OrganizationList.get(0));
		
	/*	if (dto_list.size() < 1) {
			throw new Exception("Oraganization Base in User id " + id
					+ " was not found");
		}*/
		if(OrganizationList.size()<1){
			throw new Exception("Oraganization Base in User id " + id
					+ " was not found");
		}
		return (GpOrganization) OrganizationList.get(0);
	}
	
	
}
