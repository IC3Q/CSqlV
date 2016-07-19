package com.csqlv.model.statement;

import com.csqlv.model.statement.utils.Quantifier;

public class SimpleStatement extends Statement {
    private String columnName;
    private Quantifier quantifier;

    public SimpleStatement(String columnName, Quantifier quantifier) {
        this.columnName = columnName;
        this.quantifier = quantifier;
    }

    public String getColumnName() {
        return columnName;
    }

    public Quantifier getQuantifier() {
        return quantifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleStatement)) return false;

        SimpleStatement that = (SimpleStatement) o;

        if (!columnName.equals(that.columnName)) return false;
        return quantifier.equals(that.quantifier);
    }

    @Override
    public int hashCode() {
        int result = columnName.hashCode();
        result = 31 * result + quantifier.hashCode();
        return result;
    }

}
