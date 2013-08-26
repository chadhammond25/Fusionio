package fusionIO.labTracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VerboseMode 
{
	public static void verboseOut(String[] c, Process p) throws IOException, InterruptedException
	{
		Process ps = p;
		String[] cmd = c;
		
        BufferedReader readerStd = new BufferedReader(new InputStreamReader(ps.getInputStream()));  
        BufferedReader readerErr = new BufferedReader(new InputStreamReader(ps.getInputStream()));  

        System.out.print("Executing command: ");
        for(int i = 0; i < cmd.length; i++)
        {
        	System.out.print(cmd[i]+" ");
        }
        System.out.println();
        
        String line = null;  
        while ((line = readerStd.readLine()) != null) {  
            System.out.println(line);  
        }  
        
        if((line = readerErr.readLine()) != null)
        {
        	System.out.println("------ Std Err -------");
        	System.out.println(line);
        	while ((line = readerErr.readLine()) != null) 
        	{  
        		System.out.println(line);  
        	}
        }
	}

}
