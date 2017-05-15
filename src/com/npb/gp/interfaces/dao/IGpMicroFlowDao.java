package com.npb.gp.interfaces.dao;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpMicroFlowBase;

/***
 * <b>Created Date: 04/09/2015<br>
 * Modified By: Reinaldo Lopez<b><br>
 * 
 * <p>
 * Created the following methods
 * insert_micro_flow
 * </p>
 * 
 * <b>Modified Date: 15/10/2015<br>
 * Modified By: Reinaldo Lopez<b><br>
 * 
 * <p>
 * Added delete_micro_flow method
 * </p>
 * 
 * <p>
 *  Modified By : Rashmi
 *  Date: 29/03/2016
 *  Added get_records_from_micro_flow_base 
 * </p>
 * 
 * */
public interface IGpMicroFlowDao {
	
	public GpMicroFlow insert_micro_flow(GpMicroFlow base)
			throws Exception;
	
	public void delete_micro_flow(long id)
			throws Exception;

	public ArrayList<GpMicroFlowBase> get_records_from_micro_flow_base(String base_verb_name) 
			throws Exception;
	
}
