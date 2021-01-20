package com.example.admin.center.dao;

import com.example.admin.center.model.HotelOrders;
import com.example.admin.center.model.HotelOrdersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HotelOrdersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_orders
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    long countByExample(HotelOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_orders
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int deleteByExample(HotelOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_orders
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_orders
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int insert(HotelOrders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_orders
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int insertSelective(HotelOrders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_orders
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    List<HotelOrders> selectByExample(HotelOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_orders
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    HotelOrders selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_orders
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int updateByExampleSelective(@Param("record") HotelOrders record, @Param("example") HotelOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_orders
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int updateByExample(@Param("record") HotelOrders record, @Param("example") HotelOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_orders
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int updateByPrimaryKeySelective(HotelOrders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hotel_orders
     *
     * @mbg.generated Tue Jan 12 11:21:11 CST 2021
     */
    int updateByPrimaryKey(HotelOrders record);
}