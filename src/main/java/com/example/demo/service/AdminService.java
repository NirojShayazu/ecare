package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Admin;

public interface AdminService {

	void addAdmin(Admin admin);

	void deleteAdmin(int id);

	void updateAdmin(Admin admin);

	Admin getById(int aid);

	List<Admin> getAllAdmins();

}
