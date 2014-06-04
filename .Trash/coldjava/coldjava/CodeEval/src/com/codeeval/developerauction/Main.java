package com.codeeval.developerauction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Main
{
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException 
	{
		File filename = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
				
		while( (line = br.readLine()) != null)
		{	
			String[] tempLine = line.split(";");
			int rows = Integer.parseInt(tempLine[0]);
			int cols = Integer.parseInt(tempLine[1]);
			String[] temparray = tempLine[2].split(" ");
			
			//System.out.println("rows : "+rows+" cols : "+cols);
			
			String[][] array = new String[rows][cols];
			int q=0, r=0;
			
			for(int p = 0; p<temparray.length; p++)
			{
				array[q][r]=temparray[p];
				r++;
				if( r%cols == 0)
				{
					q++;
					r=0;
				}
				
			}
			/*System.out.println("Array is");
			for(int i = 0; i<rows ;i++)
			{
				for(int j = 0; j<cols ; j++)
				{
					System.out.print(array[i][j]+" ");
				}
				System.out.println();
			}*/
			
			
			int rmin = 0;
			int cmin = 0;
			int i=rmin;
			int j=cmin;
			//System.out.println();
			while(rmin < rows && cmin < cols)
			{
				for(j=cmin; j<cols; j++)
				{
					System.out.print(array[rmin][j]+" ");
				}
				rmin++;
				for(i=rmin; i<rows; i++)
				{
					System.out.print(array[i][cols-1]+" ");	
				}
				cols--;
				if(cols-1>cmin)
				{
					for(j=cols-1; j>=cmin;j--)
					{
						System.out.print(array[rows-1][j]+" ");
					}
					rows--;
				}
				if(rows-1>rmin)
				{
					for(i=rows-1; i>=rmin;i--)
					{
						System.out.print(array[i][cmin]+" ");
					}
					cmin++;
				}
			}
			System.out.println();
		}
	}
}
