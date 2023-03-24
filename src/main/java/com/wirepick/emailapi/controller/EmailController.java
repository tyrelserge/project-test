package com.wirepick.emailapi.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wirepick.emailapi.exception.RessourceNotFoundException;
import com.wirepick.emailapi.model.AdressBook;
import com.wirepick.emailapi.model.Email;
import com.wirepick.emailapi.model.SmtpData;
import com.wirepick.emailapi.repository.AdressBookRepository;
import com.wirepick.emailapi.repository.EmailRepository;
import com.wirepick.emailapi.repository.SmtpDataRepository;
import com.wirepick.emailapi.service.EmailService;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
	private SmtpDataRepository smtpdataRepository;
    @Autowired
	private  AdressBookRepository adressbookRepository;

   
    @GetMapping("/emailids")
    public ResponseEntity<List<Long>> getAllEmailIDs() {
     List<Long> ids = new ArrayList<Long>();
     ids = emailRepository.findEmailIds();
     return new ResponseEntity<>(ids, HttpStatus.OK);
    }
  //this api retrieve all the emails sent
    @GetMapping("/emails")
    public ResponseEntity<List<Email>> getAllEmails() {
     List<Email> emails = new ArrayList<Email>();
     emails = emailRepository.findAll();
     return new ResponseEntity<>(emails, HttpStatus.OK);
    }
    //
    @GetMapping("/smtpdatas/{smtpdataId}/emails")
    public ResponseEntity<List<Email>> getAllAdressBooksBySmtpDataId(@PathVariable(value = "smtpdataId")
    Long smtpdataId) {
   	 if (!smtpdataRepository.existsById(smtpdataId)) {
   	      throw new RessourceNotFoundException("Invalid smtpdata Id:" + smtpdataId);
   	    }
   	    List<Email> emails = emailRepository.findBySmtpDataId(smtpdataId);
   	    return new ResponseEntity<>(emails, HttpStatus.OK);
     
    }
    //this api upload file, extract adresses and save 
    @PostMapping("/upload/{smtpdataId}/adressbook")
    public ResponseEntity<?> addAdressBook( @PathVariable(value = "smtpdataId") Long smtpdataId,
    		@RequestParam("file") MultipartFile file) throws Exception {
   	     //System.out.println(adressbookrequest);
   	  
    	/*System.out.println(smtpid);
      SmtpData smtpdata= smtpdataRepository.findById(smtpdataId).orElseThrow(() -> 
   	new RessourceNotFoundException("Invalid smtpdata Id:" + smtpdataId));*/
    	
 	InputStream inputStream = file.getInputStream();
 	if(inputStream == null)
         System.out.println("input stream is null");
     else
         System.out.println("input stream is NOT null :-)");
 	
 	try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
	          CSVParser csvParser = new CSVParser(fileReader,
	              CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
	  
	    	List<AdressBook> adressbooks = new ArrayList<>();
	    	ArrayList<String> emails = new ArrayList<>();
	        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
	        for (CSVRecord csvRecord : csvRecords) {
	        	AdressBook tempadressbook = new AdressBook ();
	        	System.out.println(csvRecord.get("Name"));
	        	AdressBook adressbook = smtpdataRepository.findById(smtpdataId).map(smtpdata -> {
	        		System.out.println(csvRecord.get("Name"));
	        		   tempadressbook.setName(csvRecord.get("Name"));
	        		   tempadressbook.setContact(csvRecord.get("Contact"));
	        		   tempadressbook.setEmail(csvRecord.get("Email"));
	        		   tempadressbook.setSmtpdata(smtpdata);
	        		   emails.add(csvRecord.get("Email"));
	    	           return adressbookRepository.save(tempadressbook);
	    	       	//adressbooks.add(adressbook);
	    	        //adressbookRepository.saveAll(adressbooks);
                }).orElseThrow(() -> 
                new RessourceNotFoundException("Invalid smtpdata Id:" + smtpdataId));
	        }
	        return  ResponseEntity.ok("Adress exracted and saved Successfully... ");
        } catch (IOException e) {
        throw new RuntimeException("fail to get adresses: " + e.getMessage());
        } 
 	
    }
  //this api upload file, extract adresses and save
    @PostMapping("/upload")
    public ResponseEntity<?> Upload(@RequestParam("file") MultipartFile file, Long smtpid) throws Exception
    {
    	 //System.out.println(smtpid);
         SmtpData smtpdata= smtpdataRepository.findById(smtpid).orElseThrow(() -> 
      	new RessourceNotFoundException("Invalid smtpdata Id:" + smtpid));
    	InputStream inputStream = file.getInputStream();
    	if(inputStream == null)
            System.out.println("input stream is null");
        else
            System.out.println("input stream is NOT null :-)");
    	
    	  try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
    	          CSVParser csvParser = new CSVParser(fileReader,
    	              CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
    	  
    	    	List<AdressBook> adressbooks = new ArrayList<>();
    	        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
    	        for (CSVRecord csvRecord : csvRecords) {
    	        	AdressBook adressbook = new AdressBook(
    	                     csvRecord.get("Name"),
    	                     csvRecord.get("Contact"),
    	                     csvRecord.get("Email"),
    	                     smtpdata);	
    	        	//adressbooks.add(adressbook);
    	        	adressbookRepository.save(adressbook);
    	        	//adressbookRepository.saveAll(adressbooks);
    	        	
    	        }
    	        return  ResponseEntity.ok("Adress exracted and saved Successfully... ");
            } catch (IOException e) {
            throw new RuntimeException("fail to get adresses: " + e.getMessage());
            } 

       
    }
    
    //this api send simple email
    @PostMapping("/sendingemail")
    public ResponseEntity<?> sendEmail(@RequestBody Email email)
    {

        System.out.println(email);

        boolean result = this.emailService.sendEmail(email.getSubject(),  email.getMessage(), email.getRecipient(), email.getSmtpid());

        if(result){
        	
        	 emailRepository.save(email);
            return  ResponseEntity.ok("Email Sent and saved Successfully... ");
         
        }else{

            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("email sending fail");
        }
    }
    //this api send simple email from csv file
    @PostMapping("/sendingemailfromcsv")
    public ResponseEntity<?> sendEmailFromCSV(@RequestBody Email email)
    {

        System.out.println(email);

        boolean result = this.emailService.sendEmailToEmailsViaCSV(email.getSubject(), email.getMessage(), email.getSmtpid());

        if(result){
        	
        	 emailRepository.save(email);
            return  ResponseEntity.ok("Email Sent and saved Successfully... ");
         
        }else{

            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("email sending fail");
        }
    }

    //this api send email with file
    @PostMapping("/sendemailattachement")
    public ResponseEntity<?> sendEmailWithAttachment(@RequestBody Email email)
    {
        System.out.println(email);

        boolean result = this.emailService.sendWithAttachment(email.getSubject(), email.getMessage(), email.getRecipient(), email.getSmtpid());

        if(result){

            return  ResponseEntity.ok("Sent Email With Attchment Successfully... ");

        }else{

            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("attachment email sending fail");
        }

    }

    //this api send email with html content
    @PostMapping("/sendemailhtml")
    public ResponseEntity<?> sendEmailHtml(@RequestBody Email email)
    {
        System.out.println(email);


        boolean result = this.emailService.sendHtmlTemplate(email.getSubject(), email.getMessage(), email.getRecipient(), email.getSmtpid());

        if(result){

            return  ResponseEntity.ok("Sent Email With HTML template style Successfully... ");

        }else{

            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("html template style email sending fail");
        }
    }

    //this api send email with inline image
    @PostMapping("/sendemailinlineimage")
    public ResponseEntity<?> sendEmailWithInlineImage(@RequestBody Email email)
    {
        System.out.println(email);


        boolean result = this.emailService.sendEmailInlineImage(email.getSubject(), email.getMessage(), email.getRecipient(), email.getSmtpid());

        if(result){

            return  ResponseEntity.ok("Sent Email With Inline Image Successfully... ");

        }else{

            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("inline image email sending fail");
        }
    }

}
