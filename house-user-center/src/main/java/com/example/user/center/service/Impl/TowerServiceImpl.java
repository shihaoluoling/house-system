package com.example.user.center.service.Impl;

import com.example.user.center.manual.dao.TowerMapper;
import com.example.user.center.manual.model.Tower;
import com.example.user.center.service.TowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shihao
 * @Title: TowerServiceImpl
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@Service
public class TowerServiceImpl implements TowerService {
    @Autowired
    private TowerMapper towerMapper;
    @Override
    public List<Tower> select(Integer projectId, Integer plateId, String typeName, Integer adminId) {
        return towerMapper.select(projectId,plateId,typeName,adminId);
    }
}
