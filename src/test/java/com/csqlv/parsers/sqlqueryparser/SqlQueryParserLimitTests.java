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

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class SqlQueryParserLimitTests {

    @Autowired
    public SQLQueryParser sqlQueryParser;

    @Test
    @DirtiesContext
    public void testParseCorrectLimit() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\" LIMIT 10";
        int limit = 10;
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertEquals(limit, queryEntity.getLimit());
    }

    @Test(expected = ParseQueryException.class)
    @DirtiesContext
    public void testParseIncorrectLimit() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\" LIMIT -1";
        sqlQueryParser.parseQuery(query);
    }

    @Test(expected = ParseQueryException.class)
    @DirtiesContext
    public void testParseStringLimit() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\" LIMIT asd";
        sqlQueryParser.parseQuery(query);
    }

    @Test(expected = ParseQueryException.class)
    @DirtiesContext
    public void testParseEmptyLimit() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\" LIMIT";
        sqlQueryParser.parseQuery(query);
    }

    @Test
    @DirtiesContext
    public void testNoLimit() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\"";
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertEquals(0, queryEntity.getLimit());
    }
}
