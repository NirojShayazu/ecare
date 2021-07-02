package com.example.demo.model;

import java.beans.Transient;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

@Entity
public class Doctors {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(unique = true)
//	@Pattern(regexp = "[0-9]", message = "Numbers Only")
	private int nmcNumb;
	@NotNull(message = "salutation is required")
	private String salutation;
	@Pattern(regexp = "^[a-zA-Z, ]*$", message = "alphabets Only allowed")
	@Size(min = 2, message = "inappropriate name length")
	private String firstName;
	@Pattern(regexp = "^[a-zA-Z, ]*$", message = "alphabets Only allowed")
	@Size(min = 2, message = "inappropriate name length")
	private String lastName;
	@Column(unique = true)
	@Email
	private String email;
	@Column(unique = true)
	@Pattern(regexp = "^(98)\\d{8}$", message = "Phone number must match 98-xxxxxxxx format")
	private String phone;
	private int yearsOfPractice;
	@NotNull(message = "speciality is required")
	private String speciality;
	@NotNull(message = "qualification is required")
	private String qualification;
	@NotNull(message = "gender is required")
	private String gender;
	private String description;
	@Column(nullable = true)
	private String password;
	private String status;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "infoId")
	private DoctorInfo info;
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime created;
	@UpdateTimestamp
	private LocalDateTime updated;
	@Nullable
	private String image;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNmcNumb() {
		return nmcNumb;
	}

	public void setNmcNumb(int nmcNumb) {
		this.nmcNumb = nmcNumb;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public int getYearsOfPractice() {
		return yearsOfPractice;
	}

	public void setYearsOfPractice(int yearsOfPractice) {
		this.yearsOfPractice = yearsOfPractice;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DoctorInfo getInfo() {
		return info;
	}

	public void setInfo(DoctorInfo info) {
		this.info = info;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getImage() {
		return image;
	}

	@Transient
    public String getImagePath() {
		if (image == null) return null;
        return "/user-image/"+ image;
//        <img th:src="/@{${user.photosImagePath}}" />
    }

}
