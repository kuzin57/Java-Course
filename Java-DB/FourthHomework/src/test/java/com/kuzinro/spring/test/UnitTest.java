package com.kuzinro.spring.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.junit.Test;
import org.mockito.Mock;

import com.kuzinro.spring.dto.StudentDto;
import com.kuzinro.spring.entities.FilteredData;
import com.kuzinro.spring.entities.Student;
import com.kuzinro.spring.services.IDataFilter;
import com.kuzinro.spring.services.IDataReader;
import com.kuzinro.spring.services.IDbManager;
import com.kuzinro.spring.services.ServiceFactory;
import java.util.logging.Logger;

/**
 * Unit test for simple App.
 */
public class UnitTest {
	private final String firstTestFile = "./testDir/test1.txt";
	private final String secondTestFile = "./testDir/test2.txt";
	private final String testDirectoryPath = "./testDir";
	private ArrayList<Student> students;
	
	@Mock
	private IDbManager dbManager;
	
    @Test
    public void testFileDataReader() {
        IDataReader dataReader = ServiceFactory.buildFileDataReader();
        String contentString = (String)dataReader.scan(firstTestFile);
        String[] lines = contentString.split("\n");
        assertTrue(lines.length == 20);
        assertTrue(lines[0].split("\\s+").length >= 6);
    }
    
    @Test
    public void testStudentDataReader() {
    	IDataReader dataReader = ServiceFactory.buildStudentDataReader(Logger.getLogger("testStudentDataReader"));
    	ArrayList<StudentDto> data = (ArrayList<StudentDto>)dataReader.scan(testDirectoryPath);
    	assertTrue(data.size() == 40); 
    	
    	StudentDto dtoFirst = data.get(0);
    	System.out.println(dtoFirst.getThirdname());
    	assertTrue(dtoFirst.getFirstname().equals("Иван"));
    	assertTrue(dtoFirst.getSurname().equals("Иванов"));
    	assertTrue(dtoFirst.getThirdname().equals("Иванович"));
    }
    
    @Test
    public void testFilterData() {
    	Logger logger = Logger.getLogger("testFilterData");
    	IDataReader dataReader = ServiceFactory.buildStudentDataReader(logger);
    	IDataFilter dataFilter = ServiceFactory.buildStudentDataFilter(logger);
    	ArrayList<StudentDto> data = (ArrayList<StudentDto>)dataReader.scan(testDirectoryPath);
    	FilteredData fData = dataFilter.filter(data);
    	
    	for (Student student : fData.getStudents()) {
    		assertTrue(Character.isUpperCase(student.getFio().charAt(0)));
    		assertTrue(student.getEmail().contains("@"));
    	}
    	
    	
    }
}
