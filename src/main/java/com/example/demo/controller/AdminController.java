package com.example.demo.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.AdminService;
import com.example.demo.service.DoctorService;
import com.example.demo.service.MailService;
import com.example.demo.service.PasswordGenerator;
import com.example.demo.service.PatientService;

@Controller
public class AdminController {

	@Autowired
	private AdminRepository adRepo;
	@Autowired
	private AdminService adservice;
	@Autowired
	private DoctorService docService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private PasswordGenerator password;
	@Autowired
	private MailService mailService;
	

	@PostMapping("/admin")
	public String adminLogin(@ModelAttribute Admin admin, Model model, HttpSession session) {
		admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
		Admin a = adRepo.findByUsernameAndPassword(admin.getUsername(), admin.getPassword());
		if (a != null) {
			session.setAttribute("active", a.getUsername());
//			session.setMaxInactiveInterval(1000);
			model.addAttribute("admin", a.getUsername());
			return "redirect:/index";
		}
		model.addAttribute("msg", "User not found!!");
		return "redirect:/login";
	}

	@GetMapping("/index")
	public String getIndex(HttpSession session) {
		return checkSession(session) ? "admin/index" : "redirect:/login";
	}

	@RequestMapping(value = "/admin/register", method = RequestMethod.GET) 
	public String getRegister(Model model, HttpSession session) {
		if (checkSession(session)) {
			model.addAttribute("amodel", new Admin());
			return "admin/register";
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute Admin admin, HttpSession session) {
		if (checkSession(session)) {
			admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
			adservice.addAdmin(admin);
			return "redirect:/index";
		}else {
			return "redirect:/login";
		}
	}

	@RequestMapping(value = "/admin/doctors/search", method = RequestMethod.GET)
	public String getDocByStatus(@RequestParam("q") String s, HttpSession session, Model model) {
		if (checkSession(session)) {
			model.addAttribute("list", docService.getByStatus(s));
			return "admin/doctors";
		} else
			return "redirect:/login";
	}

	@GetMapping("/admin/approve-doctor/{id}&{email}")
	public String getApprove(@PathVariable int id, @PathVariable String email, HttpSession session) {
		if (checkSession(session)) {
			String psw = password.generatePassword();
			docService.approveDoc(id, "approved", docService.encryptPassword(psw));
			mailService.sendMail(email, psw);
			return "redirect:/admin/doctors/search?q=pending";
		} else
			return "redirect:/login";
	}
	
	@GetMapping("/admin/delete-doctor/{id}")
	public String deleteDoctor(@PathVariable int id, HttpSession session) {
		if (checkSession(session)) {
			docService.deleteDoctor(id);
			return "redirect:/admin/doctors/search?q=approved";
		} else
			return "redirect:/login";
	}
	
	@GetMapping("/admin/block-doctor/{id}")
	public String blockDoctor(@PathVariable int id, HttpSession session) {
		if (checkSession(session)) {
			docService.blockDoc(id, "blocked");
			return "redirect:/admin/doctors/search?q=approved";
		} else
			return "redirect:/login";
	}
	
	@GetMapping("/admin/details-doctor/{id}")
	private String addDetails(@PathVariable int id, HttpSession session) {
		if (checkSession(session)) {
			
			return "redirect:/admin/doctors/search?q=approved";
		} else
			return "redirect:/login";
	}

	@GetMapping("/admin/appointments")
	public String getAlist(HttpSession session, Model model) {
		if (checkSession(session)) {
//			System.out.println(LocalDate.now().minusDays(1));
			model.addAttribute("alist",patientService.getByAppointment(LocalDate.now().minusDays(1)));
			return "/admin/patients";
		} else
			return "redirect:/login";
	}
	
	@GetMapping("/admin/delete-appointment/{id}")
	private String deleteAppointment(@PathVariable int id, HttpSession session) {
		if (checkSession(session)) {
			patientService.deleteAppointment(id);
			return "redirect:/admin/appointments";
		} else
			return "redirect:/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "/login";
	}

	@GetMapping("/charts")
	public String getCharts(HttpSession session) {
		return checkSession(session) ? "admin/charts" : "redirect:/login";
	}

	private Boolean checkSession(HttpSession session) {
		if (session.getAttribute("active") == null) {
			return false;
		}
		return true;
	}
}
