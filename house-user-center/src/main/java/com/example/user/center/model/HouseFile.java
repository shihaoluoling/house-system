package com.example.user.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HouseFile implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_file.id
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_file.file_name
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    private String fileName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_file.user_id
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_file.group_name
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    private String groupName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_file.remote_filename
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    private String remoteFilename;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_file.suffix
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    private String suffix;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_file.create_time
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_file.modify_time
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_file.is_deleted
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table house_file
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_file.id
     *
     * @return the value of house_file.id
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_file.id
     *
     * @param id the value for house_file.id
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_file.file_name
     *
     * @return the value of house_file.file_name
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_file.file_name
     *
     * @param fileName the value for house_file.file_name
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_file.user_id
     *
     * @return the value of house_file.user_id
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_file.user_id
     *
     * @param userId the value for house_file.user_id
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_file.group_name
     *
     * @return the value of house_file.group_name
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_file.group_name
     *
     * @param groupName the value for house_file.group_name
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_file.remote_filename
     *
     * @return the value of house_file.remote_filename
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public String getRemoteFilename() {
        return remoteFilename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_file.remote_filename
     *
     * @param remoteFilename the value for house_file.remote_filename
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public void setRemoteFilename(String remoteFilename) {
        this.remoteFilename = remoteFilename == null ? null : remoteFilename.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_file.suffix
     *
     * @return the value of house_file.suffix
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_file.suffix
     *
     * @param suffix the value for house_file.suffix
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_file.create_time
     *
     * @return the value of house_file.create_time
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_file.create_time
     *
     * @param createTime the value for house_file.create_time
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_file.modify_time
     *
     * @return the value of house_file.modify_time
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_file.modify_time
     *
     * @param modifyTime the value for house_file.modify_time
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_file.is_deleted
     *
     * @return the value of house_file.is_deleted
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_file.is_deleted
     *
     * @param isDeleted the value for house_file.is_deleted
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_file
     *
     * @mbg.generated Tue Feb 02 10:35:45 CST 2021
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fileName=").append(fileName);
        sb.append(", userId=").append(userId);
        sb.append(", groupName=").append(groupName);
        sb.append(", remoteFilename=").append(remoteFilename);
        sb.append(", suffix=").append(suffix);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}