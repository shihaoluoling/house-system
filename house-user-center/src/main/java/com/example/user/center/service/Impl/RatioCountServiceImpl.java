package com.example.user.center.service.Impl;

import com.example.user.center.manual.dao.RatioCountMapper;
import com.example.user.center.manual.model.RatioCount;
import com.example.user.center.service.RatioCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shihao
 * @Title: RatioCountServiceImpl
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@Service
public class RatioCountServiceImpl implements RatioCountService {
    @Autowired
    private RatioCountMapper ratioCountMapper;
    @Override
    public List<RatioCount> count(List<Integer> ids) {
        return ratioCountMapper.count(ids);
    }
}
