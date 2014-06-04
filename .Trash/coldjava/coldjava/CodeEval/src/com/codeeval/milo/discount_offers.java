package com.codeeval.milo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Kathan
 *
 */
public class discount_offers
{
	static double bestsum;
    static double[][] ssValue;
    static boolean[] used;
    static int totalCustomer;
    static int totalProduct;
    static boolean transpose;

    
	@SuppressWarnings("resource")
	public static void main(String[] args) throws NumberFormatException, IOException 
	{
		File file = new File(args[0]);
	    BufferedReader br = new BufferedReader(new FileReader(file));
		String strLine; 
		while((strLine = br.readLine()) != null )
		{
			transpose = false;
			String[] line = strLine.toLowerCase().split(";");
			String[] customers = line[0].split(",");
			String[] products = line[1].split(",");
			
			
			int imax = totalCustomer = customers.length;
			int jmax = totalProduct = products.length;
		    
		    
			if (totalCustomer > totalProduct) 
			{
				totalCustomer -= totalProduct;
				totalProduct += totalCustomer;
                totalCustomer = totalProduct-totalCustomer;
                transpose = true;
            }
			
			ssValue = new double[totalProduct][totalCustomer];
			for(int i=0; i< imax; i++)
			{
				for(int j=0; j< jmax;j++)
				{
					 if (transpose)
					 {
						 ssValue[i][j] = calcualteSS(products[j],customers[i]);
					 }
					 else
					 {
						 ssValue[i][j] = calcualteSS(products[i],customers[j]);
					 }
				}
			}
			
			used = new boolean[totalProduct];
			for (int k = 0; k < totalProduct; k++)
			{
				used[k] = false;
			}
	        bestsum = 0.0;
	        best(0, 0.0);
	        System.out.println(bestsum);
		}
		System.exit(0);		
	}
	
	static void best(int i, double sum) 
	{
        if (i == totalCustomer) 
        {
            if (sum > bestsum)
            {
                bestsum = sum;
            }
            return;
        }
        for (int j = 0; j < totalProduct; j++)
        {
            if (!used[j]) 
            {
                used[j] = true;
                if(transpose)
                {
                	best(i+1, sum+ssValue[j][i]);	
                }
                else
                {
                	best(i+1, sum+ssValue[i][j]);
                }
                used[j] = false;
            }
        }
    }
	
	public static float calcualteSS(String productName, String customerName)
	{	
		float ss = (float) 0.0;
		int countVowels=0;
		int countConsonents=0;
		
		int productLetter = 0;
		int customerLetter = 0;
		
		for(int i=0; i <productName.length();i++)
		{
			if(productName.charAt(i) > 96 && productName.charAt(i) < 123)
			{
				productLetter++;
			}
		}
		
		for(int i=0; i <customerName.length();i++)
		{
			if(customerName.charAt(i) > 96 && customerName.charAt(i) < 123)
			{
				customerLetter++;
			}
		}
		
		if(productLetter%2 == 0)
		{
			for(int i=0; i < customerName.length(); i++)
			{
				char c = customerName.charAt(i);
				if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y')
				{
					countVowels++;
				}
			} 
			ss = (float) (countVowels * 1.5);
		}
		else
		{
			for(int i=0; i < customerName.length(); i++)
			{
				char c = customerName.charAt(i);
				
				if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y' || c == ' ')
				{
					
				}
				else
				{
					countConsonents++;
				}
			} 
			ss = (float) (countConsonents);
		}
		
		boolean commonFactorFlag = commanFactor(productLetter, customerLetter);
		
		if(commonFactorFlag)
		{
			ss = (float) (ss * 1.5);
		}
		
		return ss;
	}
	
	public static boolean commanFactor(int p, int q)
	{
		if(q > p)
		{
			for(int i = 2;i < p;i++)
			{
				if((q%i==0)&&(p%i==0))
				{
					return true;
				}
			}
		}
		else if(p > q)
		{
			for(int i = 2;i < q;i++)
			{
				if((p%i==0)&&(q%i==0))
				{
					return true;
				}
			}
		}
		else 
		{
			return true;
		}
		return false;
	}
}