package com.kuzinro.spring.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kuzinro.spring.dto.StudentDto;
import com.kuzinro.spring.entities.Student;
import com.kuzinro.spring.entities.University;

public interface IDbManager {
	public void putStudents(ArrayList<Student> students);
	public void putUniversities(ArrayList<University> universities);
	public void closeConnection();
	
}

class MySqlDbManager implements IDbManager {
	private final String insertStudentsQueryTemplate = "INSERT INTO students (fio, email, phone_number, university_id) VALUES ";
	private final String insertUniversitiesQueryTemplate = "INSERT INTO university (id, name) VALUES ";
	
	private Logger logger;
	private Connection conn;
	
	public MySqlDbManager(String url, String dbUser, String dbPassword, Logger logger) {
		this.logger = logger;
		initConnection(url, dbUser, dbPassword);
	}
	
	private void initConnection(String dbUrl, String dbUser, String dbPassword) {
		try {
			Class.forName("org.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		
		try {
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}
	
	@Override
	public void putStudents(ArrayList<Student> students) {
		logger.log(Level.INFO, "Putting students into database...");
		StringBuilder queryBuilder = new StringBuilder(insertStudentsQueryTemplate);
		StringBuilder entryBuilder = new StringBuilder();
		int i = 0;
		for (Student student : students) {
			entryBuilder.append("(").
					append("'").
					append(student.getFio()).
					append("'").
					append(", ").
					append("'").
					append(student.getEmail()).
					append("'").
					append(", ").
					append("'").
					append(student.getPhoneNumber()).
					append("'").
					append(", ").
					append(student.getUniversityId()).
					append(")");
			
			if (i == students.size() - 1) {
				entryBuilder.append(";");
			} else {
				entryBuilder.append(",");
			}
			
			queryBuilder.append(entryBuilder.toString());
			entryBuilder.delete(0, entryBuilder.length());
			i++;
		}
		
		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.executeUpdate(queryBuilder.toString());
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		logger.log(Level.INFO, "Successfully finished putting students into database");
	}
	
	@Override
	public void putUniversities(ArrayList<University> universities) {
		logger.log(Level.INFO, "Putting universities into database...");
		StringBuilder queryBuilder = new StringBuilder(insertUniversitiesQueryTemplate);
		StringBuilder entryBuilder = new StringBuilder();
		int i = 0;
		for (University university : universities) {
			entryBuilder.append("(").
					append(university.getId()).
					append(", ").
					append("'").
					append(university.getName()).
					append("'").
					append(")");
			
			if (i == universities.size() - 1) {
				entryBuilder.append(";");
			} else {
				entryBuilder.append(",");
			}
			
			queryBuilder.append(entryBuilder.toString());
			entryBuilder.delete(0, entryBuilder.length());
			i++;
		}
		
		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.executeUpdate(queryBuilder.toString());
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		logger.log(Level.INFO, "Successfully finished putting universities into database");
	}
	
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}
}