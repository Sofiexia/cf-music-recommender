package com.example.musicrecommend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.musicrecommend.dto.Result;
import com.example.musicrecommend.entity.Music;
import com.example.musicrecommend.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping("/search")
    public Result<Page<Music>> search(@RequestParam String keyword,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size) {
        Page<Music> musicPage = musicService.searchByKeyword(keyword, page, size);
        return Result.success(musicPage);
    }

    @GetMapping("/{id}")
    public Result<Music> getById(@PathVariable Integer id) {
        Music music = musicService.getById(id);
        if (music == null) {
            return Result.error("音乐不存在");
        }
        return Result.success(music);
    }
}