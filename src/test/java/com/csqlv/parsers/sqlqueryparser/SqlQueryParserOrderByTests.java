package com.csqlv.parsers.sqlqueryparser;

import com.csqlv.TestConfig;
import com.csqlv.model.QueryEntity;
import com.csqlv.model.statement.utils.Order;
import com.csqlv.model.statement.utils.OrderBy;
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
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class SqlQueryParserOrderByTests {

    public QueryParser sqlQueryParser = new SQLQueryParser(new TGSqlParser(EDbVendor.dbvmysql));

    @Test
    public void testParseCorrectOrderBy() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\" ORDER BY a DESC";
        OrderBy orderBy = new OrderBy("a", Order.DESC);
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertEquals(orderBy, queryEntity.getOrderBy());
    }

    @Test
    public void testParseOrderByWithoutOrder() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\" ORDER BY a";
        OrderBy orderBy = new OrderBy("a", Order.ASC);
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertEquals(orderBy, queryEntity.getOrderBy());
    }

    @Test(expected = ParseQueryException.class)
    public void testParseEmptyOrderBy() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\" ORDER BY";
        sqlQueryParser.parseQuery(query);
    }

    @Test
    public void testParseNoOrderBy() throws ParseQueryException {
        String query = "SELECT * FROM \"./somefile.csv\"";
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertNull(queryEntity.getOrderBy());
    }
}
