package com.example.admin.center.dao;

import com.example.admin.center.model.HotelAuth;
import com.example.admin.center.model.HotelAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HotelAuthMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_auth
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    long countByExample(HotelAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_auth
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int deleteByExample(HotelAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_auth
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_auth
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int insert(HotelAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_auth
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int insertSelective(HotelAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_auth
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    List<HotelAuth> selectByExample(HotelAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_auth
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    HotelAuth selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_auth
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int updateByExampleSelective(@Param("record") HotelAuth record, @Param("example") HotelAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_auth
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int updateByExample(@Param("record") HotelAuth record, @Param("example") HotelAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_auth
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int updateByPrimaryKeySelective(HotelAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_auth
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int updateByPrimaryKey(HotelAuth record);
}