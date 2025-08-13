package com.ijinge.Idempotent.Aop;

import com.ijinge.Idempotent.Annotation.Idempotent;
import com.ijinge.Idempotent.Consistent.IdempotentSceneEnum;
import com.ijinge.Idempotent.Entity.R;
import com.ijinge.Idempotent.Exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class IdempotentAspect {

    private final StringRedisTemplate redisTemplate;

    @Around("@annotation(idem)")
    public Object around(ProceedingJoinPoint pjp, Idempotent idem) throws Throwable {
        if (idem.scene() != IdempotentSceneEnum.RESTAPI) {
            return pjp.proceed();
        }
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Idem-Token");
        if (!StringUtils.hasText(token)) {
            return R.fail(400, "缺少幂等 Token");
        }

        String redisKey = idem.tokenKey() + token;
        Boolean success = redisTemplate.execute(
                new DefaultRedisScript<>(
                        "if redis.call('GET', KEYS[1]) == '1' then " +
                                "  redis.call('DEL', KEYS[1]); " +
                                "  return 1; " +
                                "else " +
                                "  return 0; " +
                                "end",
                        Boolean.class),
                Collections.singletonList(redisKey)
        );

        if (!Boolean.TRUE.equals(success)) {
            return R.fail(409, idem.message());
        }
        // 执行业务
        return pjp.proceed();
    }
}
