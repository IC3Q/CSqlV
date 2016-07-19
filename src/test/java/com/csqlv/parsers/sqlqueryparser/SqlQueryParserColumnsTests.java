package com.csqlv.parsers.sqlqueryparser;

import com.csqlv.TestConfig;
import com.csqlv.model.QueryEntity;
import com.csqlv.parsers.QueryParser;
import com.csqlv.parsers.SQLQueryParser;
import com.csqlv.parsers.exceptions.ParseQueryException;
import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TGSqlParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class SqlQueryParserColumnsTests {

    public QueryParser sqlQueryParser = new SQLQueryParser(new TGSqlParser(EDbVendor.dbvmysql));

    @Test
    public void testParseAsteriskColumns() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\"";
        List<String> columns = Arrays.asList("*");
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertEquals(new HashSet(columns), new HashSet(queryEntity.getColumns()));
    }

    @Test
    public void testParseCorrectColumns() throws ParseQueryException {
        String query = "SELECT a, b FROM \"./somefile.csv\"";
        List<String> columns = Arrays.asList("a", "b");
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertEquals(new HashSet(columns), new HashSet(queryEntity.getColumns()));
    }

    @Test
    public void testParseWithoutSpaceColumns() throws ParseQueryException {
        String query = "SELECT a,b FROM \"./somefile.csv\"";
        List<String> columns = Arrays.asList("a", "b");
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertEquals(new HashSet(columns), new HashSet(queryEntity.getColumns()));
    }

    @Test(expected = ParseQueryException.class)
    public void testParseNoColumns() throws ParseQueryException {
        String query = "SELECT FROM \"./somefile.csv\"";
        sqlQueryParser.parseQuery(query);
    }
}
