package com.npb.gp.dao.mysql;

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
 *       <b> Modified Date : 15/09/2015 <br>
 * 			Modified By Rashmi <b><br>
 * 		
 * 		<p>Modified get_language_by_id method , to fix IndeXOutOfBoundException in case there is no record </p>
 */
 
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.language.GpLanguageBaseMapper;
import com.npb.gp.domain.core.GpLanguage;
import com.npb.gp.interfaces.dao.IGpLanguageDao;

@Repository("GpLanguageDao")
public class GpLanguageDao implements IGpLanguageDao {

	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${getAllLanguageRecords.sql}")
	private String getAllLanguageRecords;

	@Value("${findById.sql}")
	private String findById;

	@Value("${findByISOId.sql}")
	private String findByISOId;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Override
	public ArrayList<GpLanguage> get_all_languages() {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		GpLanguageBaseMapper langauage_mapper = new GpLanguageBaseMapper();
		List<GpLanguage> the_all_language_record = this.namedParameterJdbcTemplate
				.query(this.getAllLanguageRecords, parameters, langauage_mapper);
		return (ArrayList<GpLanguage>) the_all_language_record;
	}

	@Override
	public GpLanguage get_language_by_id(long id) {
		System.out.println("we have got dao class");
		System.out.println("Lanaguage id &&&&&&&" + id);
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		GpLanguageBaseMapper the_mapper = new GpLanguageBaseMapper();
		List<GpLanguage> dto_list = this.namedParameterJdbcTemplate.query(
				this.findById, parameters, the_mapper);
		if(dto_list!=null && dto_list.size()>0){
			return (GpLanguage) dto_list.get(0);
		}
		return null; 
	}

	@Override
	public GpLanguage get_language_by_iso_id(String iso_id) {

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("iso_id", iso_id);
		GpLanguageBaseMapper the_mapper = new GpLanguageBaseMapper();
		List<GpLanguage> dto_list = this.namedParameterJdbcTemplate.query(
				this.findByISOId, parameters, the_mapper);
		return (GpLanguage) dto_list.get(0);
	}

}
