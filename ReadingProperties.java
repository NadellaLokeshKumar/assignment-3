package com.ito.assignment3.exceltodatabase;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadingProperties {
	
	public static void main(String[] args) throws PropertiesFileNotFoundException {
		
		readingProperties();
	}

	// Reading properties from properties file.
	public static void readingProperties() throws PropertiesFileNotFoundException {
		
		try {
		FileReader reader = new FileReader("properties");
		Properties properties = new Properties();
		properties.load(reader);

		JDBCImpl jdbc = new JDBCImpl();
		jdbc.storingDataExcelToDB(properties);
	} catch(Exception e) {
		throw new PropertiesFileNotFoundException();
	}
}
}
