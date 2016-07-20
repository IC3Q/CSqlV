package com.csqlv.resolvers;

import com.csqlv.model.QueryEntity;
import com.csqlv.printers.ConsoleDataPrinter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryEntityResolver {
    private QueryEntity queryEntity;
    private Stream<Map<String, String>> dataStream;
    private ConsoleDataPrinter dataPrinter;

    public QueryEntityResolver(QueryEntity queryEntity, Stream<Map<String, String>> data, ConsoleDataPrinter dataPrinter) {
        this.queryEntity = queryEntity;
        this.dataStream = data;
        this.dataPrinter = dataPrinter;
    }

    public void applyClausesToData() {
        if (queryEntity.isCount()) {
            long count = dataStream.filter(new WhereResolver(queryEntity.getWhereStatement()))
                    .count();
            dataPrinter.printCount(count);
        } else {
            List<Map<String, String>> data = dataStream.filter(new WhereResolver(queryEntity.getWhereStatement()))
                    .sorted(new OrderByResolver(queryEntity.getOrderBy()))
                    .limit(queryEntity.getLimit())
                    .map(new ColumnResolver(queryEntity.getColumns()))
                    .collect(Collectors.toList());
            dataPrinter.printData(data);
        }

    }
}
