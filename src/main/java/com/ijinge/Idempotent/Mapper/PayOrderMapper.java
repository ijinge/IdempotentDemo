package com.ijinge.Idempotent.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ijinge.Idempotent.Entity.PayOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PayOrderMapper extends BaseMapper<PayOrder> {

    /**
     *
     */
    @Update("UPDATE t_pay_order SET status = 2 " +
            "WHERE order_no = #{orderNo} AND status IN (0,1)")
    int markSuccessIfInitOrProcessing(@Param("orderNo") String orderNo);
}
