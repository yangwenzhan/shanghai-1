package com.tianqiauto.textile.weaving.caiji.jintianju;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @ClassName Test
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-03 19:20
 * @Version 1.0
 **/
public class Test {

    public static void main(String[] args) {

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();

        Lock lock = new ReentrantLock();


        IntStream.range(0,100).forEach(value->{
            try {
                TimeUnit.SECONDS.sleep(1);
                list1.add(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        IntStream.range(0,100).parallel().forEach(value->{
            try {
                TimeUnit.SECONDS.sleep(1);
                list2.add(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        IntStream.range(0, 100).forEach(i -> {
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
                list3.add(i);
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });

        System.out.println("串行执行的大小：" + list1.size());
        System.out.println("并行执行的大小：" + list2.size());
        System.out.println("加锁并行执行的大小：" + list3.size());


    }

}
