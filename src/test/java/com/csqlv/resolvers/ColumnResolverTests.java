package com.csqlv.resolvers;

import com.csqlv.utils.ObjectCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ColumnResolverTests {

    private List<String> selectColumns;
    private Stream<Map<String, String>> inputStream;

    @Before
    public void init() {
        inputStream = ObjectCreator.createTestStream();
    }

    @Test
    public void testColumnResolver() {
        selectColumns = Arrays.asList("a");
        List<Map<String, String>> outputList = inputStream.map(new ColumnResolver(selectColumns)).collect(Collectors.toList());
        assertEquals(3, outputList.size());
        assertEquals(1, outputList.get(0).keySet().size());
        assertTrue(outputList.get(0).keySet().contains("a"));
    }

    @Test
    public void testAsteriskColumnResolver() {
        selectColumns = Arrays.asList("*");
        List<Map<String, String>> outputList = inputStream.map(new ColumnResolver(selectColumns)).collect(Collectors.toList());
        assertEquals(3, outputList.size());
        assertEquals(5, outputList.get(0).keySet().size());
    }

    @Test
    public void testEmptyListColumnResolver() {
        selectColumns = new ArrayList<>();
        List<Map<String, String>> outputList = inputStream.map(new ColumnResolver(selectColumns)).collect(Collectors.toList());
        assertEquals(3, outputList.size());
        assertEquals(5, outputList.get(0).keySet().size());
    }

    @Test
    public void testNullListColumnResolver() {
        List<Map<String, String>> outputList = inputStream.map(new ColumnResolver(null)).collect(Collectors.toList());
        assertEquals(3, outputList.size());
        assertEquals(5, outputList.get(0).keySet().size());
    }
}
