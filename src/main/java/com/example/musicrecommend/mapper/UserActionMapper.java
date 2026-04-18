package com.example.musicrecommend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.musicrecommend.entity.UserAction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserActionMapper extends BaseMapper<UserAction> {
    @Select("SELECT * FROM user_actions WHERE user_id = #{userId} AND action_type = 'play'")
    List<UserAction> selectPlayActionsByUserId(@Param("userId") Integer userId);
}
