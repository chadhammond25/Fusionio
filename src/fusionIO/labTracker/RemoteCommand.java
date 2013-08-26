package fusionIO.labTracker;

import java.io.IOException;

public class RemoteCommand 
{
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		send(args);
	}
	
	public static void send(String[] args) throws IOException, InterruptedException
	{
		Runtime rt = Runtime.getRuntime();
		
		String ip = args[0];
		String pwd = args[1];
		String cmd = args[2];
		
		String cmd0[] = {"yum","-y","install","sshpass"};
		rt.exec(cmd0);
	    
	    String cmd1[] = {"sshpass","-p",pwd,"ssh","root@"+ip,"'"+cmd+"'"};
	    rt.exec(cmd1);
	}
}
