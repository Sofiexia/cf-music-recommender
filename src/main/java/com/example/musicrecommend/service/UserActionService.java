package com.example.musicrecommend.service;

import com.example.musicrecommend.entity.UserAction;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserActionService extends IService<UserAction> {
    void recordAction(String userId, String musicId, UserAction.ActionType actionType, Integer duration);
}