package com.example.user.center.service;

import com.example.user.center.manual.model.Tower;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TowerService {
    /**
     *楼号查询
     * @param premisesId 楼盘id
     * @param plateId 板块id
     * @param typeName 楼号名称
     * @param adminId 区域id
     * */
    List<Tower> select(@Param("premisesId")Integer premisesId, @Param("plateId")Integer plateId, @Param("typeName")String typeName, @Param("adminId")Integer adminId);
}
