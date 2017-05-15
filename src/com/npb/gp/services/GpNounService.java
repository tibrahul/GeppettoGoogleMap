package com.npb.gp.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;

import com.npb.gb.utils.GpUserUtils;
import com.npb.gp.dao.mysql.GpNounDao;
import com.npb.gp.dao.mysql.support.screen.GpScreenWidgetParser;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpCouchBasedomain;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpNounAttributeType;
import com.npb.gp.domain.core.GpOtherNoun;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.interfaces.services.IGpNounService;

/**
 * @author Dan Castillo</br> Date Created: 04/08/2014</br>
 * @since .35</p>
 * 
 * 
 *        The purpose of this class is to provide the entry point for any
 *        functions having to do with nouns</br>
 * 
 *        please note that a form of this class has been in use since version
 *        .10 of the</br> Geppetto system. The .10 version is also known as
 *        "Cancun"</p>
 * 
 *        Modified Date: 10/22/2014</br> Modified By: Dan Castillo</p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex
 * 
 *        Modified Date: 13/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Modified the update noun method to pass the noun and noun attributes
 *        values to noun dao
 * 
 *        wrote update_noun_attribute method to supporting update noun method
 * 
 *        <b>Modified Date: 25/03/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        modified "update_noun" to update the noun attributes based on its
 *        status like if deleted or if added
 * 
 *        wrote new method as delete_noun_attribute
 *        </p>
 * 
 *        <b>Modified Date: 26/03/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Wrote the "delete_noun" to delete the nouns and its attributes
 *        </p>
 *        
 *        <b>Modified Date: 05/31/2016<br>
 *        Modified By: Kumaresan Perumal<b><br>
 *        <p>
 *       I wrote some code create_noun method to check up the validation. here we find noun name exist or not
 *        </p>
 *        
 */
@Service("GpNounService")
public class GpNounService extends GpBaseService implements IGpNounService {

	private GpNounDao noun_dao;
	private GpActivityService activityService;
	private GpScreenWidgetParser gpScreenWidgetParser;

	public GpNounDao getNoun_dao() {
		return noun_dao;
	}

	@Resource(name = "GpNounDao")
	public void setNoun_dao(GpNounDao noun_dao) {
		this.noun_dao = noun_dao;
	}

	public GpActivityService getActivityService() {
		return activityService;
	}

	@Resource(name = "GpActivityService")
	public void setActivityService(GpActivityService activityService) {
		this.activityService = activityService;
	}

	public GpScreenWidgetParser getGpScreenWidgetParser() {
		return gpScreenWidgetParser;
	}

	@Resource(name = "GpScreenWidgetParser")
	public void setGpScreenWidgetParser(
			GpScreenWidgetParser gpScreenWidgetParser) {
		this.gpScreenWidgetParser = gpScreenWidgetParser;
	}

	GpPcScreenService pc_service;

	public GpPcScreenService getPc_service() {
		return pc_service;
	}

	@Resource(name = "GpPcScreenService")
	public void setPc_service(GpPcScreenService pc_service) {
		this.pc_service = pc_service;
	}
	
	@Override
	public GpNoun create_noun(GpNoun anoun, GpUser user) throws Exception {

		anoun.setCreatedby(user.getId());
		anoun.setLastmodifiedby(user.getId());
		anoun.setDefault_activity_id(0);

		ArrayList<GpNoun> the_nouns  = this.search_for_nouns_by_project_id(anoun.getProjectid(),user);
		for (GpNoun theNoun : the_nouns) {
			if(theNoun.getName().equals(anoun.getName())){
				return null;
			}
		}
		
		
		this.noun_dao.insert(anoun);
				
		if (anoun.getNounattributes().size() > 0) {
			for (GpNounAttribute an_attrib : anoun.getNounattributes()) {
				this.insert_noun_attribute(an_attrib, anoun.getId(),
						user.getId());
			}
		}
/*		// create activity
		String value = null;
		// validate if activity name duplicate

		GpActivity activity = new GpActivity();
		activity.setProjectid(anoun.getProjectid());
		activity.setModuleid(anoun.getModuleid());

		value = anoun.getName()+"_Default_Activity";
		
		activity.setLabel(value);
		activity.setDescription(value);
		activity.setName(value);
		activity.setPrimary_noun(anoun);
		activity.setModule_type("default");
		

		this.activityService.create_activity(activity, user);
		// create verbs
		this.gpScreenWidgetParser.insert_default_activity_verbs(activity
				.getId());
		// update noun default activity id
		anoun.setDefault_activity_id(activity.getId());
		this.noun_dao.update_default_activity_id(anoun);
*/		return anoun;
	}

