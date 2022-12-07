package forcleancleanTomcat;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class Mytransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)  {
        if(!className.equals("org/apache/catalina/core/ApplicationFilterChain"))
        {
            return classfileBuffer;
        }
        try{
            ClassPool classPool = ClassPool.getDefault();
            ClassClassPath classClassPath = new ClassClassPath(className.getClass());
            classPool.insertClassPath(classClassPath);
            CtClass classs = classPool.get("org.apache.catalina.core.ApplicationFilterChain");
//            CtMethod ctMethod = classs.getDeclaredMethod("internalDoFilter");
            byte [] s = classs.toBytecode();
            System.out.println("ApplicationFilterChain has been recovered");
//            FileOutputStream fos = new FileOutputStream("orgApplicationFilterChain.class");
//            fos.write(s);
            return  s;

        }catch (Exception e){
            System.out.println("faied at get classbytes"+e);
        }
        return  classfileBuffer;
    }
}
