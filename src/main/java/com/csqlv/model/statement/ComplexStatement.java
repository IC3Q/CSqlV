package com.csqlv.model.statement;

import com.csqlv.model.statement.utils.LogicalCondition;

public class ComplexStatement extends Statement {
    private Statement leftStatement;
    private Statement rightStatement;
    private LogicalCondition logicalCondition;

    public ComplexStatement(Statement leftStatement, Statement rightStatement, LogicalCondition logicalCondition) {
        this.leftStatement = leftStatement;
        this.rightStatement = rightStatement;
        this.logicalCondition = logicalCondition;
    }

    public Statement getLeftStatement() {
        return leftStatement;
    }

    public Statement getRightStatement() {
        return rightStatement;
    }

    public LogicalCondition getLogicalCondition() {
        return logicalCondition;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplexStatement)) return false;

        ComplexStatement that = (ComplexStatement) o;

        if (!leftStatement.equals(that.leftStatement)) return false;
        if (!rightStatement.equals(that.rightStatement)) return false;
        return logicalCondition == that.logicalCondition;

    }

    @Override
    public int hashCode() {
        int result = leftStatement.hashCode();
        result = 31 * result + rightStatement.hashCode();
        result = 31 * result + logicalCondition.hashCode();
        return result;
    }
}
