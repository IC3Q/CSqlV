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
    private boolean errorOcurred;

    public WhereConditionParser(TExpression condition) {
        this.condition = condition;
        stack = new ArrayDeque<>();
        errorOcurred = false;
    }

    public Statement getStatement() throws NotSupportedWhereException {
        condition.postOrderTraverse(this);
        if (stack.peek() != null)
            return stack.pop();
        else throw new NotSupportedWhereException("Where statement couldn't be parsed.");
    }

    boolean isSimpleComparison(EExpressionType t) {
        return ((t == EExpressionType.simple_comparison_t)
                || (t == EExpressionType.group_comparison_t)
                || (t == EExpressionType.in_t));
    }

    boolean isLogicalOperator(EExpressionType t) {
        return ((t == EExpressionType.logical_and_t)
                || (t == EExpressionType.logical_or_t));
    }

    public boolean exprVisit(TParseTreeNode pnode, boolean pIsLeafNode) {
        TExpression lcexpr = (TExpression) pnode;
        if (isSimpleComparison(lcexpr.getExpressionType())) {
            TExpression leftExpr = (TExpression) lcexpr.getLeftOperand();

            System.out.println("column: " + leftExpr.toString());
            if (lcexpr.getComparisonOperator() != null) {
                System.out.println("Operator: "
                        + lcexpr.getComparisonOperator().astext);
            }
            System.out.println("value: "
                    + lcexpr.getRightOperand().toString());
            System.out.println("");

            Operator myOperator = getOperator(lcexpr);

            Statement statement = StatementCreator.createSimpleStatement(leftExpr.toString(), myOperator, lcexpr.getRightOperand());

            stack.push(statement);
        } else if (isLogicalOperator(lcexpr.getExpressionType())) {
            if (stack.size() >= 2) {
                Statement statement1 = stack.pop();
                Statement statement2 = stack.pop();
                Statement resultStatement = null;
                String operator = lcexpr.getOperatorToken().astext;
                switch (operator) {
                    case "OR":
                        resultStatement = statement1.or(statement2);
                        break;
                    case "AND":
                        resultStatement = statement1.and(statement2);
                        break;
                    default:
                        errorOcurred = true;
                }
                stack.push(resultStatement);
            } else {
                errorOcurred = true;
            }
        } else if (lcexpr.getExpressionType() != EExpressionType.parenthesis_t) {
            errorOcurred = true;
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
                errorOcurred = true;
        }
        return myOperator;
    }
}
