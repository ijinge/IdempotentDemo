package com.ijinge.Idempotent.Controller;

import com.ijinge.Idempotent.Annotation.Idempotent;
import com.ijinge.Idempotent.Consistent.IdempotentSceneEnum;
import com.ijinge.Idempotent.Consistent.RedisConsistent;
import com.ijinge.Idempotent.Entity.R;
import com.ijinge.Idempotent.Mapper.UserWalletMapper;
import com.ijinge.Idempotent.Service.WalletService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/wallet")
@RequiredArgsConstructor
public class UserWalletController {
    private final WalletService walletService;

    @PostMapping("/addBalance")
    @Idempotent(scene = IdempotentSceneEnum.RESTAPI,tokenKey = RedisConsistent.USER_WALLET_IDEM ,message = "点击太快了")
    public R<String> addBalance(@RequestParam Long userId, @RequestParam Long amount) {
        walletService.addBalance(userId,amount);
        return R.ok("充值成功");
    }

}
