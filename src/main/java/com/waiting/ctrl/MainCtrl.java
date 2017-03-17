package com.waiting.ctrl;

import com.waiting.entity.User;
import com.waiting.svc.UserSvc;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by fish on 17/03/2017.
 */
@RestController
public class MainCtrl {
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private UserSvc userSvc;
    @RequestMapping(value = "jaccount/auth", method = RequestMethod.GET)
    public void jaccountAuth(@RequestParam(value = "code", required = false) String code,
                             HttpServletRequest request, HttpServletResponse response) throws IOException, WxErrorException {
        if (code == null) {
            response.setStatus(302);
            URL url = new URL(request.getRequestURL().toString());
            String redirectUrl = url.getProtocol() + "://" + url.getHost() + "/jaccount/auth";
            response.setHeader("Location", wxMpService.oauth2buildAuthorizationUrl(redirectUrl, WxConsts.OAUTH2_SCOPE_BASE, null));
            return;
        }
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        String openid = wxMpOAuth2AccessToken.getOpenId();
        User user = userSvc.findByOpenid(openid);
        if (user != null && user.getStudentid() != null) {
            response.getWriter().write("You have alreadysuccessfully bind the jaccount");
            response.setStatus(200);
            return;
        }
        // todo: bind jaccount
    }
}
