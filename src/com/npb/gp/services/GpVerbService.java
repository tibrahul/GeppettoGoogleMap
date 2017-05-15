package com.npb.gp.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpVerbsDao;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.interfaces.services.IGpVerbService;

/**
 * 
 * @author Suresh Palanisamy</br>
 *         Date Created: 16/10/2015</br>
 * @since 1.0
 *        </p>
 * 
 */

@Service("GpVerbService")
public class GpVerbService extends GpBaseService implements IGpVerbService {

	private GpVerbsDao verbsDao;

	public GpVerbsDao getVerbsDao() {
		return verbsDao;
	}

	@Resource(name = "GpVerbsDao")
	public void setVerbsDao(GpVerbsDao verbsDao) {
		this.verbsDao = verbsDao;
	}

	@Override
	public GpVerb insert_a_verb(GpVerb averb) throws Exception {
		return this.verbsDao.insert_a_verb(averb);
	}

	@Override
	public GpVerb find_by_id(long verb_id) throws Exception {
		return null;
	}

	@Override
	public List<GpVerb> get_all_verbs_by_activity_id(long activity_id) throws Exception {
		return this.verbsDao.find_all_verbs_by_activity_id(activity_id);
	}

	@Override
	public ArrayList<GpVerb> get_all_verbs() throws Exception {
		return null;
	}

	@Override
	public List<GpVerb> get_all_base_verbs() throws Exception {
		return this.verbsDao.get_all_base_verbs();
	}

	@Override
	public GpVerb find_base_verbs_by_id(long id) throws Exception {
		return null;
	}
	
	public List<GpVerb> get_all_verbs_by_base_verb_id(long base_verb_id, long activity_id) throws Exception {
		return this.verbsDao.get_verbs_by_base_verb_id(base_verb_id, activity_id);
	}
}