package com.kuzinro.spring.dto;

import lombok.Getter;
import lombok.Setter;

public class StudentDto {
	private String surname;
	private String firstname;
	private String thirdname;
	private String phoneNumber;
	private String email;
	private String university;
	
	public StudentDto(
		String surname,
		String firstname,
		String thirdname,
		String phoneNumber,
		String email,
		String university
	) {
		this.surname = surname;
		this.firstname = firstname;
		this.thirdname = thirdname;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.university = university;
	}

	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getThirdname() {
		return thirdname;
	}
	public void setThirdname(String thirdname) {
		this.thirdname = thirdname;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
}
