package com.example.user.center.dao;

import com.example.user.center.model.HousePremisesPicture;
import com.example.user.center.model.HousePremisesPictureExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HousePremisesPictureMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_premises_picture
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    long countByExample(HousePremisesPictureExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_premises_picture
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int deleteByExample(HousePremisesPictureExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_premises_picture
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_premises_picture
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int insert(HousePremisesPicture record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_premises_picture
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int insertSelective(HousePremisesPicture record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_premises_picture
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    List<HousePremisesPicture> selectByExample(HousePremisesPictureExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_premises_picture
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    HousePremisesPicture selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_premises_picture
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByExampleSelective(@Param("record") HousePremisesPicture record, @Param("example") HousePremisesPictureExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_premises_picture
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByExample(@Param("record") HousePremisesPicture record, @Param("example") HousePremisesPictureExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_premises_picture
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByPrimaryKeySelective(HousePremisesPicture record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_premises_picture
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByPrimaryKey(HousePremisesPicture record);
}