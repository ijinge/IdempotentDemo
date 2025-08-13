package com.ijinge.Idempotent.Entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_pay_order")
public class PayOrder {
    @TableId
    private Long id;
    private String orderNo;
    private Long userId;
    private Long amount;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}