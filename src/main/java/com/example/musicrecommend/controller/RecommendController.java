package com.example.musicrecommend.controller;

import com.example.musicrecommend.dto.Result;
import com.example.musicrecommend.entity.Music;
import com.example.musicrecommend.service.MusicService;
import com.example.musicrecommend.service.RecommendService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private MusicService musicService;

    @GetMapping("/personal")
    public Result<Page<Music>> getPersonalRecommendations(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        String userId = (String) request.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "未登录");
        }
        // 获取推荐 ID 列表（假设返回 List<Integer>）
        List<String> recommendIds = recommendService.getPersonalRecommendationIds(userId);
        // 分页查询音乐详情
        Page<Music> musicPage = musicService.pageByIds(recommendIds, page, size);
        return Result.success(musicPage);
    }

    @GetMapping("/similar/{musicId}")
    public Result<List<Music>> getSimilarMusics(@PathVariable Integer musicId) {
        List<Music> similarMusics = recommendService.getSimilarMusics(musicId);
        return Result.success(similarMusics);
    }

}