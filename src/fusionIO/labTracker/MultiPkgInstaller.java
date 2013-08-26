package fusionIO.labTracker;

import java.io.IOException;

public class MultiPkgInstaller 
{
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		run(args);
	}
	
	public static void run(String[] args) throws IOException, InterruptedException 
	{		
		int option = Integer.valueOf(args[0]);		
		
		for(int i=0; i<args.length;i++)
			if(args[i].contains("-v"))
				args[0] = "-v";
			
		switch(option) 
		{
			case 0:
				MountNFS.run(args);
				LocalYum.initialize(args);
				InitializeLabInfoLog.run(args);	
				MuninInstall.run(args);
				GangliaInstall.run(args);
				BuildVSL.run(args);
				break;
			case 1:
				MountNFS.run(args);
				LocalYum.initialize(args);
				MuninInstall.run(args);
				GangliaInstall.run(args);
				break;
			case 2:
				MountNFS.run(args);
				LocalYum.initialize(args);
				InitializeLabInfoLog.run(args);	
				MuninInstall.run(args);
				GangliaInstall.run(args);	
				break;
			case 3:
				MountNFS.run(args);
				LocalYum.initialize(args);
				InitializeLabInfoLog.run(args);
				break;
			case 4:
				MountNFS.run(args);
				break;
			case 5:
				LocalYum.initialize(args);
				break;
			case 6:
				InitializeLabInfoLog.run(args);
				break;
			case 7:
				MuninInstall.run(args);	
				break;
			case 8:
				GangliaInstall.run(args);
				break;
			case 9:
				BuildVSL.run(args);
				break;
		}
		
		
		LineEditor.removeLineFromFile("/etc/rc.local","java -jar /mnt/fioa/fusion/nfs/setup/MultiPkgInstaller_1.1.jar 1" );		
	}
}
