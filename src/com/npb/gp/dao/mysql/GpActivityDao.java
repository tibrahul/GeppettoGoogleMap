package com.npb.gp.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.npb.gb.utils.GpGenericRecordParserBuilder;
import com.npb.gp.constants.GpBaseConstants;
import com.npb.gp.dao.mysql.support.activity.ActivityBaseMapper;
import com.npb.gp.dao.mysql.support.activity.ActivityWithScreensMapper;
import com.npb.gp.dao.mysql.support.activity.ActivityWithScreensMapper_Min;
import com.npb.gp.dao.mysql.support.activity.GpDtoActivity_and_Screens;
//import com.npb.gp.dao.mysql.GpActivityTestDao.NounMapper;
//import com.npb.gp.dao.mysql.GpActivityTestDao.NounMapper;
import com.npb.gp.dao.mysql.support.activity.InsertActivity;
import com.npb.gp.dao.mysql.support.activity.PredefinedActivityMapper;
import com.npb.gp.dao.mysql.support.activity.UpdateActivity;
import com.npb.gp.dao.mysql.support.noun.GpDto_noun_and_attributes;
import com.npb.gp.dao.mysql.support.noun.Noun_with_attributes_mapper;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpWizard;
import com.npb.gp.interfaces.dao.IGpActivityDao;

/**
 * 
 * @author Dan Castillo</br> Date Created: 02/26/2014</br>
 * @since .35</p>
 *
 *        The purpose of this class is to interact with the db for the basic
 *        search</br> and CRUD operations for an activity</p>
 *
 *        please note that a form of this class has been in use since version
 *        .10 of the</br> Geppetto system. The .10 version is also known as
 *        "Cancun", back then Activity</br used to be known as operation</p>
 * 
 *        <b> Modified date: 06/05/2015</br> Modified by: Kumaresan perumal<b><br>
 * 
 *        <p>
 *        Moved get_default_module_id method to GpProjectDao
 *        </p>
 * 
 *        <b>Modified Date: 01/04/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        Added the new method as get_default_module_id
 *        </p>
 *
 *        <b>Modified Date: 27/04/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Changed the secondary_nouns default value to "null" in update method
 *        and solved the bugs in update functionalites
 *        </p>
 *
 *        Modified Date: 15/04/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Changed the GpScreen to GpScreenX in all methods and declarations
 *
 *
 *        Modified Date: 10/22/2014</br> Modified By: Dan Castillo</p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex
 * 
 *
 *        <b>Modified Date: 01/04/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        Wrote the "delete" uses to delete the activities and the
 *        insert_activity and update_activity was moved to inside the method
 *        because of the sql error.
 *        </p>
 *
 *
 *        <b>Modified Date: 01/04/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Changed the secondary_nouns default value to "null" in insert method
 *        </p>
 *
 */
@Repository("GpActivityDao")
public class GpActivityDao implements IGpActivityDao {
	// private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private InsertActivity insert_activity;
	private UpdateActivity update_activity;

	private GpWizardDao wizard_Dao;
	private GpScreenXDao screenx_Dao;
	
	private GpMenuBuilderDao menu_builder_dao;
	
	// private ActivitySearch activity_search;
	@Value("${findAllBaseProjectActivity.sql}")
	private String findAllBaseProjectActivities;

	@Value("${updateActivity.sql}")
	private String updateActivity;

	@Value("${insertActivity.sql}")
	private String insertActivity;

	@Value("${findActivitesWithScreens.sql}")
	private String findActivitesWithScreens;

	@Value("${findAllProjectById.sql}")
	private String findAllProjectById;

	@Value("${findActivityById.sql}")
	private String findActivityById;

	@Value("${findAllNounsByActivity.sql}")
	private String findAllNounsByActivity;

	@Value("${deleteActivity.sql}")
	private String deleteActivity;

	@Value("${deleteActivityByNounId.sql}")
	private String deleteActivityByNounId;
	
	@Value("${get_module_id.sql}")
	private String get_module_id;

	@Value("${get_all_predefined_activities.sql}")
	private String get_all_predefined_activities_sql;

