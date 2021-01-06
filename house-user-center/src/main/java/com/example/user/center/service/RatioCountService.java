package com.example.user.center.service;

import com.example.user.center.manual.model.RatioCount;

import java.util.List;

/**
 * @author shihao
 * @Title: RatioCountService
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public interface RatioCountService {
    /**
     *求容积率和出现的次数
     * */
    List<RatioCount> count(List<Integer> ids);
}
