package com.npb.gp.interfaces.dao;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpLanguage;

public interface IGpLanguageDao {

	public ArrayList<GpLanguage> get_all_languages() throws Exception;

	public GpLanguage get_language_by_id(long id) throws Exception;

	public GpLanguage get_language_by_iso_id(String iso_id) throws Exception;
}
