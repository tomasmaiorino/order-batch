package com.order.service;

import com.order.exception.OrderServiceException;
import com.order.service.impl.OrderServiceImpl;
import com.order.vo.OrderVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.notNullValue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderServiceImplTest {


    private OrderServiceImpl service;

    @Before
    public void setUp() {
        service = new OrderServiceImpl();
    }

    @Test(expected = OrderServiceException.class)
    public void buildOrders_NullLinesGiven_ShouldThrowException() {
        Set<String> lines = null;

        service.buildOrders(lines);
    }

    @Test(expected = OrderServiceException.class)
    public void buildOrders_EmptyLinesGiven_ShouldThrowException() {

        Set<String> lines = Collections.emptySet();

        service.buildOrders(lines);
    }

    @Test
    public void buildOrders_ValidLinesGiven_ShouldReturnNotNullSet() {

        Stream<String> streamLines = Stream.of("123,12.33", "321,100.00", "1233,101.00", "112,45.12");
        Set<String> lines = streamLines.collect(Collectors.toSet());

        Set<OrderVO> result = service.buildOrders(lines);

        Assert.assertNotNull(result);
    }

    @Test
    public void buildOrders_ValidLinesGiven_ShouldReturnNotEmptySet() {

        Stream<String> streamLines = Stream.of("1,123,12.33", "2,321,100.00", "3,1233,101.00", "3,112,45.12");
        Set<String> lines = streamLines.collect(Collectors.toSet());

        Set<OrderVO> result = service.buildOrders(lines);

        Assert.assertTrue(!result.isEmpty());
    }

    @Test
    public void buildOrders_ValidLinesGiven_ShouldReturnSameSizeInputSet() {
        Stream<String> streamLines = Stream.of("1,123,12.33", "2,321,100.00", "3,1233,101.00", "4,112,45.12");
        Set<String> lines = streamLines.collect(Collectors.toSet());

        Set<OrderVO> result = service.buildOrders(lines);

        Assert.assertTrue(result.size() == lines.size());
    }

    @Test
    public void buildOrders_ValidLinesGiven_ShouldReturnValidOrder() {
        Stream<String> streamLines = Stream.of("1,123,12.33");
        Set<String> lines = streamLines.collect(Collectors.toSet());

        Set<OrderVO> result = service.buildOrders(lines);

        OrderVO orderVO = result.iterator().next();

        Assert.assertThat(orderVO.getBoutiqueId(), notNullValue());
        Assert.assertThat(orderVO.getOrderId(), notNullValue());
        Assert.assertThat(orderVO.getTotalOrderPrice(), notNullValue());
    }

    @Test
    public void buildOrders_AllLinesInvalidGiven_ShouldEmptySet() {
        Stream<String> streamLines = Stream.of("1,123,", "2,321,1t", "3,1233");
        Set<String> lines = streamLines.collect(Collectors.toSet());

        Set<OrderVO> result = service.buildOrders(lines);

        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void buildOrders_AtLeastOneLineInvalidGiven_ShouldReturnSmallSet() {
        Stream<String> streamLines = Stream.of("1,123,120.", "2,321,1", "2,1233");
        Set<String> lines = streamLines.collect(Collectors.toSet());

        Set<OrderVO> result = service.buildOrders(lines);

        Assert.assertTrue(result.size() == lines.size() - 1);
    }
}