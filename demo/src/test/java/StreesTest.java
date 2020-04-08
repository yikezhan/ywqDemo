import org.apache.commons.lang3.RandomUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 压测
 */
public class StreesTest implements Runnable {
    @Override
    public void run() {
        try {
            String urls = "http://47.102.222.69:8888/demo/seckill/orderBookingTest?userId="+ RandomUtils.nextInt(1000,100000) +"&key=bingo";
            URL url = new URL(urls);
            InputStream is = url.openStream();
//            InputStreamReader isr = new InputStreamReader(is,"utf-8");

//            BufferedReader br = new BufferedReader(isr);
//            String data = br.readLine();//读取数据

//            while (data!=null){//循环读取数据
//                System.out.println(data);//输出数据
//                data = br.readLine();
//            }
//            br.close();
//            isr.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<100000;i++){
            (new StreesTest()).run();
        }
    }
}
