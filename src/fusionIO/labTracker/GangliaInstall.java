package fusionIO.labTracker;

import java.io.IOException;
import java.lang.Runtime;

public class GangliaInstall 
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		run(args);
	}
	
	public static void run(String[] args) throws IOException, InterruptedException 
	{
		Runtime rt = Runtime.getRuntime();
		Process ps;
		boolean vMode = false;
		
		if(args[0].equalsIgnoreCase("-v"))
    	{
    		System.out.println("Verbose mode");
    		vMode = true;
    	}
		
		String cmd0[] = {"yum","-y","install","ganglia-gmond","sshpass"};
	    ps = rt.exec(cmd0);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd0,ps);
	    ps.waitFor();
	    
		String cmd1[] = {"/bin/sh","-c","cp /mnt/fioa/fusion/nfs/setup/ganglia/gmond_default.conf /etc/ganglia/gmond.conf"};
	    ps = rt.exec(cmd1);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd1,ps);
	    ps.waitFor();
	    
		String cmd3[] = {"service","gmond","start"};
	    ps = rt.exec(cmd3);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd3,ps);
	    ps.waitFor();
	    
		String cmd4[] = {"sshpass","-p","fusion123","ssh","roo@10.40.2.15","'service gmetad restart'"};
	    ps = rt.exec(cmd4);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd4,ps);
	    ps.waitFor();
	    
	    String cmd6[] = {"chkconfig","--add","gmond"};
	    ps = rt.exec(cmd6);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd6,ps);
	    ps.waitFor();
	    
	    String cmd7[] = {"chkconfig","gmond","on","--level","235"};
	    ps = rt.exec(cmd7);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd7,ps);
	    ps.waitFor();
	    
	    LineEditor.removeLineFromFile("/etc/rc.d/rc.local","java -jar /mnt/fioa/fusion/nfs/setup/ganglia/GangliaInstall.jar");
	}
}
