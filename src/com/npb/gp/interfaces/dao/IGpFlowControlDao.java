package com.npb.gp.interfaces.dao;

import java.util.List;

import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpFlowControlBase;

/***
 * <b>Created Date: 29/04/2015<br>
 * Modified By: Kumaresan Perumal<b><br>
 * 
 * <p>
 * Created the following methods get_records_from_flow_controlbase,
 * insert_flow_control, update_master_flow_id and
 * update_masterflow_id_to_activity.
 * </p>
 * 
 * Modified By: Reinaldo Lopez <b><br>
 * Date:04/09/2015
 * <p>
 * Added the method get_flow_control_by_activity
 * </p>
 * 
 * */
public interface IGpFlowControlDao {

	public GpFlowControl insert_flow_control(GpFlowControl base)
			throws Exception;

	public List<GpFlowControlBase> get_records_from_flowcontrol_base()
			throws Exception;

	public List<GpFlowControlBase> get_records_from_flowcontrol_base(
			String devFramework) throws Exception;

	public void update_masterflow_id(long master_flow_id) throws Exception;

	public void update_masterflow_id_to_activity(long master_flow_id,
			long activity_integer) throws Exception;

	public List<GpFlowControl> get_flow_control_by_activity(long activity_id)
			throws Exception;

	public List<GpFlowControl> get_flow_control_by_activity_and_component(long activity_id,String component_type)
			throws Exception;
}
