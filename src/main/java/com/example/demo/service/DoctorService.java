package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Doctors;

public interface DoctorService {
	void addDoctor(Doctors doc);

	void deleteDoctor(int did);

	void updateDoctor(Doctors doc);

	Doctors getById(int did);

	void approveDoc(int did, String status, String password);

	void blockDoc(int did, String status);

	List<Doctors> getAllDoctors();
	
	List<Doctors> searchDoctors(String data);

	List<Doctors> getByStatus(String status);
	
	String encryptPassword(String psw);
	
	Doctors getAccess(String phone,String email,String password, String status);
	
	boolean checkCurrentPsw(String password, int id);
	
	boolean validatePsw(String password);
	
	boolean matchPsw(String password, String repassword);
	
	void changePassword(String passsword, int did);
	
	void uploadImage(String image, int id);

}
