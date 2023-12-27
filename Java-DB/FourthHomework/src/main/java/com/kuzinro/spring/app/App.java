package com.kuzinro.spring.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import com.kuzinro.spring.config.Config;
import com.kuzinro.spring.config.ConfigLoaderFactory;
import com.kuzinro.spring.config.IConfigLoader;
import com.kuzinro.spring.dto.StudentDto;
import com.kuzinro.spring.entities.FilteredData;
import com.kuzinro.spring.services.IDataFilter;
import com.kuzinro.spring.services.IDataReader;
import com.kuzinro.spring.services.IDbManager;
import com.kuzinro.spring.services.ServiceFactory;

/**
 * Hello world!
 *
 */
public class App {
	private final static String pathToConfig = "../FourthHomework/config/config.json";
	private Config config;
	private IDataReader studentsDataReader;
	private IDataFilter studentsDataFilter;
	private IDbManager dbManager;
	private Logger logger;
	
	private App() {}
	
    public static void main(String[] args) {
    	App app = new App();
    	app.init();
    	app.run();
    }
    
    private void init() {
    	IConfigLoader configLoader = ConfigLoaderFactory.buildJsonConfigLoader();
    	config = configLoader.load(pathToConfig);
    	logger = Logger.getLogger("MyApp");
    	
    	FileHandler fh = null;
		try {
			fh = new FileHandler(config.getLogFile());
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
    	logger.addHandler(fh);
    	
    	studentsDataReader = ServiceFactory.buildStudentDataReader(logger);
    	studentsDataFilter = ServiceFactory.buildStudentDataFilter(logger);
    	dbManager = ServiceFactory.buildMySqlDbManager(
    		config.getDbUrl(),
    		config.getDbUser(),
    		config.getDbPassword(),
    		logger
    	);
    }
    
    private void run() {
    	ArrayList<StudentDto> studentsData =
    			(ArrayList<StudentDto>) studentsDataReader.scan(config.getPathToDir());
    	
    	FilteredData fd = studentsDataFilter.filter(studentsData);
    	dbManager.putStudents(fd.getStudents());
    	dbManager.putUniversities(fd.getUniversities());
    	dbManager.closeConnection();
    }
}
