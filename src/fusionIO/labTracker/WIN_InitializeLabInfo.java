package fusionIO.labTracker;

import java.io.IOException;

public class WIN_InitializeLabInfo 
{
	public static void main(String[] args) throws IOException, InterruptedException 
    {  
    	run(args);
    }
    
    public static void run(String[] args) throws IOException, InterruptedException 
    {    	
    	Runtime rt = Runtime.getRuntime();
		Process ps;
		
		String cmd0[] = {"yum" ,"-y","install", "crontabs"};
	    ps = rt.exec(cmd0);
	    ps.waitFor();
	    
	    String cmd1[] = {"/bin/sh","-c","(crontab -l; echo \"SHELL=/bin/bash\" ) | crontab -"};
	    ps = rt.exec(cmd1);
	    ps.waitFor();
	    
	    String cmd2[] = {"/bin/sh","-c","(crontab -l; echo \"PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/root/bexport\" ) | crontab -"};
	    ps = rt.exec(cmd2);
	    ps.waitFor();
	    
	    String cmd3[] = {"/bin/sh","-c","(crontab -l; echo \"MAILTO=chammond@fusionio.com\" ) | crontab -"};
	    ps = rt.exec(cmd3);
	    ps.waitFor();
	    
	    String cmd4[] = {"/bin/sh","-c","(crontab -l; echo \"*/15 * * * * java -jar /root/labStats/LabInfoLog.jar \" ) | crontab -"};
	    ps = rt.exec(cmd4);
	    ps.waitFor();
	    
	    String cmd5[] = {"/bin/sh","-c","service crond restart"};
	    ps = rt.exec(cmd5);
	    ps.waitFor();
	    
	    String cmd6[] = {"/bin/sh","-c","chkconfig crond on"};
	    ps = rt.exec(cmd6);
	    ps.waitFor();
	    
	    LineEditor.removeLineFromFile("/etc/rc.d/rc.local","java -jar /root/labStats/LabInfoSetup.jar");
	}
}
