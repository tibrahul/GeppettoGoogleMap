package com.npb.gp.interfaces.dao;

import java.util.ArrayList;
import java.util.List;

import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpNoun;

/**
 * 
 * @author Dan Castillo</br> Date Created: 04/08/2014</br>
 * @since .35</p>
 *
 *        The purpose of this interface is to declare the standard db operations
 *        required</br> for the noun functions</p>
 *
 *        please note that a form of this class has been in use since version
 *        .10 of the</br> Geppetto system. The .10 version is also known as
 *        "Cancun"</p>
 *
 *
 *        Modified Date: 10/22/2014</br> Modified By: Dan Castillo</p>
 * 
 *        removed all references to the "backing" types - as these were legacy
 *        from the early days of Geppetto when the ui was Flex
 * 
 *
 *        Modified Date: 10/04/2014</br> Modified By: Dan Castillo</p>
 *
 *        added find_all_base_by_projectid(long projectid) method to handle
 *        searches where you only want to obtain the parent (base) noun by none
 *        of its children
 * 
 *        <b>Modified Date: 13/03/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        wrote new methods update noun attributes to update the noun attributes
 *        values
 *        </p>
 * 
 *        <b>Modified Date: 18/03/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Added new method as findAllNounAttribute to fetch all the noun
 *        attributes based on the noun id
 *        </p>
 * 
 *        <b>Modified Date: 23/03/2015<br>
 *        Modified By: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Added new method as deleteNounAttribute to delete the noun attributes
 *        based on the noun id and its id
 *        </p>
 */

public interface IGpNounDao {

	public void insert(GpNoun anoun);

	public void update(GpNoun anoun);
	
	public void update_default_activity_id(GpNoun anoun);

	public void delete(long noun_id);

	public GpNoun find_by_id(long noun_id) throws Exception;

	public ArrayList<GpNoun> find_by_project_id(long project_id)
			throws Exception;

	public List<GpNoun> find_all_base_by_projectid(long projectid)
			throws Exception;

	public void insert_a_noun_attribute(GpNounAttribute anttribute,
			long noun_id, long user_id);

	public void update_a_noun_attribute(GpNounAttribute anttribute,
			long noun_id, long user_id);

	public List<GpNounAttribute> find_All_Noun_Attributes(long noun_id)
			throws Exception;

	public void delete_a_noun_attribute(long id, long noun_id);
	
	public void insert_wsdl_link(String link,int project_id , long user_id);
	
	public void insert_rest_link(String link , int project_id , long user_id);
}
