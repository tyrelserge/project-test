package com.wirepick.emailapi.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.wirepick.emailapi.component.CSVHelper;
import com.wirepick.emailapi.exception.RessourceNotFoundException;
import com.wirepick.emailapi.model.SmtpData;
import com.wirepick.emailapi.repository.SmtpDataRepository;
@Service
public class EmailService {
	@Autowired
	private SmtpDataRepository smtpdataRepository;
	@Autowired
    private CSVHelper csvHelper;
	
    public boolean sendEmail(String subject, String message, String to, long smtpid)
    {
        boolean foo = false; // Set the false, default variable "foo", we will allow it after sending code process email
        System.out.println(smtpid);
        SmtpData smtpdata= smtpdataRepository.findById(smtpid).orElseThrow(() -> 
     	new RessourceNotFoundException("Invalid smtpdata Id:" + smtpid));
        
        String senderEmail = smtpdata.getUsername(); // your gmail email id /"fdoucfousse@gmail.com";
        String senderPassword = smtpdata.getPassword();  // your gmail id password /"rtzcwsportyqvnww";

        // Properties class enables us to connect to the host SMTP server
        Properties properties = new Properties();

        // Setting necessary information for object property

        // Setup host and mail server
        properties.put("mail.smtp.auth", smtpdata.getAuth()); // enable authentication
        properties.put("mail.smtp.starttls.enable", smtpdata.getStarttls_enable()); // enable TLS-protected connection
        properties.put("mail.smtp.host", smtpdata.getHost()); // Mention the SMTP server address. Here Gmail's SMTP server is being used to send email
        properties.put("mail.smtp.port", smtpdata.getPort()); // 587 is TLS port number
        properties.put("mail.smtp.port", smtpdata.getStarttls_required()); // require TLS-protected connection
        

        // get the session object and pass username and password
        Session session = Session.getInstance(properties, new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication(){

                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {

            MimeMessage msg = new MimeMessage(session); // Create a default MimeMessage object for compose the message

            msg.setFrom(new InternetAddress(senderEmail)); // adding sender email id to msg object

            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // adding recipient to msg object

            msg.setSubject(subject); // adding subject to msg object
            msg.setText(message); // adding text to msg object

            Transport.send(msg); // Transport class send the message using send() method
            System.out.println("Email Sent Successfully...");

            foo = true; // Set the "foo" variable to true after successfully sending emails

        }catch(Exception e){

            System.out.println("EmailService File Error"+ e);
        }

        return foo; // and return foo variable
    }
// test with csv file
    public boolean sendEmailToEmailsViaCSV(String subject, String message, long smtpid)
    {
        boolean foo = false; // Set the false, default variable "foo", we will allow it after sending code process email
        System.out.println(smtpid);
        SmtpData smtpdata= smtpdataRepository.findById(smtpid).orElseThrow(() -> 
     	new RessourceNotFoundException("Invalid smtpdata Id:" + smtpid));
        
        String senderEmail = smtpdata.getUsername(); // your gmail email id /"fdoucfousse@gmail.com";
        String senderPassword = smtpdata.getPassword();  // your gmail id password /"rtzcwsportyqvnww";
        // Properties class enables us to connect to the host SMTP server
        Properties properties = new Properties();

        // Setting necessary information for object property

        // Setup host and mail server
        properties.put("mail.smtp.auth", smtpdata.getAuth()); // enable authentication
        properties.put("mail.smtp.starttls.enable", smtpdata.getStarttls_enable()); // enable TLS-protected connection
        properties.put("mail.smtp.host", smtpdata.getHost()); // Mention the SMTP server address. Here Gmail's SMTP server is being used to send email
        properties.put("mail.smtp.port", smtpdata.getPort()); // 587 is TLS port number
        properties.put("mail.smtp.port", smtpdata.getStarttls_required()); // require TLS-protected connection
        

        // get the session object and pass username and password
        Session session = Session.getInstance(properties, new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication(){

                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
        	
        	   List<String> emails = csvHelper.getEmails();
        	 //List<String> recipients = csvHelper.csvToEmails();
             //String[] to_ = recipients.stream().toArray(String[]::new);
             MimeMessage msg = new MimeMessage(session); // Create a default MimeMessage object for compose the message
          
            msg.setFrom(new InternetAddress(senderEmail)); // adding sender email id to msg object
            msg.addRecipients(Message.RecipientType.BCC, csvHelper.getRecipients1(emails));
            String emailBCC = properties.getProperty("BCC");
            System.out.println(emailBCC);
            msg.setSubject(subject); // adding subject to msg object
            msg.setText(message); // adding text to msg object

            Transport.send(msg); // Transport class send the message using send() method
            System.out.println("Email Sent Successfully...");

            foo = true; // Set the "foo" variable to true after successfully sending emails

        }catch(Exception e){

            System.out.println("EmailService File Error"+ e);
            String emailBCC = properties.getProperty("BCC");
            System.out.println(emailBCC);
        }

        return foo; // and return foo variable
    }

    public boolean sendWithAttachment(String subject, String message, String to, long smtpid)
    {
        boolean foo = false; // Set the false, default variable "foo", we will allow it after sending code process email
        System.out.println(smtpid);
        SmtpData smtpdata= smtpdataRepository.findById(smtpid).orElseThrow(() -> 
     	new RessourceNotFoundException("Invalid smtpdata Id:" + smtpid));
        
        String senderEmail = smtpdata.getUsername(); // your gmail email id /"fdoucfousse@gmail.com";
        String senderPassword = smtpdata.getPassword();  // your gmail id password /"rtzcwsportyqvnww";

        // Properties class enables us to connect to the host SMTP server
        Properties properties = new Properties();

        // Setting necessary information for object property

        // Setup host and mail server
        properties.put("mail.smtp.auth", smtpdata.getAuth()); // enable authentication
        properties.put("mail.smtp.starttls.enable", smtpdata.getStarttls_enable()); // enable TLS-protected connection
        properties.put("mail.smtp.host", smtpdata.getHost()); // Mention the SMTP server address. Here Gmail's SMTP server is being used to send email
        properties.put("mail.smtp.port", smtpdata.getPort()); // 587 is TLS port number
        properties.put("mail.smtp.port", smtpdata.getStarttls_required()); // require TLS-protected connection

        // get the session object and pass username and password
        Session session = Session.getInstance(properties, new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication(){

                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {

            MimeMessage msg = new MimeMessage(session); // Create a default MimeMessage object for compose the message

            msg.setFrom(new InternetAddress(senderEmail)); // adding sender email id to msg object

            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // adding recipient to msg object

            msg.setSubject(subject); // adding subject to msg object

            // sets file location
            String path = "C:\\Users\\Public\\Downloads\\listeEmails.csv";

            MimeMultipart mimeMultipart = new MimeMultipart(); // create MimeMultipart object

            MimeBodyPart textMime = new MimeBodyPart(); // create first MimeBodyPart object textMime for containing the message

            MimeBodyPart fileMime = new MimeBodyPart(); //create second MimeBodyPart object fileMime for containing the file

            textMime.setText(message); //sets message to textMime object

            File file = new File(path); //Initialize the File and Move Path variable
            fileMime.attachFile(file); //attach file to fileMime object

            //The mimeMmultipart adds textMime and fileMime to the
            mimeMultipart.addBodyPart(textMime);
            mimeMultipart.addBodyPart(fileMime);

            msg.setContent(mimeMultipart); // Sets the mimeMultipart the contents of the msg

            Transport.send(msg); // Transport class send the message using send() method
            System.out.println("Email Sent With Attachment Successfully...");

            foo = true; // Set the "foo" variable to true after successfully sending emails with attchment

        }catch(Exception e){

            System.out.println("EmailService File Error"+ e);
        }

        return foo; // and return foo variable
    }

    public boolean sendHtmlTemplate(String subject, String message, String to, long smtpid)
    {
        boolean foo = false; // Set the false, default variable "foo", we will allow it after sending code process email

        System.out.println(smtpid);
        SmtpData smtpdata= smtpdataRepository.findById(smtpid).orElseThrow(() -> 
     	new RessourceNotFoundException("Invalid smtpdata Id:" + smtpid));
        
        String senderEmail = smtpdata.getUsername(); // your gmail email id /"fdoucfousse@gmail.com";
        String senderPassword = smtpdata.getPassword();  // your gmail id password /"rtzcwsportyqvnww";

        // Properties class enables us to connect to the host SMTP server
        Properties properties = new Properties();

        // Setting necessary information for object property

        // Setup host and mail server
        properties.put("mail.smtp.auth", smtpdata.getAuth()); // enable authentication
        properties.put("mail.smtp.starttls.enable", smtpdata.getStarttls_enable()); // enable TLS-protected connection
        properties.put("mail.smtp.host", smtpdata.getHost()); // Mention the SMTP server address. Here Gmail's SMTP server is being used to send email
        properties.put("mail.smtp.port", smtpdata.getPort()); // 587 is TLS port number
        properties.put("mail.smtp.port", smtpdata.getStarttls_required()); // require TLS-protected connection

        // get the session object and pass username and password
        Session session = Session.getInstance(properties, new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication(){

                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {

            MimeMessage msg = new MimeMessage(session); // Create a default MimeMessage object for compose the message

            MimeMessageHelper helper = new MimeMessageHelper(msg, true); // create MimeMessageHelper class

            helper.setFrom(new InternetAddress(senderEmail)); // adding sender email id to helper object

            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); //adding recipient to msg object

            helper.setSubject(subject); // adding subject to helper object

            MimeMultipart mimeMultipart = new MimeMultipart(); // create MimeMultipart object

            MimeBodyPart textMime = new MimeBodyPart(); // create first MimeBodyPart object textMime for containing the message

            MimeBodyPart messageBodyPart = new MimeBodyPart(); // create second MimeBodyPart object messageBodyPart for containing the html format data

            textMime.setText(message); // sets message to textMime object

            // create message within html format tag and assign to the content variable
            String content = "<br><br><b>Hi friends</b>,<br><i>This email is HTML template style</i>";

            // sets html format content to the messageBodyPart object
            messageBodyPart.setContent(content,"text/html; charset=utf-8");

            //The mimeMultipart adds textMime and messageBodypart to the
            mimeMultipart.addBodyPart(textMime);
            mimeMultipart.addBodyPart(messageBodyPart);

            msg.setContent(mimeMultipart); // Set the mimeMultipart the contents of the msg

            Transport.send(msg); // Transport class send the message using send() method
            System.out.println("Email Sent With HTML Template Style Successfully...");

            foo = true; // Set the "foo" variable to true after successfully sending emails

        }catch(Exception e){

            System.out.println("EmailService File Error"+ e);
        }

        return foo; //and return foo variable
    }

    public boolean sendEmailInlineImage(String subject, String message, String to, long smtpid)
    {
        boolean foo = false; // Set the false, default variable "foo", we will allow it after sending code process email

        System.out.println(smtpid);
        SmtpData smtpdata= smtpdataRepository.findById(smtpid).orElseThrow(() -> 
     	new RessourceNotFoundException("Invalid smtpdata Id:" + smtpid));
        
        String senderEmail = smtpdata.getUsername(); // your gmail email id /"fdoucfousse@gmail.com";
        String senderPassword = smtpdata.getPassword();  // your gmail id password /"rtzcwsportyqvnww";

        // Properties class enables us to connect to the host SMTP server
        Properties properties = new Properties();

        // Setting necessary information for object property

        // Setup host and mail server
        properties.put("mail.smtp.auth", smtpdata.getAuth()); // enable authentication
        properties.put("mail.smtp.starttls.enable", smtpdata.getStarttls_enable()); // enable TLS-protected connection
        properties.put("mail.smtp.host", smtpdata.getHost()); // Mention the SMTP server address. Here Gmail's SMTP server is being used to send email
        properties.put("mail.smtp.port", smtpdata.getPort()); // 587 is TLS port number
        properties.put("mail.smtp.port", smtpdata.getStarttls_required()); // require TLS-protected connection

        // get the session object and pass username and password
        Session session = Session.getInstance(properties, new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication(){

                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {

            MimeMessage msg = new MimeMessage(session); //compose the message (multi media, text )

            MimeMessageHelper helper = new MimeMessageHelper(msg, true); // create MimeMessageHelper class

            helper.setFrom(new InternetAddress(senderEmail)); //adding sender email id to helper object

            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); //adding recipient to msg object

            helper.setSubject(subject); // adding subject to helper object

            // sets file path location
            String path = "C:\\Users\\Public\\Downloads\\Wpk.png";

            MimeMultipart mimeMultipart = new MimeMultipart(); // create MimeMultipart object

            MimeBodyPart textMime = new MimeBodyPart(); // create first MimeBodyPart object textMime for containing the message

            MimeBodyPart messageBodyPart = new MimeBodyPart(); // create second MimeBodyPart object messageBodyPart for containing the html format message

            MimeBodyPart fileMime = new MimeBodyPart(); // create third MimeBodyPart object fileMime for containing the file

            textMime.setText(message); // sets message to the textMime object

            // create message within html format tag and assign to the content variable
            String content = "<br><b>Hi friends</b>,<br><i>look at this nice logo :)</i>"
            + "<br><img src='cid:image52'/><br><b>Your Regards Onlyxcodes</b>";

            // sets html format content to the messageBodyPart object
            messageBodyPart.setContent(content,"text/html; charset=utf-8");

            File file = new File(path); //Initialize the File and Move Path variable
            fileMime.attachFile(file); //attach file to fileMime object

            helper.addInline("image52", file); //inline image attach using addInline() method

            //The mimeMultipart adds textMime, messageBodypart and fileMime to the
            mimeMultipart.addBodyPart(textMime);
            mimeMultipart.addBodyPart(messageBodyPart);
            mimeMultipart.addBodyPart(fileMime);

            msg.setContent(mimeMultipart); // Set the mimeMultipart the contents of the msg

            Transport.send(msg); // Transport class send the message using send() method
            System.out.println("Email Sent With Inline Image Successfully...");

            foo = true; // Set the "foo" variable to true after successfully sending emails with attchment

        }catch(Exception e){

            System.out.println("EmailService File Error"+ e);
        }

        return foo; //and return foo variable
    }
}
