package com.demo;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;


public class MainClass 
{
	public static void main(String args[]) throws HttpException, IOException
	{
		testAddCustomer();
		//testUpdateCustomer();
		//testDeleteCustomer();
	}
	
	private static void testAddCustomer() throws IOException, HttpException 
	{
		final String addCustomerXML = "<customer>" + "<firstname>aaa</firstname>" + "<lastname>sss</lastname>" + "<zipcode>99999</zipcode>" + "</customer>";
		
		PostMethod postMethod = new PostMethod("http://localhost:8080/RestExample/customer");
		RequestEntity entity = new InputStreamRequestEntity(new ByteArrayInputStream(addCustomerXML.getBytes()),"application/xml");
		postMethod.setRequestEntity(entity);
		HttpClient client = new HttpClient();
		try 
		{
			int result = client.executeMethod(postMethod);
			System.out.println("Response status code: " + result);
			System.out.println("Response headers:");
			Header[] headers = postMethod.getResponseHeaders();
			for (int i = 0; i < headers.length; i++) 
			{
				System.out.print(headers[i].toString());
			}	
		}
		finally 
		{
			postMethod.releaseConnection();
		}
	}
	
	
	
	private static void testUpdateCustomer() throws IOException, HttpException 
	{
		final String addCustomerXML = "<customer>" + "<zipcode>00000</zipcode>" + "</customer>";
		PutMethod putMethod = new PutMethod("http://localhost:8080/RestExample/customer/1312321606546");
		RequestEntity entity = new InputStreamRequestEntity(new ByteArrayInputStream(addCustomerXML.getBytes()),"application/xml");
		putMethod.setRequestEntity(entity);
		HttpClient client = new HttpClient();
		try 
		{
			int result = client.executeMethod(putMethod);
			System.out.println("Response status code: " + result);
			System.out.println("Response headers:");
			Header[] headers = putMethod.getResponseHeaders();
			for (int i = 0; i < headers.length; i++) 
			{
				System.out.print(headers[i].toString());
			}
		} 
		finally 
		{
			putMethod.releaseConnection();
		}
	}
	
	
	
	private static void testDeleteCustomer() throws HttpException, IOException 
	{
		DeleteMethod deleteMethod = new DeleteMethod("http://localhost:8080/RestExample/customer/1311890427565");
		HttpClient client = new HttpClient();
		try
		{
			int result = client.executeMethod(deleteMethod);	
			System.out.println("Response status code: " + result);
			System.out.println("Response headers:");
			Header[] headers = deleteMethod.getResponseHeaders();
			for (int i = 0; i < headers.length; i++) 
			{	
				System.out.print(headers[i].toString());
			}
		} 
		finally 
		{	
			deleteMethod.releaseConnection();
		}
	}
}
