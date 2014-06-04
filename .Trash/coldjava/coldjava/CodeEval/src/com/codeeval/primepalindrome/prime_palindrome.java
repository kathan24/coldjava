package com.codeeval.primepalindrome;

public class prime_palindrome 
{
	public static void main(String[] args) 
	{
		int inputNumber = 1000;
		for (int i=inputNumber; i > 0; i--)
		{
			int number = i;
			int b=0;
			int a;
			while(number > 0)
			{				
				a = number % 10;   
				number = number / 10; 
				b =  b * 10 + a; 	
			}
			if(i == b)
			{
				if(isPrime(b))
				{
					System.out.println(b);
					System.exit(0);
				}
			}
		}
	}
	
	static boolean isPrime(int number)
	{
		for(int i=2; i <number/2;i++)
		{
			if(number%i==0)
			{
				return false;
			}
		}
		return true;
	}
}
