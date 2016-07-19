package com.csqlv.dataproviders;

import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

public interface DataProvider {
    Stream<Map<String, String>> parseData(Path filePath) throws IOException;
}
