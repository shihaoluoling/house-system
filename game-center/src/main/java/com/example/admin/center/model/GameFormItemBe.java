package com.example.admin.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GameFormItemBe implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_form_item_be.id
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_form_item_be.form_id
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private Integer formId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_form_item_be.form_item_id
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private Integer formItemId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_form_item_be.sort
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private Integer sort;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_form_item_be.create_time
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_form_item_be.modify_time
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_form_item_be.is_deleted
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table game_form_item_be
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_form_item_be.id
     *
     * @return the value of game_form_item_be.id
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_form_item_be.id
     *
     * @param id the value for game_form_item_be.id
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_form_item_be.form_id
     *
     * @return the value of game_form_item_be.form_id
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public Integer getFormId() {
        return formId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_form_item_be.form_id
     *
     * @param formId the value for game_form_item_be.form_id
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_form_item_be.form_item_id
     *
     * @return the value of game_form_item_be.form_item_id
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public Integer getFormItemId() {
        return formItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_form_item_be.form_item_id
     *
     * @param formItemId the value for game_form_item_be.form_item_id
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public void setFormItemId(Integer formItemId) {
        this.formItemId = formItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_form_item_be.sort
     *
     * @return the value of game_form_item_be.sort
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_form_item_be.sort
     *
     * @param sort the value for game_form_item_be.sort
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_form_item_be.create_time
     *
     * @return the value of game_form_item_be.create_time
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_form_item_be.create_time
     *
     * @param createTime the value for game_form_item_be.create_time
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_form_item_be.modify_time
     *
     * @return the value of game_form_item_be.modify_time
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_form_item_be.modify_time
     *
     * @param modifyTime the value for game_form_item_be.modify_time
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_form_item_be.is_deleted
     *
     * @return the value of game_form_item_be.is_deleted
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_form_item_be.is_deleted
     *
     * @param isDeleted the value for game_form_item_be.is_deleted
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_form_item_be
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
        sb.append(", formId=").append(formId);
        sb.append(", formItemId=").append(formItemId);
        sb.append(", sort=").append(sort);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}