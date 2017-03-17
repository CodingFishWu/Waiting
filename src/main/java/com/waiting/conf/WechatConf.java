package com.waiting.conf;

import com.waiting.handler.CommonHandler;
import com.waiting.handler.HelpHandler;
import com.waiting.handler.QueryHandler;
import com.waiting.handler.ScoreHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by fish on 17/03/2017.
 */
@Configuration
public class WechatConf {
    @Value("${wechat.appid}")
    private String appid;
    @Value("${wechat.secret}")
    private String secret;
    @Value("${wechat.token}")
    private String token;
    @Value("${wechat.aeskey}")
    private String aeskey;

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId(appid); // 设置微信公众号的appid
        config.setSecret(secret); // 设置微信公众号的app corpSecret
        config.setToken(token); // 设置微信公众号的token
        config.setAesKey(aeskey); // 设置微信公众号的EncodingAESKey
        return config;
    }
    @Bean
    public WxMpService wxMpService(WxMpConfigStorage wxMpConfigStorage) {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
        return wxMpService;
    }

    @Bean
    public WxMpMessageRouter wxMpMessageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        router.rule()
                // the help info
                .msgType(WxConsts.XML_MSG_TEXT)
                .async(false)
                .content("help")
                .handler(helpHandler)
                .end()
                // the query action
                .rule()
                .async(false)
                .rContent("^\\d{11}$")
                .handler(queryHandler)
                .end()
                // the score action
                .rule()
                .msgType(WxConsts.XML_MSG_TEXT)
                .async(false)
                .rContent("^\\d{11}\\s\\d{1,2}$")
                .handler(scoreHandler)
                .end()
                // other rules
                .rule()
                .async(false)
                .handler(helpHandler)
                .end();
        return router;
    }

    @Autowired
    private CommonHandler commonHandler;
    @Autowired
    private HelpHandler helpHandler;
    @Autowired
    private QueryHandler queryHandler;
    @Autowired
    private ScoreHandler scoreHandler;
}
