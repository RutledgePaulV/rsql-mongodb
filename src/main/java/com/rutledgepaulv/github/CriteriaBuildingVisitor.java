package com.rutledgepaulv.github;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.springframework.data.mongodb.core.query.Criteria;

public class CriteriaBuildingVisitor implements RSQLVisitor<Criteria, Void> {

    TreeToCriteriaConverter converter;

    public CriteriaBuildingVisitor(TreeToCriteriaConverter converter) {
        this.converter = converter;
    }

    public Criteria visit(AndNode andNode, Void aVoid) {
        return converter.createCriteria(andNode);
    }

    public Criteria visit(OrNode orNode, Void aVoid) {
        return converter.createCriteria(orNode);
    }

    public Criteria visit(ComparisonNode comparisonNode, Void aVoid) {
        return converter.createCriteria(comparisonNode);
    }

}
