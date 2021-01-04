package com.example.user.center.dao;

import com.example.user.center.model.HouseUser;
import com.example.user.center.model.HouseUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HouseUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_user
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    long countByExample(HouseUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_user
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int deleteByExample(HouseUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_user
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_user
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int insert(HouseUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_user
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int insertSelective(HouseUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_user
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    List<HouseUser> selectByExample(HouseUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_user
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    HouseUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_user
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByExampleSelective(@Param("record") HouseUser record, @Param("example") HouseUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_user
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByExample(@Param("record") HouseUser record, @Param("example") HouseUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_user
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByPrimaryKeySelective(HouseUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_user
     *
     * @mbg.generated Thu Dec 31 10:09:49 CST 2020
     */
    int updateByPrimaryKey(HouseUser record);
}