package com.example.musicrecommend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.musicrecommend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User selectByUsername(@Param("username") String username);
}