	@Override
	public void update_noun(GpNoun anoun, GpUser user) throws Exception {

		anoun.setLastmodifiedby(user.getId());

		this.noun_dao.update(anoun);

		List<GpNounAttribute> nounAtrribList = new ArrayList<GpNounAttribute>();
		if (anoun.getId() != 0L) {
			nounAtrribList = this.noun_dao.find_All_Noun_Attributes(anoun
					.getId());
		}
		
		//attribute is added for the first time
		if(anoun.getNounattributes().size() > 0 && nounAtrribList.size() == 0 ){
			
			//create activity
			String value = null;
			// validate if activity name duplicate

			GpActivity activity = new GpActivity();
			activity.setProjectid(anoun.getProjectid());
			activity.setModuleid(anoun.getModuleid());

			value = anoun.getName()+"_Default_Activity";
			
			activity.setLabel(value);
			activity.setDescription(value);
			activity.setName(value);
			activity.setPrimary_noun(anoun);
			activity.setModule_type("default");

			this.activityService.create_activity(activity, user);
			// create verbs
			this.gpScreenWidgetParser.insert_default_activity_verbs(activity
					.getId());
			// update noun default activity id
			anoun.setDefault_activity_id(activity.getId());
			this.noun_dao.update_default_activity_id(anoun);
			
		}
		
		//if user deletes all the attributes then delete the default activity as well
		if(anoun.getNounattributes().size() == 0 && nounAtrribList.size() > 0 ){
			delete_default_activity(anoun);
		}
			
		
		if (anoun.getNounattributes().size() > 0 || nounAtrribList.size() > 0) {
			this.delete_noun_attribute(nounAtrribList,
					anoun.getNounattributes());
			ArrayList<GpNoun> created_secondary_noun = new ArrayList<GpNoun>();
			for (GpNounAttribute aNounAttribute : anoun.getNounattributes()) {
				if (aNounAttribute.getId() == 0L) {
					this.insert_noun_attribute(aNounAttribute, anoun.getId(),
							user.getId());
				} else {
					this.update_noun_attribute(aNounAttribute, anoun.getId(),
							user.getId());
					if(aNounAttribute.isIs_secondary_noun()){
						List<GpNoun> existing_nouns = this.search_for_nouns_by_project_id(anoun.getProjectid(), user);
						boolean already_created_noun = false;
						for(GpNoun existing_noun : existing_nouns){
							if(existing_noun.getName().equals(aNounAttribute.getName())){
								already_created_noun = true;
								break;
							}
						}
						if(!already_created_noun){
							ArrayList<GpNounAttribute> list_to_be_add = new ArrayList<GpNounAttribute>();
							GpNoun noun_to_insert = new GpNoun();
							noun_to_insert.setName(aNounAttribute.getName());
							noun_to_insert.setLabel(aNounAttribute.getName());
							noun_to_insert.setDescription(aNounAttribute.getName());
							noun_to_insert.setProjectid(anoun.getProjectid());
							noun_to_insert.setNounattributes(list_to_be_add);
							GpNoun createdNoun = this.create_noun(noun_to_insert,GpUserUtils.getLoggedUser());
							GpNounAttribute attribute = new GpNounAttribute();
						    attribute.setNounid(createdNoun.getId());
						    attribute.setName(aNounAttribute.getName());
						    attribute.setLabel(aNounAttribute.getName().toLowerCase());
						    attribute.setDefault_activity_id(0);
						    attribute.setDescription("Description");
						    attribute.setBase_attr_type_id(4);
						    attribute.setIspart_of_unique_key(false);
						    attribute.setDatalength("");
						    attribute.setModifierId(0);
						    attribute.setModifierName("Text1234");
						    list_to_be_add.add(attribute);
						    GpNounAttribute attribute_2 = new GpNounAttribute();
						    attribute_2.setNounid(createdNoun.getId());
						    attribute_2.setName("Description");
						    attribute_2.setLabel("description");
						    attribute_2.setDefault_activity_id(0);
						    attribute_2.setDescription("Description");
						    attribute_2.setBase_attr_type_id(4);
						    attribute_2.setIspart_of_unique_key(false);
						    attribute_2.setDatalength("");
						    attribute_2.setModifierId(0);
						    attribute_2.setModifierName("Text1234");
						    list_to_be_add.add(attribute_2);
						    noun_to_insert.setNounattributes(list_to_be_add);
							this.update_noun(noun_to_insert, GpUserUtils.getLoggedUser());
							created_secondary_noun.add(noun_to_insert);
						}
					}
					else {
						
					}
				}
			}
			GpActivity activity = this.activityService.search_for_update(anoun.getDefault_activity_id());
			activity.setSecondary_nouns(created_secondary_noun);
			this.activityService.update_activity(activity, user);
		}
	}

