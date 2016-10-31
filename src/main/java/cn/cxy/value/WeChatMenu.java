package cn.cxy.value;

import lombok.Data;

import java.util.List;

/**
 * Created by lenovo on 2016/10/31.
 */
@Data
public class WeChatMenu {

    private Integer id;
    private String type;
    private String name;
    private String key;
    private String url;
    private Integer pid;
    private List<WeChatMenu> sub_button;

}
