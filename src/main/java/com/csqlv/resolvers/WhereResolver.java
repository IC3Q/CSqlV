package com.csqlv.resolvers;

import com.csqlv.model.QueryEntity;
import com.csqlv.model.statement.ComplexStatement;
import com.csqlv.model.statement.SimpleStatement;
import com.csqlv.model.statement.Statement;
import com.csqlv.model.statement.utils.Quantifier;

import java.util.Map;
import java.util.function.Predicate;

public class WhereResolver implements Predicate<Map<String, String>> {

    private Statement statement;

    public WhereResolver(Statement statement) {
        this.statement = statement;
    }

    @Override
    public boolean test(Map<String, String> record) {
        if (statement == null) return true;
        Predicate<Map<String, String>> predicate = getPredicateForStatement(statement);
        return predicate.test(record);
    }

    private Predicate<Map<String, String>> getPredicateForStatement(Statement statement) {
        if (statement instanceof SimpleStatement) {
            return getPredicateForSimpleStatement((SimpleStatement) statement);
        } else if (statement instanceof ComplexStatement) {
            return getPredicateForComplexStatement((ComplexStatement) statement);
        } else {
            throw new IllegalArgumentException("Wrong statement type.");
        }
    }

    private Predicate<Map<String, String>> getPredicateForSimpleStatement(SimpleStatement statement) {
        Quantifier quantifier = statement.getQuantifier();
        String column = statement.getColumnName();
        switch(quantifier.getOperator()) {
            case EQUALS:
                return (element) ->
                        element.get(column).equals(quantifier.getValue().toString());
            case GREATER:
                return (element) ->
                        Double.parseDouble(element.get(column)) > Double.parseDouble(quantifier.getValue().toString());
            case GREATER_EQUALS:
                return (element) ->
                        Double.parseDouble(element.get(column)) >= Double.parseDouble(quantifier.getValue().toString());
            case LESS:
                return (element) ->
                        Double.parseDouble(element.get(column)) < Double.parseDouble(quantifier.getValue().toString());
            case LESS_EQUALS:
                return (element) ->
                        Double.parseDouble(element.get(column)) <= Double.parseDouble(quantifier.getValue().toString());
            case LIKE:
                return (element) ->
                    resolveLike(element, quantifier, column);
            default:
                throw new IllegalArgumentException("Not supported operator.");
        }
    }

    private boolean resolveLike(Map<String, String> element, Quantifier quantifier, String column) {
        String rule = quantifier.getValue().toString();
        if (rule.startsWith("%") && rule.endsWith("%")) {
            String searchedPhrase = rule.substring(1, rule.length() - 1);
            return element.get(column).contains(searchedPhrase);
        } else if (rule.startsWith("%")) {
            String searchedPhrase = rule.substring(1);
            return element.get(column).endsWith(searchedPhrase);
        } else if (rule.endsWith("%")) {
            String searchedPhrase = rule.substring(0, rule.length() - 1);
            return element.get(column).startsWith(searchedPhrase);
        } else {
            return element.get(column).equals(rule);
        }
    }

    private Predicate<Map<String, String>> getPredicateForComplexStatement(ComplexStatement statement) {
        Statement left = statement.getLeftStatement();
        Statement right = statement.getRightStatement();
        if (left == null || right == null) {
            throw new IllegalArgumentException("Complex where statement is incorrectly built.");
        } else {
            switch(statement.getLogicalCondition()) {
                case AND:
                    return getPredicateForStatement(left).and(getPredicateForStatement(right));
                case OR:
                    return getPredicateForStatement(left).or(getPredicateForStatement(right));
                default:
                    throw new IllegalArgumentException("Illegal logical condition");
            }
        }
    }
}
