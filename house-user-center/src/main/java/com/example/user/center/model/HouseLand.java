package com.example.user.center.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HouseLand implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_land.id
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_land.land_name
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private String landName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_land.project_id
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private Integer projectId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_land.land_address
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private String landAddress;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_land.administrative_name
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private String administrativeName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_land.plate_id
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private Integer plateId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_land.density
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private Double density;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_land.succeed_time
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private LocalDateTime succeedTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_land.transaction_price
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private BigDecimal transactionPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_land.starting_price
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private BigDecimal startingPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_land.transfer
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private String transfer;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_land.remark
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_land.create_date
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_land.modify_date
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private LocalDateTime modifyDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column house_land.is_deleted
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table house_land
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_land.id
     *
     * @return the value of house_land.id
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_land.id
     *
     * @param id the value for house_land.id
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_land.land_name
     *
     * @return the value of house_land.land_name
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public String getLandName() {
        return landName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_land.land_name
     *
     * @param landName the value for house_land.land_name
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public void setLandName(String landName) {
        this.landName = landName == null ? null : landName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_land.project_id
     *
     * @return the value of house_land.project_id
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_land.project_id
     *
     * @param projectId the value for house_land.project_id
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_land.land_address
     *
     * @return the value of house_land.land_address
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public String getLandAddress() {
        return landAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_land.land_address
     *
     * @param landAddress the value for house_land.land_address
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public void setLandAddress(String landAddress) {
        this.landAddress = landAddress == null ? null : landAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_land.administrative_name
     *
     * @return the value of house_land.administrative_name
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public String getAdministrativeName() {
        return administrativeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_land.administrative_name
     *
     * @param administrativeName the value for house_land.administrative_name
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public void setAdministrativeName(String administrativeName) {
        this.administrativeName = administrativeName == null ? null : administrativeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_land.plate_id
     *
     * @return the value of house_land.plate_id
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public Integer getPlateId() {
        return plateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_land.plate_id
     *
     * @param plateId the value for house_land.plate_id
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public void setPlateId(Integer plateId) {
        this.plateId = plateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_land.density
     *
     * @return the value of house_land.density
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public Double getDensity() {
        return density;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_land.density
     *
     * @param density the value for house_land.density
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public void setDensity(Double density) {
        this.density = density;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_land.succeed_time
     *
     * @return the value of house_land.succeed_time
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public LocalDateTime getSucceedTime() {
        return succeedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_land.succeed_time
     *
     * @param succeedTime the value for house_land.succeed_time
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public void setSucceedTime(LocalDateTime succeedTime) {
        this.succeedTime = succeedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_land.transaction_price
     *
     * @return the value of house_land.transaction_price
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public BigDecimal getTransactionPrice() {
        return transactionPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_land.transaction_price
     *
     * @param transactionPrice the value for house_land.transaction_price
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public void setTransactionPrice(BigDecimal transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_land.starting_price
     *
     * @return the value of house_land.starting_price
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_land.starting_price
     *
     * @param startingPrice the value for house_land.starting_price
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_land.transfer
     *
     * @return the value of house_land.transfer
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public String getTransfer() {
        return transfer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_land.transfer
     *
     * @param transfer the value for house_land.transfer
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public void setTransfer(String transfer) {
        this.transfer = transfer == null ? null : transfer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_land.remark
     *
     * @return the value of house_land.remark
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_land.remark
     *
     * @param remark the value for house_land.remark
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_land.create_date
     *
     * @return the value of house_land.create_date
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_land.create_date
     *
     * @param createDate the value for house_land.create_date
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_land.modify_date
     *
     * @return the value of house_land.modify_date
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_land.modify_date
     *
     * @param modifyDate the value for house_land.modify_date
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column house_land.is_deleted
     *
     * @return the value of house_land.is_deleted
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column house_land.is_deleted
     *
     * @param isDeleted the value for house_land.is_deleted
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_land
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", landName=").append(landName);
        sb.append(", projectId=").append(projectId);
        sb.append(", landAddress=").append(landAddress);
        sb.append(", administrativeName=").append(administrativeName);
        sb.append(", plateId=").append(plateId);
        sb.append(", density=").append(density);
        sb.append(", succeedTime=").append(succeedTime);
        sb.append(", transactionPrice=").append(transactionPrice);
        sb.append(", startingPrice=").append(startingPrice);
        sb.append(", transfer=").append(transfer);
        sb.append(", remark=").append(remark);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}