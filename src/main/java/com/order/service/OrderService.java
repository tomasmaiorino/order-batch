package com.order.service;

import com.order.vo.OrderVO;

import java.util.Set;

public interface OrderService {

    public Set<OrderVO> buildOrders(final Set<String> lines);
}