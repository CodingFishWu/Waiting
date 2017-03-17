package com.waiting.ctrl;

import com.waiting.entity.User;
import com.waiting.handler.CommonHandler;
import com.waiting.handler.ScoreHandler;
import com.waiting.svc.UserSvc;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fish on 17/03/2017.
 */
@RestController
@RequestMapping(value = "wechat")
public class WechatCtrl {
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpConfigStorage wxMpConfigStorage;
    @Autowired
    private UserSvc userSvc;

    @Autowired
    private WxMpMessageRouter router;

    @RequestMapping(value = "cb", method = RequestMethod.GET)
    public void validation(@RequestParam(value = "signature") String signature,
                         @RequestParam(value = "nonce") String nonce,
                         @RequestParam(value = "timestamp") String timestamp,
                         @RequestParam(value = "echostr") String echostr,
                         HttpServletRequest request, HttpServletResponse response) throws IOException{
        System.out.println(signature);
        System.out.println(nonce);
        System.out.println(timestamp);

        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            // the signature is not correct
            response.getWriter().println("error");
            return;
        }
        response.getWriter().print(echostr);
        response.setStatus(200);
    }

    @RequestMapping(value = "cb", method = RequestMethod.POST)
    public void callback(@RequestParam(value = "signature") String signature,
                         @RequestParam(value = "nonce") String nonce,
                         @RequestParam(value = "timestamp") String timestamp,
                         @RequestParam(value = "encrypt_type") String encryptType,
                         @RequestParam(value = "msg_signature") String msgSignature,
                           HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            // the signature is not correct
            response.getWriter().println("error");
            return;
        }

        // Only accept encrypted msg
        if ("aes".equals(encryptType)) {
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), wxMpConfigStorage, timestamp, nonce, msgSignature);
            WxMpXmlOutMessage outMessage = router.route(inMessage);
            response.getWriter().write(outMessage.toEncryptedXml(wxMpConfigStorage));
        }

    }
}
