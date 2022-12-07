package forTomcat;

import java.io.*;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws Exception {
//        try {
//            System.out.println(getshellstring());
//            System.out.println(readSource());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Process pc =Runtime.getRuntime().exec("ipconfig");
        /*
        字符流：
         */
        Scanner sc = new Scanner(pc.getInputStream(),"utf-8").useDelimiter("\\a");
        String s = sc.hasNext()?sc.next():"";
        System.out.println(s);

        System.out.println("——————————————————————————");
        /*
        字节流
         */
        BufferedInputStream bufferedInputStream = new BufferedInputStream(pc.getInputStream());
        int b;
        byte [] bytes = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while ((b=bufferedInputStream.read(bytes))!=-1){
            sb.append(new String(bytes,0,b));
        }
        System.out.println(sb);

        String result="";
        Process p = Runtime.getRuntime().exec("ipconfig");
        InputStream in = p.getInputStream();
        DataInputStream dis = new DataInputStream(in);
        String disr = dis.readLine();
        while (disr != null) {
            result = result + disr + "\n";
            disr = dis.readLine();
        }


    }

    public static String getshellstring() throws Exception{
        try(
                FileInputStream fileInputStream = new FileInputStream("source.txt")
        ){
            int b ;
            StringBuilder sb = new StringBuilder();
            while((b=fileInputStream.read())!=-1){
                sb.append(b);
            }
            return new String(sb);
        }
    }
    public static String readSource() {
        StringBuilder source=new StringBuilder();
        InputStream is = Mytransformer.class.getClassLoader().getResourceAsStream("source.txt");
        InputStreamReader isr = new InputStreamReader(is);
        String line=null;
        try {
            BufferedReader br = new BufferedReader(isr);
            while((line=br.readLine()) != null) {
                source.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(source);
    }

}
