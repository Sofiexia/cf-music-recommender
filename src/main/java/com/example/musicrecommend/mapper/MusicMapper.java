package com.example.musicrecommend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.musicrecommend.entity.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MusicMapper extends BaseMapper<Music> {
    @Select("SELECT m.*, " +
            "(CASE WHEN ua.action_id IS NOT NULL THEN 1 ELSE 0 END) as is_liked " +
            "FROM musics m " +
            "LEFT JOIN user_actions ua ON m.music_id = ua.music_id " +
            "AND ua.user_id = #{currentUserId} " +
            "AND ua.action_type = 'like'")
    List<Music> selectMusicWithLikeStatus(@Param("currentUserId") Integer currentUserId);
}
