package com.example.admin.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GameCenter implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_center.id
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_center.name
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_center.file
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private String file;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_center.form_id
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private Integer formId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_center.create_time
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_center.modify_time
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_center.is_deleted
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table game_center
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_center.id
     *
     * @return the value of game_center.id
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_center.id
     *
     * @param id the value for game_center.id
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_center.name
     *
     * @return the value of game_center.name
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_center.name
     *
     * @param name the value for game_center.name
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_center.file
     *
     * @return the value of game_center.file
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public String getFile() {
        return file;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_center.file
     *
     * @param file the value for game_center.file
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public void setFile(String file) {
        this.file = file == null ? null : file.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_center.form_id
     *
     * @return the value of game_center.form_id
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public Integer getFormId() {
        return formId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_center.form_id
     *
     * @param formId the value for game_center.form_id
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_center.create_time
     *
     * @return the value of game_center.create_time
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_center.create_time
     *
     * @param createTime the value for game_center.create_time
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_center.modify_time
     *
     * @return the value of game_center.modify_time
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_center.modify_time
     *
     * @param modifyTime the value for game_center.modify_time
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_center.is_deleted
     *
     * @return the value of game_center.is_deleted
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_center.is_deleted
     *
     * @param isDeleted the value for game_center.is_deleted
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_center
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", file=").append(file);
        sb.append(", formId=").append(formId);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}