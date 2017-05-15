package com.npb.gp.dao.mysql.support.menubuilder;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.npb.gp.domain.core.GpMenuDetail;
import com.npb.gp.domain.core.GpMenuScreenDetail;

public class MenuDetailMapper implements RowMapper<GpMenuDetail> {
	public GpMenuDetail mapRow(ResultSet rs, int rowNum)throws SQLException {
		GpMenuDetail menu_detail = new GpMenuDetail();
		menu_detail.setId(rs.getLong("ID"));
		//menu_detail.setMenu_master_id(rs.getLong("MENU_MASTER_ID"));
		menu_detail.setName(rs.getString("NAME"));
		menu_detail.setLabel(rs.getString("LABEL"));
		menu_detail.setDescription(rs.getString("DESCRIPTION"));
		menu_detail.setAuth_id(rs.getLong("AUTH_ID"));
		menu_detail.setActivity_id(rs.getLong("ACTIVITY_ID"));
		menu_detail.setMenu_tree(rs.getString("MENU_TREE"));
		//menu_detail.setVerb_id(rs.getLong("VERB_ID"));
		//menu_detail.setScreen_id(rs.getLong("SCREEN_ID"));
		String str=rs.getString("MENU_TREE");
		if(!str.isEmpty()){
		ObjectMapper objectMapper = new ObjectMapper();
		List<GpMenuScreenDetail> list = null;
		try {
			list = objectMapper.readValue(rs.getString("MENU_TREE"),
					TypeFactory.defaultInstance().constructCollectionType(
							List.class, GpMenuScreenDetail.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
	   List<GpMenuScreenDetail> dto_list=new ArrayList<GpMenuScreenDetail>();
		dto_list.addAll(list);
		menu_detail.setScreen_detail(dto_list);
		}
		return menu_detail;
	}
}