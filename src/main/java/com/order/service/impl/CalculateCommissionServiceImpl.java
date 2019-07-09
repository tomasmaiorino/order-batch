package com.order.service.impl;

import com.order.exception.CalculateCommissionException;
import com.order.service.CalculateCommissionService;
import com.order.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class CalculateCommissionServiceImpl implements CalculateCommissionService {


    public static final int PERCENTAGE_TO_ADD = 10;

    public Set<OrderVO> doesCalculation(final Set<OrderVO> orders) {

        if (Objects.isNull(orders) || orders.isEmpty()) {
            throw new CalculateCommissionException("The orders must not be null nor empty!");
        }

        return calculate(orders);
    }

    private Double doesCalculation(final OrderVO c) {
        return c.getTotalOrderPrice() * PERCENTAGE_TO_ADD / 100;
    }

    private Set<OrderVO> calculate(final Set<OrderVO> orders) {

        Set<Double> numbers = orders.stream().map(OrderVO::getTotalOrderPrice)
                .collect(Collectors.toCollection(TreeSet::new));

        Double maxValue = numbers.size() == 1 ? null : (Double) numbers.toArray()[numbers.size() - 1];

        orders.stream().forEach(c -> {
            if (orders.size() == 1
                    || Objects.isNull(maxValue)
                    || !maxValue.equals(c.getTotalOrderPrice())) {
                c.setComissionValue(doesCalculation(c));
            } else {
                c.setComissionValue(0d);
            }
        });

        return orders;
    }
}
