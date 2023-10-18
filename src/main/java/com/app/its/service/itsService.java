package com.app.its.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.app.its.model.jobDetails;
import com.app.its.model.referDetails;
import com.app.its.model.userDetails;
import com.app.its.repository.jobRepository;
import com.app.its.repository.referRepository;
import com.app.its.repository.userRepository;

@Service
public class itsService {
	private String input = "C:\\Users\\Arul\\Documents\\upload\\";
	private String output = "C:\\Users\\Arul\\Desktop\\its\\public\\upload\\";
	private String outputCsv = "C:\\Users\\Arul\\Desktop\\its\\public\\csv\\";
	@Autowired
	private userRepository userAction;
	
	@Autowired
    private referRepository referAtion;
	
	@Autowired
    private jobRepository jobAction;
	
	@Autowired
	private Environment env;
	
	public int getNewId () {
	List<userDetails> userList = userAction.findAll();
	int id =  userList.size() != 0 ? userList.get(userList.size() - 1).getId() : 0 ;
	return id + 1;	
	}
	
	public int getNewReferId () {
		List<referDetails> referList = referAtion.findAll();
		int id =  referList.size() != 0 ? referList.get(referList.size() - 1).getReferId() : 0 ;
		return id + 1;	
		}
	
	public int getNewJobId () {
		List<jobDetails> jobList = jobAction.findAll();
		int id =  jobList.size() != 0 ? jobList.get(jobList.size() - 1).getJobId() : 0 ;
		return id + 1;	
		}
	
	public ArrayList getUserNames() {
		List<userDetails> userList = userAction.findAll();
		ArrayList<String> al = new ArrayList<>();
		for (int index = 0; index < userList.size(); index++ ) {
			userDetails ud = userList.get(index);
			al.add(ud.getUserName());
		}
		return al; 
	}
	
	public String uploadFiles(String resume) throws IOException {	
		File sourceResume = new File(input + resume);		
		long folder = System.currentTimeMillis();
		File mkdir = new File(output + folder);
		mkdir.mkdir();
		File destResume = new File(output + folder + "\\" + resume);		
		FileCopyUtils.copy(sourceResume, destResume);		
		return String.valueOf(folder);
	}
	
	public ArrayList getJobs() {
		List<jobDetails> userList = jobAction.findAll();
		ArrayList<String> al = new ArrayList<>();
		for (int index = 0; index < userList.size(); index++ ) {
			jobDetails ud = userList.get(index);
			if (ud.getStatus() == null) {
				al.add(ud.getTitle());	
			}
			
		}
		return al; 
	}
	public String getLocation () {
		return input;
	}
	public String getOutput () {
		return outputCsv;
	}
}
