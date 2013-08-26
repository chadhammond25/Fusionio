package fusionIO.labTracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GetIP 
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		run();		
	}
	public static String run() throws IOException, InterruptedException
	{
		String[] cmd = {"ifconfig"};
		String line;
		String ip = null;
		Runtime rt = Runtime.getRuntime();
	    Process proc = rt.exec(cmd);
	    ArrayList<String> ips = new ArrayList<String>();
	    BufferedReader is = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	    
	    while ((line = is.readLine()) != null) 
	    {	       
	        if(line.contains("inet ") && !line.contains("127.0.0.1"))
	        {
	        	ips.add(line);
	        }
	    }
	    ip = ips.get(0).substring(ips.get(0).indexOf(":")+1, ips.get(0).indexOf("B")-2);
		return ip;
	}
}
