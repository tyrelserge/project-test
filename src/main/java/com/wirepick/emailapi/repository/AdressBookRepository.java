package com.wirepick.emailapi.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wirepick.emailapi.model.AdressBook;

@Repository
@Transactional
public interface AdressBookRepository extends JpaRepository <AdressBook, Long> {
	 
	  @Query("select a from AdressBook a where a.smtpdata.id = ?1")
	  List<AdressBook> findBySmtpDataId(Long smtpDataId);
	  @Query("select a.id from AdressBook a")
		 List<Long> findAdressBookIds();
	   /* @Modifying
	    @Query("delete from AdressBook u where u.Name = ?1")
	    void deleteName(String Name);*/
	    @Modifying
	    @Query("DELETE AdressBook a WHERE a.smtpdata.id = ?1")
	    void deleteBySmtpId(long smtpDataId);
}
