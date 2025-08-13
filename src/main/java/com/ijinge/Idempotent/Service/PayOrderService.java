package com.ijinge.Idempotent.Service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ijinge.Idempotent.Entity.PayOrder;
import com.ijinge.Idempotent.Mapper.PayOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class  PayOrderService {

    private final PayOrderMapper payOrderMapper;
    private final StringRedisTemplate redisTemplate;
    private final WalletService walletService;

    @Transactional
    public PayOrder createOrder(Long userId, Long amount) {
        String orderNo = IdUtil.getSnowflakeNextIdStr();
        PayOrder o = new PayOrder();
        o.setOrderNo(orderNo);
        o.setUserId(userId);
        o.setAmount(amount);
        o.setStatus(0); // INIT
        try {
            payOrderMapper.insert(o);
        } catch (DuplicateKeyException e) {
            // 理论上不会重复，但幂等兜底
            return payOrderMapper.selectOne(
                    new LambdaQueryWrapper<PayOrder>().eq(PayOrder::getOrderNo, orderNo));
        }
        return o;
    }

    public boolean markSuccessIfAtInit(String orderNo) {
        return payOrderMapper.markSuccessIfInitOrProcessing(orderNo) > 0;
    }

}
