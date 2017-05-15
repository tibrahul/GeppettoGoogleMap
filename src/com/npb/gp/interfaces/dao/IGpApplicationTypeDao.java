package com.npb.gp.interfaces.dao;

import java.util.ArrayList;
import java.util.List;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpApplicationType;
import com.npb.gp.domain.core.GpMobileDeviceType;

/**
 * 
 * @author Dan Castillo</br> Date Created: 02/26/2014</br>
 * @since .35</p>
 *
 *        The purpose of this interface is to declare the standard db operations
 *        required</br> for the Activity functions</p>
 *
 *        please note that a form of this class has been in use since version
 *        .10 of the</br> Geppetto system. The .10 version is also known as
 *        "Cancun", back then Activity</br> used to be known as operation</p>
 *        
 *         <b> Modified date: 11/19/2015</br> Modified by: Suresh Palanisamy<b><br>
 * 
 *        <p>
 *        Added new method as get_all_predefined_activities to retrieve all other activities
 *        </p>
 * 
 *        <b> Modified date: 06/05/2015</br> Modified by: Kumaresan perumal<b><br>
 * 
 *        <p>
 *        Moved get_default_module_id method to GpProjectDao
 *        </p>
 * 
 *        <b>Modified Date: 28/04/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        Added new method as get_module_default_id
 *        </p>
 * 
 *
 *        Modified Date: 10/04/2014</br> Modified By: Dan Castillo</p>
 *
 *        added find_all_base_by_projectid(long projectid) method to handle
 *        searches where you only want to obtain the parent (base) noun by none
 *        of its children
 *
 *        <b>Modified Date: 01/04/2015<br>
 *        Modified By: Kumaresan Perumal<b><br>
 * 
 *        <p>
 *        Return type changed for the insert method
 *        </p>
 *
 */
public interface IGpApplicationTypeDao {
	public List<GpApplicationType> find_all_Application_type() throws Exception;
}
