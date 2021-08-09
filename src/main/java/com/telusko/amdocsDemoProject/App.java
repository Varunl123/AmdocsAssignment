package com.telusko.amdocsDemoProject;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args ) throws IOException
    {
    	// Testing CSV File
    	CustomerReader customer=new CustomerReader("Customers.csv","csv");
    	customer.readCsvContent();
    	
    	// Test Text File
    	CustomerReader customer1=new CustomerReader("Customers.txt","txt");
    	customer1.readTextContent();
    }
}
