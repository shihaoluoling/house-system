package com.example.admin.center.dao;

import com.example.admin.center.model.GameUserBook;
import com.example.admin.center.model.GameUserBookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GameUserBookMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user_book
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    long countByExample(GameUserBookExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user_book
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int deleteByExample(GameUserBookExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user_book
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user_book
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int insert(GameUserBook record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user_book
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int insertSelective(GameUserBook record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user_book
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    List<GameUserBook> selectByExampleWithBLOBs(GameUserBookExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user_book
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    List<GameUserBook> selectByExample(GameUserBookExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user_book
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    GameUserBook selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user_book
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int updateByExampleSelective(@Param("record") GameUserBook record, @Param("example") GameUserBookExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user_book
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int updateByExampleWithBLOBs(@Param("record") GameUserBook record, @Param("example") GameUserBookExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user_book
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int updateByExample(@Param("record") GameUserBook record, @Param("example") GameUserBookExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user_book
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int updateByPrimaryKeySelective(GameUserBook record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user_book
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int updateByPrimaryKeyWithBLOBs(GameUserBook record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user_book
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int updateByPrimaryKey(GameUserBook record);
}