package com.example.musicrecommend.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

@Data
@TableName("users")
public class User {
    @TableId(value = "user_id", type = IdType.ASSIGN_UUID)
    private String userId;

    private String username;
    private String password;
    private String email;
    private String gender;
    private Integer age;
    private Integer userType;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
