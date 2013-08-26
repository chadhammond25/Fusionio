package fusionIO.labTracker;

import java.io.IOException;

public class WIN_MuninInstall 
{
	public  static void main(String[] args) throws IOException, InterruptedException
	{
		run(args);
	}
	
	public static void run(String[] args) throws IOException, InterruptedException 
	{
		Runtime rt = Runtime.getRuntime();
		Process ps;
		boolean vMode = false;
		
		String ip = GetIP.run();
		String[] newNode = {"["+ip+"]","     address "+ip,"     use_node_name yes"};
		
		if(args[0].equalsIgnoreCase("-v"))
    	{
    		System.out.println("Verbose mode");
    		vMode = true;
    	}
		
		String cmd0[] = {"yum" ,"-y","install", "munin-node"};
	    ps = rt.exec(cmd0);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd0,ps);
	 
	    LineEditor.editLineFromFile("/etc/munin/munin-node.conf", "host *", "host *", "host "+ip);
	    LineEditor.editLineFromFile("/etc/munin/munin-node.conf", "allow ^127\\.0\\.0\\.1$" ,  "allow ^127\\.0\\.0\\.1$", "allow ^10\\.40\\.2\\.15$");
	    LineEditor.editLineFromFile("/etc/munin/munin-node.conf", "#host_name localhost.localdomain", "#", "");
	    
	    
		String cmd1[] = {"sudo","/sbin/iptables","-I","INPUT","-p","tcp","--dport","4949","-m","state","--state","NEW,ESTABLISHED","-j","ACCEPT"};
	    ps = rt.exec(cmd1);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd1,ps);
	    ps.waitFor();
	    
	    String cmd2[] = {"sudo","/sbin/iptables","-I","OUTPUT","-p","tcp","--dport","4949","-m","state","--state","ESTABLISHED","-j","ACCEPT"};
	    ps = rt.exec(cmd2);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd2,ps);
	    ps.waitFor();
	    
	    String cmd3[] = {"sudo","/sbin/service","iptables","save"};
	    ps = rt.exec(cmd3);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd3,ps);
	    ps.waitFor();
	    
	    String cmd6[] = {"service","munin-node","start"};
	    ps = rt.exec(cmd6);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd6,ps);
	    ps.waitFor();
	    
	    LineEditor.addLinesToFile("/etc/munin/host/munin.conf", newNode);
	    
	    String cmd4[] = {"chkconfig","--add","munin-node"};
	    ps = rt.exec(cmd4);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd4,ps);
	    ps.waitFor();
	    
	    String cmd5[] = {"chkconfig","munin-node","on","--level","235"};
	    ps = rt.exec(cmd5);
	    if(vMode == true)
			VerboseMode.verboseOut(cmd5,ps);
	    ps.waitFor();
	    
	    LineEditor.removeLineFromFile("/etc/rc.d/rc.local","java -jar /mnt/fioa/fusion/nfs/setup/munin/MuninInstaller.jar");
	}
}

