package concurrent;/**
 * Created by Easong on 2017/11/14.
 */

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Zookeeper实现分布式锁测试
 *
 * @author Easong
 * @create 2017-11-14 12:40
 **/
public class Recipes_NoLock {

    static String lock_path = "/curator_recipes_lock_path";
    /*static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.17.128:2181").retryPolicy(
            new ExponentialBackoffRetry(1000, 3)
    ).build();*/


    public static void main(String args[]){

        /*client.start();
        final InterProcessMutex lock = new InterProcessMutex(client, lock_path);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        lock.acquire();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    String orderNo = sdf.format(new Date());
                    System.err.println("生成的订单号是：" + orderNo);
                    try {
                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }*/
    }

    @Test
    public void testOrderNumCreated() {
        ///final CountDownLatch down = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    String orderNo = sdf.format(new Date());
                    System.err.println("生成的订单号是：" + orderNo);
                }
            }).start();
        }
    }
}
