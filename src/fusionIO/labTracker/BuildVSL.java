package fusionIO.labTracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BuildVSL 
{
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		run(args);
	}
	
	public static void run(String[] args) throws IOException, InterruptedException 
	{
		Runtime rt = Runtime.getRuntime();
		String kernel,line;
		ArrayList<String> path = new ArrayList<String>();
		
		BufferedReader readerStd;
        Process ps;
        Boolean vMode = false;
        
        if(args.length != 0 && args[0].equalsIgnoreCase("-v"))
    	{
    		System.out.println("Verbose mode A");
    		vMode = true;
    	}
       
		kernel = GetKernel.run();
		
		String cmd1[] = {"yum", "install", "-y", "kernel-headers", "kernel-devel", "gcc", "rsync", "rpm-build", "make", "lsof", "pciutils"};
		ps = rt.exec(cmd1);
		if(vMode == true)
			VerboseMode.verboseOut(cmd1,ps);
		ps.waitFor();
		
	    String cmd2[] = {"rpmbuild", "--rebuild", "/mnt/fioa/fusion/nfs/IoDrives/iomemory-vsl-3.2.4.1086-1.0.el6.src.rpm","{noformat}"};
	    ps = rt.exec(cmd2);
	    if(vMode == true)
	    	VerboseMode.verboseOut(cmd2,ps);
	    ps.waitFor();
	    
	    String cmd3[] = {"rpmbuild", "--rebuild","--define", "'rpm_kernel_version "+kernel+"'", "/mnt/fioa/fusion/nfs/IoDrives/iomemory-vsl-3.2.4.1086-1.0.el6.src.rpm"};
	    ps = rt.exec(cmd3);
	    ps.waitFor();
	    
	    readerStd = new BufferedReader(new InputStreamReader(ps.getInputStream()));    
	    BufferedReader readerErr = new BufferedReader(new InputStreamReader(ps.getInputStream()));
	    
	    line = null;  
        while ((line = readerStd.readLine()) != null) 
        {  
        	if(vMode = true)
        		System.out.println(line); 
            if(line.contains("Wrote:"))
           		path.add(line.substring(7, line.length()).trim());
        }  
        
        if((line = readerErr.readLine()) != null)
        {
        	if(vMode = true)
        	{
        		System.out.println("------ Std Err -------");
        		System.out.println(line);
        		while ((line = readerErr.readLine()) != null) 
           			System.out.println(line);  
       		}        
        }
	    
        String rpm = path.get(0);
        
        String cmd4[] = {"mkdir", "-p","/mnt/fio"};
	    ps = rt.exec(cmd4);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd4,ps);
	    ps.waitFor();
	    
	    String cmd7[] = {"cp", rpm, "/mnt/fio"};
	    ps = rt.exec(cmd7);
	    if(vMode == true)
	    	VerboseMode.verboseOut(cmd7,ps);
	    ps.waitFor();
	    
	    int start = rpm.indexOf("iomemory");
	    rpm = path.get(0).substring(start, rpm.length());
	    
	    
	    String cmd8[] = {"rpm", "-Uvh", "/mnt/fio/"+rpm };
	    ps = rt.exec(cmd8);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd8,ps);
	    ps.waitFor();
	    
	    String cmd9[] = {"rpm", "-Uvh", "/mnt/fioa/fusion/nfs/IoDrives/install/lib*.rpm"};
	    ps = rt.exec(cmd9);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd9,ps);
	    ps.waitFor();
	    
	    String cmd10[] = {"rpm",  "-Uvh",  "/mnt/fioa/fusion/nfs/IoDrives/install/fio*.rpm"};
	    ps = rt.exec(cmd10);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd10,ps);
	    ps.waitFor();
	    
	    String cmd11[] = {"modprobe", "iomemory-vsl"};
	    ps = rt.exec(cmd11);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd11,ps);
	    ps.waitFor();
	    
	    String cmd12[] = {"chkconfig", "--add" ,"iomemory-vsl"};
	    ps = rt.exec(cmd12);	
	    if(vMode == true)
			VerboseMode.verboseOut(cmd12,ps);
	    ps.waitFor();
	    
	    String cmd13[] = {"fio-update-iodrive","/usr/share/fio/firmware/*.fff"};
	    ps = rt.exec(cmd13);	
	    if(vMode == true)
			VerboseMode.verboseOut(cmd13,ps);
	    ps.waitFor();
	    
	    String cmd14[] = {"fio-status"};
	    ps = rt.exec(cmd14);	
		VerboseMode.verboseOut(cmd14,ps);	
		ps.waitFor();
		
		LineEditor.removeLineFromFile("/etc/rc.d/rc.local","java -jar /mnt/fioa/fusion/nfs/IoDrives/lib/IoDriveInstaller.jar");
	}	
}