	@Value("${findActivityByName.sql}")
	private String find_activity_by_name_sql;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	public DataSource getDataSource() {
		return dataSource;
	}
	
	public GpWizardDao getWizard_Dao() {
		return wizard_Dao;
	}

	@Resource(name = "GpWizardDao")
	public void setWizard_Dao(GpWizardDao wizard_Dao) {
		this.wizard_Dao = wizard_Dao;
	}
	
	public GpScreenXDao getScreenx_Dao() {
		return screenx_Dao;
	}

	@Resource(name = "GpScreenXDao")
	public void setScreenx_Dao(GpScreenXDao screenx_Dao) {
		this.screenx_Dao = screenx_Dao;
	}

	
	public GpMenuBuilderDao getMenu_builder_dao() {
		return menu_builder_dao;
	}

	@Resource(name = "GpMenuBuilderDao")
	public void setMenu_builder_dao(GpMenuBuilderDao menu_builder_dao) {
		this.menu_builder_dao = menu_builder_dao;
	}

	// get rid of this - since you always pull the screens
	// well you will have non-visible - call it screens for now
	// these will be objects that you need to communicate with services
	// or do logic - or run in the background - see the Android non
	public void find_activites_with_screens(long activityid) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", activityid);
		ActivityWithScreensMapper the_mapper = new ActivityWithScreensMapper();
		List<GpDtoActivity_and_Screens> the_activity_list = this.namedParameterJdbcTemplate
				.query(findActivitesWithScreens, parameters, the_mapper);
		System.out
				.println("In find_activites_with_screens the_activity_list.size() is: "
						+ the_activity_list.size());
	}

	@Override
	public List<GpActivity> find_all_base_by_projectid(long projectid)
			throws Exception {
		System.out.println("In find_all_base_by_projectid -1");
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("projectid", projectid);
		ActivityBaseMapper the_mapper = new ActivityBaseMapper();
		List<GpActivity> dto_list = this.namedParameterJdbcTemplate.query(
				findAllBaseProjectActivities, parameters, the_mapper);
		System.out.println("In find_all_base_by_projectid - dto_list is: "
				+ dto_list.size());
		/*
		 * if (dto_list.size() < 1) { throw new
		 * Exception("no activities for project id   " + projectid +
		 * " were found");
		 * 
		 * }
		 */

		return dto_list;
	}

	@Override
	public List<GpActivity> find_all_by_projectid(long projectid)
			throws Exception {
		// TODO Auto-generated method stub
		MapSqlParameterSource parameters;
		/** handle secondary nouns - screens */
		parameters = new MapSqlParameterSource();
		parameters.addValue("projectid", projectid);
		ActivityWithScreensMapper_Min activity_and_screen_mapper = new ActivityWithScreensMapper_Min();
		List<GpDtoActivity_and_Screens> dto_list = this.namedParameterJdbcTemplate
				.query(findAllProjectById, parameters,
						activity_and_screen_mapper);
		/*
		 * if (dto_list.size() < 1) { // throw new
		 * Exception("no activities for project id   " + projectid // +
		 * " were found"); throw new
		 * Exception("no activities for project were found", new
		 * Throwable("99")); }
		 */
		// System.out.println("In find_by_id the_activity_list.size() is: " +
		// dto_list.size());
		ArrayList<GpActivity> activity_list = new ArrayList<GpActivity>();
		GpDtoActivity_and_Screens current_dto = new GpDtoActivity_and_Screens();
		for (GpDtoActivity_and_Screens a_dto : dto_list) {
			if (current_dto.getName() == null) {
				current_dto.setName(a_dto.getName());
			}
			if (!current_dto.getName().equals(a_dto.getName())) { // you have a
																	// new
																	// activity
				current_dto.setName(a_dto.getName());
			}
			GpActivity the_activity = new GpActivity();
			the_activity.setId(a_dto.getId());
			the_activity.setName(a_dto.getName());
			the_activity.setLabel(a_dto.getLabel());
			the_activity.setDescription(a_dto.getDescription());
			activity_list.add(the_activity);

		}

		return activity_list;
	}

	@Override
	public GpActivity find_by_id(long activity_id) throws Exception {
		System.out.println("The activity_id is: " + activity_id);
		MapSqlParameterSource parameters;
		/** handle secondary nouns - screens */
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", activity_id);
		ActivityWithScreensMapper activity_and_screen_mapper = new ActivityWithScreensMapper();
		List<GpDtoActivity_and_Screens> dto_list = this.namedParameterJdbcTemplate
				.query(findActivityById, parameters, activity_and_screen_mapper);
		if (dto_list.size() < 1) {
			throw new Exception("activity id number " + activity_id
					+ " was not found");
		}
		
		

		System.out.println("In find_by_id the_activity_list.size() is: "
				+ dto_list.size());

		// OJO HERE YOU SHOULD PREPARE THE GpBaseActivity off of the
		// dto_list.get(0)
		GpActivity the_activity = new GpActivity();
		// ArrayList<GpScreenX> screen_list = new ArrayList<GpScreenX>();
		ArrayList<GpScreenX> phone_screen_list = new ArrayList<GpScreenX>();
		ArrayList<GpScreenX> tablet_screen_list = new ArrayList<GpScreenX>();
		ArrayList<GpScreenX> pc_screen_list = new ArrayList<GpScreenX>();
		ArrayList<GpScreenX> wizard_phone_screens = new ArrayList<GpScreenX>();
		ArrayList<GpScreenX> wizard_pc_screens = new ArrayList<GpScreenX>();

		// the_activity.setThescreens(screen_list);
		the_activity.setPhone_screens(phone_screen_list);
		the_activity.setTablet_screens(tablet_screen_list);
		the_activity.setPc_screens(pc_screen_list);

		//set wizard screen
				List<GpWizard> wizards = this.wizard_Dao.find_all_by_activityid(activity_id,GpBaseConstants.GpDeviceType_Phone);
				for (GpWizard gpWizard : wizards) {
						the_activity.setActivity_phone_wizard(gpWizard);
						ArrayList<GpScreenX> screenData=this.screenx_Dao.find_by_activity_id(activity_id); 
						for (GpScreenX gpScreenX : screenData) {
							if(gpScreenX.getWizard_id()!=0 && gpScreenX.getClient_device_type().equals(GpBaseConstants.GpDeviceType_Phone)){
								gpScreenX.setWizard(gpWizard);
								wizard_phone_screens.add(gpScreenX);
							}
						}
				}
				wizards = this.wizard_Dao.find_all_by_activityid(activity_id,GpBaseConstants.GpDeviceType_Pc);
				for (GpWizard gpWizard : wizards) {
						the_activity.setActivity_pc_wizard(gpWizard);
						ArrayList<GpScreenX> screenData=this.screenx_Dao.find_by_activity_id(activity_id); 
						for (GpScreenX gpScreenX : screenData) {
							if(gpScreenX.getWizard_id()!=0 && gpScreenX.getClient_device_type().equals(GpBaseConstants.GpDeviceType_Pc)){
								gpScreenX.setWizard(gpWizard);
								wizard_pc_screens.add(gpScreenX);
							}
						}
				}
				the_activity.setWizard_phone_screens(wizard_phone_screens);
				the_activity.setWizard_pc_screens(wizard_pc_screens);

		String[] secondary_noun_ids = null;
		long primary_noun_id = 0;
		// GpBaseActivity the_activity = dto_list.get(0); --> you cant to this
		int i = 0;
		for (GpDtoActivity_and_Screens a_dto : dto_list) {
			if (i == 0) {
				// GpBaseActivity the_activity = new GpBaseActivity();
				the_activity.setId(a_dto.getId());
				the_activity.setName(a_dto.getName());
				the_activity.setLabel(a_dto.getLabel());
				the_activity.setDescription(a_dto.getDescription());
				the_activity.setModule_type(a_dto.getModule_type());
				the_activity.setWsdl_id(a_dto.getWsdl_id());
				System.err.println("wsdl ID set in DAO leayer - > "+i+"a_dto.getWsdl_id()- > "+a_dto.getWsdl_id());

				String activity_types[] = GpGenericRecordParserBuilder
						.parseDelimitedString(a_dto.getActivity_types());
				ArrayList<String> types = new ArrayList<String>();
				for (String type : activity_types) {
					types.add(type);
				}
				the_activity.setActivity_types(types);

				// System.out.println("$$$$$$$$$$$$$$$  a_dto.getPrimary_noun_id() is: "
				// + a_dto.getPrimary_noun_id());
				// if(a_dto.getPrimary_noun_id() > 0){
				// GpNoun temp_noun = new GpNoun();
				// temp_noun.setId(a_dto.getPrimary_noun_id());

				// }
				// the_activity.setPrimary_noun_id(a_dto.getPrimary_noun_id());
				primary_noun_id = a_dto.getPrimary_noun_id();
				the_activity.setNotes(a_dto.getNotes());
				the_activity.setCreatedate(a_dto.getCreated_date());
				the_activity.setCreatedby(a_dto.getCreated_by());
				the_activity.setLastmodifieddate(a_dto.getLast_modified_date());
				the_activity.setLastmodifiedby(a_dto.getLast_modified_by());
				the_activity.setProjectid(a_dto.getProjectid());

				if (a_dto.getSecondary_nouns() != null) {
					if (a_dto.getSecondary_nouns().length() > 0) {
						secondary_noun_ids = GpGenericRecordParserBuilder
								.parseDelimitedString(a_dto
										.getSecondary_nouns());
					}
				}

			}
			if (a_dto.getScreen_id() != 0) {
				GpScreenX a_screen = new GpScreenX();
				a_screen.setId(a_dto.getScreen_id());
				a_screen.setLabel(a_dto.getScreen_label());
				a_screen.setName(a_dto.getScreen_name());
				a_screen.setDescription(a_dto.getScreen_description());
				a_screen.setClient_device_type(a_dto
						.getScreen_client_device_type());
				a_screen.setClient_device_type_label(a_dto
						.getScreen_client_device_type_label());
				a_screen.setClient_device_type_os_name(a_dto
						.getScreen_client_device_type_os_name());
				a_screen.setHuman_language_id(a_dto
						.getScreen_human_language_id());
				List<GpWizard> wizardList = this.wizard_Dao.find_all_by_activityid(activity_id,a_screen.getClient_device_type());
				if(wizardList.size()>0){
					a_screen.setWizard(wizardList.get(0));
					a_screen.setScreen_wizard_sequence_id(a_dto.getScreen_wizard_sequence_id());
				}

				if (a_screen.getClient_device_type().equals(
						GpBaseConstants.GpDeviceType_Phone)) {
					phone_screen_list.add(a_screen);
				} else if (a_screen.getClient_device_type().equals(
						GpBaseConstants.GpDeviceType_Tablet)) {
					tablet_screen_list.add(a_screen);
				} else if (a_screen.getClient_device_type().equals(
						GpBaseConstants.GpDeviceType_Pc)) {
					pc_screen_list.add(a_screen);
				}
			}

			i++;
			/*
			 * System.out.println("\n a_screen.getId() is: " + a_screen.getId()
			 * +"\n a_screen.getLabel() is: " + a_screen.getLabel() +
			 * "\n a_screen.getName() is: " + a_screen.getName() +
			 * "\n a_screen.getDescription() is: " + a_screen.getDescription() +
			 * "\n a_screen.getClient_device_type() is: " +
			 * a_screen.getClient_device_type() +
			 * "\n a_screen.getClient_device_type_label() is: " +
			 * a_screen.getClient_device_type_label() +
			 * "\n  a_screen.getClient_device_type_os_name() is: " +
			 * a_screen.getClient_device_type_os_name() +
			 * "\n a_screen.getHuman_language_id() is: " +
			 * a_screen.getHuman_language_id());
			 */
		}

		/** handle primary and secondary nouns */
		/*
		 * OJO DONT DO THIS IF THE
		 * the_activity_list.get(0).getSecondary_nouns().length() <= 0* --> THIS
		 * MEANS THAT THERE ARE NO SECONDARY NOUNS
		 */
		System.out
				.println("*************************************** secondary_noun_ids is:  "
						+ secondary_noun_ids);
		ArrayList<Long> nounids = new ArrayList<Long>(); // these have to come
															// from the
															// secondary noun
															// field above
		if (primary_noun_id > 0) {
			System.out.println("$$$$$$$$$ ADDING THE PRIMARY NOUNID "
					+ primary_noun_id);
			nounids.add(new Long(primary_noun_id)); // you always want to get
													// the primary noun
		}

		System.out
				.println("$$$$$$$$$ BEFORE  secondary_noun_ids - nounids.size() IS: "
						+ nounids.size());
		if (secondary_noun_ids != null) {
			for (String noun_id : secondary_noun_ids) {
				nounids.add(new Long(noun_id));
			}
		}

		System.out
				.println("$$$$$$$$$ BEFORE  final check - nounids.size() IS: "
						+ nounids.size());
		if (nounids.size() == 0) {
			System.out.println("there are no nouns - going to return");
			the_activity.setSecondary_nouns(new ArrayList<GpNoun>(
					new ArrayList<GpNoun>()));
			return the_activity;
		}
		parameters = new MapSqlParameterSource();
		parameters.addValue("ids", nounids);
		Noun_with_attributes_mapper noun_and_attribute_mapper = new Noun_with_attributes_mapper();
		List<GpDto_noun_and_attributes> the_noun_attrib_list = this.namedParameterJdbcTemplate
				.query(findAllNounsByActivity, parameters,
						noun_and_attribute_mapper);
		if (the_noun_attrib_list.size() < 1) {
			throw new Exception("nouns not found for activity ");
		} else {
			System.out.println("&&&&&&&&&&&&& BINGO! &&&&&&&&&&&&&&&&&&&&"
					+ "\n the size of the nounids is: " + nounids.size());
		}

		ArrayList<GpNoun> the_noun_list = new ArrayList<GpNoun>();
		ArrayList<Long> processed = new ArrayList<Long>();
		GpNoun current_noun = null;
		boolean found = false;
		// looking and adding distinct nouns
		for (GpDto_noun_and_attributes noun_and_attribs : the_noun_attrib_list) {
			found = false;
			for (Long an_id : processed) {
				if (noun_and_attribs.getNoun_id() == an_id.longValue()) {
					found = true;
				}

			}
			if (!found) {
				
				if (noun_and_attribs.getNoun_id() == primary_noun_id) {
					current_noun = this.noun_processing(noun_and_attribs);
					processed.add(new Long((current_noun.getId())));
					// if(current_noun.getId() == primary_noun_id){
					// the_activity.setPrimary_noun(current_noun);
					// }else{
					the_noun_list.add(current_noun);
				}else{
				processed.add(new Long((noun_and_attribs.getNoun_id())));
				// if(current_noun.getId() == primary_noun_id){
				// the_activity.setPrimary_noun(current_noun);
				// }else{
				the_noun_list.add(this.noun_processing(noun_and_attribs));
				// }
				}
				found = false;
			}

		}

		System.out.println("the size of the_noun_list is: "
				+ the_noun_list.size());
		// now handle the noun attributes
		for (GpNoun a_noun : the_noun_list) {
			ArrayList<GpNounAttribute> noun_attribs = new ArrayList<GpNounAttribute>();
			for (GpDto_noun_and_attributes noun_and_attribs : the_noun_attrib_list) {
				if (noun_and_attribs.getNoun_id() == a_noun.getId()) {
					if (noun_and_attribs.getAttribute_id() > 0) {
						GpNounAttribute attrib = this
								.attribute_processing(noun_and_attribs);
						noun_attribs.add(attrib);
					}
				}
			}
			a_noun.setNounattributes(noun_attribs);
		}

		/* wow what a HACK! */
		ArrayList<GpNoun> secondary_nouns = new ArrayList<GpNoun>();
		for (GpNoun a_noun : the_noun_list) {
			if (a_noun.getId() == primary_noun_id) {
				the_activity.setPrimary_noun(current_noun);
			} else {
				secondary_nouns.add(a_noun);
			}
		}
		the_activity.setSecondary_nouns(new ArrayList<GpNoun>(secondary_nouns));

		return the_activity;
	}

	@Override
	public GpActivity insert(GpActivity activity) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", activity.getName());
		paramMap.put("label", activity.getLabel());
		paramMap.put("description", activity.getDescription());
		paramMap.put("projectid", activity.getProjectid());
		paramMap.put("moduleid", activity.getModuleid());
		paramMap.put("notes", activity.getNotes());

		String the_types = GpGenericRecordParserBuilder
				.buildDelimitedString(activity.getActivity_types());
		paramMap.put("activity_types", the_types);

		String secondary_nouns = null;
		ArrayList<String> noun_id_strings = new ArrayList<String>();

		if (activity.getSecondary_nouns() != null
				&& activity.getSecondary_nouns().size() > 0) {

			for (GpNoun anoun : activity.getSecondary_nouns()) {
				String a_string = new Long(anoun.getId()).toString();
				noun_id_strings.add(a_string);
			}
			secondary_nouns = GpGenericRecordParserBuilder
					.buildDelimitedString(noun_id_strings);
		}

		paramMap.put("secondary_nouns", secondary_nouns);
		String primary_nouns = null;
		if (activity.getPrimary_noun() != null) {
			primary_nouns = "" + activity.getPrimary_noun().getId();
		}
		paramMap.put("primary_noun_id", primary_nouns);
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", activity.getCreatedby());
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", activity.getLastmodifiedby());
		paramMap.put("module_type", activity.getModule_type());
		paramMap.put("predefined_activity_id",
				activity.getPredefined_activity_id());
		if( activity.getWsdl_id()!=null){
			System.err.println("-- wsdl id is  - > > - > "+activity.getWsdl_id());
			//wsdl_id
			paramMap.put("wsdl_id", activity.getWsdl_id());
		}else{
			System.err.println("-- wsdl id is NULL "+activity.getWsdl_id());
			paramMap.put("wsdl_id",null);
		}
		

		// this.namedParameterJdbcTemplate.update(sql, paramSource,
		// generatedKeyHolder);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertActivity.SQL_INSERT_ACTIVITY = insertActivity;
		this.insert_activity = new InsertActivity(this.dataSource);
		this.insert_activity.updateByNamedParam(paramMap, keyHolder);
		activity.setId(keyHolder.getKey().longValue());
		System.out.println("The activity id is: " + activity.getId());
		// log.info("New contact inserted with id: " + contact.getId());
		return activity;
	}

	@Override
	public void update(GpActivity activity) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", activity.getId());
		paramMap.put("name", activity.getName());
		paramMap.put("label", activity.getLabel());
		paramMap.put("description", activity.getDescription());
		paramMap.put("projectid", activity.getProjectid());
		paramMap.put("moduleid", activity.getModuleid());
		paramMap.put("notes", activity.getNotes());

		String the_types = GpGenericRecordParserBuilder
				.buildDelimitedString(activity.getActivity_types());
		paramMap.put("activity_types", the_types);

		String secondary_nouns = null;
		ArrayList<String> noun_id_strings = new ArrayList<String>();

		if (activity.getSecondary_nouns().size() > 0) {
			for (GpNoun anoun : activity.getSecondary_nouns()) {

				String a_string = new Long(anoun.getId()).toString();
				noun_id_strings.add(a_string);

				secondary_nouns = GpGenericRecordParserBuilder
						.buildDelimitedString(noun_id_strings);
			}
		}

		paramMap.put("secondary_nouns", secondary_nouns);

		if (activity.getPrimary_noun() != null) {
			paramMap.put("primary_noun_id", activity.getPrimary_noun().getId());
		} else {
			paramMap.put("primary_noun_id", 0);
		}

		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", activity.getLastmodifiedby());

		UpdateActivity.SQL_UPDATE_ACTIVITY = updateActivity;
		this.update_activity = new UpdateActivity(this.dataSource);

		this.update_activity.updateByNamedParam(paramMap);

	}

	@Override
	public void delete(long activity_id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", activity_id);

		this.namedParameterJdbcTemplate.execute(deleteActivity, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
	}

	private GpNoun noun_processing(GpDto_noun_and_attributes noun_and_attribs) {
		GpNoun noun = new GpNoun();
		ArrayList<GpNounAttribute> noun_attribs = new ArrayList<GpNounAttribute>();

		noun.setId(noun_and_attribs.getNoun_id());
		noun.setName(noun_and_attribs.getNoun_name());
		noun.setDescription(noun_and_attribs.getNoun_description());
		noun.setLabel(noun_and_attribs.getNoun_label());
		noun.setDefault_activity_id(noun_and_attribs.getNoun_default_activity_id());
		noun.setNotes(noun_and_attribs.getNoun_notes());

		noun.setCreatedate(noun_and_attribs.getNoun_createdate());
		noun.setCreatedby(noun_and_attribs.getNoun_createdby());
		noun.setLastmodifieddate(noun_and_attribs.getNoun_lastmodifieddate());
		noun.setLastmodifiedby(noun_and_attribs.getNoun_lastmodifiedby());
		noun.setModuleid(noun_and_attribs.getNoun_moduleid()); // this is
																// probably
																// superfluous
																// but it might
																// make things
																// easier later
		noun.setProjectid(noun_and_attribs.getNoun_projectid()); // this is
																	// probably
																	// superfluous
																	// but it
																	// might
																	// make
																	// things
																	// easier
																	// later
		noun.setNounattributes(noun_attribs);

		return noun;
	}

	private GpNounAttribute attribute_processing(
			GpDto_noun_and_attributes noun_and_attribs) {

		GpNounAttribute noun_attrib = new GpNounAttribute();

		noun_attrib.setId(noun_and_attribs.getAttribute_id());
		noun_attrib.setNounid(noun_and_attribs.getAttribute_nounid());
		noun_attrib.setName(noun_and_attribs.getAttribute_name());
		noun_attrib.setLabel(noun_and_attribs.getAttribute_label());
		noun_attrib.setDescription(noun_and_attribs.getAttribute_description());
		noun_attrib.setDatalength(noun_and_attribs.getAttribute_data_length());
		noun_attrib.setBase_attr_type_id(noun_and_attribs.getBase_attr_type_id());
		noun_attrib.setNotes(noun_and_attribs.getAttribute_notes());
		// noun_attrib_dto.getAttribute_relationships(); --> you dont have this
		// in the core model yet 4/29/2014

		noun_attrib.setCreatedate(noun_and_attribs.getAttribute_createdate());
		noun_attrib.setCreatedby(noun_and_attribs.getAttribute_createdby());
		noun_attrib.setLastmodifieddate(noun_and_attribs
				.getAttribute_lastmodifieddate());

		noun_attrib.setLastmodifiedby(noun_and_attribs
				.getAttribute_lastmodifiedby());
		noun_attrib.setModifierName(noun_and_attribs.getAttribute_modifier_name());
		noun_attrib.setModifierId(noun_and_attribs.getAttribute_modifier_id());

		return noun_attrib;

	}

	@Override
	public ArrayList<GpActivity> get_all_predefined_activities()
			throws Exception {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();

		PredefinedActivityMapper predefinedActivityMapper = new PredefinedActivityMapper();

		List<GpActivity> predefined_activities_list = this.namedParameterJdbcTemplate
				.query(this.get_all_predefined_activities_sql, parameters,
						predefinedActivityMapper);
		if (predefined_activities_list.size() < 1) {
			throw new Exception("no activities found");
		}
		return (ArrayList<GpActivity>) predefined_activities_list;
	}

	public List<GpActivity> get_activity_by_name(String name) throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("name", name);
		ActivityBaseMapper the_mapper = new ActivityBaseMapper();
		return this.namedParameterJdbcTemplate.query(find_activity_by_name_sql,
				parameters, the_mapper);
	}
	
	public void deleteByNounId(long activityId) throws Exception {
		this.delete(activityId);
		this.menu_builder_dao.delete_menudetail_by_activityid(activityId);
	}
}
