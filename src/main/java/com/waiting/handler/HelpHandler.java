package com.waiting.handler;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by fish on 17/03/2017.
 */
@Component
public class HelpHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        String help = "1, 查询: 直接输入手机号 (例如输入: '13123456789')\n\n" +
                "2, 记录: 直接输入手机号和最高两位数字 (例如输入: '13123456789 5' 表示这个手机号让我等了5分钟)\n\n" +
                "3, 其他帮助请直接找Fish(codingfishwu@gmail.com)";
        WxMpXmlOutTextMessage m
                = WxMpXmlOutMessage.TEXT().content(help).fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser()).build();
        return m;
    }
}
