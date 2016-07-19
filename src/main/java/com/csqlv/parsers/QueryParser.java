package com.csqlv.parsers;

import com.csqlv.model.QueryEntity;
import com.csqlv.parsers.exceptions.ParseQueryException;

public interface QueryParser {

    QueryEntity parseQuery(String query) throws ParseQueryException;

}
