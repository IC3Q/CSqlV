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

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class SqlQueryParserFromTests {

    public QueryParser sqlQueryParser = new SQLQueryParser(new TGSqlParser(EDbVendor.dbvmysql));

    @Test
    public void testParseCorrectPath() throws ParseQueryException {
        String query = "SELECT a, b FROM \"./somedir/somefile.csv\"";
        String path = "./somedir/somefile.csv";
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        assertEquals(Paths.get(path), queryEntity.getSourceFile());
    }

    @Test(expected = ParseQueryException.class)
    public void testParseEmptyPath() throws ParseQueryException {
        String query = "SELECT a, b FROM";
        sqlQueryParser.parseQuery(query);
    }
}
