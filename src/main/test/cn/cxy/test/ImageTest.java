package cn.cxy.test;

import cn.cxy.util.MsgUtils;
import cn.cxy.value.WeChatMsgTypes;
import org.junit.Test;

/**
 * Created by lenovo on 2016/11/2.
 */
public class ImageTest {

    @Test
    public void postImage(){
        MsgUtils.handleImage("C:\\Users\\lenovo\\Pictures\\Saved Pictures\\thinkpad.jpg", WeChatMsgTypes.MSG_IAMGE);
    }
}
