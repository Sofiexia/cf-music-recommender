package com.example.musicrecommend.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("musics")
public class Music {

    @TableId(type = IdType.AUTO)
    private String musicId;

    private String title;
    private String artist;
    private String genre;
    private String tags;
    private String url;

    private String album;
    private Integer duration;      // 时长（秒）
    private Integer releaseYear;
    private String filePath;
    private String coverUrl;

    @TableField(exist = false)
    private Boolean isLiked = false;
}