package com.waiting.svc.impl;

import com.waiting.dao.ScoreRepo;
import com.waiting.dao.UserRepo;
import com.waiting.entity.Score;
import com.waiting.entity.User;
import com.waiting.svc.ScoreSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by fish on 17/03/2017.
 */
@Service
public class ScoreSvcImpl implements ScoreSvc{
    // can not submit 2 times in 2 hours
    final static private int SUBMIT_TIME_LIMIT=2*60*60;
    @Autowired
    private ScoreRepo scoreRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    @Transactional
    public Score save(Score score) {
        score.setCreateTime(new Date());
        // add submit time to the specific user
        User user = userRepo.findOne(score.getUserId());
        user.setLastSubmit(new Date());
        userRepo.save(user);

        return scoreRepo.save(score);
    }

    @Override
    public List<Score> findByMobile(String mobile) {
        return scoreRepo.findByMobile(mobile);
    }
}
