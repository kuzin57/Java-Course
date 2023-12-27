package com.kuzinro.spring.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuzinro.spring.services.IDataReader;
import com.kuzinro.spring.services.ServiceFactory;

public interface IConfigLoader {
	public Config load(String configFile);
}


class JsonConfigLoader implements IConfigLoader {
	public JsonConfigLoader() {}
	
	public Config load(String configFile) {
		IDataReader fileReader = ServiceFactory.buildFileDataReader();
		String jsonString = (String)fileReader.scan(configFile);
		ObjectMapper mapper = new ObjectMapper();
		
		Config config = null;
		try {
			config = mapper.readerFor(Config.class).readValue(jsonString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return config;
	}
}
