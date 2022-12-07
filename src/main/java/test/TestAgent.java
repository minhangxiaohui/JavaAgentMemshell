package test;

import java.lang.instrument.Instrumentation;

public class TestAgent {
        public static  void agentmain(String agentArgs, Instrumentation inst )throws Exception{
            inst.addTransformer(new PeoplesTransformer(), true);
            inst.retransformClasses(Testpeople.class);
            System.out.println("retransform success");
        }
}
