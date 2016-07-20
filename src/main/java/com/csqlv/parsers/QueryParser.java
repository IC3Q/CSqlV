package com.csqlv.parsers;

import com.csqlv.model.QueryEntity;
import com.csqlv.parsers.exceptions.ParseQueryException;

/**
 * Interface for parsing query.
 */
public interface QueryParser {

    /**
     * Parses provided query.
     * @param query query
     * @return QueryEntity which is a representation of the provided query.
     * @throws ParseQueryException if there was a problem with parsing query.
     */
    QueryEntity parseQuery(String query) throws ParseQueryException;

}
