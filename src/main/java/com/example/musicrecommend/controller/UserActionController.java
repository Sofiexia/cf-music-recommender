package com.example.musicrecommend.controller;

import com.example.musicrecommend.dto.ActionDTO;
import com.example.musicrecommend.dto.Result;
import com.example.musicrecommend.entity.UserAction;
import com.example.musicrecommend.service.UserActionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/actions")
public class UserActionController {

    @Autowired
    private UserActionService userActionService;

    @PostMapping
    public Result<Void> recordAction(@RequestBody ActionDTO actionDTO,
                                     HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "未登录");
        }
        userActionService.recordAction(
                userId,
                actionDTO.getMusicId(),
                UserAction.ActionType.valueOf(actionDTO.getActionType()),
                actionDTO.getDuration()
        );
        return Result.success();
    }
}