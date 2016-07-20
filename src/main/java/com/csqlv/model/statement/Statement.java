package com.csqlv.model.statement;

import com.csqlv.model.statement.utils.LogicalCondition;

public abstract class Statement {
    public ComplexStatement and(Statement statement) {
        return new ComplexStatement(this, statement, LogicalCondition.AND);
    }

    public ComplexStatement or(Statement statement) {
        return new ComplexStatement(this, statement, LogicalCondition.OR);
    }
}