	@Override
	public void delete_noun(GpNoun anoun) throws Exception {
		
		GpNoun gpNoun=this.noun_dao.find_by_id(anoun.getId());
		
		delete_default_activity(gpNoun);
				
		this.noun_dao.delete(anoun.getId());
		
		for (GpNounAttribute an_attrib : anoun.getNounattributes()) {
			this.noun_dao.delete_a_noun_attribute(an_attrib.getId(),
					anoun.getId());
		}
	}

	@Override
	public GpNoun search_for_noun_by_noun_id(long noun_id, GpUser user)
			throws Exception {

		GpNoun the_noun = this.noun_dao.find_by_id(noun_id);

		return the_noun;

	}

	@Override
	public ArrayList<GpNoun> search_for_nouns_by_project_id(long project_id,
			GpUser user) throws Exception {
		ArrayList<GpNoun> the_nouns = null;
		try {
			the_nouns = this.noun_dao.find_by_project_id(project_id);

			return the_nouns;

		} catch (Exception e) {
			if (e.getCause().toString().contains("99")) {
				ArrayList<GpNoun> nouns = new ArrayList<GpNoun>();
				return nouns;
			} else {
				e.printStackTrace();
			}
		}
		return the_nouns;
	}

	private void insert_noun_attribute(GpNounAttribute an_attribute,
			long noun_id, long user_id) throws Exception {
		an_attribute.setNounid(noun_id);
		an_attribute.setCreatedby(user_id);
		an_attribute.setLastmodifiedby(user_id);
		//if base_attr_type_id == 9 then insert in relationships column {type:noun,id:"id"}
		if(an_attribute.getBase_attr_type_id()==9){
			String json = "{relationshiptype:child,type:Noun,id:"+an_attribute.getModifierId()+"}";
			an_attribute.setRelationships(json);
		}
		//if list of noun then also same , if list is of stnd types then {type:"noun",id:""}
		if (an_attribute.getBase_attr_type_id()==10  &&  (an_attribute.getModifierId()!=0)){
			String json = "{relationshiptype:child,type:Noun,id:"+an_attribute.getModifierId()+"}";
			an_attribute.setRelationships(json);
		}else if(an_attribute.getBase_attr_type_id()==10  &&  (an_attribute.getModifierId()==0)){
			String json = "{relationshiptype:child,type:'"+ an_attribute.getModifierName() +"'}";
			an_attribute.setRelationships(json);
		}
		
		
		this.noun_dao.insert_a_noun_attribute(an_attribute, noun_id, user_id);
	}

	
	public void insert_wsdl_link(String link, int project_id , long user_id) throws Exception{
		System.err.println("Inside the Noun service "+link);
		this.noun_dao.insert_wsdl_link(link,project_id,user_id);
	}
	
