/*Input 
3 5 10
2 7 15


Output
1 2 F 4 B F 7 8 F B
1 F 3 F 5 F B F 9 F 11 F 13 FB 15*/

package com.codeeval.fizzbuzz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class fizzbuzz
{
    //TESTING THE CHANGE MADE FOR GIT
	public static void main(String[] args) throws IOException 
	{
		File file = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String currentLine;
		while((currentLine = br.readLine())!= null)
		{
			
			//System.out.println(currentLine);
			String[] line = currentLine.trim().split(" ");
			int A = Integer.parseInt(line[0]);
			int B = Integer.parseInt(line[1]);
			int N = Integer.parseInt(line[2]);
			
			for(int i = 1; i <= N; i++)
			{
				boolean flagA = false;
				boolean flagB = false;
				
				if(i%A == 0)
				{
					flagA = true;
				}
				if(i%B == 0)
				{
					flagB = true;
				}
				
				
				if(flagA == true && flagB == true)
				{
					System.out.print("FB ");
				}
				else if(flagA == true)
				{
					System.out.print("F ");
				}
				else if(flagB == true)
				{
					System.out.print("B ");
				}
				else
				{
					System.out.print(i+" ");
				}
			}
			System.out.println();
		} 
		br.close();
		System.exit(0);
	}	
}