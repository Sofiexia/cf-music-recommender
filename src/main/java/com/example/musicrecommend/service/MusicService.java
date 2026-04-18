package com.example.musicrecommend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musicrecommend.entity.Music;

import java.util.List;

public interface MusicService extends IService<Music> {
    // 分页查询：根据 ID 列表返回分页对象（保持顺序）
    Page<Music> pageByIds(List<String> ids, Integer page, Integer size);

    // 分页搜索：根据关键字分页查询音乐
    Page<Music> searchByKeyword(String keyword, Integer page, Integer size);
}