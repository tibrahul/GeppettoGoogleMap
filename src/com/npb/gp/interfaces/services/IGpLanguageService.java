package com.npb.gp.interfaces.services;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpLanguage;

public interface IGpLanguageService {

	public ArrayList<GpLanguage> get_all_languages() throws Exception;

	public GpLanguage get_language_by_id(long id) throws Exception;

	public GpLanguage get_language_by_iso_id(String iso_id) throws Exception;

}
