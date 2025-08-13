package com.ijinge.Idempotent.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ijinge.Idempotent.Entity.UserWallet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

@Mapper
public interface UserWalletMapper extends BaseMapper<UserWallet> {

    @Update("UPDATE t_user_wallet SET balance = balance + #{amount} WHERE user_id = #{user_id}")
    void addBalance(@Param("user_id") Long userId,@Param("amount") Long amount);

}
