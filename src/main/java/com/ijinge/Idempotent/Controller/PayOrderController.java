package com.ijinge.Idempotent.Controller;

import com.ijinge.Idempotent.Annotation.Idempotent;
import com.ijinge.Idempotent.Consistent.IdempotentSceneEnum;
import com.ijinge.Idempotent.Consistent.RedisConsistent;
import com.ijinge.Idempotent.Entity.PayOrder;
import com.ijinge.Idempotent.Entity.R;
import com.ijinge.Idempotent.Service.PayOrderService;
import com.ijinge.Idempotent.Service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class PayOrderController {
    private final PayOrderService payOrderService;
    private final TokenService tokenService;

    // 1. 获取一次性 Token
    @GetMapping("/token/{business}")
    public String token(@PathVariable String business) {
        if (business.equals("wallet"))
            return tokenService.generate(RedisConsistent.USER_WALLET_IDEM);
        else
            return tokenService.generate(RedisConsistent.USER_ORDER_IDEM);
    }


    @PostMapping("/create")
    @Idempotent(scene = IdempotentSceneEnum.RESTAPI,tokenKey = RedisConsistent.USER_ORDER_IDEM,message = "点击太快了")
    public R<PayOrder> create(@RequestParam Long userId,
                              @RequestParam Long amount) {
        return R.ok(payOrderService.createOrder(userId, amount));
    }


}
