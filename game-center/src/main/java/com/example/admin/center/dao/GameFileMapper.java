package com.example.admin.center.dao;

import com.example.admin.center.model.GameFile;
import com.example.admin.center.model.GameFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GameFileMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_file
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    long countByExample(GameFileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_file
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int deleteByExample(GameFileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_file
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_file
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int insert(GameFile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_file
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int insertSelective(GameFile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_file
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    List<GameFile> selectByExample(GameFileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_file
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    GameFile selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_file
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int updateByExampleSelective(@Param("record") GameFile record, @Param("example") GameFileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_file
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int updateByExample(@Param("record") GameFile record, @Param("example") GameFileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_file
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int updateByPrimaryKeySelective(GameFile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_file
     *
     * @mbg.generated Wed Jan 20 09:08:18 CST 2021
     */
    int updateByPrimaryKey(GameFile record);
}