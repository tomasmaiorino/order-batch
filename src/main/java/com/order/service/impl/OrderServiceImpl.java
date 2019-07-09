package com.order.service.impl;

import com.order.exception.OrderServiceException;
import com.order.service.OrderService;
import com.order.vo.OrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    public static final int BOUTIQUE_SEPARATOR = 0;
    public static final int ORDER_ID_SEPARATOR = 1;
    public static final int ORDER_VALUE_SEPARATOR = 2;
    private static final int QT_ITEMS_REQUIRED = 3;

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public Set<OrderVO> buildOrders(final Set<String> lines) {

        if (Objects.isNull(lines) || lines.isEmpty()) {
            throw new OrderServiceException("The lines must not be null nor empty!");
        }

        Set<OrderVO> orders = lines.stream().map(line -> buildOrder(line)).collect(Collectors.toSet());

        orders.removeIf(Objects::isNull);

        return orders;
    }

    private OrderVO buildOrder(final String line) {
        try {
            String l[] = line.split(",");
            if (l.length < QT_ITEMS_REQUIRED) {

                logger.warn("Invalid line length [{}].", line);
                return null;
            }
            return new OrderVO(l[BOUTIQUE_SEPARATOR], l[ORDER_ID_SEPARATOR], Double.parseDouble(l[ORDER_VALUE_SEPARATOR]));
        } catch (Exception e) {
            //log error
            logger.warn(String.format("Invalid line [%s]. Error message [%s].", line, e.getMessage()));
        }
        return null;
    }
}