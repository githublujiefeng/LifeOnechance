import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test extends Thread{
    public static void main1(String[] args) {
        int []s = new int[]{5,7,6,1,3,2,8};
        int length =s.length;
        for(int i = 1;i<length;i++){
            for(int j=0 ;j<length-i ;j++){
                if(s[j]>s[j+1]){
                    int temp = s[j];
                    s[j]=s[j+1];
                    s[j+1]=temp;
                }
            }
        }
        for(int i=0;i<length;i++){
            System.out.print(s[i]+" ");
        }
        String a = "1,2,3";
        String[] m= a.split(",");
        Arrays.asList(m);
    }

    public String complexNumberMultiply(String num1, String num2) {
        String[] s1 = num1.split("\\+");
        String[] s2 = num2.split("\\+");
        int a=Integer.parseInt(s1[0]);
        int b=Integer.parseInt(s1[1].replace("i",""));
        int c=Integer.parseInt(s2[0]);
        int d=Integer.parseInt(s2[1].replace("i",""));
        int m = a*c-b*d;
        int l = a*d+b*c;
        List<Integer> list = new ArrayList<Integer>();


        return ""+m+l+"i";
    }
    private volatile int m;
    @Override
    public void run(){
//        try {
//            Thread.sleep(100L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        doTheardLock();

    }

    synchronized void  doAdd(){
        m++;
    }

    synchronized void doTheardLock(){
        synchronized (this) {
            try {
                System.out.println(this.getName()+"hello");
                if (m == 0) {
                    this.wait();
                }
                if (m==1){
                    this.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println(this.getName()+"11111");
    }

    public static void main(String[] args) throws InterruptedException {
        Test thread1 = new Test();
        Test thread2 = new Test();
        thread1.run();
        thread2.notify();
        thread1.join();
        thread2.join();
    }
}
