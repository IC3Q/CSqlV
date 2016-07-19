package com.csqlv.model.statement.utils;

public class Quantifier {
    private Operator operator;
    private Object value;

    public Quantifier(Operator operator, Object value) {
        this.operator = operator;
        this.value = value;
    }

    public Operator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quantifier)) return false;

        Quantifier that = (Quantifier) o;

        if (operator != that.operator) return false;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        int result = operator.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
