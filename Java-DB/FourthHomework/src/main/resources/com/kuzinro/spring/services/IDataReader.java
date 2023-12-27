package com.kuzinro.spring.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kuzinro.spring.dto.StudentDto;

public interface IDataReader {
	public Object scan(String path);
}

class FileDataReader implements IDataReader {
	public FileDataReader() {}
	
	@Override
	public String scan(String file) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return sb.toString();
	}
}

class StudentDataReader implements IDataReader {
	private Logger logger;
	
	public StudentDataReader(Logger logger) {
		this.logger = logger;
	}
	
	private enum Format {
		Surname(0),
		FirstName(1),
		ThirdName(2),
		PhoneNumber(3),
		Email(4),
		University(5);

		private int partNum;
		
		Format(int partNum) {
			this.partNum = partNum;
		}
		
		public int getPartNum() {			
			return partNum;
		}
	}

	private void scanFile(String path, ArrayList<StudentDto> studentsStorage) {
		logger.info("Scanning file " + path + "...");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			StringBuilder universityNameBuilder = new StringBuilder();
			
			while (line != null) {
				String[] parts = line.split("\\s+");
				for (int i = Format.University.getPartNum(); i < parts.length; i++) {
					universityNameBuilder.append(parts[i]);
					if (i != parts.length - 1) {
						universityNameBuilder.append(" ");
					}
				}
				
				studentsStorage.add(new StudentDto(
					parts[Format.Surname.getPartNum()],
					parts[Format.FirstName.getPartNum()],
					parts[Format.ThirdName.getPartNum()],
					parts[Format.PhoneNumber.getPartNum()],
					parts[Format.Email.getPartNum()],
					universityNameBuilder.toString()
				));
				
				line = br.readLine();
				universityNameBuilder.delete(0, universityNameBuilder.length());
			}
			
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
		}
		
		logger.log(Level.INFO, "File " + path + " successfully scanned!");
	}
	
	@Override
	public ArrayList<StudentDto> scan(String dirPath) {
		logger.log(Level.INFO, "Starting to read data from directory " + dirPath + "...");
		ArrayList<StudentDto> studentsData = new ArrayList<StudentDto>();
		File[] files = new File(dirPath).listFiles();
		for (File file : files) {
			scanFile(file.getAbsolutePath(), studentsData);
		}
		logger.log(Level.INFO, "All data has been read");
		return studentsData;
	}
}
