package com.csqlv.model.statement.utils;

public enum Operator {

    LIKE(" LIKE "),
    EQUALS(" = "),
    GREATER_EQUALS(" >= "),
    LESS_EQUALS(" <= "),
    GREATER(" > "),
    LESS(" < ");

    private String operatorBody;

    private Operator(String operatorBody) {
        this.operatorBody = operatorBody;
    }

    public String getOperatorBody() {
        return operatorBody;
    }
}
