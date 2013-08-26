package fusionIO.labTracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class GetLabData
{
	static String model;
	static String sn;
	static String kernel;
	static String OS;
	static String ioDrive;
	static String ip;
	static String line = "";
	static String user;
	static String[] cmd;
	static int index = 0;
	static Runtime rt = Runtime.getRuntime();
	static ArrayList<String> modelInfo = new ArrayList<String>();
	static ArrayList<String> snInfo = new ArrayList<String>();
	static ArrayList<String> ioDrives = new ArrayList<String>();
	static ArrayList<String> ips = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException, InterruptedException 
    {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    	Date today = Calendar.getInstance().getTime();        
    	String reportDate = df.format(today);
		run(reportDate);
    }

	public static void run(String DATE) throws IOException, InterruptedException 
    {
		modelInfo.clear();
		ioDrives.clear();
		ips.clear();
		
    	getModel();
    	getSN();
        getKernel();
        getOS();
        getIoDrives();
        getIP();
        getUser();
        logData(DATE);        
    }	
	
    public static void getModel() throws IOException, InterruptedException 
    {    	
    	index = 0;
	    String[] cmd = {"/bin/sh","-c","dmidecode | egrep -i \"product name\"" };
	    Process proc = rt.exec(cmd);
			proc.waitFor();
			
	    BufferedReader is = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	    while ((line = is.readLine()) != null) 
	    {	     
	        if(index == 0)
	        	modelInfo.add(line);
	        else if (!modelInfo.contains(line) && !line.contains("Not Specified") && !line.contains("Number:    "))
	        	modelInfo.add(line);	
	        index++;
	    }
    }
    
    public static void getSN() throws IOException, InterruptedException 
    {    	
    	index = 0;
	    String[] cmd = {"/bin/sh","-c","dmidecode | egrep -i \"serial number\"" };
	    Process proc = rt.exec(cmd);
			proc.waitFor();
			
	    BufferedReader is = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	    while ((line = is.readLine()) != null) 
	    {	     
	        if(index == 0)
	        	snInfo.add(line);
	        else if (!snInfo.contains(line) && !line.contains("Not Specified") && !line.contains("To Be Filled By O.E.M.") && !line.contains("Number:     "))
	        	snInfo.add(line);	
	        index++;
	    }
    }
    
    public static void getKernel() throws IOException, InterruptedException
    {
		kernel = GetKernel.run();
    }
    
   public static void getOS() throws IOException, InterruptedException
   {
	   String[] cmd = {"/bin/sh","-c","cat /etc/*-release" };
	   Process proc = rt.exec(cmd);
	   proc.waitFor();
	   
	   BufferedReader is = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	   line = is.readLine();
	   OS = line.trim();
   }
   
   public static void getIoDrives() throws IOException, InterruptedException
   {
	   String[] cmd = {"/bin/sh","-c","fio-status" };
	    Process proc = rt.exec(cmd);
	    proc.waitFor();
	 
	    BufferedReader is = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	    while ((line = is.readLine()) != null) 
	    {
	    	ioDrives.add(line);	       
	    }
   }

   public static void getIP() throws IOException, InterruptedException
   {
	    String[] cmd = {"/bin/sh","-c","ifconfig eth0" };
	    Process proc = rt.exec(cmd);
	    proc.waitFor();
	    
	    BufferedReader is = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	    while ((line = is.readLine()) != null) 
	    {	       
	        if(line.contains("inet addr:"))
	        {
	        	ips.add(line);
	        }
	    }
   }
   
   public static void getUser() throws IOException, InterruptedException
   {
	    String[] cmd = {"/bin/sh","-c","echo $USER" };
	    Process proc = rt.exec(cmd);
	    proc.waitFor();
	    
	    BufferedReader is = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	    user = is.readLine();
   }
   
   public static void logData(String D) throws IOException
   {
	   File file = new File("/root/labStats/logs/"+modelInfo.get(0).substring(14, modelInfo.get(0).length()).trim().replaceAll("\\s","_")+"_"+ips.get(0).substring(20, ips.get(0).indexOf("Bcast")));
	   BufferedWriter out= new BufferedWriter(new FileWriter(file));
	   out.newLine(); 
       out.write("Last updated on: "+D);
       out.newLine();  
       
       out.write("Model: ");
       out.newLine();
       for(int i = 0; i < modelInfo.size();i++)
       {
    	   out.write("       "+modelInfo.get(i).substring(14, modelInfo.get(0).length()));
    	   out.newLine();
       }   
       
       out.write("Serial #(s): ");
       out.newLine();
       for(int i = 0; i < snInfo.size();i++)
       {
    	   out.write(snInfo.get(i));
    	   out.newLine();
       }  
       
       out.write("User:");
       out.newLine();
       out.write("        "+user);
       out.newLine();
       
       out.write("IP Address:");
       out.newLine();
       for(int i = 0; i < ips.size();i++)
       {
    	   out.write("        "+ips.get(i).substring(20, ips.get(i).indexOf("Bcast")).trim());
    	   out.newLine();
       }
          
       out.write("Kernel: ");
       out.newLine();
       out.write("        "+kernel);
       out.newLine();
       
       out.write("IO Drives: ");
       out.newLine();
       if(ioDrives.size() == 0)
       {
		   out.write("        None Attached");
		   out.newLine();
       }
	   else
	       for(int i = 0; i < ioDrives.size();i++)
	       {   	   
	    	   out.write(ioDrives.get(i));
	    	   out.newLine();
	       }   
       
       out.write("OS: ");
       out.newLine();
       out.write("        "+OS);
       out.newLine();
       out.close();
   }
}
