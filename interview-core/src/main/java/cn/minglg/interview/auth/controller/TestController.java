package cn.minglg.interview.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * ClassName:TestController
 * Package:cn.minglg.interview.controller
 * Description:
 *
 * @Author kfzx-minglg
 * @Create 2025/6/26
 * @Version 1.0
 */
@RestController
public class TestController {
    @Autowired
    StringRedisTemplate redisService;

    @GetMapping("/set/{key}")
    public ResponseEntity<String> testSet(@PathVariable String key) {
        String value = UUID.randomUUID().toString().replace("-", "");
        redisService.opsForValue().set(key, value);
        return ResponseEntity.ok(key + ":" + value);
    }

    @GetMapping("/get/{key}")
    public ResponseEntity<String> testGet(@PathVariable String key) {
        String value = redisService.opsForValue().get(key) == null ? "nil" : redisService.opsForValue().get(key).toString();
        return ResponseEntity.ok(key + ":" + value);
    }
}
