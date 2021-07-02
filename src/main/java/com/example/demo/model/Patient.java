package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.*;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
	@Size(min=1, max=50)
	@Pattern(regexp = "^[a-zA-Z ]*$", message = "Fullname must only have alphabets")
	private String fullname;
	@Column(unique = true)
	@Email
	private String email;
	@Column(unique = true)
	@Pattern(regexp = "^(98)\\d{8}$", message = "Phone number must match 98-xxxxxxxx format")
	private String phone;
	@Pattern(regexp = "^[a-zA-Z, ]*$", message = "alphabets Only")
	private String address;
	private String gender;
	@Min(1) @Max(120)
	private int age;
	private String atime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate adate;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="docID")
	private Doctors did;
	@Column(columnDefinition = "varchar(15) default 'pending'")
	private String status;
	@CreationTimestamp
	private LocalDateTime created;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAtime() {
		return atime;
	}

	public void setAtime(String atime) {
		this.atime = atime;
	}

	public LocalDate getAdate() {
		return adate;
	}

	public void setAdate(LocalDate adate) {
		this.adate = adate;
	}

	public Doctors getDid() {
		return did;
	}

	public void setDid(Doctors did) {
		this.did = did;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

}
