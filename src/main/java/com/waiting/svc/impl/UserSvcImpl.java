package com.waiting.svc.impl;

import com.waiting.dao.UserRepo;
import com.waiting.entity.User;
import com.waiting.svc.UserSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fish on 17/03/2017.
 */
@Service
public class UserSvcImpl implements UserSvc {
    @Autowired
    private UserRepo userRepo;
    @Override
    public User findByOpenid(String openid) {
        return userRepo.findByOpenid(openid);
    }

    @Override
    @Transactional
    public User createOrGetByOpenid(String openid) {
        User user = userRepo.findByOpenid(openid);
        if (user != null) {
            return user;
        }
        else {
            user = new User();
            user.setOpeinid(openid);
            return userRepo.save(user);
        }
    }

}
