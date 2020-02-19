package com.ito.assignment3.exceltodatabase;

public class PropertiesFileNotFoundException extends Exception {

	public PropertiesFileNotFoundException() {
		System.err.println("Properties file not found");
	}
}
