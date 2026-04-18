package com.example.musicrecommend.service.impl;

import com.example.musicrecommend.dto.RecommendResponseDTO;
import com.example.musicrecommend.dto.SimilarMusicResponseDTO;
import com.example.musicrecommend.entity.Music;
import com.example.musicrecommend.service.MusicService;
import com.example.musicrecommend.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MusicService musicService;

    @Value("${recommend.service.url}")
    private String recommendServiceUrl;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String RECOMMEND_CACHE_KEY = "recommend:user:";

    @Override
    public List<Music> getSimilarMusics(Integer musicId) {
        String url = recommendServiceUrl + "/similar/music/" + musicId;
        try{
            SimilarMusicResponseDTO response = restTemplate.getForObject(url, SimilarMusicResponseDTO.class);
            if (response == null || response.getSimilar_musics() == null || response.getSimilar_musics().isEmpty()) {
                return Collections.emptyList();
            }
            return musicService.listByIds(response.getSimilar_musics());
        } catch (RestClientException e) {
            log.error("调用相似音乐服务失败，musicId={}, url={}", musicId, url, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> getPersonalRecommendationIds(String userId) {

        String cacheKey = RECOMMEND_CACHE_KEY + userId;
        // 从缓存获取
        log.info("尝试从 Redis 获取缓存，key: {}", cacheKey);
        Object cacheObj = redisTemplate.opsForValue().get(cacheKey);
        List<String> cachedIds = null;
        if (cacheObj instanceof List<?> list) {
            cachedIds = list.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .collect(Collectors.toList());
        }
        if (cachedIds != null) {
            log.info("缓存命中，用户 {} 的推荐列表从 Redis 获取: {}", userId, cachedIds);
            return cachedIds;
        }
        log.info("缓存未命中，调用 Python 推荐服务，用户: {}", userId);
        // 缓存未命中，调用推荐服务
        String url = recommendServiceUrl + "/recommend/user/" + userId;
        try {
            RecommendResponseDTO responseDTO = restTemplate.getForObject(url, RecommendResponseDTO.class);
            List<String> ids = (responseDTO != null && responseDTO.getRecommendations() != null)
                    ? responseDTO.getRecommendations()
                    : Collections.emptyList();
            // 存入缓存
            if (!ids.isEmpty()){
                redisTemplate.opsForValue().set(cacheKey, ids, 600, TimeUnit.SECONDS);
            }
            return ids;
        } catch (Exception e) {
            log.error("调用推荐服务失败", e);
            return Collections.emptyList();
        }
    }
}