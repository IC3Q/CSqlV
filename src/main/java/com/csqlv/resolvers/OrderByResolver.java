package com.csqlv.resolvers;

import com.csqlv.model.statement.utils.Order;
import com.csqlv.model.statement.utils.OrderBy;

import java.util.Comparator;
import java.util.Map;

public class OrderByResolver implements Comparator<Map<String, String>> {

    private OrderBy orderBy;

    public OrderByResolver(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public int compare(Map<String, String> o1, Map<String, String> o2) {
        if (orderBy == null) return 0;
        int result = o1.get(orderBy.getColumn()).compareTo(o2.get(orderBy.getColumn()));
        if (orderBy.getOrder() == Order.DESC) result = -result;
        return result;
    }
}
