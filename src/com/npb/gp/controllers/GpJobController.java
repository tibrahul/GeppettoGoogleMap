package com.npb.gp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npb.gp.domain.core.GpJob;
import com.npb.gp.services.GpJobService;

/***
 * 
 * @author Kumaresan Perumal
 * 
 *         The purpose of the job controller is to get the information about the
 *         project When The project is generated by a user.It has the following
 *         methods to accomplish the task.
 * 
 *
 */

@Controller("GpJobController")
@RequestMapping("/job/")
public class GpJobController {
	private GpJobService job_service;

	public GpJobService getJob_service() {
		return job_service;
	}

	@Resource(name = "GpJobService")
	public void setJob_service(GpJobService job_service) {
		this.job_service = job_service;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/insert/", headers = "Accept=application/json")
	@ResponseBody
	public void insert(long project_id, long user_id) {

	}

	@RequestMapping(method = RequestMethod.POST, value = "/update/", headers = "Accept=application/json")
	@ResponseBody
	public void update(long project_id, long user_id) {

	}

	@RequestMapping(method = RequestMethod.POST, value = "/update_status/", headers = "Accept=application/json")
	@ResponseBody
	public void update_status(long job_id, String status_info) {

	}

	@RequestMapping(method = RequestMethod.POST, value = "/delete/", headers = "Accept=application/json")
	@ResponseBody
	public void delete(long project_id, long user_id) {

	}

	@RequestMapping(method = RequestMethod.GET, value = "/find_by_id/", headers = "Accept=application/json")
	@ResponseBody
	public GpJob find_by_id(long noun_id) throws Exception {
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/find_and_lock/", headers = "Accept=application/json")
	@ResponseBody
	public long find_and_lock() throws Exception {
		return 0;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/start_generation", headers = "Accept=application/json", produces = "text/plain")
	@ResponseBody
	public GpJob start_generation(@RequestBody GpJob the_job) throws Exception {
		System.out.println("Gp Job controller Entered");
		return job_service.start_generation(the_job);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get_generation_status")
	@ResponseBody
	public GpJob get_generation_status(@RequestParam("parent_job_id") long parent_job_id) throws Exception {
		System.out.println("Gp Job controller Entered");
		return job_service.get_generation_status(parent_job_id);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/get_job_by_status")
	@ResponseBody
	public GpJob get_job_by_pid_uid_status(@RequestParam("parent_job_id") long parent_job_id,@RequestParam("gen_status") String gen_status) {
		System.out.println("Gp Job controller Entered");
		try {
			return job_service.get_job_by_pid_uid_status(parent_job_id, gen_status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			GpJob job = new GpJob();
			job.setStatus("gen_status_not_found");
			return job;
		}
	}
	@RequestMapping(method = RequestMethod.GET, value = "/get_job_errors/{parent_job_id}")
	@ResponseBody
	public List<GpJob> get_job_by_pid_uid_status(@PathVariable String parent_job_id) {
		System.out.println("Gp Job controller Entered");
		try {
			return job_service.get_job_errors(parent_job_id);
		} catch (Exception e) {
			List<GpJob> jobs = new ArrayList<>();
			return jobs;
		}
	}
}