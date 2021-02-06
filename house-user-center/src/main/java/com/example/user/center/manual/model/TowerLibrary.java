package com.example.user.center.manual.model;

import java.time.LocalDateTime;

/**
 * @author shihao
 * @Title: TowerLibrary
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class TowerLibrary {
    private Integer id;
    private String libraryName;
    private String state;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Byte isDeleted;
    private Integer towerLibrarys;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getTowerLibrarys() {
        return towerLibrarys;
    }

    public void setTowerLibrarys(Integer towerLibrarys) {
        this.towerLibrarys = towerLibrarys;
    }
}
