package com.order.service;

import com.order.exception.CalculateCommissionException;
import com.order.service.impl.CalculateCommissionServiceImpl;
import com.order.vo.OrderVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.order.service.impl.CalculateCommissionServiceImpl.PERCENTAGE_TO_ADD;
import static org.hamcrest.CoreMatchers.is;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CalculateCommissionServiceImplTest {

    private CalculateCommissionServiceImpl service;

    @Before
    public void setUp() {
        service = new CalculateCommissionServiceImpl();
    }

    @Test(expected = CalculateCommissionException.class)
    public void doesCalculation_NullOrdersGiven_ShouldThrowException() {
        Set<OrderVO> orders = null;

        service.doesCalculation(orders);
    }

    @Test(expected = CalculateCommissionException.class)
    public void doesCalculation_EmptyOrdersGiven_ShouldThrowException() {
        Set<OrderVO> orders = Collections.emptySet();

        service.doesCalculation(orders);
    }

    @Test
    public void doesCalculation_ValidOrdersWithDifferentValueGiven_ShouldReturnNotNullResult() {
        final Double commonValue = 100d;
        Set<OrderVO> orders = new HashSet<>();
        OrderVO orderVO = new OrderVO();
        orderVO.setBoutiqueId("1");
        orderVO.setTotalOrderPrice(commonValue);
        orderVO.setOrderId("o1");
        orders.add(orderVO);

        orderVO = new OrderVO();
        orderVO.setBoutiqueId("2");
        orderVO.setTotalOrderPrice(commonValue);
        orderVO.setOrderId("o2");
        orders.add(orderVO);

        orderVO = new OrderVO();
        orderVO.setBoutiqueId("3");
        orderVO.setTotalOrderPrice(103.);
        orderVO.setOrderId("o3");
        orders.add(orderVO);

        Set<OrderVO> result = service.doesCalculation(orders);
        Assert.assertNotNull(result);
    }

    @Test
    public void doesCalculation_ValidOrdersWithDifferentValueGiven_ShouldReturnSameSizeOrdersSet() {
        final Double commonValue = 100d;
        Set<OrderVO> orders = new HashSet<>();
        OrderVO orderVO = new OrderVO();
        orderVO.setBoutiqueId("1");
        orderVO.setTotalOrderPrice(commonValue);
        orderVO.setOrderId("o1");
        orders.add(orderVO);

        orderVO = new OrderVO();
        orderVO.setBoutiqueId("2");
        orderVO.setTotalOrderPrice(commonValue);
        orderVO.setOrderId("o2");
        orders.add(orderVO);

        orderVO = new OrderVO();
        orderVO.setBoutiqueId("3");
        orderVO.setTotalOrderPrice(103.);
        orderVO.setOrderId("o3");
        orders.add(orderVO);

        Set<OrderVO> result = service.doesCalculation(orders);
        Assert.assertThat(result.size(), is(orders.size()));
    }

    @Test
    public void doesCalculation_ValidOrdersWithDifferentValueGiven_ShouldReturnValidOrders() {
        Set<OrderVO> orders = new HashSet<>();
        OrderVO orderVO1 = new OrderVO();
        orderVO1.setBoutiqueId("1");
        orderVO1.setTotalOrderPrice(20d);
        orderVO1.setOrderId("o1");
        orders.add(orderVO1);

        OrderVO orderVO2 = new OrderVO();
        orderVO2.setBoutiqueId("2");
        orderVO2.setTotalOrderPrice(20d);
        orderVO2.setOrderId("o2");
        orders.add(orderVO2);

        OrderVO orderVO3 = new OrderVO();
        orderVO3.setBoutiqueId("3");
        orderVO3.setTotalOrderPrice(103.);
        orderVO3.setOrderId("o3");
        orders.add(orderVO3);

        Set<OrderVO> result = service.doesCalculation(orders);

        OrderVO order3 = result.stream().filter(o -> o.getOrderId().equals(orderVO3.getOrderId())).findFirst().get();
        OrderVO order2 = result.stream().filter(o -> o.getOrderId().equals(orderVO2.getOrderId())).findFirst().get();
        OrderVO order1 = result.stream().filter(o -> o.getOrderId().equals(orderVO1.getOrderId())).findFirst().get();

        Assert.assertThat(order3.getComissionValue(), is(0d));
        Assert.assertThat(order2.getComissionValue(), is(
                orderVO2.getTotalOrderPrice() * PERCENTAGE_TO_ADD / 100));
        Assert.assertThat(order1.getComissionValue(), is(
                orderVO1.getTotalOrderPrice() * PERCENTAGE_TO_ADD / 100));
    }

    @Test
    public void doesCalculation_ValidOrdersWithSameValueGiven_ShouldReturnValidOrders() {
        double orderValue = 100.;
        Set<OrderVO> orders = new HashSet<>();
        OrderVO orderVO1 = new OrderVO();
        orderVO1.setBoutiqueId("1");
        orderVO1.setTotalOrderPrice(orderValue);
        orderVO1.setOrderId("o1");
        orders.add(orderVO1);

        OrderVO orderVO2 = new OrderVO();
        orderVO2.setBoutiqueId("2");
        orderVO2.setTotalOrderPrice(orderValue);
        orderVO2.setOrderId("o2");
        orders.add(orderVO2);

        Set<OrderVO> result = service.doesCalculation(orders);

        OrderVO order2 = result.stream().filter(o -> o.getOrderId().equals(orderVO2.getOrderId())).findFirst().get();
        OrderVO order1 = result.stream().filter(o -> o.getOrderId().equals(orderVO1.getOrderId())).findFirst().get();

        Assert.assertThat(order2.getComissionValue(), is(
                orderVO2.getTotalOrderPrice() * PERCENTAGE_TO_ADD / 100));
        Assert.assertThat(order1.getComissionValue(), is(
                orderVO1.getTotalOrderPrice() * PERCENTAGE_TO_ADD / 100));
    }

    @Test
    public void doesCalculation_OneValidOrderValueGiven_ShouldReturnValidOrder() {
        double orderValue = 100.;
        Set<OrderVO> orders = new HashSet<>();
        OrderVO orderVO1 = new OrderVO();
        orderVO1.setBoutiqueId("1");
        orderVO1.setTotalOrderPrice(orderValue);
        orderVO1.setOrderId("o1");
        orders.add(orderVO1);

        Set<OrderVO> result = service.doesCalculation(orders);

        OrderVO order1 = result.stream().filter(o -> o.getOrderId().equals(orderVO1.getOrderId())).findFirst().get();

        Assert.assertThat(order1.getComissionValue(), is(
                orderVO1.getTotalOrderPrice() * PERCENTAGE_TO_ADD / 100));
    }
}
