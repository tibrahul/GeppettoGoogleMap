package com.npb.gp.services;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpKeyGenDao;
import com.npb.gp.interfaces.services.IGpKeyGenService;

/**
 * 
 * @author Suresh Palanisamy
 * @since version.95<br>
 *        <p>
 *        Creation Date: 25/09/2015
 *        </p>
 *
 */

@Service("GpKeyGenService")
public class GpKeyGenService extends GpBaseService implements IGpKeyGenService {

	private GpKeyGenDao key_gen_dao;

	public GpKeyGenDao getKey_gen_dao() {
		return key_gen_dao;
	}

	@Resource(name = "GpKeyGenDao")
	public void setKey_gen_dao(GpKeyGenDao key_gen_dao) {
		this.key_gen_dao = key_gen_dao;
	}

	@Override
	public ArrayList<Long> get_max_widget_count() throws Exception {
		long widget_count = this.key_gen_dao.get_max_widget_count(); // We need change, becuase it must be syncronized.
																	 // Date: 25-09-15, By: Suresh Palanisamy
		System.out.println("$$$$$$$$$$$ Max. of widget count is: " + widget_count + " $$$$$$$$$$$$");

		ArrayList<Long> no_of_widgets = new ArrayList<Long>();

		for (long i = widget_count; i < widget_count + 50; i++) {
			no_of_widgets.add(i);
		}

		widget_count += 50;
		this.key_gen_dao.update_widget_count(widget_count);
		
		return no_of_widgets;
	}
}