package forcleancleanTomcat;


import com.sun.tools.attach.VirtualMachine;

import java.util.Scanner;

public class Attach {
    public static void main(String[] args) throws Exception{

        String pid = getpid();
        if (!pid.equals("NOT Found")){
            System.out.println("find a Tomcat vm:"+pid);
            String currentPath = forTomcat.Attach.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            currentPath = currentPath.substring(0, currentPath.lastIndexOf("/") + 1);
            //        System.out.println("path:"+currentPath);
            String agentFile = currentPath.substring(1,currentPath.length())+"JavaAgentMemshell_Recoverclear_Agent-1.0-SNAPSHOT-jar-with-dependencies.jar".replace("/","\\");
            System.out.println("Recover_Agent file path :"+agentFile);
            VirtualMachine vm = VirtualMachine.attach(pid);
            vm.loadAgent(agentFile);
            vm.detach();
            System.out.println("Recover_Agent injected");
        }
    }

    private static String getpid() throws Exception{
        Scanner scanner = new Scanner(Runtime.getRuntime().exec("jps").getInputStream());
        while(scanner.hasNextLine())
        {
            String a = scanner.nextLine();
            if (a.contains("Bootstrap"))
            {
                System.out.println(a);
                return a.substring(0,a.length()-10);
            }
        }
        return "NOT Found";
    }
}

