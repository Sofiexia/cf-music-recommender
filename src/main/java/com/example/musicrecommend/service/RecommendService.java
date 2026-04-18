package com.example.musicrecommend.service;

import com.example.musicrecommend.entity.Music;
import java.util.List;

public interface RecommendService {
    List<Music> getSimilarMusics(Integer musicId);

    List<String> getPersonalRecommendationIds(String userId);
}