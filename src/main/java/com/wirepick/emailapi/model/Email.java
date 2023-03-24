package com.wirepick.emailapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Email")
public class Email {
		@Id
		@Column(name = "id")
		@GeneratedValue(strategy =  GenerationType.IDENTITY)
		private long id;
		private String recipient;
	    private String subject;
	    private String message;
	    private long smtpid;
	    

		public Email() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Email(long id, String recipient, String subject, String message, long smtpid) {
			super();
			this.id = id;
			this.recipient = recipient;
			this.subject = subject;
			this.message = message;
			this.smtpid = smtpid;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
	    public String getRecipient() { 
	    	 return recipient; 
	    }

	    public void setRecipient(String recipient) {
	    	this.recipient = recipient; 
	    	}

	    public String getSubject() {
	        return subject;
	    }

	    public void setSubject(String subject) {
	        this.subject = subject;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }
        
	    public long getSmtpid() {
			return smtpid;
		}

		public void setSmtpid(long smtpid) {
			this.smtpid = smtpid;
		}

		@Override
	    public String toString() {
	        return "EmailRequest{" +
	                "recipient='" + recipient + '\'' +
	                ", subject='" + subject + '\'' +
	                ", message='" + message + '\'' +
	                ", smtpid='" + smtpid + '\'' +
	                '}';
	    }
	}
