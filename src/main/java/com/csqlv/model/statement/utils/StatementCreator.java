package com.csqlv.model.statement.utils;

import com.csqlv.model.statement.SimpleStatement;
import com.csqlv.model.statement.Statement;

public class StatementCreator {
    public static Statement createSimpleStatement(String columnName, Operator operator, Object value) {
        return new SimpleStatement(columnName, new Quantifier(operator, value));
    }
}
