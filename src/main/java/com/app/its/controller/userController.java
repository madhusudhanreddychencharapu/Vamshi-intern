package com.app.its.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.its.model.userDetails;
import com.app.its.repository.userRepository;
import com.app.its.service.itsService;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class userController {

	
	@Autowired
	private userRepository userAction;
	
	@Autowired
	private itsService appService;
	
	@PostMapping("/user/new")
	public userDetails addUser(@RequestBody userDetails userData) {		
		userData.setId(appService.getNewId());
		userData.setCreatedTime( new java.util.Date().toString());
		return userAction.save(userData);
	}
	
	@PostMapping("/user/check")
	public HashMap checkUser(@RequestBody userDetails userData) {
		 HashMap<String, String> status = new HashMap<String, String>();
		userDetails ud = userAction.findAllByUserName(userData.getUserName());
		 status.put("status", "failed");
		
		if (ud != null && ud.getUserName() != null && ud.getPassword().equals(userData.getPassword())) {
			 if (!userData.getUserType().equals(ud.getUserType())) {
				 status.put("type", "Wrong user type");
				 return status; 
			 }
			 status.put("status", "success");	
			 status.put("id", String.valueOf(ud.getId()));
			 status.put("type", ud.getUserType());
		} 
		return status;
	}
	
	@GetMapping("/user/{id}")
	public userDetails getEmployee(@PathVariable int id) {			 
		userDetails emp = userAction.findById(id).get();			
		return emp;
	}
	
	
	@GetMapping("/user/all")
	public ArrayList getEmployee() {					
		return appService.getUserNames();
	}
}
