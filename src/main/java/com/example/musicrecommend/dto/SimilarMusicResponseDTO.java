package com.example.musicrecommend.dto;

import lombok.Data;

import java.util.List;

@Data
public class SimilarMusicResponseDTO {
    private Integer music_id;
    private List<Integer> similar_musics;
}
