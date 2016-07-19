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

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class SqlQueryParserLimitTests {

    public QueryParser sqlQueryParser = new SQLQueryParser(new TGSqlParser(EDbVendor.dbvmysql));

    @Test
    public void testParseCorrectLimit() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\" LIMIT 10";
        int limit = 10;
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertEquals(limit, queryEntity.getLimit());
    }

    @Test(expected = ParseQueryException.class)
    public void testParseIncorrectLimit() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\" LIMIT -1";
        sqlQueryParser.parseQuery(query);
    }

    @Test(expected = ParseQueryException.class)
    public void testParseStringLimit() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\" LIMIT asd";
        sqlQueryParser.parseQuery(query);
    }

    @Test(expected = ParseQueryException.class)
    public void testParseEmptyLimit() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\" LIMIT";
        sqlQueryParser.parseQuery(query);
    }

    @Test
    public void testNoLimit() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\"";
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertEquals(0, queryEntity.getLimit());
    }
}
