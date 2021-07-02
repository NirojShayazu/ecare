package com.example.demo.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.example.demo.model.Doctors;
import com.example.demo.repository.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService{
	
	@Autowired
	private DoctorRepository docRepo;

	@Override
	public void addDoctor(Doctors doc) {
		docRepo.save(doc);
	}

	@Override
	public void deleteDoctor(int did) {
		docRepo.deleteById(did);
		
	}

	@Override
	public void updateDoctor(Doctors doc) {
		docRepo.save(doc);
		
	}

	@Override
	public Doctors getById(int did) {
		return docRepo.getOne(did);
	}

	@Override
	public List<Doctors> getAllDoctors() {
		
		return docRepo.findAll();
	}

	@Override
	public List<Doctors> getByStatus(String status) {
		return docRepo.findByStatus(status);
	}

	@Override
	@Transactional
	public void approveDoc(int did, String status, String password) {
		docRepo.approveDoctor(status, password, did);
	}

	@Override
	@Transactional
	public void blockDoc(int did, String status) {
		docRepo.blockDoctor(status, did);
		
	}

	@Override
	public String encryptPassword(String psw) {
		return DigestUtils.md5DigestAsHex(psw.getBytes());
	}

	@Override
	public Doctors getAccess(String phone, String email, String password, String status) {
		return docRepo.findByPhoneOrEmailAndPasswordAndStatus(phone, email, password, status);
	}

	@Override
	public List<Doctors> searchDoctors(String data) {
		return docRepo.searchDoc(data);
	}

	@Override
	@Transactional
	public void changePassword(String password, int did) {
		docRepo.changePassword(password, did);
		
	}

	@Override
	public boolean checkCurrentPsw(String password, int id) {
		if(docRepo.findByPasswordAndId(password, id) != null) {
			return true; 
		}else
			return false;
	}

	@Override
	public boolean validatePsw(String password) {
		// Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                       + "(?=.*[a-z])(?=.*[A-Z])"
                       + "(?=.*[@#$%^&+=])"
                       + "(?=\\S+$).{8,20}$";
        
		// Compile the ReGex
        Pattern p = Pattern.compile(regex);
  
        // If the password is empty
        // return false
        if (password == null) {
            return false;
        }
        
     // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);
  
        // Return if the password
        // matched the ReGex
        return m.matches();
        
	}

	@Override
	public boolean matchPsw(String password, String repassword) {
		if(password.equals(repassword)) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public void uploadImage(String image, int id) {
		docRepo.changeImage(image, id);
	}



}
