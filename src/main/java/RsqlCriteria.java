/*
 * Copyright (c) 2015. Vodori, Inc.  All rights reserved.
 *
 * This software is not open source and license for its use and
 * modification can only be granted through an agreement with
 * Vodori, Inc.
 *
 * http://www.vodori.com
 */

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public class RsqlCriteria {

    private String property;
    private ComparisonOperator operator;
    private List<String> arguments;

    public RsqlCriteria(String property, ComparisonOperator operator, List<String> arguments) {
        this.property = property;
        this.operator = operator;
        this.arguments = arguments;
    }


    public Criteria toCriteria() {
        return null;
    }

}
