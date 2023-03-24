package com.wirepick.emailapi.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.wirepick.emailapi.model.SmtpData;
import com.wirepick.emailapi.repository.SmtpDataRepository;

@RestController
public class SmtpDataController {
	
@Autowired
private SmtpDataRepository smtpdataRepository;

 
@GetMapping("/smtpdataids")
public ResponseEntity<List<Long>> getAllSmtpdataIDs() {
 List<Long> ids = new ArrayList<Long>();
 ids = smtpdataRepository.findSmtpDataIds();
 return new ResponseEntity<>(ids, HttpStatus.OK);
}

@GetMapping("/smtpdatas")
 public ResponseEntity<List<SmtpData>> getAllSmtpdatas() {
  List<SmtpData> smtpdatas = new ArrayList<SmtpData>();
  smtpdatas = smtpdataRepository.findAll();
  return new ResponseEntity<>(smtpdatas, HttpStatus.OK);
 }
	    
 @PostMapping("/addsmtpdata")
 public ResponseEntity<?> addSmtpData( @RequestBody SmtpData smtpdata) {
	 System.out.println(smtpdata);
     smtpdataRepository.save(smtpdata);
     return ResponseEntity.ok("SmtpData saved Successfully... ");
 }
 
 @GetMapping("/smtpdata/{id}")
 public  ResponseEntity<SmtpData> updateSmtpData(@PathVariable("id") long id) {
	 
 	SmtpData smtpdata= smtpdataRepository.findById(id).orElseThrow(() -> 
 	new IllegalArgumentException("Invalid smtpdata Id:" + id));
 	return new ResponseEntity<>(smtpdata, HttpStatus.FOUND);
 }
 
 @PutMapping("/smtpdata/{id}")
 public ResponseEntity<SmtpData> updateSmtpData(@PathVariable("id") long id, @RequestBody SmtpData smtpdata) {
	 Optional<SmtpData> smtpdataop = smtpdataRepository.findById(id);

	 if (smtpdataop.isPresent()) {
		 SmtpData _smtpdata = smtpdataop.get();
		 _smtpdata.setHost(smtpdata.getHost());
		 _smtpdata.setUsername(smtpdata.getUsername());
		 _smtpdata.setPassword(smtpdata.getPassword());
		 _smtpdata.setPort(smtpdata.getPort());
		 _smtpdata.setAuth(smtpdata.getAuth());
		 _smtpdata.setStarttls_enable(smtpdata.getStarttls_enable());
		 _smtpdata.setStarttls_required(smtpdata.getStarttls_required());
		 _smtpdata.setId(id);
	      return new ResponseEntity<>(smtpdataRepository.save(_smtpdata), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
 }

 @DeleteMapping("/smtpdata/{id}")
 public ResponseEntity<HttpStatus> deleteSmtpData(@PathVariable("id") long id) {
	 Optional<SmtpData> smtpdata = smtpdataRepository.findById(id);
	 if (smtpdata.isPresent()) {
		  smtpdataRepository.deleteById(id);
		  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
 }



}
