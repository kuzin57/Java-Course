package com.kuzinro.spring.entities;

public class Student {
	private String fio;
	private String phoneNumber;
	private String email;
	private int universityId;
	
	public Student(
		String fio,
		String phoneNumber,
		String email,
		int universityId
	) {
		this.setFio(fio);
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.universityId = universityId;
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
	public int getUniversityId() {
		return universityId;
	}
	public void setUniversityId(int universityId) {
		this.universityId = universityId;
	}
	public String getFio() {
		return fio;
	}
	public void setFio(String fio) {
		this.fio = fio;
	}
}
