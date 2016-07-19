package com.csqlv.dataproviders;

import com.csqlv.TestConfig;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class CsvDataProviderTests {

    private CsvDataProvider csvDataProvider;

    @Autowired
    @Qualifier("ProperPath")
    private Path properFilePath;

    @Autowired
    @Qualifier("NonExistingPath")
    private Path nonExistingFilePath;

    @Test
    public void properParseTest() throws IOException {
        csvDataProvider = new CsvDataProvider();
        Stream<Map<String, String>> dataStream = csvDataProvider.parseData(properFilePath);
        assertNotNull(dataStream);
        List<Map<String, String>> data = dataStream.collect(Collectors.toList());
        assertEquals(2, data.size());
        Map<String, String> mappedRecord = data.get(0);
        assertEquals("1", mappedRecord.get("a"));
        assertEquals("2", mappedRecord.get("b"));
    }

    @Test(expected = IOException.class)
    public void parseNonExistingFile() throws IOException {
        csvDataProvider = new CsvDataProvider();
        csvDataProvider.parseData(nonExistingFilePath);
    }
}
