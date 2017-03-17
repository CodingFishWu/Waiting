package com.waiting.dao;

import com.waiting.entity.Score;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by fish on 17/03/2017.
 */
public interface ScoreRepo extends PagingAndSortingRepository<Score, Integer> {
    List<Score> findByMobile(String mobile);
    Score findFirstByUserIdOrderByCreateTimeDesc(Integer userId);
}
