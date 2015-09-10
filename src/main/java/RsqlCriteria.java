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
import cz.jirutka.rsql.parser.ast.RSQLOperators;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public class RsqlCriteria {

    enum RsqlSearchOperation {
        EQUAL(RSQLOperators.EQUAL),
        NOT_EQUAL(RSQLOperators.NOT_EQUAL),
        GREATER_THAN(RSQLOperators.GREATER_THAN),
        GREATER_THAN_OR_EQUAL(RSQLOperators.GREATER_THAN_OR_EQUAL),
        LESS_THAN(RSQLOperators.LESS_THAN),
        LESS_THAN_OR_EQUAL(RSQLOperators.LESS_THAN_OR_EQUAL),
        IN(RSQLOperators.IN),
        NOT_IN(RSQLOperators.NOT_IN);

        private ComparisonOperator operator;

        RsqlSearchOperation(ComparisonOperator operator) {
            this.operator = operator;
        }

        public static RsqlSearchOperation getSimpleOperator(ComparisonOperator operator) {
            for (RsqlSearchOperation operation : values()) {
                if (operation.operator == operator) {
                    return operation;
                }
            }
            return null;
        }
    }


    private String property;
    private ComparisonOperator operator;
    private List<String> arguments;

    public RsqlCriteria(String property, ComparisonOperator operator, List<String> arguments) {
        this.property = property;
        this.operator = operator;
        this.arguments = arguments;
    }


    public Criteria toCriteria() {


        switch (RsqlSearchOperation.getSimpleOperator(operator)) {
            case EQUAL:
                return Criteria.where(property).is(arguments);
            case NOT_EQUAL:
                return Criteria.where(property).ne(arguments);
            case GREATER_THAN:
                return Criteria.where(property).gt(arguments);
            case GREATER_THAN_OR_EQUAL:
                return Criteria.where(property).gte(arguments);
            case LESS_THAN:
                return Criteria.where(property).lt(arguments);
            case LESS_THAN_OR_EQUAL:
                return Criteria.where(property).lte(arguments);
            case IN:
                return Criteria.where(property).in(arguments);
            case NOT_IN:
                return Criteria.where(property).nin(arguments);
            default:
                return new Criteria();
        }

    }

}
