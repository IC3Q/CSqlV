package com.csqlv.resolvers;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ColumnResolver implements Function<Map<String, String>, Map<String, String>> {

    private List<String> selectColumns;

    public ColumnResolver(List<String> columns) {
        this.selectColumns = columns;
    }

    @Override
    public Map<String, String> apply(Map<String, String> record) {
        if (selectColumns != null && selectColumns.size() > 0)
            record.keySet().removeIf(column -> !selectColumns.contains(column));
        return record;
    }
}
