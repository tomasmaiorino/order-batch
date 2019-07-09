package com.order.service.impl;

import com.order.service.CalculateCommissionService;
import com.order.service.OrderService;
import com.order.service.ProcessCommissionService;
import com.order.service.ReadFileService;
import com.order.vo.OrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@Service
public class ProcessCommissionServiceImpl implements ProcessCommissionService {

    private Logger logger = LoggerFactory.getLogger(ProcessCommissionServiceImpl.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private CalculateCommissionService calculateCommissionService;

    @Autowired
    private ReadFileService readFileService;

    @Override
    public void processCommission(final String batchFilePath) {

        Objects.requireNonNull(batchFilePath, "The batchFilePath must not be null.");

        Set<String> lines = readFileService.readFile(batchFilePath);

        Set<OrderVO> orderVOs = orderService.buildOrders(lines);

        Set<OrderVO> ordersResult = calculateCommissionService.doesCalculation(orderVOs);

        printResult(ordersResult);
    }

    private void printResult(final Set<OrderVO> orders) {


        orders.forEach(order -> {
            System.out.printf(Locale.US, "%s, %.2f" + System.lineSeparator(), order.getBoutiqueId(), order.getComissionValue());
        });
    }
}
