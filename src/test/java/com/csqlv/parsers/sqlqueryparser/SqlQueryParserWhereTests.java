package com.csqlv.parsers.sqlqueryparser;

import com.csqlv.config.TestConfig;
import com.csqlv.model.QueryEntity;
import com.csqlv.model.statement.Statement;
import com.csqlv.model.statement.utils.Operator;
import com.csqlv.model.statement.utils.StatementCreator;
import com.csqlv.parsers.SQLQueryParser;
import com.csqlv.parsers.exceptions.NotSupportedWhereException;
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
public class SqlQueryParserWhereTests {

    @Autowired
    public SQLQueryParser sqlQueryParser;

    @Test
    @DirtiesContext
    public void testParseOneWhereStatement() throws ParseQueryException {
        String query = "SELECT * FROM \"./file\" WHERE a > 1";
        Statement expectedStatement = StatementCreator.createSimpleStatement("a", Operator.GREATER, "1");
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        Statement statement = queryEntity.getWhereStatement();
        assertEquals(expectedStatement, statement);
    }

    @Test
    @DirtiesContext
    public void testParseTwoConnectedWhereStatements() throws ParseQueryException {
        String query = "SELECT * FROM \"./file\" WHERE a > 1 and b < 2";
        Statement expectedStatement = StatementCreator.createSimpleStatement("b", Operator.LESS, "2")
                .and(StatementCreator.createSimpleStatement("a", Operator.GREATER, "1"));
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        Statement statement = queryEntity.getWhereStatement();
        assertEquals(expectedStatement, statement);
    }

    @Test
    @DirtiesContext
    public void testParseThreeConnectedWhereStatements() throws ParseQueryException {
        String query = "SELECT * FROM \"./file\" WHERE c = 5 or a > 1 and b < 2";
        Statement expectedStatement = StatementCreator.createSimpleStatement("b", Operator.LESS, "2")
                .and(StatementCreator.createSimpleStatement("a", Operator.GREATER, "1"));
        expectedStatement = expectedStatement
                .or(StatementCreator.createSimpleStatement("c", Operator.EQUALS, "5"));
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        Statement statement = queryEntity.getWhereStatement();
        assertEquals(expectedStatement, statement);
    }

    @Test
    @DirtiesContext
    public void testParseThreeConnectedWhereStatementsWithParenthesis() throws ParseQueryException {
        String query = "SELECT * FROM \"./file\" WHERE (c = 5 or a > 1) and b < 2";
        Statement expectedStatement = StatementCreator.createSimpleStatement("a", Operator.GREATER, "1")
                .or(StatementCreator.createSimpleStatement("c", Operator.EQUALS, "5"));
        expectedStatement = StatementCreator.createSimpleStatement("b", Operator.LESS, "2")
                .and(expectedStatement);
        QueryEntity queryEntity = sqlQueryParser.parseQuery(query);
        Statement statement = queryEntity.getWhereStatement();
        assertEquals(expectedStatement, statement);
    }

    @Test(expected = NotSupportedWhereException.class)
    @DirtiesContext
    public void testParseNotSupportedWhereStatement() throws ParseQueryException {
        String query = "SELECT * FROM \"./file\" WHERE a IN (1, 2)";
        sqlQueryParser.parseQuery(query);
    }

    @Test(expected = ParseQueryException.class)
    @DirtiesContext
    public void testParseIncorrectWhereStatement() throws ParseQueryException {
        String query = "SELECT * FROM \"./file\" WHERE";
        sqlQueryParser.parseQuery(query);
    }

}
