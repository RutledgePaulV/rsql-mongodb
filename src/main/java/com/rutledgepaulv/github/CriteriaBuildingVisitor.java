package com.rutledgepaulv.github;

import cz.jirutka.rsql.parser.ast.*;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;
import java.util.stream.Collectors;

public class CriteriaBuildingVisitor extends NoArgRSQLVisitorAdapter<Criteria> {

    private ComparisonToCriteriaConverter converter;
    private Class<?> targetEntityType;

    public CriteriaBuildingVisitor(ComparisonToCriteriaConverter converter, Class<?> targetEntity) {
        this.converter = converter;
        this.targetEntityType = targetEntity;
    }

    @Override
    public Criteria visit(AndNode node) {
        Criteria parent = new Criteria();
        List<Criteria> children = getChildCriteria(node);
        return parent.andOperator(children.toArray(new Criteria[children.size()]));
    }

    @Override
    public Criteria visit(OrNode node) {
        Criteria parent = new Criteria();
        List<Criteria> children = getChildCriteria(node);
        return parent.orOperator(children.toArray(new Criteria[children.size()]));
    }

    @Override
    public Criteria visit(ComparisonNode node) {
        return converter.asCriteria(node, targetEntityType);
    }

    private List<Criteria> getChildCriteria(LogicalNode node) {
        return node.getChildren().stream().map(this::visit).collect(Collectors.toList());
    }

    private Criteria visit(Node node) {
        if (node instanceof AndNode) {
            return visit((AndNode) node);
        } else if (node instanceof OrNode) {
            return visit((OrNode) node);
        } else{
            return visit((ComparisonNode) node);
        }
    }

}
