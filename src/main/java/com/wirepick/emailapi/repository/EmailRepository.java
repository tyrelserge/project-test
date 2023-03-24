package com.wirepick.emailapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wirepick.emailapi.model.AdressBook;
import com.wirepick.emailapi.model.Email;


public interface EmailRepository extends JpaRepository<Email, Long> {
	 @Query("select e.id from Email e")
	 List<Long> findEmailIds();
	 @Query("select e from Email e where smtpid = ?1")
	  List<Email> findBySmtpDataId(Long smtpDataId);
}
