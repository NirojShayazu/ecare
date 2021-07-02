package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Doctors;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientService;

@Controller
public class DoctorController {
	@Autowired
	private DoctorService docService;
	@Autowired
	private PatientService patientService;

	@GetMapping("/signup")
	public String getSignupForm(Model model) {
		model.addAttribute("dmodel", new Doctors());
		return "signup";
	}

	@PostMapping("/signup")
	public String saveDoctor(@Valid @ModelAttribute("dmodel") Doctors doc, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "signup";
		}
		docService.addDoctor(doc);
		String html = "<strong>'Congratulations!'</strong>";
		model.addAttribute("alert", html);
		return "signup";
	}

	@PostMapping("/upload-image")
	public String changeImage(@RequestParam("id") int id, @RequestParam("image") MultipartFile file,
			HttpSession session) throws IOException {
		if (checkSession(session)) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			docService.uploadImage(fileName, id);
			String uploadDir = "src/main/resources/static/user-image/";
			Path filePath = Paths.get(uploadDir + fileName);
//			System.out.println(filePath.toFile().getAbsolutePath());
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			return "redirect:/doctor/profile/" + id;
		} else
			return "redirect:/login";
	}

	@GetMapping("/doctors")
	public String getAll(Model model) {
		model.addAttribute("dlist", docService.getByStatus("approved"));
		return "doctors";
	}

	@GetMapping("/member")
	public String getMember(@RequestParam("docId") int id, Model model) {
		model.addAttribute("dmodel", docService.getById(id));
		return "member";
	}

	// after login

	@GetMapping("/doctor/home")
	public String getIndex() {
		return "/doctor/index";
	}

	@PostMapping("/doctor/login")
	public String getHomepage(@ModelAttribute Doctors doc, Model model, HttpSession session) {
		doc.setPassword(docService.encryptPassword(doc.getPassword()));
		Doctors d = docService.getAccess(doc.getPhone(), doc.getEmail(), doc.getPassword(), "approved");
		if (d != null) {
			session.setAttribute("activedoctor", d.getFirstName());
			session.setAttribute("id", d.getId());
//			session.setMaxInactiveInterval(1000);
			model.addAttribute("doc", d.getFirstName());
			return "redirect:/doctor-index";
		}
		model.addAttribute("msg", "User not found!!");
		return "redirect:/login";
	}

	@GetMapping("/doctor-index")
	public String getHomepage(HttpSession session) {
		return checkSession(session) ? "doctor/index" : "redirect:/login";
	}

	@RequestMapping(value = "/doctor-appointments", method = RequestMethod.GET)
	public String getAppointments(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
			HttpSession session, Model model) {
		if (checkSession(session)) {
			int did = (int) session.getAttribute("id");
//			LocalDate date = LocalDate.now().minusDays(1);
			model.addAttribute("appointments", patientService.getByDoctorAndDate(did, date));
			return "/doctor/appointments";
		} else
			return "redirect:/login";
	}

	@GetMapping("/doctor/checked/{id}&{date}")
	public String patientChecked(@PathVariable int id, @PathVariable String date, HttpSession session) {
		if (checkSession(session)) {
			patientService.checkPatient(id, "checked");
			LocalDate parseDate = LocalDate.parse(date);
			return "redirect:/doctor-appointments?date=" + parseDate;
		} else
			return "redirect:/login";
	}

	@GetMapping("/doctor/recheck/{id}&{date}")
	public String patientRecheck(@PathVariable int id, @PathVariable String date, HttpSession session) {
		if (checkSession(session)) {
			patientService.checkPatient(id, "pending");
			LocalDate parseDate = LocalDate.parse(date);
			return "redirect:/doctor-appointments?date=" + parseDate;
		} else
			return "redirect:/login";
	}

	@GetMapping("/doctor-logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	@RequestMapping(value = "doctors-search", method = RequestMethod.GET)
	public String searchDoctors(@RequestParam("q") String data, Model model) {
		model.addAttribute("dlist", docService.searchDoctors(data));
		return "doctors";
	}

	@GetMapping("/doctor/profile/{id}")
	public String getProfile(@PathVariable int id, Model model, HttpSession session) {
		if (checkSession(session)) {
//			File dir = new File("src/main/resources/static/imgs");
//			String[] fileName = dir.list();
//			model.addAttribute("img", fileName);
			model.addAttribute("dmodel", docService.getById(id));
			return "/doctor/profile";
		} else
			return "redirect:/login";
	}

	@GetMapping("/doctor/edit-profile/{id}")
	public String editProfile(@PathVariable int id, Model model, HttpSession session) {
		if (checkSession(session)) {
			model.addAttribute("dmodel", docService.getById(id));
			return "/doctor/edit-profile";
		} else
			return "redirect:/login";
	}

	@PostMapping("/update")
	public String updateProfile(@ModelAttribute Doctors doc, HttpSession session) {
		if (checkSession(session)) {
			docService.updateDoctor(doc);
			return "redirect:/doctor/profile/" + doc.getId();
		} else
			return "redirect:/login";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam("password") String password, @RequestParam("id") int id,
			HttpSession session) {
		if (checkSession(session)) {
			docService.changePassword(docService.encryptPassword(password), id);
			return "redirect:/doctor/profile/" + id;
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/check-currentpassword", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkCurrentPass(@RequestParam String password, @RequestParam int id, HttpSession session) {
		if (docService.checkCurrentPsw(docService.encryptPassword(password), id)) {
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping(value = "/validate-password", method = RequestMethod.POST)
	@ResponseBody
	public boolean validatePass(@RequestParam String password, HttpSession session) {
		if (docService.validatePsw(password)) {
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping(value = "/match-password", method = RequestMethod.POST)
	@ResponseBody
	public boolean matchPass(@RequestParam String password, @RequestParam String repassword, HttpSession session) {
		if (docService.matchPsw(password, repassword)) {
			return true;
		} else {
			return false;
		}
	}

	private Boolean checkSession(HttpSession session) {
		if (session.getAttribute("activedoctor") == null) {
			return false;
		}
		return true;
	}

}