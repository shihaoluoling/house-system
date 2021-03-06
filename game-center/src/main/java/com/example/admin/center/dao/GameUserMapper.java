package com.example.admin.center.dao;

import com.example.admin.center.model.GameUser;
import com.example.admin.center.model.GameUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GameUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    long countByExample(GameUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int deleteByExample(GameUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int insert(GameUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int insertSelective(GameUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    List<GameUser> selectByExample(GameUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    GameUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int updateByExampleSelective(@Param("record") GameUser record, @Param("example") GameUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int updateByExample(@Param("record") GameUser record, @Param("example") GameUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int updateByPrimaryKeySelective(GameUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_user
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int updateByPrimaryKey(GameUser record);
}