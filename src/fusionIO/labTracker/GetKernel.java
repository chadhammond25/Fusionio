package fusionIO.labTracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetKernel 
{
	public static String run() throws IOException 
	{
		String kernel;
		String[] cmd = {"uname", "-r"};
		Runtime rt = Runtime.getRuntime();
	    Process proc = rt.exec(cmd);
	    
	    BufferedReader is = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	    kernel = is.readLine();
		return kernel;
	}
}
