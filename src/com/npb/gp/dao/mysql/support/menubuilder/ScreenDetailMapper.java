package com.npb.gp.dao.mysql.support.menubuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpMenuScreenDetail;

public class ScreenDetailMapper implements RowMapper<GpMenuScreenDetail> {
	public GpMenuScreenDetail mapRow(ResultSet rs, int rowNum)throws SQLException {
		GpMenuScreenDetail menu_detail = new GpMenuScreenDetail();
		//menu_detail.setId(rs.getLong("ID"));
		//menu_detail.setMenu_master_id(rs.getLong("MENU_MASTER_ID"));
		//menu_detail.setName(rs.getString("NAME"));
		//menu_detail.setLabel(rs.getString("LABEL"));
		//menu_detail.setDescription(rs.getString("DESCRIPTION"));
		//menu_detail.setAuth_id(rs.getLong("AUTH_ID"));
		menu_detail.setActivity_id(rs.getLong("ACTIVITY_ID"));
		menu_detail.setProject_id(rs.getLong("PROJECT_ID"));
		//menu_detail.setMenu_tree(rs.getString("MENU_TREE"));
		//menu_detail.setVerb_id(rs.getLong("VERB_ID"));
		//menu_detail.setScreen_id(rs.getLong("SCREEN_ID"));

		return menu_detail;
	}
}