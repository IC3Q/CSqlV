package com.csqlv.resolvers;

import com.csqlv.TestConfig;
import com.csqlv.model.statement.Statement;
import com.csqlv.model.statement.utils.Operator;
import com.csqlv.model.statement.utils.StatementCreator;
import com.csqlv.utils.ObjectCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class WhereResolverTests {

    private Statement statement;
    private Stream<Map<String, String>> inputStream;

    @Before
    public void init() {
        inputStream = ObjectCreator.createTestStream();
    }

    @Test
    public void testSimpleStatement() {
        statement = StatementCreator.createSimpleStatement("a", Operator.LESS, "2");
        List<Map<String, String>> outputList = inputStream.filter(new WhereResolver(statement)).collect(Collectors.toList());
        assertEquals(1,outputList.size());
        assertEqualSetsOfIds(Arrays.asList("1"), outputList);
        assertEquals(5, outputList.get(0).entrySet().size());
    }

    @Test
    public void testMixedStatement() {
        statement = StatementCreator.createSimpleStatement("a", Operator.LESS, "2").or(StatementCreator.createSimpleStatement("a", Operator.GREATER, "2"));
        List<Map<String, String>> outputList = inputStream.filter(new WhereResolver(statement)).collect(Collectors.toList());
        assertEquals(2,outputList.size());
        assertEqualSetsOfIds(Arrays.asList("1", "3"), outputList);
        assertEquals(5, outputList.get(0).entrySet().size());
    }

    @Test
    public void testNestedStatement() {
        statement = StatementCreator.createSimpleStatement("a", Operator.LESS, "2").or(StatementCreator.createSimpleStatement("a", Operator.GREATER, "2"))
                .and(StatementCreator.createSimpleStatement("b", Operator.EQUALS, "1"));
        List<Map<String, String>> outputList = inputStream.filter(new WhereResolver(statement)).collect(Collectors.toList());
        assertEquals(1,outputList.size());
        assertEqualSetsOfIds(Arrays.asList("1"), outputList);
        assertEquals(5, outputList.get(0).entrySet().size());
    }

    @Test
    public void testExactLikeStatement() {
        statement = StatementCreator.createSimpleStatement("d", Operator.LIKE, "aaa");
        List<Map<String, String>> outputList = inputStream.filter(new WhereResolver(statement)).collect(Collectors.toList());
        assertEquals(1, outputList.size());
        assertEqualSetsOfIds(Arrays.asList("1"), outputList);
        assertEquals(5, outputList.get(0).entrySet().size());
    }

    @Test
    public void testLeftSideLikeStatement() {
        statement = StatementCreator.createSimpleStatement("d", Operator.LIKE, "%a");
        List<Map<String, String>> outputList = inputStream.filter(new WhereResolver(statement)).collect(Collectors.toList());
        assertEquals(1, outputList.size());
        assertEqualSetsOfIds(Arrays.asList("1"), outputList);
        assertEquals(5, outputList.get(0).entrySet().size());
    }

    @Test
    public void testRightSideLikeStatement() {
        statement = StatementCreator.createSimpleStatement("d", Operator.LIKE, "a%");
        List<Map<String, String>> outputList = inputStream.filter(new WhereResolver(statement)).collect(Collectors.toList());
        assertEquals(2, outputList.size());
        assertEqualSetsOfIds(Arrays.asList("1", "3"), outputList);
        assertEquals(5, outputList.get(0).entrySet().size());
    }

    @Test
    public void testBothSideLikeStatement() {
        statement = StatementCreator.createSimpleStatement("d", Operator.LIKE, "%a%");
        List<Map<String, String>> outputList = inputStream.filter(new WhereResolver(statement)).collect(Collectors.toList());
        assertEquals(2, outputList.size());
        assertEqualSetsOfIds(Arrays.asList("1", "3"), outputList);
        assertEquals(5, outputList.get(0).entrySet().size());
    }

    @Test
    public void testLikeStatementWithNoResult() {
        statement = StatementCreator.createSimpleStatement("d", Operator.LIKE, "%z%");
        List<Map<String, String>> outputList = inputStream.filter(new WhereResolver(statement)).collect(Collectors.toList());
        assertEquals(0, outputList.size());
    }

    @Test
    public void testNullStatement() {
        List<Map<String, String>> outputList = inputStream.filter(new WhereResolver(null)).collect(Collectors.toList());
        assertEquals(3, outputList.size());
        assertEqualSetsOfIds(Arrays.asList("1", "2", "3"), outputList);
        assertEquals(5, outputList.get(0).entrySet().size());
    }

    private void assertEqualSetsOfIds(List<String> expectedIds, List<Map<String, String>> result) {
        assertEquals(
                new HashSet<>(expectedIds),
                new HashSet<>(result.stream().map((x) -> x.get("id")).collect(Collectors.toList()))
        );
    }

}
