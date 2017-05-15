package com.npb.gb.utils;

import javax.annotation.Resource;

import com.npb.gp.dao.mysql.GpScreenXDao;
import com.npb.gp.services.GpBaseService;

public class GpWidgetCounter extends GpBaseService {

	private GpScreenXDao screenx_dao;

	public GpScreenXDao getScreenx_dao() {
		return screenx_dao;
	}

	@Resource(name = "GpScreenXDao")
	public void setScreenx_dao(GpScreenXDao screenx_dao) {
		this.screenx_dao = screenx_dao;
	}

	public long getWidgetCount() throws Exception {
		long count = this.screenx_dao.get_widget_count();
		count = count + 1;
		return count;
	}
}