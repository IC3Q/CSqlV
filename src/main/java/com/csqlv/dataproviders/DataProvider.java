package com.csqlv.dataproviders;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Interface responsible for converting data to stream of rows.
 */
public interface DataProvider {
    /**
     * Transforms provided file to Stream of data.
     * @param filePath path to the file.
     * @return Stream of Map of String, String. Keys are headers, values are values for given row.
     * @throws IOException if there was a problem with file.
     */
    Stream<Map<String, String>> parseData(Path filePath) throws IOException;
}
