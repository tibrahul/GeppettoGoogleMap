package com.npb.gp.interfaces.services;

import java.util.List;

import com.npb.gp.domain.core.GpFlowControl;

/***
 * <b>Created Date: 29/04/2015<br>
 * Modified By: Kumaresan Perumal<b><br>
 * 
 * <p>
 * Created the following methods as get_records_from_flowcontrol_base
 * </p>
 * 
 * Modified By: Reinaldo Lopez <b><br>
 * Date:04/09/2015
 * <p>
 * Added the method get_flow_control_by_activity
 * </p>
 * */
public interface IGpFlowControlService {

	public void get_records_from_flowcontrol_base(long project, long activity)
			throws Exception;

	public List<GpFlowControl> get_flow_control_by_activity(long activity_id)
			throws Exception;
	
	public List<GpFlowControl> get_flow_control_by_activity_and_component(long activity_id,String componentType)
			throws Exception ;
}
