package com.app.its.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.its.model.referDetails;
import com.app.its.repository.referRepository;
import com.app.its.service.itsService;
import com.opencsv.CSVWriter;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class referController {
	
	@Autowired
    private referRepository referAtion;
	
	@Autowired
	private itsService appService;
	
	@PostMapping("/refer/new")
	public referDetails addRefer(@RequestBody referDetails refer) throws IOException {
		String id  = appService.uploadFiles(refer.getResume());
		String file = "\\upload\\"+ id + "\\"+ refer.getResume();	
		refer.setResume(file);		
		refer.setReferId(appService.getNewReferId());
		refer.setCreatedTime( new java.util.Date().toString());
		return referAtion.save(refer);
	}
	
	@GetMapping("/refer/{id}")
	public List<referDetails> viewReferDetails(@PathVariable String id) {		
		return referAtion.findByUserId(id);
	}
	
	@GetMapping("/view-refer/{id}")
	public referDetails ReferDetails(@PathVariable int id) {
		System.out.println(id);
		System.out.print(referAtion.findByReferId(id));
		return referAtion.findByReferId(id);
	}
	
	@GetMapping("/refer/all")
	public List<referDetails> viewReferDetailsAll() {
		return referAtion.findAll();
	}
	@GetMapping("/refer/fullList")
	public HashMap duplicateCheck() {
		List<referDetails> li =  referAtion.findAll();
		List<String> elist=new ArrayList<String>();
		List<String> mlist=new ArrayList<String>();
		 HashMap<String, List> values = new HashMap<>();
		   for (int i= 0; i < li.size(); i++) {
			   referDetails rd = li.get(i);	
			   elist.add(rd.getEmail());
			   mlist.add(rd.getMobile());		  
		   }
		   values.put("mobile", mlist);
		   values.put("email", elist);
		return values;
	}
	
	@GetMapping("/refer/filter/{status}")
	public List<referDetails> filterDetails(@PathVariable String status) {
		if (status.equals("all")) {
			return referAtion.findAll();
		}
		return referAtion.findByStatus(status);
	}
	
	@GetMapping("/refer/download/{status}")
	public Map downloadData(@PathVariable String status) throws IOException {
		List<referDetails> li = null;
		HashMap<String, String> numbers = new HashMap<>();
		li = referAtion.findByStatus(status);
		if (status.equals("all")) {
			li =  referAtion.findAll();
		}
		
		String fileName = System.currentTimeMillis() +".csv";
		String out = appService.getOutput() + fileName;
		File file = new File(out);
	    try {	       
	        FileWriter outputfile = new FileWriter(file);	       
	        CSVWriter writer = new CSVWriter(outputfile);	  
	        String[] header = { "Referal Id", "Full Name", "Email", "Mobile", "Address", "Applied for", "Refered by", "Employee Id", "Status" };
	        writer.writeNext(header);
	        for (int i= 0; i < li.size(); i++) {
	        	referDetails rd = li.get(i);
	        	System.out.println(li.toString());
	        	 String[] data1 = {Integer.toString(rd.getReferId()) , rd.getFullName(), rd.getEmail(), rd.getMobile(), rd.getAddress(), rd.getTitle(),
	        			 rd.getEducation(),rd.getEmpId(), rd.getStatus() };
	 	        writer.writeNext(data1);	
	        }	       	         
	        writer.close();
	        numbers.put("location", fileName);
	    }
	    catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
		return numbers;
	}
	
	@PostMapping("/refer-edit/{id}")
	public referDetails editReferDetails(@PathVariable int id, @RequestBody referDetails refer) {
		referDetails rd = referAtion.findByReferId(id);		
		rd.setStatus(refer.getStatus());
		rd.setEmpId(refer.getEmpId());
		if (refer.getTitle() != null && !refer.getTitle().isBlank()) {
		rd.setTitle(refer.getTitle());	
		}
		rd.setEmpId(refer.getEmpId());
		return referAtion.save(rd);
	}
	
	
}
