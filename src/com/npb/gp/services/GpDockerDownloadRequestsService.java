package com.npb.gp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpDockerDownloadRequestsDao;

@Service("GpDockerDownloadRequestsService")
public class GpDockerDownloadRequestsService {
	@Autowired
	private GpDockerDownloadRequestsDao docker_request_dao;
	
	public String request_download(long user_id){
		return this.docker_request_dao.request_download(user_id);
	}
}
