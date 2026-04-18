package com.example.musicrecommend;

import com.example.musicrecommend.filter.JwtAuthenticationFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.example.musicrecommend.mapper")
@EnableAsync
public class MusicRecommendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicRecommendApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter(JwtAuthenticationFilter jwtFilter) {
		FilterRegistrationBean<JwtAuthenticationFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(jwtFilter);
		registration.addUrlPatterns("/api/*"); // 拦截所有 /api 请求
		registration.setOrder(1);
		return registration;
	}

}
