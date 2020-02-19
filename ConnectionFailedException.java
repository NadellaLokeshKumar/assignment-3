package com.ito.assignment3.exceltodatabase;

public class ConnectionFailedException extends Exception{

	public ConnectionFailedException() {
		System.err.println("Not connected to Database");
	}
}
