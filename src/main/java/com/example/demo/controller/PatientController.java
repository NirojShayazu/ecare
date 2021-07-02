package com.example.demo.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Doctors;
import com.example.demo.model.Patient;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientService;

@Controller
public class PatientController {

	@Autowired
	private PatientService patientService;
	@Autowired
	private DoctorService docService;
	
	Patient p = new Patient();
	
	@GetMapping("/appointment")
	public String getAppointment(@RequestParam("docId") int did, Model model) {
//		Patient p = new Patient();
		Doctors doc = docService.getById(did);
		p.setDid(doc);
		model.addAttribute("pmodel", p);
		return "make-appointment";
	}
	
	@PostMapping("/appointment")
	public String saveAppointment(@Valid @ModelAttribute("pmodel") Patient p, BindingResult result, @RequestParam("did") int did, Model model) {
		if(result.hasErrors()) {
			Doctors doc = docService.getById(did);
			p.setDid(doc);
			return "make-appointment";
		}
		patientService.addPatient(p);
		return "/index";
	}
	
	public List<Patient> getAppointmentList() {
		return patientService.getByAppointment(LocalDate.now().plus(1,ChronoUnit.DAYS));
	}
}
