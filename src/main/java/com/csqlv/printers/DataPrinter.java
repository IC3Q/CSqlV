package com.csqlv.printers;

import java.util.List;
import java.util.Map;

/**
 * Interface for printing data.
 */
public interface DataPrinter {
    /**
     * Prints list of result rows.
     * @param data List of rows in Map representation. Keys are headers, values are values.
     */
    void printData(List<Map<String, String>> data);

    /**
     * Prints number of the result set.
     * @param count number of the result set.
     */
    void printCount(long count);
}
