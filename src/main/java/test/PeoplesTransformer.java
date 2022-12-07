package test;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class PeoplesTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if(!className.startsWith( "test/Testpeople"))
        {
            return classfileBuffer;
        }
        try{
            ClassPool cp = ClassPool.getDefault();
            ClassClassPath classPath = new ClassClassPath(classBeingRedefined);  //get current class's classpath

//            System.out.println("transform方法里面的classBeindRedefined的值是:"+classPath);

            cp.insertClassPath(classPath);  //add the classpath to classpool
            CtClass cc = cp.get("test.Testpeople");
            CtMethod m = cc.getDeclaredMethod("peoplesay");
            System.out.println("changing class method to add some code ........");
            m.addLocalVariable("elapsedTime", CtClass.longType);
            m.insertBefore("System.out.println(\"do some exercise\");");
            byte[] byteCode = cc.toBytecode();//after toBytecode() the ctClass has been frozen
            cc.detach();
            return byteCode;  //change
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("falied change");
            return null;
        }
    }
}
