package com.ijinge.Idempotent.Service;

import com.ijinge.Idempotent.Mapper.UserWalletMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final UserWalletMapper userWalletMapper;

    public void addBalance(Long userId, Long amount) {
        userWalletMapper.addBalance(userId, amount);
    }

}
