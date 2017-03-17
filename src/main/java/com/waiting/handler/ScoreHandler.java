package com.waiting.handler;

import com.waiting.entity.Score;
import com.waiting.entity.User;
import com.waiting.svc.ScoreSvc;
import com.waiting.svc.UserSvc;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by fish on 17/03/2017.
 */
@Component
public class ScoreHandler implements WxMpMessageHandler{
    @Autowired
    private UserSvc userSvc;
    @Autowired
    private ScoreSvc scoreSvc;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        String openid = wxMessage.getFromUser();
        User user = userSvc.createOrGetByOpenid(openid);
        // read the information
        Scanner scanner = new Scanner(wxMessage.getContent());
        Score score = new Score();
        score.setMobile(scanner.next());
        score.setScore(scanner.nextInt());
        score.setUserId(user.getId());
        score = scoreSvc.save(score);


        WxMpXmlOutTextMessage m
                = WxMpXmlOutMessage.TEXT().content("记录成功!\n\nremark: 目前正在等待网络中心给予jaccount的介入权限,为保证记录值的可靠性,届时未绑定jaccount的用户将无法记录").fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser()).build();
        return m;
    }
}
