package com.kuzinro.spring.entities;

import java.util.ArrayList;

public class FilteredData {
	private final ArrayList<Student> students;
	private final ArrayList<University> universities;
	
	public FilteredData(ArrayList<Student> students, ArrayList<University> universities) {
		this.students = students;
		this.universities = universities;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public ArrayList<University> getUniversities() {
		return universities;
	}
}
