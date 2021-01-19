package com.example.admin.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GameAuth implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_auth.id
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_auth.user_id
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_auth.auth_type
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    private String authType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_auth.auth_key
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    private String authKey;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_auth.encode_type
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    private String encodeType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_auth.password
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_auth.auth_status
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    private Byte authStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_auth.create_date
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    private LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_auth.modify_date
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    private LocalDateTime modifyDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_auth.is_deleted
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table game_auth
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_auth.id
     *
     * @return the value of game_auth.id
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_auth.id
     *
     * @param id the value for game_auth.id
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_auth.user_id
     *
     * @return the value of game_auth.user_id
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_auth.user_id
     *
     * @param userId the value for game_auth.user_id
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_auth.auth_type
     *
     * @return the value of game_auth.auth_type
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public String getAuthType() {
        return authType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_auth.auth_type
     *
     * @param authType the value for game_auth.auth_type
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public void setAuthType(String authType) {
        this.authType = authType == null ? null : authType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_auth.auth_key
     *
     * @return the value of game_auth.auth_key
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public String getAuthKey() {
        return authKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_auth.auth_key
     *
     * @param authKey the value for game_auth.auth_key
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public void setAuthKey(String authKey) {
        this.authKey = authKey == null ? null : authKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_auth.encode_type
     *
     * @return the value of game_auth.encode_type
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public String getEncodeType() {
        return encodeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_auth.encode_type
     *
     * @param encodeType the value for game_auth.encode_type
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public void setEncodeType(String encodeType) {
        this.encodeType = encodeType == null ? null : encodeType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_auth.password
     *
     * @return the value of game_auth.password
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_auth.password
     *
     * @param password the value for game_auth.password
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_auth.auth_status
     *
     * @return the value of game_auth.auth_status
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public Byte getAuthStatus() {
        return authStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_auth.auth_status
     *
     * @param authStatus the value for game_auth.auth_status
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public void setAuthStatus(Byte authStatus) {
        this.authStatus = authStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_auth.create_date
     *
     * @return the value of game_auth.create_date
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_auth.create_date
     *
     * @param createDate the value for game_auth.create_date
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_auth.modify_date
     *
     * @return the value of game_auth.modify_date
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_auth.modify_date
     *
     * @param modifyDate the value for game_auth.modify_date
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_auth.is_deleted
     *
     * @return the value of game_auth.is_deleted
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_auth.is_deleted
     *
     * @param isDeleted the value for game_auth.is_deleted
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_auth
     *
     * @mbg.generated Tue Jan 19 10:02:05 CST 2021
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", authType=").append(authType);
        sb.append(", authKey=").append(authKey);
        sb.append(", encodeType=").append(encodeType);
        sb.append(", password=").append(password);
        sb.append(", authStatus=").append(authStatus);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}