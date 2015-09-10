/*
 * Copyright (c) 2015. Vodori, Inc.  All rights reserved.
 *
 * This software is not open source and license for its use and
 * modification can only be granted through an agreement with
 * Vodori, Inc.
 *
 * http://www.vodori.com
 */

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.LogicalOperator;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class MongoQueryRsqlAdapter {

    public Criteria createSpecification(Node node) {
        if (node instanceof LogicalNode) {
            return createSpecification((LogicalNode) node);
        }
        if (node instanceof ComparisonNode) {
            return createSpecification((ComparisonNode) node);
        }
        return null;
    }


    public Criteria createSpecification(LogicalNode logicalNode) {
        List<Criteria> specs = new ArrayList<>();

        Criteria temp;
        for (Node node : logicalNode.getChildren()) {
            temp = createSpecification(node);
            if (temp != null) {
                specs.add(temp);
            }
        }

        Criteria result = specs.get(0);
        if (logicalNode.getOperator() == LogicalOperator.AND) {
            for (int i = 1; i < specs.size(); i++) {
                result = result.andOperator(specs.get(i));
            }
        } else if (logicalNode.getOperator() == LogicalOperator.OR) {
            for (int i = 1; i < specs.size(); i++) {
                result = result.orOperator(specs.get(i));
            }
        }

        return result;
    }

    public Criteria createSpecification(ComparisonNode comparisonNode) {
        return new RsqlCriteria(comparisonNode.getSelector(), comparisonNode.getOperator(),
                comparisonNode.getArguments()).toCriteria();
    }


}
