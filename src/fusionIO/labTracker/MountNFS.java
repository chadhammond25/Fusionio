package fusionIO.labTracker;

import java.io.IOException;

public class MountNFS 
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
		
		if(args.length != 0 && args[0].equalsIgnoreCase("-v"))
    	{
    		System.out.println("Verbose mode");
    		vMode = true;
    	}
		
		String cmd1[] = {"mkdir", "-p", "/mnt/fioa/fusion/nfs"};
	    ps = rt.exec(cmd1);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd1,ps);
	    ps.waitFor();
	    
	    String cmd1B[] = {"mkdir" ,"-p","/etc/munin/host/"};
	    ps = rt.exec(cmd1B);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd1B,ps);
	    ps.waitFor();
	    
	    String cmd1C[] = {"mkdir" ,"-p","/root/labStats/"};
	    ps = rt.exec(cmd1C);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd1C,ps);
	    ps.waitFor();
	    
	    String cmd1D[] = {"mkdir" ,"-p","/mnt/fioa/www/html/munin"};
	    ps = rt.exec(cmd1D);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd1D,ps);
	    ps.waitFor();
	    
	    String cmd2[] = {"mount" ,"-o" ,"rw","10.40.2.15:/mnt/fioa/fusion/nfs", "/mnt/fioa/fusion/nfs"};
	    ps = rt.exec(cmd2);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd2,ps);	
	    ps.waitFor();
	    
	    String cmd2B[] = {"mount" ,"-o" ,"rw","10.40.2.15:/root/labStats/", "/root/labStats/"};
	    ps = rt.exec(cmd2B);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd2B,ps);
	    ps.waitFor();
	    
	    String cmd2C[] = {"mount" ,"-o" ,"rw","10.40.2.15:/mnt/fioa/www/html/munin", "/mnt/fioa/www/html/munin"};
	    ps = rt.exec(cmd2C);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd2C,ps);
	    ps.waitFor();
	    
	    String cmd7[] = {"mount" ,"-o" ,"rw" ,"10.40.2.15:/etc/munin/" ,"/etc/munin/host/"};
	    ps = rt.exec(cmd7);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd7,ps);
	    ps.waitFor();
	    
	    LineEditor.addLinesToFile( "/etc/fstab", "10.40.2.15:/mnt/fioa/fusion/nfs  /mnt/fioa/fusion/nfs   nfs  rw,sync,hard,intr  0  0");
		LineEditor.addLinesToFile( "/etc/fstab", "10.40.2.15:/etc/munin/ /etc/munin/host/   nfs  rw,sync,hard,intr  0  0");  
		LineEditor.addLinesToFile( "/etc/fstab", "10.40.2.15:/root/labStats/ /root/labStats/ nsf  rw,sync,hard,intr  0  0"); 
		LineEditor.addLinesToFile( "/etc/fstab", "10.40.2.15:/mnt/fioa/www/html/munin /mnt/fioa/www/html/munin nsf  rw,sync,hard,intr  0  0"); 
		LineEditor.removeLineFromFile("/etc/rc.d/rc.local","java -jar /mnt/fioa/fusion/nfs/setup/nfs/NfsMounter.jar");		
	}
}