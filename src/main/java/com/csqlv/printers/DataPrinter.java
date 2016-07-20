package com.csqlv.printers;

import java.util.List;
import java.util.Map;

public interface DataPrinter {
    void printData(List<Map<String, String>> data);

    void printCount(long count);
}
