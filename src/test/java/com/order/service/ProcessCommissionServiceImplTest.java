package com.order.service;


import com.order.service.impl.ProcessCommissionServiceImpl;
import com.order.vo.OrderVO;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProcessCommissionServiceImplTest {


    @InjectMocks
    private ProcessCommissionServiceImpl service;

    @Mock
    private OrderService orderService;

    @Mock
    private CalculateCommissionService calculateCommissionService;

    @Mock
    private ReadFileService readFileService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void processCommission_NullFilePathGiven_ShouldThrowException() {
        String filePath = null;

        service.processCommission(filePath);
    }

    @Test
    public void processCommission_ValidParamsGiven_ShouldInvokeAllServices() {
        String filePath = "file-path";
        Set<String> lines = Stream.of("1,123,12.33", "2,321,100.00", "3,1233,101.00", "3,112,45.12").collect(Collectors.toSet());
        OrderVO orderVO = new OrderVO();
        orderVO.setBoutiqueId("B1");
        orderVO.setOrderId("O1");
        orderVO.setComissionValue(100.);
        Set<OrderVO> orders = new HashSet<>();
        orders.add(orderVO);

        when(readFileService.readFile(filePath)).thenReturn(lines);
        when(orderService.buildOrders(lines)).thenReturn(Collections.singleton(orderVO));
        when(calculateCommissionService.doesCalculation(orders)).then(returnsFirstArg());

        service.processCommission(filePath);

        verify(readFileService).readFile(filePath);
        verify(orderService).buildOrders(lines);
        verify(calculateCommissionService).doesCalculation(orders);
    }
}
