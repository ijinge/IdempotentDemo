package com.ijinge.Idempotent.Entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("t_user_wallet")
public class UserWallet {
    @TableId(value = "user_id")
    private Long userId;
    private Long balance;
}
