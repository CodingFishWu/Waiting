package com.waiting.dao;

import com.waiting.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by fish on 17/03/2017.
 */
public interface UserRepo extends PagingAndSortingRepository<User, Integer> {
    User findByOpenid(String openid);
}
