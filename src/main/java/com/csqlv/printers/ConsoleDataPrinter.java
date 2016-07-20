package com.csqlv.printers;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ConsoleDataPrinter implements DataPrinter {

    @Override
    public void printData(List<Map<String, String>> data) {
        if (data.size() == 0) {
            System.out.println("No data found.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String header : data.get(0).keySet()) {
            sb.append("\t" + header);
        }
        sb.append("\n");
        for (Map<String, String> row : data) {
            for (String value : row.values()) {
                sb.append("\t" + value);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    @Override
    public void printCount(long count) {
        System.out.println("Number of results: " + count);
    }
}
