package com.example.user.center.dao;

import com.example.user.center.model.HousePlateLabel;
import com.example.user.center.model.HousePlateLabelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HousePlateLabelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_plate_label
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    long countByExample(HousePlateLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_plate_label
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int deleteByExample(HousePlateLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_plate_label
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_plate_label
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int insert(HousePlateLabel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_plate_label
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int insertSelective(HousePlateLabel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_plate_label
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    List<HousePlateLabel> selectByExample(HousePlateLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_plate_label
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    HousePlateLabel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_plate_label
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByExampleSelective(@Param("record") HousePlateLabel record, @Param("example") HousePlateLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_plate_label
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByExample(@Param("record") HousePlateLabel record, @Param("example") HousePlateLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_plate_label
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByPrimaryKeySelective(HousePlateLabel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_plate_label
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByPrimaryKey(HousePlateLabel record);
}