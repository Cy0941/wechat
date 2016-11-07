package cn.cxy.test;

import cn.cxy.util.JsonUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2016/11/8.
 */
public class JsonTest {

    @Test
    public void formatJson(){
        String[] openIds = {"sadfafsagasfsdgfsaf53dsfa","dsfdagdsgasra2354dgd"};
        String groupId = "76344";
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("openid_list",openIds);
        map.put("to_groupid",groupId);
        String data = JsonUtils.serialize(map);
        System.out.println(data);
    }
}
