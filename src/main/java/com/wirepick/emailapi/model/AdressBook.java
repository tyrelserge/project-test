package com.wirepick.emailapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "AdressBook")
public class AdressBook {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
    private String Name;
    private String Contact;
    private String Email;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "smtpDataId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SmtpData smtpdata;
    public AdressBook() {
        super();
    }
    
    public AdressBook(String name, String contact, String email, SmtpData smtpdata) {
		super();
		Name = name;
		Contact = contact;
		Email = email;
		this.smtpdata = smtpdata;
	}
    
    public AdressBook(String name, String contact, String email) {
		super();
		Name = name;
		Contact = contact;
		Email = email;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public SmtpData getSmtpdata() {
		return smtpdata;
	}
	public void setSmtpdata(SmtpData smtpdata) {
		this.smtpdata = smtpdata;
	}

}
