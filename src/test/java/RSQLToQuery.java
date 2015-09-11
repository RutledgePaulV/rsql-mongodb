/*
 * Copyright (c) 2015. Vodori, Inc.  All rights reserved.
 *
 * This software is not open source and license for its use and
 * modification can only be granted through an agreement with
 * Vodori, Inc.
 *
 * http://www.vodori.com
 */

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import static org.junit.Assert.assertEquals;

public class RSQLToQuery {


    @Test
    public void testEquality() {
        test("firstName==joe", "{ \"firstName\" : \"joe\"}");
    }

    @Test
    public void testEqualityOfTwoAndedThings() {
        test("firstName!=joe;lastName==dummy", "{ \"$and\" : [ { \"firstName\" : { \"$ne\" : \"joe\"}} , { \"lastName\" : \"dummy\"}]}");
    }

    @Test
    public void testInequality() {
        test("firstName!=joe","{ \"firstName\" : { \"$ne\" : \"joe\"}}");
    }

    @Test
    public void testThingsThatAreOredTogether() {
        test("firstName!=joe,lastName==donovan","{ \"$or\" : [ { \"firstName\" : { \"$ne\" : \"joe\"}} , { \"lastName\" : \"donovan\"}]}");
    }

    @Test
    public void testInListOfThings() {
        test("firstName=in=(billy,bob,joel)", "{ \"firstName\" : { \"$in\" : [ \"billy\" , \"bob\" , \"joel\"]}}");
    }



    private void test(String rsql, String mongo) {
        Node rootNode = new RSQLParser().parse(rsql);
        Criteria criteria = rootNode.accept(new CriteriaBuildingVisitor());
        Query query = Query.query(criteria);
        assertEquals(mongo, query.getQueryObject().toString());
    }

}
