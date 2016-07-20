package com.csqlv.parsers;

import com.csqlv.model.QueryEntity;
import com.csqlv.model.statement.Statement;
import com.csqlv.model.statement.utils.Order;
import com.csqlv.model.statement.utils.OrderBy;
import com.csqlv.parsers.exceptions.NotSupportedWhereException;
import com.csqlv.parsers.exceptions.ParseQueryException;
import gudusoft.gsqlparser.ESqlStatementType;
import gudusoft.gsqlparser.TBaseType;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.TGSqlParser;
import gudusoft.gsqlparser.nodes.TConstant;
import gudusoft.gsqlparser.nodes.TJoin;
import gudusoft.gsqlparser.nodes.TResultColumn;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SQLQueryParser implements QueryParser {

    private TGSqlParser parser;

    @Autowired
    public SQLQueryParser(TGSqlParser tgSqlParser) {
        parser = tgSqlParser;
    }

    @Override
    public QueryEntity parseQuery(String query) throws ParseQueryException {
        parser.sqltext = query;

        QueryEntity queryEntity = parseQuery();

        if (queryEntity != null)
            return queryEntity;
        else
            throw new ParseQueryException();
    }

    private QueryEntity parseQuery() throws ParseQueryException {
        QueryEntity queryEntity = new QueryEntity();
        int result = parser.parse();
        if (result == 0) {
            if (parser.sqlstatements.hasNext()) {
                TCustomSqlStatement stmt = parser.sqlstatements.next();
                if (stmt.sqlstatementtype == ESqlStatementType.sstselect) {
                    parseSelectStatement(queryEntity, stmt);
                } else {
                    throw new ParseQueryException("Provided statement is not proper SELECT.");
                }
            }
            return queryEntity;
        } else {
            throw new ParseQueryException(parser.getErrormessage());
        }
    }

    private void parseSelectStatement(QueryEntity queryEntity, TCustomSqlStatement stmt) throws ParseQueryException {
        TSelectSqlStatement statement = (TSelectSqlStatement) stmt;
        parseColumnsClause(queryEntity, statement);
        parseFromClause(queryEntity, statement);
        parseWhereClause(queryEntity, statement);
        parseOrderByClause(queryEntity, statement);
        parseLimitClause(queryEntity, statement);
    }

    private void parseLimitClause(QueryEntity queryEntity, TSelectSqlStatement statement) throws ParseQueryException {
        if (statement.getLimitClause() != null) {
            TConstant limitConstant = statement.getLimitClause().getRow_count().getConstantOperand();
            if (limitConstant != null) {
                int limit = Integer.parseInt(limitConstant.getStringValue());
                queryEntity.setLimit(limit);
            } else {
                throw new ParseQueryException("Limit is not a number.");
            }
        }
    }

    private void parseOrderByClause(QueryEntity queryEntity, TSelectSqlStatement statement) {
        if (statement.getOrderbyClause() != null) {
            String orderByColumn = statement.getOrderbyClause().getItems().getOrderByItem(0).getSortKey().toString();
            Order order;
            switch (statement.getOrderbyClause().getItems().getOrderByItem(0).getSortOrder()) {
                case desc:
                    order = Order.DESC;
                    break;
                default:
                    order = Order.ASC;
            }
            queryEntity.setOrderBy(new OrderBy(orderByColumn, order));
        }
    }

    private void parseWhereClause(QueryEntity queryEntity, TSelectSqlStatement statement) throws NotSupportedWhereException {
        if (statement.getWhereClause() != null) {
            WhereConditionParser conditionParser = new WhereConditionParser(statement.getWhereClause().getCondition());
            Statement whereStatement = conditionParser.getStatement();
            queryEntity.setWhereStatement(whereStatement);
        }
    }

    private void parseFromClause(QueryEntity queryEntity, TSelectSqlStatement statement) {
        for (int i = 0; i < statement.joins.size(); i++) {
            TJoin join = statement.joins.getJoin(i);
            if (join.getKind() == TBaseType.join_source_fake) {
                String filePathWithQuotes = join.getTable().toString();
                String filePathWithoutQuotes = filePathWithQuotes.substring(1, filePathWithQuotes.length() - 1);
                queryEntity.setSourceFile(Paths.get(filePathWithoutQuotes));
            }
        }
    }

    private void parseColumnsClause(QueryEntity queryEntity, TSelectSqlStatement statement) {
        for (int i = 0; i < statement.getResultColumnList().size(); i++) {
            TResultColumn resultColumn = statement.getResultColumnList().getResultColumn(i);
            Pattern r = Pattern.compile("COUNT\\((.*)\\)");
            Matcher m = r.matcher(resultColumn.getExpr().toString());
            if (m.find()) {
                queryEntity.setCount(true);
            } else {
                queryEntity.addColumn(resultColumn.getExpr().toString());
            }
        }
    }

}
