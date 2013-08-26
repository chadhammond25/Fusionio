package fusionIO.labTracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class WebStatsUpdater 
{
	static Runtime rt = Runtime.getRuntime();
	static String cmd,tmp;
	static int selection;
	static String path = "/root/labStats/logs"; 
	static String file;
	static File tmpFile, folder = new File(path);
	static File[] listOfFiles = folder.listFiles(); 
	static Scanner input = new Scanner(System.in);
	static FileReader reader;
	static BufferedReader buffer; 
	static String fileContent;
	
	public static void main(String[] args) throws InterruptedException, IOException 
    {      	
		clearOldFileData();
		updateFiles(); // only run WebStatsUpdater on pxe server  	
    }  
	
	public static boolean removeOldFile(File file)
	{
		int x = 1;
		long diff = new Date().getTime() - file.lastModified();

		if (diff > x * 24 * 60 * 60 * 1000) 
		{
	       file.delete();
	       return true;
		}
		return false;
	}
	
	public static void updateFiles() throws IOException 
	{
		File file = new File("/mnt/fioa/www/html/labInfo");
		BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
		for( int i = 0; i < listOfFiles.length; i++)
		{
			tmpFile = new File("/root/labStats/logs/"+listOfFiles[i].getName());
			if(!removeOldFile(tmpFile))
			{	
				try 
				{
					reader = new FileReader(tmpFile);
				} catch (FileNotFoundException e) {
					System.out.println("Error reading file. Please try again.");
				}
				buffer = new BufferedReader(reader); 
			
				out.newLine(); 
				while ((fileContent = buffer.readLine()) != null) 
				{
					out.write(fileContent);
					out.newLine();
				}
				out.newLine(); 
				out.write("**********************************************************************************************");
				out.newLine();
			}
		}
		out.close();
	}
	
	public static void clearOldFileData() throws IOException
	{
		File file = new File("/mnt/fioa/www/html/labInfo");
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		out.write("");
		out.close();
	}
}
