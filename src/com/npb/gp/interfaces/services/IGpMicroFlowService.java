package com.npb.gp.interfaces.services;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpMicroFlowBase;
import com.npb.gp.domain.core.GpVerb;

/***
 * <b>Created Date: 04/09/2015<br>
 * Modified By: Reinaldo Lopez<b><br>
 * 
 * <p>
 * Created the following methods insert_verb_method_implementation,
 * handleVerb
 * </p>
 * 
 * Modified Date: 15/10/2015</br>
 * Modified By: Reinaldo Lopez
 * </p>
 * 
 * Added delete_verb_method_implementation method
 * 
 * <p>
 *  Modified By : Rashmi
 *  Date: 29/03/2016
 *  Modified handleVerb 
 * </p>
 * 
 * */
public interface IGpMicroFlowService {

	public void insert_verb_method_implementation(long activity_id,long verb_id, GpVerb verb)
			throws Exception;
	
	public void delete_verb_method_implementation(long id)
			throws Exception;
	
	public void handleVerb(long activity_id,long verb_id,String action) throws Exception;
	
	public ArrayList<GpMicroFlowBase> get_records_from_micro_flow_base (String base_verb_name) throws Exception;
}
