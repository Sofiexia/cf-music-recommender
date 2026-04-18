package com.example.musicrecommend.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("user_actions")
public class UserAction {

    @TableId(type = IdType.AUTO)
    private Integer actionId;

    private String userId;
    private String musicId;

    private ActionType actionType;   // 枚举类型
    private LocalDateTime actionTime;
    private Integer duration;        // 播放时长（秒）

    // 行为枚举
    public enum ActionType {
        play, pause, skip, like, collect, unlike
    }
}