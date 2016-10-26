package cn.cxy.entity;

import java.util.TimerTask;

/**
 * Created by lenovo on 2016/10/26.
 */
public class MyTimer extends TimerTask{

    //TODO 通过TimerTask与Spring整合的方式执行定时任务已过时，不推荐使用了
    public void run() {
        System.out.println("-------timer quartz is running-------");
    }
}
