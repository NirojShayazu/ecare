package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

	List<Patient> findByAdateAfter(LocalDate date);
	
	@Query(value = "Select * FROM Patient p WHERE p.docid = :did AND p.adate = :date ORDER BY atime desc", nativeQuery = true)
	List<Patient> getAppointmentList(@Param("did") int did, @Param("date") LocalDate date);
	
	@Modifying
	@Query("update Patient p set p.status = :status where p.id = :id")
	void checkPatient(@Param("status") String status, @Param("id") int id);
}
