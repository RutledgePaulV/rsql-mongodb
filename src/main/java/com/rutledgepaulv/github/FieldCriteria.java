package com.rutledgepaulv.github;


import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class FieldCriteria {

    private String property;
    private ComparisonOperator operator;
    private List<Object> arguments;

    public FieldCriteria(String property, ComparisonOperator operator, List<Object> arguments) {
        this.property = property;
        this.operator = operator;
        this.arguments = arguments;
    }

    public Criteria toCriteria() {
        if(arguments.isEmpty()) {
            return null;
        }

        Operators mongoOperator = Operators.toOperator(operator);

        if(arguments.size() == 1 && mongoOperator != Operators.IN && mongoOperator != Operators.NOT_IN) {
            Object first = arguments.get(0);
            switch (mongoOperator) {
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
                default:
                    return null;
            }
        } else if (mongoOperator == Operators.IN || mongoOperator == Operators.NOT_IN) {

            switch(mongoOperator) {
                case IN:
                    return Criteria.where(property).in(arguments);
                case NOT_IN:
                    return Criteria.where(property).nin(arguments);
                default: return null;
            }

        } else {
            throw new RuntimeException("Unsupported argument / operator configuration. Bad query.");
        }

    }

}
