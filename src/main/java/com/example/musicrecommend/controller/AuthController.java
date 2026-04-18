package com.example.musicrecommend.controller;

import com.example.musicrecommend.dto.LoginDTO;
import com.example.musicrecommend.dto.LoginVO;
import com.example.musicrecommend.dto.RegisterDTO;
import com.example.musicrecommend.dto.Result;
import com.example.musicrecommend.entity.User;
import com.example.musicrecommend.service.UserService;
import com.example.musicrecommend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        User user = userService.login(loginDTO.getUsername(), loginDTO.getPassword());
        if (user != null) {
            if (!user.getUserType().equals(loginDTO.getUserType())) {
                return Result.error("权限不匹配，请选择正确的角色登录");
            }
            String token = jwtUtil.generateToken(user.getUserId(), user.getUserType());
            LoginVO loginVO = new LoginVO(token, user.getUserType(), user.getUsername());
            return Result.success(loginVO);
        } else {
            return Result.error("用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterDTO registerDTO) {
        // 检查用户名是否已存在
        if (userService.getUserByUsername(registerDTO.getUsername()) != null) {
            return Result.error("用户名已存在");
        }
        if (userService.getUserByEmail(registerDTO.getEmail()) != null) {
            return Result.error("该邮箱已被注册");
        }
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword()); // 实际应加密
        user.setEmail(registerDTO.getEmail());
        user.setGender(registerDTO.getGender());
        user.setAge(registerDTO.getAge());
        userService.register(user);
        return Result.success("注册成功");
    }
}