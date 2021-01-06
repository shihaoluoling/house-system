package com.example.user.center.manual.dao;

import com.example.user.center.manual.model.RatioCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shihao
 * @Title: RatioCountMapper
 * @ProjectName Second-order-center
 * @Description: 求和
 * @date Created in
 * @Version: $
 */
@Mapper
public interface RatioCountMapper {
    /**
     *求容积率和出现的次数
     * ids 楼盘id
     * */
    List<RatioCount> count(@Param("ids")List<Integer> ids);
}
