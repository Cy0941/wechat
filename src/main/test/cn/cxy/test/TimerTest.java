package cn.cxy.test;

import cn.cxy.entity.MyTimer;

import java.util.Timer;

/**
 * Created by lenovo on 2016/10/26.
 */
public class TimerTest {

    public static void main(String[] args){
        test(2);
    }

    public static void test(int s){
        Timer timer = new Timer();
        timer.schedule(new MyTimer(),1,s * 1000);
    }
}
