package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepo;
	
	@Override
	public void addPatient(Patient p) {
		patientRepo.save(p);
	}

	@Override
	public Patient getById(int pid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patient> getAllPatients() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patient> getByAppointment(LocalDate date) {
		return patientRepo.findByAdateAfter(date);
	}

	@Override
	public void deleteAppointment(int id) {
		patientRepo.deleteById(id);
		
	}

	@Override
	public List<Patient> getByDoctorAndDate(int did, LocalDate date) {
		return patientRepo.getAppointmentList(did, date);
	}

	@Override
	@Transactional
	public void checkPatient(int id, String status) {
		patientRepo.checkPatient(status, id);
		
	}


}
