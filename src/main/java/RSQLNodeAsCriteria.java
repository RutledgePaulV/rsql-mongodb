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

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class RSQLNodeAsCriteria {

    enum Operators {
        EQUAL(RSQLOperators.EQUAL),
        NOT_EQUAL(RSQLOperators.NOT_EQUAL),
        GREATER_THAN(RSQLOperators.GREATER_THAN),
        GREATER_THAN_OR_EQUAL(RSQLOperators.GREATER_THAN_OR_EQUAL),
        LESS_THAN(RSQLOperators.LESS_THAN),
        LESS_THAN_OR_EQUAL(RSQLOperators.LESS_THAN_OR_EQUAL),
        IN(RSQLOperators.IN),
        NOT_IN(RSQLOperators.NOT_IN);

        private ComparisonOperator operator;
        Operators(ComparisonOperator operator) {
            this.operator = operator;
        }

        public static Operators toOperator(ComparisonOperator operator) {
            return Arrays.stream(values())
                    .filter(value -> value.operator.equals(operator))
                    .findFirst().orElse(null);
        }
    }


    private String property;
    private ComparisonOperator operator;
    private List<String> arguments;

    public RSQLNodeAsCriteria(String property, ComparisonOperator operator, List<String> arguments) {
        this.property = property;
        this.operator = operator;
        this.arguments = arguments;
    }


    public Criteria toCriteria() {
        if(arguments.isEmpty()) {
            return null;
        }

        String first = arguments.get(0);

        switch (Operators.toOperator(operator)) {
            case EQUAL:
                return Criteria.where(property).is(first);
            case NOT_EQUAL:
                return Criteria.where(property).ne(first);
            case GREATER_THAN:
                return Criteria.where(property).gt(first);
            case GREATER_THAN_OR_EQUAL:
                return Criteria.where(property).gte(first);
            case LESS_THAN:
                return Criteria.where(property).lt(first);
            case LESS_THAN_OR_EQUAL:
                return Criteria.where(property).lte(first);
            case IN:
                return Criteria.where(property).in(arguments);
            case NOT_IN:
                return Criteria.where(property).nin(arguments);
            default:
                return null;
        }

    }

}
