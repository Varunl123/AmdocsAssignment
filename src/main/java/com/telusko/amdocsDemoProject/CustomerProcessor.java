package com.telusko.amdocsDemoProject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CustomerProcessor {

		private CustomerReader customerreader;
		private FileWriter fw;
		private PrintWriter pw;
		private Integer customerCount;
		ScheduledExecutorService executorService;
		//Timer timer;
		
		CustomerProcessor(){
			//System.out.println("CustomerId"+"\t"+"CustomerName"+"\t"+"CreatedDate");
			customerCount=0;
			executorService=Executors.newSingleThreadScheduledExecutor();
			
			//timer=new Timer();
			
			try {
				fw=new FileWriter("finalcustomer.txt",true);
				pw=new PrintWriter(fw);
				//pw.println("CustomerId\"+\"\\t\"+\"CustomerName\"+\"\\t\"+\"CreatedDate");
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			finally {
				pw.close();
			}
		}
		
		/* Scheduling the task to print the number of customerReader read and CustomerCreatedDate */
		public void scheduleTask() {
			executorService.scheduleAtFixedRate(new Runnable() {
				 public void run() {
					  System.out.println("Customer records count: "+customerCount);
				  }
			  }, 0, 20, TimeUnit.MILLISECONDS);

//			timer.schedule(new TimerTask() {
//				@Override
//				public void run() {
//					System.out.println("Count: "+customerCount);
//				}
//			},0,20);
			
		}
		public void printCustomerData(String customerId, String customerName,String createdDate) {
				if(customerName==null)
						customerName="";
				if(createdDate==null)
						createdDate="";
			  System.out.println(customerId+" "+customerName+" "+createdDate);
			  customerCount++;
			  writeCustomerData(customerId,customerName,createdDate);
		}
		
		public void replaceAndPrintCustomer(String customerId, String customerName,String createdDate, String replaceChar) {
			if(customerName.contains("%")) {
				customerName=customerName.replace("%",replaceChar);
			}
			if(customerName.contains("&")) {
				customerName=customerName.replace("&", replaceChar);
			}
			printCustomerData(customerId,customerName,createdDate);
		}
		
		/* Writing the customer data to the finalcustomer.txt*/
		public void writeCustomerData(String customerId, String customerName,String createdDate) {
		
			try {
				fw=new FileWriter("finalcustomer.txt",true);
				pw=new PrintWriter(fw);
				pw.println(customerId+"\t"+customerName+"\t"+createdDate);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			finally {
				pw.close();
			}
			
		}
		public void closingTask() {
				//timer.cancel();
			executorService.shutdown();
				
		}
}
