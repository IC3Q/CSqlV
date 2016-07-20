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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderBy)) return false;

        OrderBy orderBy = (OrderBy) o;

        if (!column.equals(orderBy.column)) return false;
        return order == orderBy.order;

    }

    @Override
    public int hashCode() {
        int result = column.hashCode();
        result = 31 * result + order.hashCode();
        return result;
    }
}
