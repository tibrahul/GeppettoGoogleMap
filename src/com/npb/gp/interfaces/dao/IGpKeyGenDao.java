package com.npb.gp.interfaces.dao;

/**
 * 
 * @author Suresh Palanisamy
 * @since version.95<br>
 *        <p>
 *        Creation Date: 25/09/2015
 *        </p>
 *
 */

public interface IGpKeyGenDao {

	public long get_max_widget_count() throws Exception;

	public long update_widget_count(long widget_count) throws Exception;
}