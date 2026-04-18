package com.example.musicrecommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.musicrecommend.entity.Music;
import com.example.musicrecommend.mapper.MusicMapper;
import com.example.musicrecommend.service.MusicService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {

    @Override
    public Page<Music> pageByIds(List<String> ids, Integer page, Integer size) {
        Page<Music> mpPage = new Page<>(page, size);
        // 若推荐 ID 列表为空，直接返回空页
        if (ids == null || ids.isEmpty()) {
            mpPage.setRecords(Collections.emptyList());
            mpPage.setTotal(0);
            return mpPage;
        }

        // 计算当前页在 ids 中的起止索引
        int start = (int) ((mpPage.getCurrent() - 1) * mpPage.getSize());
        int end = Math.min(start + (int) mpPage.getSize(), ids.size());

        // 如果起始索引超出范围，返回空页
        if (start >= ids.size()) {
            mpPage.setRecords(Collections.emptyList());
            mpPage.setTotal(ids.size());
            return mpPage;
        }

        List<String> subIds = ids.subList(start, end);
        // 查询音乐详情
        List<Music> records = this.listByIds(subIds);
        // 按原始顺序排序
        Map<String, Music> idMap = records.stream().collect(Collectors.toMap(Music::getMusicId, Function.identity()));
        List<Music> orderedList = subIds.stream()
                .map(idMap::get)
                .filter(Objects::nonNull) // 防止某些 ID 不存在
                .collect(Collectors.toList());

        mpPage.setRecords(orderedList);
        mpPage.setTotal(ids.size());
        return mpPage;
    }

    @Override
    public Page<Music> searchByKeyword(String keyword, Integer page, Integer size) {
        Page<Music> mpPage = new Page<>(page, size);
        if (keyword == null || keyword.trim().isEmpty()) {
            mpPage.setRecords(Collections.emptyList());
            mpPage.setTotal(0);
            return mpPage;
        }
        LambdaQueryWrapper<Music> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Music::getTitle, keyword)
                .or()
                .like(Music::getArtist, keyword);
        return this.page(mpPage, wrapper);
    }
}