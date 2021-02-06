package com.example.user.center.service;

import com.example.user.center.manual.model.Tower;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TowerService {
    /**
     *楼号查询
     * @param projectId 项目id
     * @param plateId 板块id
     * @param typeName 楼号名称
     * @param adminId 区域id
     * */
    List<Tower> select(@Param("projectId")Integer projectId, @Param("plateId")Integer plateId, @Param("typeName")String typeName, @Param("adminId")Integer adminId);
}
