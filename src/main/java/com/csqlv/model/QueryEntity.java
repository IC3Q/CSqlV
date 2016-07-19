package com.csqlv.model;

import com.csqlv.model.statement.utils.OrderBy;
import com.csqlv.model.statement.Statement;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class QueryEntity {
    private Path sourceFile;
    private Statement whereStatement;
    private List<String> columns;
    private boolean isCount;
    private OrderBy orderBy;
    private int limit;

    public QueryEntity() {
        isCount = false;
        columns = new ArrayList<>();
    }

    public void addColumn(String column) {
        columns.add(column);
    }

    public Path getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(Path sourceFile) {
        this.sourceFile = sourceFile;
    }

    public Statement getWhereStatement() {
        return whereStatement;
    }

    public void setWhereStatement(Statement whereStatement) {
        this.whereStatement = whereStatement;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public boolean isCount() {
        return isCount;
    }

    public void setCount(boolean count) {
        isCount = count;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
