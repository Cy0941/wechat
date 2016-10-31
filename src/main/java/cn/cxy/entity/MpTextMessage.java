package cn.cxy.entity;

import lombok.Data;

/**
 * Created by lenovo on 2016/10/25.
 */

@Data
public class MpTextMessage {

    /**
     * 接收方账号（收的的OpenID）
     */
    private String toUser;

    /**
     * 开发者微信
     */
    private String fromUser;

    /**
     * 消息创建时间（整型）
     */
    private Integer createTime;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 回复的消息内容
     */
    private String content;
}
