/*
 FACEBOOK QUESTION
 in a matrix of nxn with black and white tile, there are two rules
 1. in a 3x3 matrix check if there are more number of black tiles the center tile should be changed to black and vise versa.
 2. the tiles on the sides/periphery will remain the same
 
 read the input from System.in and print output in System.out. there are iteration so you need to provide the output at the end of last iteration
 example:

input:
2
3 1
bbw
www
bww
4 3
bbbb
wbwb
wbbb
wbww


output:
Board 1
bbw
www
bww
Board 2
bbbb
wbbb
wwbb
wbww
*/

package gameOfLife;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution 
{
	public static void main(String[] args) throws IOException 
	{
		BufferedReader userInputBr = new BufferedReader(new InputStreamReader(System.in));
		File inputFile = new File(userInputBr.readLine());
		userInputBr.close();
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		int testCases = Integer.parseInt(br.readLine());
		for(int i=0;i<testCases; i++)
		{
			String gridSizeIteration = br.readLine();
			String[] temp = gridSizeIteration.split(" ");
			int gridSize = Integer.parseInt(temp[0]);
			int iteration = Integer.parseInt(temp[1]);
			
			int[][] matrix= new int[gridSize][gridSize];
			int[][] solved= new int[gridSize][gridSize];
			for(int j=0; j <gridSize; j++)
			{
				String line = br.readLine();
				for(int k=0;k<gridSize; k++)
				{
					if(line.charAt(k) == 'w')
					{
						matrix[j][k]=1;
					}
					else if(line.charAt(k) == 'b')
					{
						matrix[j][k]=0;
					}
				}
			}
			for(int iterate=0; iterate<iteration; iterate++)
			{
				int[] counter = new int[2];
				for(int j=0; j <(gridSize); j++)
				{
					for(int k=0;k<(gridSize); k++)
					{
						counter[0]=0;
						counter[1]=0;
						if(j == 0 || k==0 || j== (gridSize-1) || k==( gridSize-1))
						{
							solved[j][k]=matrix[j][k];
							continue;
						}
						
						counter[matrix[j-1][k-1]]++;
						counter[matrix[j-1][k]]++;
						counter[matrix[j-1][k+1]]++;
						counter[matrix[j][k-1]]++;
						counter[matrix[j][k]]++;
						counter[matrix[j][k+1]]++;
						counter[matrix[j+1][k-1]]++;
						counter[matrix[j+1][k]]++;
						counter[matrix[j+1][k+1]]++;
						if(counter[0]>counter[1])
						{
							solved[j][k]=0;
						}
						else if(counter[0]<counter[1])
						{
							solved[j][k]=1;
						}
						else
						{
							solved[j][k]=matrix[j][k];
						}
					}
				}
				for(int j=0; j <(gridSize); j++)
				{
					for(int k=0;k<(gridSize); k++)
					{
						matrix[j][k]=solved[j][k];
					}
				}
			}
			System.out.println("Board "+(i+1));
			for(int j=0; j <gridSize; j++)
			{
				for(int k=0;k<gridSize; k++)
				{
					if(solved[j][k]==0)
					{
						System.out.print("b");
					}
					else if(solved[j][k]==1)
					{
						System.out.print("w");
					}
				}
				System.out.println();
			}
		}
		br.close();
	}
}
	

