package com.example.musicrecommend.dto;

import lombok.Data;
import java.util.List;

@Data
public class RecommendResponseDTO {
    private String user_id;
    private List<String> recommendations;
}
