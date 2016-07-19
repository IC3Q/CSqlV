package com.csqlv.dataproviders;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CsvDataProviderImpl implements CsvDataProvider {

    private Path filePath;

    public CsvDataProviderImpl(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Stream<CSVRecord> parseData() throws IOException {
        File csvData = filePath.toFile();
        CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.RFC4180.withHeader());
        Iterable<CSVRecord> iterable = () -> parser.iterator();
        Stream<CSVRecord> stream = StreamSupport.stream(iterable.spliterator(), false);

        return stream;
    }

}
