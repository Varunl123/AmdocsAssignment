package com.telusko.amdocsDemoProject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class CustomerReader {

	//CSV file
	private String csvFile;
	private BufferedReader reader;
	private String line;

	//TXT file
	private String txtFileName;


	/* Constructor */
	public CustomerReader(String path,String fileType) throws IOException {

		if(fileType.contentEquals("csv")) {
			this.reader=null;
			this.csvFile=path;
			this.line="";
		}
		else if(fileType.contentEquals("txt")) {
			this.txtFileName=path;
		}
	}


	/* Reading Text File */
	public void readTextContent()  {
		String customerId="";
		String customerName="";
		String createdDate="";
		CustomerProcessor customerprocessor=new CustomerProcessor();
		

		try {
			BufferedReader br=new BufferedReader(new FileReader("Customers.txt"));
			String line=null;
			while((line=br.readLine())!=null) {
				String temp[]=line.split("\t");
				if(temp.length==1){
					customerId=temp[0];
					customerprocessor.printCustomerData(customerId,null,null);
				}
				else if(temp.length==2) {
					customerId=temp[0];
					customerName=temp[1];
					try {
						if(customerName.contains("%") || customerName.contains("&")) {
							throw new InCorrectCustomerFormatException();
						}
						else {
							customerprocessor.printCustomerData(customerId, customerName, null);
						}
					}
					catch(InCorrectCustomerFormatException e) {
						customerprocessor.replaceAndPrintCustomer(customerId, customerName,null,"EXP");
					}

				}
				else if(temp.length==3) {
					customerId=temp[0];
					customerName=temp[1];
					createdDate=temp[2];
					String convertedDate=convertDateFormat(createdDate,"dd/MM/yyyy");
					try {
						if(customerName.contains("%") || customerName.contains("&")) {
							throw new InCorrectCustomerFormatException();
						}
						else{
							customerprocessor.printCustomerData(customerId, customerName, convertedDate);
						}
					}
					catch(InCorrectCustomerFormatException e) {
						e.getMessage();
						customerprocessor.replaceAndPrintCustomer(customerId, customerName, convertedDate, "EXP");
					}
				}
			}
			customerprocessor.closingTask();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
   /* Method to convert to specified Date Format */
	public String convertDateFormat(String givenDate,String newDateFormat)  {
		String oldDateFormat="";
		String formattedDate="";
		String tempDate=givenDate;
		
		if(tempDate.matches("[0-12]{2}[/]{1}[0-31]{2}[/]{1}[0-9]{4}")) {
			oldDateFormat="MM/dd/yyyy";
		}
		else if(tempDate.matches("[0-31]{2}[/]{1}[0-12]{1}[/]{1}[0-9]{4}")) {
			tempDate=tempDate.substring(0,2)+"0"+tempDate.substring(3);
			oldDateFormat="dd/MM/yyyy";
		}
		else if (tempDate.matches("[0-31]{2}[/]{1}[0-12]{2}[/]{1}[0-9]{4}")) {
	        oldDateFormat = "dd/MM/yyyy";
	    } 
	    else if (tempDate.matches("[0-31]{2}[-]{1}[0-12]{2}[-]{1}[0-9]{4}{1}")) {
	        oldDateFormat = "dd-MM-yyyy";
	    }
	    else if (tempDate.matches("[0-12]{2}[-]{1}[0-31]{2}[-]{1}[0-9]{4}{1}")) {
	        oldDateFormat = "MM-dd-yyyy";
	    }
	    
		try {
			formattedDate=new SimpleDateFormat(newDateFormat,Locale.ENGLISH).format(new SimpleDateFormat(oldDateFormat).parse(tempDate));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return formattedDate;
	}

	/* Read CSV Content */
	public void readCsvContent() throws IOException {

		String customerId="";
		String customerName="";
		String createdDate="";
		CustomerProcessor customerprocessor=new CustomerProcessor();
		customerprocessor.scheduleTask();
		
		try {
			reader=new BufferedReader(new FileReader(csvFile));
			while((line=reader.readLine())!=null) {
				String[] temp=line.split(",");
				if(temp.length==1){
					customerId=temp[0];
					customerprocessor.printCustomerData(customerId,null,null);
				}
				else if(temp.length==2) {
					customerId=temp[0];
					customerName=temp[1];
					try {
						if(customerName.contains("%") || customerName.contains("&")) {
							throw new InCorrectCustomerFormatException();
						}
						else {
							customerprocessor.printCustomerData(customerId, customerName, null);
						}
					}
					catch(InCorrectCustomerFormatException e) {
						customerprocessor.replaceAndPrintCustomer(customerId, customerName,null,"EXP");
					}
				}
				else if(temp.length==3) {
					customerId=temp[0];
					customerName=temp[1];
					createdDate=temp[2];
					String convertedDate=convertDateFormat(createdDate,"DD/MM/YYYY");
					try {
						if(customerName.contains("%") || customerName.contains("&")) {
							throw new InCorrectCustomerFormatException();
						}
						else{
							customerprocessor.printCustomerData(customerId, customerName, convertedDate);
						}
					}
					catch(InCorrectCustomerFormatException e) {
						customerprocessor.replaceAndPrintCustomer(customerId, customerName, convertedDate, "EXP");
					}
				}
			}
			customerprocessor.closingTask();

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			reader.close();
		}

	}

}
