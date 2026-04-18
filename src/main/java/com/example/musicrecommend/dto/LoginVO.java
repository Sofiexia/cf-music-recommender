package com.example.musicrecommend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginVO {
    private String token;
    private Integer userType; // 0: 普通用户, 1: 管理员
    private String username;
}
