package com.wirepick.emailapi.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wirepick.emailapi.model.AdressBook;
import com.wirepick.emailapi.model.SmtpData;


public interface SmtpDataRepository extends JpaRepository<SmtpData, Long> {
	  @Query("select s.id from SmtpData s")
	 List<Long> findSmtpDataIds();

}