	public void insert_rest_link(String link, int project_id , long user_id) throws Exception{
		System.err.println("Inside the Noun service "+link);
		this.noun_dao.insert_rest_link(link,project_id,user_id);
	}
	
	private void update_noun_attribute(GpNounAttribute an_attribute,
			long noun_id, long user_id) throws Exception {
		an_attribute.setNounid(noun_id);
		an_attribute.setLastmodifiedby(user_id);
		
		//if base_attr_type_id == 9 then insert in relationships column {type:noun,id:"id"}
		if(an_attribute.getBase_attr_type_id()==9){
			String json = "{relationshiptype:child,type:Noun,id:"+an_attribute.getModifierId()+"}";
			an_attribute.setRelationships(json);
		}
		
		//if list of noun then also same , if list is of stnd types then {type:"noun",id:""}
		if (an_attribute.getBase_attr_type_id()==10  &&  (an_attribute.getModifierId()!=0)){
			String json = "{relationshiptype:child,type:Noun,id:"+an_attribute.getModifierId()+"}";
			an_attribute.setRelationships(json);
		}else if(an_attribute.getBase_attr_type_id()==10  &&  (an_attribute.getModifierId()==0)){
			String json = "{relationshiptype:child,type:'"+ an_attribute.getModifierName() +"'}";
			an_attribute.setRelationships(json);
		}
		
		this.noun_dao.update_a_noun_attribute(an_attribute, noun_id, user_id);
	}

	private void delete_noun_attribute(List<GpNounAttribute> firstAttribList,
			List<GpNounAttribute> secondAttribList) {

		firstAttribList.removeAll(secondAttribList);

		for (GpNounAttribute gpNounAttribute : firstAttribList) {
			this.noun_dao.delete_a_noun_attribute(gpNounAttribute.getId(),
					gpNounAttribute.getNounid());
		}
	}
	
	public List<GpNounAttributeType> get_all_base_attr_type(){
		return noun_dao.get_all_base_attr_type();
	}

	public List<GpOtherNoun> get_all_predefined_nouns() throws Exception {
		  System.out.println("Getting all predefined nouns service");
		  
		  List<GpOtherNoun> list_of_other_nouns = noun_dao.get_all_predefined_nouns();
		  
		  return list_of_other_nouns;
		  
		 }
	
	private void delete_default_activity(GpNoun gpNoun) throws Exception{
		// delete the screens associated with the default activity 
				ArrayList<GpScreenX> screens = this.pc_service.search_for_screens_by_activity_id(gpNoun.getDefault_activity_id());
				for (Iterator iterator = screens.iterator(); iterator.hasNext();) {
					GpScreenX gpScreenX = (GpScreenX) iterator.next();
					this.pc_service.delete_screen(gpScreenX);
				}		
				// update the primary_noun_id to 0 for other activities referencing this noun
				ArrayList<GpActivity> activities = this.activityService.get_all_activities_for_project(gpNoun.getProjectid(), GpUserUtils.getLoggedUser());
				for (Iterator iterator = activities.iterator(); iterator.hasNext();) {
					GpActivity gpActivity = (GpActivity) iterator.next();
					if(gpActivity.getPrimary_noun().getId()==gpNoun.getId()){
						gpActivity.getPrimary_noun().setId(0);
						this.activityService.update_activity(gpActivity, GpUserUtils.getLoggedUser());
					}
				}
				
				//delete the default activity associated with this noun as well
				this.activityService.deleteByNounId(gpNoun.getDefault_activity_id());
	}

