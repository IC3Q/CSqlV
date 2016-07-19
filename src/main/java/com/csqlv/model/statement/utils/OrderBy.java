package com.csqlv.model.statement.utils;

public class OrderBy {
    private String column;
    private Order order;
    public OrderBy(String column, Order order) {
        this.column = column;
        this.order = order;
    }
    public String getColumn() {
        return column;
    }
    public Order getOrder() {
        return order;
    }
}
