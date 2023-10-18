package com.app.its.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.its.model.jobDetails;
import com.app.its.repository.jobRepository;
import com.app.its.service.itsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class jobController {
	
	
	@Autowired
	private jobRepository jobAction;
	
	@Autowired
	private itsService appService;
	
	@PostMapping("/job/new")
	public jobDetails addJob(@RequestBody jobDetails jobData) {		
		jobData.setJobId(appService.getNewJobId());		
		return jobAction.save(jobData);
	}
	
	@GetMapping("/job/all")
	public List<jobDetails> getJobs() {		
		return jobAction.findAll();
	}
	
	@GetMapping("/job/list")
	public List<String> viewJobsList() {	
		return appService.getJobs();
	}
	
	@GetMapping("/job/{id}")
	public jobDetails viewJob(@PathVariable int id) {
		System.out.println("hello");
		return jobAction.findByJobId(id);
	}
	
	@PostMapping("/job-edit/{id}")
	public jobDetails addEdit(@PathVariable int id , @RequestBody jobDetails jobData) {	
		jobDetails jd = jobAction.findByJobId(id);
		jd.setDescription(jobData.getDescription());
		jd.setJd(jobData.getJd());
		jd.setTitle(jobData.getTitle());
		jd.setStatus(jobData.getStatus());
		return jobAction.save(jd);
	}
	
	@GetMapping("/job/list/{type}")
	public List<String> viewJobsListBasedOnDepartment(@PathVariable String type) {
		List<jobDetails> userList = jobAction.findByDescription(type);
		ArrayList<String> al = new ArrayList<>();
		for (int index = 0; index < userList.size(); index++ ) {
			jobDetails ud = userList.get(index);
			al.add(ud.getTitle());			
		}
		return al; 	
	}

}
