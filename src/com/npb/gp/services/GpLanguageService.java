package com.npb.gp.services;

import java.util.ArrayList;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpLanguageDao;
import com.npb.gp.domain.core.GpLanguage;
import com.npb.gp.interfaces.services.IGpLanguageService;
/**
 * 
 * @author KUMARESAN
 *since geppetto_server_.75
 *       
 *        <b>Modified Date: 21/05/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        I have written three methods on the class. they are
 *        i) get_all_languages method to get all the languages from the data base.
 *        ii)get_by_id method to retrieve the data by id
 *        iii) get by iso_id method uses to get the data by iso_id 
 *        </p>
 *
 */
@Service("GpLanguageService")
public class GpLanguageService extends GpBaseService implements
IGpLanguageService {

	private GpLanguageDao langauge_dao;

	public GpLanguageDao getLangauge_dao() {
		return langauge_dao;
	}

	@Resource(name="GpLanguageDao")
	public void setLangauge_dao(GpLanguageDao langauge_dao) {
		this.langauge_dao = langauge_dao;
	}
    
	@Override
	public ArrayList<GpLanguage> get_all_languages() {
    
		ArrayList<GpLanguage> the_all_langauages=this.langauge_dao.get_all_languages();
		return the_all_langauages;
	}
    
	@Override
	public GpLanguage get_language_by_id(long id) {
		System.out.println("we have got services");
		System.out.println("Lanaguage id &&&&&&&===="+id);
		GpLanguage the_langauage_id=this.langauge_dao.get_language_by_id(id);
		return the_langauage_id;
	}

	@Override
	public GpLanguage get_language_by_iso_id(String iso_id) {
		GpLanguage the_langauage_iso_id= this.langauge_dao.get_language_by_iso_id(iso_id);
		return the_langauage_iso_id;
	}

}
