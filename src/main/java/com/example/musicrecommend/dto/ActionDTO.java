package com.example.musicrecommend.dto;

import lombok.Data;

@Data
public class ActionDTO {
    private String musicId;
    private String actionType;  // play, pause, skip, like, collect
    private Integer duration;   // 播放时长（秒）
}