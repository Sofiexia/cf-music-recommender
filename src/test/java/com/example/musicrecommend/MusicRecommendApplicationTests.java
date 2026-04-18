package com.example.musicrecommend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class MusicRecommendApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Test
	void testMySQL() throws SQLException {
		System.out.println("DataSource: " + dataSource.getConnection());
	}

	@Test
	void testRedis() {
		redisTemplate.opsForValue().set("test", "hello");
		System.out.println(redisTemplate.opsForValue().get("test"));
	}

}
