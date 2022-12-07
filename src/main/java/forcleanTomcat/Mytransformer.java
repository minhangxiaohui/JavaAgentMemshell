package forcleanTomcat;

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
            int flag = 0;
            FileOutputStream fos = new FileOutputStream("classfilebuffer.class");
            fos.write(classfileBuffer);
            return classfileBuffer;

        }catch (Exception e){
            System.out.println("faied at get classbytes"+e);
        }
        return  classfileBuffer;
    }
}
