package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Doctors;

public interface DoctorRepository extends JpaRepository<Doctors, Integer> {
	List<Doctors> findByStatus(String status);
	
	Doctors findByPasswordAndId(String password, int id);
	
	Doctors findByPhoneOrEmailAndPasswordAndStatus(String phone, String email, String Password, String status);
	
	@Modifying
	@Query("update Doctors d set d.status = :status, d.password = :password where d.id = :id")
	void approveDoctor(@Param("status") String status,@Param("password") String password, @Param("id") int id);
	
	@Modifying
	@Query("update Doctors d set d.status = :status where d.id = :id")
	void blockDoctor(@Param(value = "status") String status, @Param(value = "id") int id);
	
	@Query("FROM Doctors d WHERE d.firstName LIKE %?1%"
            + " OR d.lastName LIKE %?1%"
            + " OR d.speciality LIKE %?1%"
            + " And d.status = 'approved'")
	public List<Doctors> searchDoc(String keywords);
	
	@Modifying
	@Query("update Doctors d set d.password = :password where d.id = :id")
	void changePassword(@Param("password") String password, @Param("id") int id);
	
	@Modifying
	@Query("update Doctors d set d.image = :image where d.id = :id")
	void changeImage(@Param("image") String image, @Param("id") int id);
	
}
