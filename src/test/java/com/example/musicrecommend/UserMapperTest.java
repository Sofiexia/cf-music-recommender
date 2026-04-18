package com.example.musicrecommend;

import com.example.musicrecommend.entity.User;
import com.example.musicrecommend.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert(){
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("123456");
        user.setEmail("test@example.com");
        int rows = userMapper.insert(user);
        System.out.println("影响行数："+rows);
        System.out.println("生成ID:"+user.getUserId());
    }
}
