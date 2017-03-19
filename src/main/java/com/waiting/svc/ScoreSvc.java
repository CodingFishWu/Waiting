package com.waiting.svc;

import com.waiting.entity.Score;

import java.util.List;

/**
 * Created by fish on 17/03/2017.
 */
public interface ScoreSvc {
    Score save(Score score);
    List<Score> findByMobile(String mobile);
}
