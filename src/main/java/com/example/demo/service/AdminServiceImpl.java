package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adRepo;
	
	@Override
	public void addAdmin(Admin admin) {
		adRepo.save(admin);
		
	}

	@Override
	public void deleteAdmin(int id) {
		adRepo.deleteById(id);
		
	}


	@Override
	public Admin getById(int aid) {
		return adRepo.getOne(aid);
	}

	@Override
	public List<Admin> getAllAdmins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateAdmin(Admin admin) {
		adRepo.save(admin);
		
	}

}
