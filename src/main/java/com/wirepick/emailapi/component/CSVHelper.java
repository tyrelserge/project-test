package com.wirepick.emailapi.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.*;
@Component
public class CSVHelper {
	@Autowired
	ResourceLoader resourceLoader;
	// This method is used to filter the csv file and get only the emails
    public List<String> csvToEmails() throws Exception {
      //InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("classpath:listeEmails.csv");
      Resource resource = resourceLoader.getResource("classpath:listeEmails.csv");
      InputStream inputStream = resource.getInputStream();
      if(inputStream == null)
          System.out.println("input stream is null");
      else
          System.out.println("input stream is NOT null :-)");
      
      try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
          CSVParser csvParser = new CSVParser(fileReader,
              CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
  
    	 List<String> emails1 = new ArrayList<>();
    	 ArrayList<String> emails = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        for (CSVRecord csvRecord : csvRecords) {
          String email = csvRecord.get("email");
          emails.add(email);
      	System.out.println(email);
        }
        return emails;
      } catch (IOException e) {
        throw new RuntimeException("fail to get emails: " + e.getMessage());
      } 
    }
    //
    public Address[] getRecipients1(List<String> emails) throws AddressException {
        Address[] addresses = new Address[emails.size()];
        for (int i =0;i < emails.size();i++) {
            addresses[i] = new InternetAddress(emails.get(i));
        }
        return addresses;
    }

    public List<String> getEmails() {
        ArrayList<String> emails = new ArrayList<>();
        emails.add("ldouclass@gmail.com");
        emails.add("fdoucfousse@gmail.com");
        return emails;
    }
    //
    public InternetAddress[] getRecipients(List<String> emails) throws AddressException  {
    	InternetAddress[] internetaddresses = new InternetAddress[emails.size()];
        for (int i =0;i < emails.size();i++) {
        	internetaddresses[i] = new InternetAddress(emails.get(i));
        	System.out.println(internetaddresses[i]);
        }
        for (int i=0;i<internetaddresses.length;i++) {
        	System.out.println(internetaddresses[i]);
        }
        return internetaddresses;
    }
}
