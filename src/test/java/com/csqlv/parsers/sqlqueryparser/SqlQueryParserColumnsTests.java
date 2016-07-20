package com.csqlv.parsers.sqlqueryparser;

import com.csqlv.config.TestConfig;
import com.csqlv.model.QueryEntity;
import com.csqlv.parsers.SQLQueryParser;
import com.csqlv.parsers.exceptions.ParseQueryException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class SqlQueryParserColumnsTests {

    @Autowired
    public SQLQueryParser sqlQueryParser;

    @Test
    @DirtiesContext
    public void testParseAsteriskColumns() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\"";
        List<String> columns = Arrays.asList("*");
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertEquals(new HashSet<>(columns), new HashSet<>(queryEntity.getColumns()));
    }

    @Test
    @DirtiesContext
    public void testParseCorrectColumns() throws ParseQueryException {
        String query = "SELECT a, b FROM \"./somefile.csv\"";
        List<String> columns = Arrays.asList("a", "b");
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertEquals(new HashSet<>(columns), new HashSet<>(queryEntity.getColumns()));
    }

    @Test
    @DirtiesContext
    public void testParseWithoutSpaceColumns() throws ParseQueryException {
        String query = "SELECT a,b FROM \"./somefile.csv\"";
        List<String> columns = Arrays.asList("a", "b");
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertEquals(new HashSet<>(columns), new HashSet<>(queryEntity.getColumns()));
    }

    @Test(expected = ParseQueryException.class)
    @DirtiesContext
    public void testParseNoColumns() throws ParseQueryException {
        String query = "SELECT FROM \"./somefile.csv\"";
        sqlQueryParser.parseQuery(query);
    }
}
