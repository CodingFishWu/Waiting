package com.waiting.svc;

import com.waiting.entity.User;
import org.springframework.stereotype.Service;

/**
 * Created by fish on 17/03/2017.
 */
public interface UserSvc {
    User findByOpenid(String openid);
    User createOrGetByOpenid(String openid);
}
