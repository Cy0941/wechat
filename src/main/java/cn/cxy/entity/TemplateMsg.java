package cn.cxy.entity;

import lombok.Data;

import java.util.Map;

/**
 * Created by lenovo on 2016/11/3.
 */

@Data
public class TemplateMsg {

    private String touser;
    private String template_id;
    private String url;
    private Map<String,Object> data;

}
