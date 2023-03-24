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

import com.wirepick.emailapi.exception.RessourceNotFoundException;
import com.wirepick.emailapi.model.AdressBook;
import com.wirepick.emailapi.model.SmtpData;
import com.wirepick.emailapi.repository.AdressBookRepository;
import com.wirepick.emailapi.repository.SmtpDataRepository;
@RestController
public class AdressBookController {

@Autowired
private AdressBookRepository adressbookRepository;
@Autowired
private SmtpDataRepository smtpdataRepository;

@GetMapping("/adressbookids")
public ResponseEntity<List<Long>> getAllAdressBookIDs() {
 List<Long> ids = new ArrayList<Long>();
 ids = adressbookRepository.findAdressBookIds();
 return new ResponseEntity<>(ids, HttpStatus.OK);
}


 @GetMapping("/adressbooks")
 public ResponseEntity<List<AdressBook>> getAllAdressBooks() {
  List<AdressBook> AdressBooks = new ArrayList<AdressBook>();
  AdressBooks = adressbookRepository.findAll();
  return new ResponseEntity<>(AdressBooks, HttpStatus.OK);
 }
 
 @GetMapping("/smtpdatas/{smtpdataId}/adressbooks")
 public ResponseEntity<List<AdressBook>> getAllAdressBooksBySmtpDataId(@PathVariable(value = "smtpdataId")
 Long smtpdataId) {
	 if (!smtpdataRepository.existsById(smtpdataId)) {
	      throw new RessourceNotFoundException("Invalid smtpdata Id:" + smtpdataId);
	    }
	    List<AdressBook> adressbooks = adressbookRepository.findBySmtpDataId(smtpdataId);
	    return new ResponseEntity<>(adressbooks, HttpStatus.OK);
  
 }
	    
 @PostMapping("/smtpdata/{smtpdataId}/adressbook")
 public ResponseEntity<?> addAdressBook( @PathVariable(value = "smtpdataId") Long smtpdataId,
		 @RequestBody AdressBook adressbookrequest) {
	     System.out.println(adressbookrequest);
	     AdressBook adressbook = smtpdataRepository.findById(smtpdataId).map(smtpdata -> {
	    	   adressbookrequest.setSmtpdata(smtpdata);
	           return adressbookRepository.save(adressbookrequest);
	      }).orElseThrow(() -> 
	      new RessourceNotFoundException("Invalid smtpdata Id:" + smtpdataId));
     return ResponseEntity.ok("AdressBook saved Successfully... ");
 }
 
 @GetMapping("/adressbook/{id}")
 public  ResponseEntity<AdressBook> updateAdressBook(@PathVariable("id") long id) {
	 
 	AdressBook AdressBook= adressbookRepository.findById(id).orElseThrow(() -> 
 	new RessourceNotFoundException("Invalid AdressBook Id:" + id));
 	return new ResponseEntity<>(AdressBook, HttpStatus.FOUND);
 }
 
 @PutMapping("/adressbook/{id}")
 public ResponseEntity<AdressBook> updateAdressBook(@PathVariable("id") long id, @RequestBody AdressBook adressbook) {
	 Optional<AdressBook> AdressBookop = adressbookRepository.findById(id);

	 if (AdressBookop.isPresent()) {
		 AdressBook _AdressBook = AdressBookop.get();
		 _AdressBook.setName(adressbook.getName());
		 _AdressBook.setContact(adressbook.getContact());
		 _AdressBook.setEmail(adressbook.getEmail());
		// _AdressBook.setPort(adressbook.getPort());
		 _AdressBook.setId(id);
	      return new ResponseEntity<>(adressbookRepository.save(_AdressBook), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
 }

 @DeleteMapping("/adressbook/{id}")
 public ResponseEntity<HttpStatus> deleteAdressBook(@PathVariable("id") long id) {
	 Optional<AdressBook> AdressBook = adressbookRepository.findById(id);
	 if (AdressBook.isPresent()) {
		 adressbookRepository.deleteById(id);
		  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
 }
 @DeleteMapping("/smtpdata/{smtpdataId}/adressbook")
 public ResponseEntity<List<AdressBook>> deleteAllAdressBookOfSmtpData(@PathVariable(value = "smtpdataId") Long smtpdataId) {
   if (!smtpdataRepository.existsById(smtpdataId)) {
     throw new RessourceNotFoundException("Not found SmtpData with id = " + smtpdataId);
   }
   adressbookRepository.deleteBySmtpId(smtpdataId);
   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
 }

}
