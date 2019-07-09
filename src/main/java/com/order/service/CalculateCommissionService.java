package com.order.service;

import com.order.vo.OrderVO;

import java.util.Set;

public interface CalculateCommissionService {

    public Set<OrderVO> doesCalculation(final Set<OrderVO> orders);
}
