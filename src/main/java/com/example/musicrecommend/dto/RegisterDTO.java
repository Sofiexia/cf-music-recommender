package com.example.musicrecommend.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String email;
    private String gender;
    private Integer age;
}