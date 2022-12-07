package forcleanTomcat;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class Agent {
    public  static  void  agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        inst.addTransformer(new Mytransformer(),true);

        Class [] cl = inst.getAllLoadedClasses();
        for (Class c:cl
             ) {
//            if (c.getName().indexOf("org.apache.catalina.core.ApplicationFilterChain")!=-1){
            if (c.getName().startsWith("org.apache.catalina.core.ApplicationFilterChain")){
                inst.retransformClasses(c);
            }

        }

    }
}
