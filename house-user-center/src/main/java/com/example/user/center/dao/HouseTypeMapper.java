package com.example.user.center.dao;

import com.example.user.center.model.HouseType;
import com.example.user.center.model.HouseTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HouseTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    long countByExample(HouseTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int deleteByExample(HouseTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int insert(HouseType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int insertSelective(HouseType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    List<HouseType> selectByExample(HouseTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    HouseType selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByExampleSelective(@Param("record") HouseType record, @Param("example") HouseTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByExample(@Param("record") HouseType record, @Param("example") HouseTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByPrimaryKeySelective(HouseType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByPrimaryKey(HouseType record);
}