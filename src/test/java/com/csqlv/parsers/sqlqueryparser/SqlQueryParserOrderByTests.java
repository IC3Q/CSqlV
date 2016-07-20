package com.csqlv.parsers.sqlqueryparser;

import com.csqlv.config.TestConfig;
import com.csqlv.model.QueryEntity;
import com.csqlv.model.statement.utils.Order;
import com.csqlv.model.statement.utils.OrderBy;
import com.csqlv.parsers.SQLQueryParser;
import com.csqlv.parsers.exceptions.ParseQueryException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class SqlQueryParserOrderByTests {

    @Autowired
    public SQLQueryParser sqlQueryParser;

    @Test
    @DirtiesContext
    public void testParseCorrectOrderBy() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\" ORDER BY a DESC";
        OrderBy orderBy = new OrderBy("a", Order.DESC);
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertEquals(orderBy, queryEntity.getOrderBy());
    }

    @Test
    @DirtiesContext
    public void testParseOrderByWithoutOrder() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\" ORDER BY a";
        OrderBy orderBy = new OrderBy("a", Order.ASC);
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertEquals(orderBy, queryEntity.getOrderBy());
    }

    @Test(expected = ParseQueryException.class)
    @DirtiesContext
    public void testParseEmptyOrderBy() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\" ORDER BY";
        sqlQueryParser.parseQuery(query);
    }

    @Test
    @DirtiesContext
    public void testParseNoOrderBy() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\"";
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertNull(queryEntity.getOrderBy());
    }
}
