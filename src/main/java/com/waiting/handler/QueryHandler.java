package com.waiting.handler;

import com.waiting.dao.ScoreRepo;
import com.waiting.dao.UserRepo;
import com.waiting.entity.Score;
import com.waiting.entity.User;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by fish on 17/03/2017.
 */
@Component
public class QueryHandler implements WxMpMessageHandler {
    @Autowired
    private ScoreRepo scoreRepo;
    @Autowired
    private UserRepo userRepo;
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        StringBuilder result = new StringBuilder();
        String mobile = wxMessage.getContent();
        List<Score> scores = scoreRepo.findByMobile(mobile);
        User user = userRepo.findByOpenid(wxMessage.getFromUser());

        // calculate
        double avg = 0;
        double myAvg = 0;
        int count = 0;
        int myCount = 0;
        for (Score score : scores) {
            count += score.getScore();
            if (score.getUserId().equals(user.getId())) {
                myCount += score.getScore();
            }
        }
        avg = ((double)count) / scores.size();
        myAvg = ((double)myCount) / scores.size();
        result.append("所查询的号码已被").append(scores.size()).append("个人登记过\n\n")
                .append("平均等待时间为:").append(avg).append("分钟\n\n")
                .append("其中你自己记录的平均值为:").append(myAvg).append("分钟");

        WxMpXmlOutTextMessage m
                = WxMpXmlOutMessage.TEXT().content(result.toString()).fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser()).build();
        return m;
    }
}