	public List<GpOtherNoun> get_mongo_noun_service(GpOtherNoun onoun, GpUser gpUser) {
		// TODO Auto-generated method stub
		List<GpOtherNoun> get_mnoun = this.noun_dao.get_mongo_noun(onoun, gpUser);
		return get_mnoun;
		
	}

	public List<GpCouchBasedomain> get_couch_noun_service(GpCouchBasedomain conoun,
			GpUser gpUser) {
		// TODO Auto-generated method stub
		List<GpCouchBasedomain> get_cnoun = this.noun_dao.get_couch_noun(conoun, gpUser);
		return get_cnoun;
	}

	public List<GpCouchBasedomain> get_all_couch_buckets() {
		// TODO Auto-generated method stub
		System.out.println("Getting all Couch buckets");
		  
		  List<GpCouchBasedomain> list_of_couch_buckets = noun_dao.get_all_couch_buckets();
		  
		  return list_of_couch_buckets;
		  
	}

	public List<GpCouchBasedomain> get_all_couch_design(String  bucket) {
		// TODO Auto-generated method stub
		System.out.println("Getting all Couch design");
		  
		  List<GpCouchBasedomain> list_of_couch_design = noun_dao.get_all_couch_design(bucket);
		  
		  return list_of_couch_design;
		  
	}

	public List<GpCouchBasedomain> get_all_couch_views(String design) {
		// TODO Auto-generated method stub
		System.out.println("Getting all Couch views");
		  
		  List<GpCouchBasedomain> list_of_couch_views = noun_dao.get_all_couch_views(design);
		  
		  return list_of_couch_views;
	}

