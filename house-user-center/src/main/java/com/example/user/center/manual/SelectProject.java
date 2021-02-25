package com.example.user.center.manual;

import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * @author shihao
 * @Title: SelectProject
 * @ProjectName Second-order-center
 * @Description: 查询项目信息
 * @date Created in
 * @Version: $
 */
public class SelectProject {
    private Integer projectId;//项目id
    private String projectName;//项目名称
    private List<String> developersName;//开发商
    private String exploitName;//开发名称
    private Integer exploitid;//开发id
    private Map<String, List<String>> LandType;//土地和下面的楼盘


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<String> getDevelopersName() {
        return developersName;
    }

    public void setDevelopersName(List<String> developersName) {
        this.developersName = developersName;
    }

    public Map<String, List<String>> getLandType() {
        return LandType;
    }

    public void setLandType(Map<String, List<String>> landType) {
        LandType = landType;
    }

    public String getExploitName() {
        return exploitName;
    }

    public void setExploitName(String exploitName) {
        this.exploitName = exploitName;
    }

    public Integer getExploitid() {
        return exploitid;
    }

    public void setExploitid(Integer exploitid) {
        this.exploitid = exploitid;
    }
}
