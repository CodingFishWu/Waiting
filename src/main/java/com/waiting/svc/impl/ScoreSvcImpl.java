package com.waiting.svc.impl;

import com.waiting.dao.ScoreRepo;
import com.waiting.entity.Score;
import com.waiting.svc.ScoreSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by fish on 17/03/2017.
 */
@Service
public class ScoreSvcImpl implements ScoreSvc{
    // can not submit 2 times in 2 hours
    final static private int SUBMIT_TIME_LIMIT=2*60*60;
    @Autowired
    private ScoreRepo scoreRepo;
    @Override
    public Score save(Score score) {
        score.setCreateTime(new Date());

//        scoreRepo.fin
        return scoreRepo.save(score);
    }
}
