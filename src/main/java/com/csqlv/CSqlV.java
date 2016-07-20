package com.csqlv;

import com.csqlv.dataproviders.DataProvider;
import com.csqlv.model.QueryEntity;
import com.csqlv.parsers.QueryParser;
import com.csqlv.parsers.exceptions.ParseQueryException;
import com.csqlv.printers.DataPrinter;
import com.csqlv.resolvers.ColumnResolver;
import com.csqlv.resolvers.OrderByResolver;
import com.csqlv.resolvers.WhereResolver;
import com.csqlv.resolvers.exceptions.IllegalCharacterWhileQueryingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CSqlV {

    private QueryParser queryParser;

    private DataProvider dataProvider;

    private DataPrinter dataPrinter;

    @Autowired
    public CSqlV(QueryParser queryParser, DataProvider dataProvider, DataPrinter dataPrinter) {
        this.queryParser = queryParser;
        this.dataProvider = dataProvider;
        this.dataPrinter = dataPrinter;
    }

    public void handleQuery(String query) {
        try {
            QueryEntity queryEntity = queryParser.parseQuery(query);
            Path filePath = queryEntity.getSourceFile();
            Stream<Map<String, String>> stream = dataProvider.parseData(filePath)
                    .filter(new WhereResolver(queryEntity.getWhereStatement()));
            if (queryEntity.isCount())
                dataPrinter.printCount(stream.count());
            else {
                stream = stream.sorted(new OrderByResolver(queryEntity.getOrderBy()));
                if (queryEntity.getLimit() > 0)
                    stream = stream.limit(queryEntity.getLimit());
                stream = stream.map(new ColumnResolver(queryEntity.getColumns()));
                List<Map<String, String>> data = stream.collect(Collectors.toList());
                dataPrinter.printData(data);
            }
        } catch (ParseQueryException e) {
            System.out.println("There have been a problem with parsing query. Details: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("There have been a problem with reading source file. Details: " + e.getMessage());
        } catch (IllegalCharacterWhileQueryingException e) {
            System.out.println("There have been a problem with applying where clause to the file. Details: " + e.getMessage());
        }
    }
}
