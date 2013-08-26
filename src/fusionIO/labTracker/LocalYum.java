package fusionIO.labTracker;

import java.io.File;
import java.io.IOException;

public class LocalYum 
{
	static boolean vMode = false;
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
		initialize(args);
	}
	
	public static void initialize(String[] args) throws IOException, InterruptedException 
	{
		Runtime rt = Runtime.getRuntime();
		Process ps;
		File path;
		
		if(args.length != 0 && args[0].equalsIgnoreCase("-v"))
    	{
    		System.out.println("Verbose mode");
    		vMode = true;
    	}
		
		path = new File("/etc/pki/rpm-gpg/");
		String cmd0A[] = {"wget", "http://10.40.2.15/downloads/RPM-GPG-KEY-CentOS-6-LocalRepo"};
	    ps = rt.exec(cmd0A,null,path);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd0A,ps);
	    ps.waitFor();
	    
	    String cmd0B[] = {"wget", "http://10.40.2.15/downloads/RPM-GPG-KEY-EPEL-6"};
	    ps = rt.exec(cmd0B,null,path);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd0B,ps);
	    ps.waitFor();
	    
	    String cmd0C[] = {"wget", "http://10.40.2.15/downloads/RPM-GPG-KEY-remi"};
	    ps = rt.exec(cmd0C,null,path);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd0C,ps);
		ps.waitFor();
		
	    path = new File("/etc/yum.repos.d/");
	    String cmd1[] = {"wget", "http://10.40.2.15/downloads/local.repo"};
	    ps = rt.exec(cmd1,null,path);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd1,ps);
	    ps.waitFor();
	    
	    path = new File("/etc/pki/rpm-gpg/");
	    String cmd2[] = {"gpg" ,"--import" ,"RPM-GPG-KEY-*"};
	    ps = rt.exec(cmd2,null,path);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd2,ps);
	    ps.waitFor();
	    
	    String cmd3[] = {"/bin/sh","-c","echo \"80.239.156.215          mirrors.fedoraproject.org\" >> /etc/hosts"};
	    ps = rt.exec(cmd3,null,path);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd3,ps);
	    ps.waitFor();
	    
	    String cmd4[] = {"/bin/sh","-c","echo \"213.129.242.84          mirrors.rpmfusion.org\" >> /etc/hosts"};
	    ps = rt.exec(cmd4,null,path);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd4,ps);
	    ps.waitFor();
	    
	    String cmd[] = {"/bin/sh","-c","echo \"10.40.2.1               https://diavpn.fusionio.com\" >> /etc/hosts"};
	    ps = rt.exec(cmd,null,path);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd,ps);
	    ps.waitFor();
	    
	    updateFiles();
	    LineEditor.removeLineFromFile("/etc/rc.d/rc.local","java -jar /mnt/fioa/fusion/nfs/setup/yum_repo/LocalYum.jar");
	    LineEditor.addLinesToFile("/etc/resolv.conf", "nameserver 8.8.8.8");
	}
	
	public static void updateFiles() throws IOException 
	{
		String path = "/etc/yum.repos.d"; 
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 
		
		for( int i = 0; i < listOfFiles.length; i++)
		{
			LineEditor.editLineFromFile("/etc/yum.repos.d/"+listOfFiles[i].getName(), "mirrorlist", "mirrorlist", "#mirrorlist");
			LineEditor.editLineFromFile("/etc/yum.repos.d/"+listOfFiles[i].getName(), "#baseurl", "#baseurl", "baseurl");
		}
	}
}