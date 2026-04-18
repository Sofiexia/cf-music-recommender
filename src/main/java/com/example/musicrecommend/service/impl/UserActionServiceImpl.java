package com.example.musicrecommend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.musicrecommend.entity.UserAction;
import com.example.musicrecommend.mapper.UserActionMapper;
import com.example.musicrecommend.service.UserActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserActionServiceImpl extends ServiceImpl<UserActionMapper, UserAction> implements UserActionService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void recordAction(String userId, String musicId, UserAction.ActionType actionType, Integer duration) {

        if (actionType == UserAction.ActionType.like) {
            Long count = this.lambdaQuery()
                    .eq(UserAction::getUserId, userId)
                    .eq(UserAction::getMusicId, musicId)
                    .eq(UserAction::getActionType, UserAction.ActionType.like)
                    .count();
            if (count > 0) {
                return;
            }
            saveNewAction(userId, musicId, actionType, duration);
            triggerModelRefresh();
        } else if (actionType == UserAction.ActionType.unlike) {
            this.removeLikeAction(userId, musicId);
            triggerModelRefresh();
        } else {
            saveNewAction(userId, musicId, actionType, duration);
        }

    }

    @Async("taskExecutor")
    public void triggerModelRefresh() {
        try {
            String pythonApiUrl = "http://localhost:5000/refresh";
            restTemplate.postForObject(pythonApiUrl, null, String.class);
            log.info("已通知推荐算法引擎更新矩阵");
        } catch (Exception e) {
            log.error("通知推荐引擎失败: {}", e.getMessage());
            // 即使通知失败，也不影响用户点赞的主流程
        }
    }

    private void saveNewAction(String userId, String musicId, UserAction.ActionType type, Integer duration) {
        UserAction action = new UserAction();
        action.setUserId(userId);
        action.setMusicId(musicId);
        action.setActionType(type);
        action.setDuration(duration);
        action.setActionTime(LocalDateTime.now());
        this.save(action);
    }


    public void removeLikeAction(String userId, String musicId) {
        this.lambdaUpdate()
                .eq(UserAction::getUserId, userId)
                .eq(UserAction::getMusicId, musicId)
                .eq(UserAction::getActionType, UserAction.ActionType.like)
                .remove();
    }
}