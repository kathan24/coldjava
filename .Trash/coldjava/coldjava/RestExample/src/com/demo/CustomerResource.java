package com.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.jersey.api.NotFoundException;


@Path ("customer")
public class CustomerResource 
{
	public static final String DATA_FILE = "C:\\Users\\Kathan\\workspace\\customer-data.txt";

	@POST
	@Consumes ("application/xml")
	public Response addCustomer(InputStream customerData) 
	{
		try 
		{
			Customer customer = buildCustomer(null, customerData);
			long customerId = persist(customer, 0);
			return Response.created(URI.create("/" + customerId)).build();
		}
		catch (Exception e) 
		{
			throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	private long persist(Customer customer, long customerId) throws IOException 
	{
		Properties properties = new Properties();
		properties.load(new FileInputStream(DATA_FILE));
	
		if (customerId == 0) 
		{
			customerId = System.currentTimeMillis();
		}
		properties.setProperty(String.valueOf(customerId),customer.getfirstName() + "," + customer.getlastName() +"," + customer.getzipcode());
		properties.store(new FileOutputStream(DATA_FILE), null);
		return customerId;
	}
	
	private Customer buildCustomer(Customer customer, InputStream customerData)throws ParserConfigurationException, SAXException, IOException 
	{
		if (customer == null) 
		{
			customer = new Customer();
		}
		
		DocumentBuilder documentBuilder =	DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse(customerData);
		document.getDocumentElement().normalize();
		NodeList nodeList = document.getElementsByTagName("customer");
		Node customerRoot = nodeList.item(0);
		
		if (customerRoot.getNodeType() == Node.ELEMENT_NODE) 
		{
			Element element = (Element) customerRoot;
			NodeList childNodes = element.getChildNodes();
			//System.out.println("length of xml is "+childNodes.getLength());
			for (int i = 0; i < childNodes.getLength(); i++) 
			{
				Element childElement = (Element)childNodes.item(i);
				String tagName = childElement.getTagName();
				String textContent = childElement.getTextContent();
				if (tagName.equals("firstname")) 
				{
					System.out.println("firstname called");
					customer.setfirstName(textContent);	
				}
				else if (tagName.equals("lastname")) 
				{
					System.out.println("lastname called");
					customer.setlastName(textContent);
				} 
				else if (tagName.equals("zipcode")) 
				{
					System.out.println("zipcode called");
					customer.setzipcode(textContent);
				}
			}
		} 
		else 
		{
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
			return customer;
	}
	
	@GET
	@Path ("{id}")
	@Produces ("application/xml")
	
	public StreamingOutput retrieveCustomer(@PathParam ("id") String customerId) throws FileNotFoundException, IOException 
	{
		String customerDetails = loadCustomer(customerId);
		System.out.println("customerDetails: " + customerDetails);
		if (customerDetails == null) 
		{
			throw new NotFoundException("<error>No customer with id: " + customerId + "</error>");
		}
		final String[] details = customerDetails.split(",");
		
		/*System.out.println(details[0]);
		System.out.println(details[1]);
		System.out.println(details[2]);*/
		
		return new StreamingOutput() 
		{
				public void write(OutputStream outputStream) throws IOException, WebApplicationException
				{
					PrintWriter out = new PrintWriter(outputStream);
					out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					out.println("<customer>");
					out.println("<firstname>" + details[0] + "</firstname>");
					out.println("<lastname>" + details[1] + "</lastname>");
					out.println("<zipcode>" + details[2] + "</zipcode>");
					out.println("</customer>");	
					out.close();
				}
		};
	}
	
	public String loadCustomer(String customerId) throws FileNotFoundException, IOException
	{
		Properties properties = new Properties();
		properties.load(new FileInputStream(DATA_FILE));
		String info = properties.getProperty(customerId);
		//properties.setProperty(String.valueOf(customerId),customer.getfirstName() + "," + customer.getlastName() +"," + customer.getzipcode());
		//properties.store(new FileOutputStream(DATA_FILE), null);
		System.out.println("INFO is "+info);
		return info;		
	}
	
	
	@PUT
	@Path("{id}")
	@Consumes("application/xml")
	
	public void updateCustomer(@PathParam("id") String customerId,InputStream input) 
	{
		try
		{
			String customerDetails = loadCustomer(customerId);
			if (customerDetails == null) 
			{
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}
			String[] details = customerDetails.split(",");
			Customer customer = new Customer();
			customer.setfirstName(details[0]);
			customer.setlastName(details[1]);
			customer.setzipcode(details[2]);
			buildCustomer(customer, input);
			persist(customer, Long.valueOf(customerId));
		} 
		catch (Exception e) 
		{
			throw new WebApplicationException(e,Response.Status.INTERNAL_SERVER_ERROR);	
		}
	}
	
	
	@DELETE
	@Path("{id}")
	public void deleteCustomer(@PathParam("id") String customerId) 
	{
		try 
		{
			Properties properties = new Properties();
			properties.load(new FileInputStream(DATA_FILE));
			String customerDetails = properties.getProperty(customerId);
			if (customerDetails == null) 
			{
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}
			properties.remove(customerId);
			properties.store(new FileOutputStream(DATA_FILE), null);
		} 
		catch (Exception e) 
		{
			throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
}