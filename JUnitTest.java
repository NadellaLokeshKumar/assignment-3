package com.ito.assignment3.exceltodatabase;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.jupiter.api.Test;

class JUnitTest {

	@Test
	void test1() throws IOException {
		FileReader reader = new FileReader("properties");
		Properties properties = new Properties();
		properties.load(reader);
		assertEquals("root", properties.getProperty("username"));
	}
	
	@Test
	void test2() throws ConnectionFailedException, IOException {
		FileReader reader = new FileReader("properties");
		Properties properties = new Properties();
		properties.load(reader);
		JDBCImpl jdbc = new JDBCImpl();
		assertNotNull(jdbc.getConnection(properties));
	}
	
	@Test
	void test3() throws IOException, ExcelFileNotFoundException {
		FileReader reader = new FileReader("properties");
		Properties properties = new Properties();
		properties.load(reader);
		JDBCImpl jdbc = new JDBCImpl();
		assertNotNull(jdbc.getSheet(properties));
	}
	
	@Test
	void test4() throws IOException, SQLException, ConnectionFailedException, ExcelFileNotFoundException, DataBaseRelatedException {
		FileReader reader = new FileReader("properties");
		Properties properties = new Properties();
		properties.load(reader);
		JDBCImpl jdbc = new JDBCImpl();
		Connection connection = jdbc.getConnection(properties);
		String sql = properties.getProperty("query");
		PreparedStatement statement = connection.prepareStatement(sql);
		
		int[] rows = jdbc.jdbcImplementation(statement, jdbc.getSheet(properties));
		assertTrue(rows.length == 7);
	}

}
