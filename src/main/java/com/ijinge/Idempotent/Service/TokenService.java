package com.ijinge.Idempotent.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final StringRedisTemplate redis;

    public String generate(String prefix) {
        String token = UUID.randomUUID().toString();
        redis.opsForValue().set(prefix + token, "1", Duration.ofMinutes(15));
        return token;
    }

    public boolean consume(String prefix,String token) {
        return Boolean.TRUE.equals(redis.delete( prefix + token));
    }
}
