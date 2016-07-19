package com.csqlv.parsers;

import com.csqlv.model.statement.utils.StatementCreator;
import com.csqlv.model.statement.utils.Operator;
import com.csqlv.model.statement.Statement;
import com.csqlv.parsers.exceptions.NotSupportedWhereException;
import gudusoft.gsqlparser.EExpressionType;
import gudusoft.gsqlparser.nodes.IExpressionVisitor;
import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.nodes.TParseTreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class WhereConditionParser implements IExpressionVisitor {

    private TExpression condition;
    private Deque<Statement> stack;
    private boolean errorOccurred;

    public WhereConditionParser(TExpression condition) {
        this.condition = condition;
        stack = new ArrayDeque<>();
        errorOccurred = false;
    }

    public Statement getStatement() throws NotSupportedWhereException {
        condition.postOrderTraverse(this);
        if (stack.peek() == null || errorOccurred)
            throw new NotSupportedWhereException("Where statement couldn't be parsed. Syntax not supported.");
        else
            return stack.pop();
    }

    private boolean isSimpleComparison(EExpressionType t) {
        return ((t == EExpressionType.simple_comparison_t)
                || (t == EExpressionType.group_comparison_t));
    }

    private boolean isLogicalOperator(EExpressionType t) {
        return ((t == EExpressionType.logical_and_t)
                || (t == EExpressionType.logical_or_t));
    }

    private boolean isOmittedOperator(EExpressionType t) {
        return (t == EExpressionType.parenthesis_t
                || t == EExpressionType.simple_object_name_t
                || t == EExpressionType.simple_constant_t);
    }

    public boolean exprVisit(TParseTreeNode pnode, boolean pIsLeafNode) {
        TExpression lcexpr = (TExpression) pnode;
        if (isSimpleComparison(lcexpr.getExpressionType())) {
            TExpression leftExpr = lcexpr.getLeftOperand();
            Operator myOperator = getOperator(lcexpr);
            Statement statement = StatementCreator.createSimpleStatement(leftExpr.toString(), myOperator, lcexpr.getRightOperand().toString());
            stack.push(statement);
        } else if (isLogicalOperator(lcexpr.getExpressionType())) {
            if (stack.size() >= 2) {
                Statement statement1 = stack.pop();
                Statement statement2 = stack.pop();
                Statement resultStatement = null;
                String operator = lcexpr.getOperatorToken().astext;
                switch (operator.toUpperCase()) {
                    case "OR":
                        resultStatement = statement1.or(statement2);
                        break;
                    case "AND":
                        resultStatement = statement1.and(statement2);
                        break;
                    default:
                        errorOccurred = true;
                }
                stack.push(resultStatement);
            } else {
                errorOccurred = true;
            }
        } else if (!isOmittedOperator(lcexpr.getExpressionType())) {
            errorOccurred = true;
        }
        return true;
    }

    private Operator getOperator(TExpression lcexpr) {
        String operator = lcexpr.getComparisonOperator().astext;
        Operator myOperator = Operator.EQUALS;
        switch (operator.toUpperCase()) {
            case "LIKE":
                myOperator = Operator.LIKE;
                break;
            case "=":
                myOperator = Operator.EQUALS;
                break;
            case ">=":
                myOperator = Operator.GREATER_EQUALS;
                break;
            case "<=":
                myOperator = Operator.LESS_EQUALS;
                break;
            case ">":
                myOperator = Operator.GREATER;
                break;
            case "<":
                myOperator = Operator.LESS;
                break;
            default:
                errorOccurred = true;
        }
        return myOperator;
    }
}
