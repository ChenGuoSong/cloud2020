package com.cgs.springcloud.dao;

import com.cgs.springcloud.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {

    // 创建订单
    void creat(Order order);

    // 修改订单状态
    void update(@Param("userId") Long userId,@Param("status") Integer status);
}
