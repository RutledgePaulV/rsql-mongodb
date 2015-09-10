/*
 * Copyright (c) 2015. Vodori, Inc.  All rights reserved.
 *
 * This software is not open source and license for its use and
 * modification can only be granted through an agreement with
 * Vodori, Inc.
 *
 * http://www.vodori.com
 */

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.springframework.data.mongodb.core.query.Criteria;

public class MongoQueryRsqlVisitor implements RSQLVisitor<Criteria, Void> {

    private MongoQueryRsqlAdapter adapter = new MongoQueryRsqlAdapter();

    public Criteria visit(AndNode andNode, Void aVoid) {
        return adapter.createSpecification(andNode);
    }

    public Criteria visit(OrNode orNode, Void aVoid) {
        return adapter.createSpecification(orNode);
    }

    public Criteria visit(ComparisonNode comparisonNode, Void aVoid) {
        return adapter.createSpecification(comparisonNode);
    }

}
