package com.csqlv.resolvers;

import com.csqlv.TestConfig;
import com.csqlv.model.statement.utils.Order;
import com.csqlv.model.statement.utils.OrderBy;
import com.csqlv.utils.ObjectCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class OrderByResolverTests {
    private OrderBy orderBy;
    private Stream<Map<String, String>> inputStream;

    @Before
    public void init() {
        inputStream = ObjectCreator.createTestStream();
    }

    @Test
    public void testOrderAsc() {
        orderBy = new OrderBy("a", Order.ASC);
        List<Map<String, String>> outputList = inputStream.sorted(new OrderByResolver(orderBy)).collect(Collectors.toList());

        assertEquals(3, outputList.size());
        assertTrue(isOrdered(outputList, orderBy));
    }

    @Test
    public void testOrderDesc() {
        orderBy = new OrderBy("a", Order.DESC);
        List<Map<String, String>> outputList = inputStream.sorted(new OrderByResolver(orderBy)).collect(Collectors.toList());

        assertEquals(3, outputList.size());
        assertTrue(isOrdered(outputList, orderBy));
    }

    @Test
    public void testNullOrder() {
        orderBy = new OrderBy("a", Order.DESC);
        List<Map<String, String>> outputList = inputStream.sorted(new OrderByResolver(null)).collect(Collectors.toList());

        assertEquals(3, outputList.size());
        assertEquals("1", outputList.get(0).get("id"));
        assertEquals("2", outputList.get(1).get("id"));
        assertEquals("3", outputList.get(2).get("id"));
    }

    private boolean isOrdered(List<Map<String, String>> list, OrderBy orderBy) {
        BiFunction<String, String, Boolean> notInOrder;
        if (orderBy.getOrder() == Order.ASC)
            notInOrder = (prev, next) -> prev.compareTo(next) > 0;
        else
            notInOrder = (prev, next) -> prev.compareTo(next) < 0;

        Map<String, String> previous = null;
        for (Map<String, String> actual : list) {
            if (previous != null && notInOrder.apply(previous.get(orderBy.getColumn()), actual.get(orderBy.getColumn())))
                return false;
            previous = actual;
        }
        return true;
    }
}
