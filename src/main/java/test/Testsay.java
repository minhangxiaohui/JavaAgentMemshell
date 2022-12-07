package test;

public class Testsay {
    public static void main(String[] args) throws Exception{
        Testpeople s = new Testpeople();
        while(true){
            s.peoplesay();
            Thread.sleep(5000);
        }
    }
}
