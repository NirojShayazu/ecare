package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.model.Patient;

public interface PatientService {

	void addPatient(Patient p);
	
	void deleteAppointment(int id);

	List<Patient> getByAppointment(LocalDate date);
	
	List<Patient> getByDoctorAndDate(int did, LocalDate date);

	Patient getById(int pid);

	List<Patient> getAllPatients();
	
	void checkPatient(int id, String status);
}
