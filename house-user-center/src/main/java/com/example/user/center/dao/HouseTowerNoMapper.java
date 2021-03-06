package com.example.user.center.dao;

import com.example.user.center.model.HouseTowerNo;
import com.example.user.center.model.HouseTowerNoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HouseTowerNoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_tower_no
     *
     * @mbg.generated Tue Feb 23 17:44:38 CST 2021
     */
    long countByExample(HouseTowerNoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_tower_no
     *
     * @mbg.generated Tue Feb 23 17:44:38 CST 2021
     */
    int deleteByExample(HouseTowerNoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_tower_no
     *
     * @mbg.generated Tue Feb 23 17:44:38 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_tower_no
     *
     * @mbg.generated Tue Feb 23 17:44:38 CST 2021
     */
    int insert(HouseTowerNo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_tower_no
     *
     * @mbg.generated Tue Feb 23 17:44:38 CST 2021
     */
    int insertSelective(HouseTowerNo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_tower_no
     *
     * @mbg.generated Tue Feb 23 17:44:38 CST 2021
     */
    List<HouseTowerNo> selectByExample(HouseTowerNoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_tower_no
     *
     * @mbg.generated Tue Feb 23 17:44:38 CST 2021
     */
    HouseTowerNo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_tower_no
     *
     * @mbg.generated Tue Feb 23 17:44:38 CST 2021
     */
    int updateByExampleSelective(@Param("record") HouseTowerNo record, @Param("example") HouseTowerNoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_tower_no
     *
     * @mbg.generated Tue Feb 23 17:44:38 CST 2021
     */
    int updateByExample(@Param("record") HouseTowerNo record, @Param("example") HouseTowerNoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_tower_no
     *
     * @mbg.generated Tue Feb 23 17:44:38 CST 2021
     */
    int updateByPrimaryKeySelective(HouseTowerNo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_tower_no
     *
     * @mbg.generated Tue Feb 23 17:44:38 CST 2021
     */
    int updateByPrimaryKey(HouseTowerNo record);
}