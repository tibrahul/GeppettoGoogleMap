package com.npb.gp.domain.core;

/**
 * 
 * @author Dan Castillo</br>
 *         Date Created: 06/13/2014</br>
 * @since .35
 *        </p>
 *
 *        This class represents an instance of a code generation request
 *        </p>
 *
 *
 */

public class GpJob {

	long id;
	long project_id;
	long user_id;
	String user_name;
	String status;
	String status_message;
	String stack_trace;
	String claimed;
	long parent_job_id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProject_id() {
		return project_id;
	}

	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus_message() {
		return status_message;
	}

	public void setStatus_message(String status_message) {
		this.status_message = status_message;
	}

	public String getStack_trace() {
		return stack_trace;
	}

	public void setStack_trace(String stack_trace) {
		this.stack_trace = stack_trace;
	}

	public String getClaimed() {
		return claimed;
	}

	public void setClaimed(String claimed) {
		this.claimed = claimed;
	}
	public long getParent_job_id() {
		return parent_job_id;
	}
	
	public void setParent_job_id(long parent_job_id) {
		this.parent_job_id = parent_job_id;
	}
}