	public GpNoun create_wsdl_noun(GpNoun anoun, GpUser loggedUser) throws Exception {
		anoun.setCreatedby(loggedUser.getId());
		anoun.setLastmodifiedby(loggedUser.getId());
		anoun.setDefault_activity_id(0);

		ArrayList<GpNoun> the_nouns  = this.search_for_nouns_by_project_id(anoun.getProjectid(),loggedUser);
		for (GpNoun theNoun : the_nouns) {
			if(theNoun.getName().equals(anoun.getName())){
				return null;
			}
		}
		
		
		this.noun_dao.insert(anoun);
				
		if (anoun.getNounattributes().size() > 0) {
			for (GpNounAttribute an_attrib : anoun.getNounattributes()) {
				this.insert_noun_attribute(an_attrib, anoun.getId(),
						loggedUser.getId());
			}
		}
	return anoun;
	}
	
	
	public void update_wsdl_noun(GpNoun anoun, GpUser user) throws Exception {

		anoun.setLastmodifiedby(user.getId());

		this.noun_dao.update(anoun);

		List<GpNounAttribute> nounAtrribList = new ArrayList<GpNounAttribute>();
		if (anoun.getId() != 0L) {
			nounAtrribList = this.noun_dao.find_All_Noun_Attributes(anoun
					.getId());
		}
		
		//attribute is added for the first time
		if(anoun.getNounattributes().size() > 0 && nounAtrribList.size() == 0 ){
			
			//create activity
			String value = null;
			// validate if activity name duplicate

			GpActivity activity = new GpActivity();
			activity.setProjectid(anoun.getProjectid());
			activity.setModuleid(anoun.getModuleid());
			if(anoun.getWsdl_id()!=null){
				activity.setWsdl_id(anoun.getWsdl_id());
			}
			value = anoun.getName()+"_Wsdl_Activity";
			
			activity.setLabel(value);
			activity.setDescription(value);
			activity.setName(value);
			activity.setPrimary_noun(anoun);
			activity.setModule_type("default_wsdl");

			this.activityService.create_wsdl_activity(activity, user);
			// create verbs
			//dhina changes for wsdl
			this.gpScreenWidgetParser.insert_default_activity_verbs(activity
					.getId());
			// update noun default activity id
			anoun.setDefault_activity_id(activity.getId());
			this.noun_dao.update_default_activity_id(anoun);
			
		}
		
		//if user deletes all the attributes then delete the default activity as well
		if(anoun.getNounattributes().size() == 0 && nounAtrribList.size() > 0 ){
			System.err.println("DELETE activity called");
			delete_default_activity(anoun);
		}
			
		
		if (anoun.getNounattributes().size() > 0 || nounAtrribList.size() > 0) {
			this.delete_noun_attribute(nounAtrribList,
					anoun.getNounattributes());
			ArrayList<GpNoun> created_secondary_noun = new ArrayList<GpNoun>();
			for (GpNounAttribute aNounAttribute : anoun.getNounattributes()) {
				if (aNounAttribute.getId() == 0L) {
					this.insert_noun_attribute(aNounAttribute, anoun.getId(),
							user.getId());
				} else {
					this.update_noun_attribute(aNounAttribute, anoun.getId(),
							user.getId());
					if(aNounAttribute.isIs_secondary_noun()){
						List<GpNoun> existing_nouns = this.search_for_nouns_by_project_id(anoun.getProjectid(), user);
						boolean already_created_noun = false;
						for(GpNoun existing_noun : existing_nouns){
							if(existing_noun.getName().equals(aNounAttribute.getName())){
								already_created_noun = true;
								break;
							}
						}
						if(!already_created_noun){
							ArrayList<GpNounAttribute> list_to_be_add = new ArrayList<GpNounAttribute>();
							GpNoun noun_to_insert = new GpNoun();
							noun_to_insert.setName(aNounAttribute.getName());
							noun_to_insert.setLabel(aNounAttribute.getName());
							noun_to_insert.setDescription(aNounAttribute.getName());
							noun_to_insert.setProjectid(anoun.getProjectid());
							noun_to_insert.setNounattributes(list_to_be_add);
							GpNoun createdNoun = this.create_noun(noun_to_insert,GpUserUtils.getLoggedUser());
							GpNounAttribute attribute = new GpNounAttribute();
						    attribute.setNounid(createdNoun.getId());
						    attribute.setName(aNounAttribute.getName());
						    attribute.setLabel(aNounAttribute.getName().toLowerCase());
						    attribute.setDefault_activity_id(0);
						    attribute.setDescription("Description");
						    attribute.setBase_attr_type_id(4);
						    attribute.setIspart_of_unique_key(false);
						    attribute.setDatalength("");
						    attribute.setModifierId(0);
						    attribute.setModifierName("Text1234");
						    list_to_be_add.add(attribute);
						    GpNounAttribute attribute_2 = new GpNounAttribute();
						    attribute_2.setNounid(createdNoun.getId());
						    attribute_2.setName("Description");
						    attribute_2.setLabel("description");
						    attribute_2.setDefault_activity_id(0);
						    attribute_2.setDescription("Description");
						    attribute_2.setBase_attr_type_id(4);
						    attribute_2.setIspart_of_unique_key(false);
						    attribute_2.setDatalength("");
						    attribute_2.setModifierId(0);
						    attribute_2.setModifierName("Text1234");
						    list_to_be_add.add(attribute_2);
						    noun_to_insert.setNounattributes(list_to_be_add);
							this.update_noun(noun_to_insert, GpUserUtils.getLoggedUser());
							created_secondary_noun.add(noun_to_insert);
						}
					}
					else {
						
					}
				}
			}
			GpActivity activity = this.activityService.search_for_update(anoun.getDefault_activity_id());
			activity.setSecondary_nouns(created_secondary_noun);
			this.activityService.update_activity(activity, user);
		}
	}
	
}
