package com.wirepick.emailapi.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "SmtpData")
public class SmtpData {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;
	private String host;
    private String username;
    private String password;
    private String port;
    private String auth;
    private String starttls_enable;
    private String starttls_required;
    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "smtpdata")
    private List<AdressBook> adressbooks;*/
    
    public SmtpData (){
    	super();
    }
    
	public SmtpData(long id, String host, String username, String password, String port, String auth,
			String starttls_enable, String starttls_required) {
		super();
		this.id = id;
		this.host = host;
		this.username = username;
		this.password = password;
		this.port = port;
		this.auth = auth;
		this.starttls_enable = starttls_enable;
		this.starttls_required = starttls_required;
		
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getStarttls_enable() {
		return starttls_enable;
	}
	public void setStarttls_enable(String starttls_enable) {
		this.starttls_enable = starttls_enable;
	}
	public String getStarttls_required() {
		return starttls_required;
	}
	public void setStarttls_required(String starttls_required) {
		this.starttls_required = starttls_required;
	}
	@Override
	public String toString() {
		return "SmtpData [id=" + id + ", host=" + host + ", username=" + username + ", password=" + password + ", port="
				+ port + ", auth=" + auth + ", starttls_enable=" + starttls_enable + ", starttls_required="
				+ starttls_required + "]";
	}
    
}

