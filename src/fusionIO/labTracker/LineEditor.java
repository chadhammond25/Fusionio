package fusionIO.labTracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LineEditor 
{
	public static void editLineFromFile(String file, String lineToEdit, String lineValueToReplace, String lineReplacementValue) 
	{
		try 
		{
			  File inFile = new File(file);	
			  File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
	
			  BufferedReader br = new BufferedReader(new FileReader(file));
			  PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
	
			  String line = null;
	
			  while ((line = br.readLine()) != null) 
			  {
				    if (!line.trim().contains(lineToEdit)) 
				    {
				      pw.println(line);
				      pw.flush();
				    }
				    
				    if (line.trim().contains(lineToEdit)) 
				    {
				      line = line.replace(lineValueToReplace , lineReplacementValue);
				      pw.println(line);
				      pw.flush();
				    }
			  }
			  pw.close();
			  br.close();
	
			  //Delete the original file
			  if (!inFile.delete()) 
			  {
				    System.out.println("Could not delete file");
				    return;
			  }
	
			  //Rename the new file to the filename the original file had.
			  if (!tempFile.renameTo(inFile))
				  System.out.println("Could not rename file");	
		}
		catch (FileNotFoundException ex) 
		{
			ex.printStackTrace();
		}
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
	}
	
	public static void removeLineFromFile(String file, String lineToRemove) 
	{
		try 
		{
			  File inFile = new File(file);
			  File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
	
			  BufferedReader br = new BufferedReader(new FileReader(file));
			  PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
	
			  String line = null;
	
			  while ((line = br.readLine()) != null) 
			  {
				    if (!line.trim().equals(lineToRemove)) 
				    {
				      pw.println(line);
				      pw.flush();
				    }
			  }
			  pw.close();
			  br.close();
			  
			  //Delete the original file
			  if (!inFile.delete()) 
			  {
				  System.out.println("Could not delete file");
				  return;
			  }
	
			  //Rename the new file to the filename the original file had.
			  if (!tempFile.renameTo(inFile))
				  System.out.println("Could not rename file");	
			  
			  if (!inFile.renameTo(inFile))
				  System.out.println("Could not rename file");
			  
			}
			catch (FileNotFoundException ex) {
			  ex.printStackTrace();
			}
			catch (IOException ex) {
			  ex.printStackTrace();
			}
		}
	
	public static void addLinesToFile(String file, String[] linesToAdd) 
	{
		boolean listed = false;
		
		try 
		{
			  BufferedReader br = new BufferedReader(new FileReader(file));
			  BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
	
			  String line = null;
	
			  while ((line = br.readLine()) != null) 
			  {
				    if(linesToAdd[0].equals(line.trim())) 
					{
				    	listed = true;
					}
			  }
			  if(listed == false)
			  {
			      output.newLine();
			      for(int i=0; i<linesToAdd.length;i++)
			      {
			    	  output.write(linesToAdd[i]);
			    	  output.newLine();
			      }
			  }
			  output.close();
			  br.close();
		}
		catch (FileNotFoundException ex) 
		{
			ex.printStackTrace();
		}
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
	}

	public static void addLinesToFile(String file, String lineToAdd) 
	{
		boolean listed = false;
		
		try 
		{
			  BufferedReader br = new BufferedReader(new FileReader(file));
			  BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
	
			  String line = null;
	
			  while ((line = br.readLine()) != null) 
			  {
				    if(lineToAdd.equals(line.trim())) 
					{
				    	listed = true;
					}
			  }
			  if(listed == false)
			  {
				  output.write(lineToAdd);
		    	  output.newLine();
			  }
			  output.close();
			  br.close();
		}
		catch (FileNotFoundException ex) 
		{
			ex.printStackTrace();
		}
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
	}
	
	public static void deleteFile(String file) 
	{
		File inFile = new File(file);		 
		  
		  //Delete the file
		  if (!inFile.delete()) 
		  {
			  System.out.println("Could not delete file");
			  return;
		  }
		}
}
