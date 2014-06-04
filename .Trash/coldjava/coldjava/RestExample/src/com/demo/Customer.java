package com.demo;


public class Customer
{
	//private Integer customerId;
	private String firstName;
	private String lastName;
	private String zipcode;

	/*public void setcustomerId(Integer customerId)
	{
		this.customerId = customerId;
	}
	
	public Integer getcustomerId()
	{
		return this.customerId;
	}*/
	
	
	
	public void setfirstName(String firstName)
	{
		System.out.println("set firstname"+firstName);
		this.firstName = firstName;
	}
	
	public String getfirstName()
	{
		System.out.println(this.firstName);
		return this.firstName;
	}
	
	
	
	
	public void setlastName(String lastName)
	{
		System.out.println("set lastname"+lastName);
		this.lastName = lastName;
	}
	
	public String getlastName()
	{
		System.out.println(this.lastName);
		return this.lastName;
	}
	
	
	
	
	public void setzipcode(String zipcode)
	{
		System.out.println("set zipcode"+zipcode);
		this.zipcode = zipcode;
	}
	
	public String getzipcode()
	{
		System.out.println(this.zipcode);
		return this.zipcode;
	}
}

