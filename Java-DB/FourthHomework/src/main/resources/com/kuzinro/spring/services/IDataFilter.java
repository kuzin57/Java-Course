package com.kuzinro.spring.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kuzinro.spring.dto.StudentDto;
import com.kuzinro.spring.entities.FilteredData;
import com.kuzinro.spring.entities.Student;
import com.kuzinro.spring.entities.University;

public interface IDataFilter {
	public FilteredData filter(ArrayList<StudentDto> studentsData);
}

class StudentDataFilter implements IDataFilter {
	private final String pivot = "#";
	
	private Logger logger;
	
	public StudentDataFilter(Logger logger) {
		this.logger = logger;
	}
	
	private boolean isValidFio(StudentDto dto) {
		return Character.isUpperCase(dto.getFirstname().charAt(0)) && 
				Character.isUpperCase(dto.getSurname().charAt(0)) &&
				Character.isUpperCase(dto.getThirdname().charAt(0));
	}
	
	private boolean isValidEmail(StudentDto dto) {
		return dto.getEmail().contains("@");
	}
	
	private boolean isValidPhoneNumber(StudentDto dto) {
		return dto.getPhoneNumber().length() == 16;
	}
	
	public FilteredData filter(ArrayList<StudentDto> studentsData) {
		int universityId = 1;
		HashMap<String, Integer> universitiesIds = new HashMap<String, Integer>();
		HashSet<String> studentsIdsSet = new HashSet<String>();
	
		ArrayList<Student> students = new ArrayList<Student>();
		ArrayList<University> universities = new ArrayList<University>();
		
		StringBuilder studentFioBuilder = new StringBuilder();
		StringBuilder studentIdBuilder = new StringBuilder();

		for (StudentDto dto : studentsData) {
			if (!isValidFio(dto) || !isValidEmail(dto) || !isValidPhoneNumber(dto)) {
				continue;
			}
			
			String studentId = studentIdBuilder.append(dto.getFirstname()).
					append(pivot).
					append(dto.getSurname()).
					append(pivot).
					append(dto.getThirdname()).
					append(pivot).
					append(dto.getEmail()).
					append(pivot).
					append(dto.getPhoneNumber()).
					toString();
			
			studentIdBuilder.delete(0, studentIdBuilder.length());
			if (studentsIdsSet.contains(studentId)) {
				logger.log(Level.WARNING, "Duplicate found for student: " + studentId);
				continue;
			}
			studentsIdsSet.add(studentId);
			
			String universityName = dto.getUniversity();
			Integer id = universitiesIds.get(universityName);
			if (id == null) {
				universitiesIds.put(universityName, universityId);
				universities.add(new University(universityId, universityName));
				id = universityId++;
			}
			
			String studentFio = studentFioBuilder.
					append(dto.getSurname()).
					append(" ").
					append(dto.getFirstname()).
					append(" ").
					append(dto.getThirdname()).
					toString();
			
			studentFioBuilder.delete(0, studentFioBuilder.length());
			students.add(new Student(
				studentFio,
				dto.getPhoneNumber(),
				dto.getEmail(),
				id.intValue()
			));
		}
		
		FilteredData fd = new FilteredData(students, universities);
		return fd;
	}
}
