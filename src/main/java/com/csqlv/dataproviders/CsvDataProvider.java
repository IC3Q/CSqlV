package com.csqlv.dataproviders;

import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.stream.Stream;

public interface CsvDataProvider {
    Stream<CSVRecord> parseData() throws IOException;
}
