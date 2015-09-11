import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import static org.junit.Assert.assertEquals;

public class RSQLToMongoQuery {

    // basic operators
    @Test
    public void testEquals() {
        test("firstName==joe", "{ \"firstName\" : \"joe\"}");
    }

    @Test
    public void testNotEquals() {
        test("firstName!=joe","{ \"firstName\" : { \"$ne\" : \"joe\"}}");
    }

    @Test
    public void testGreaterThan() {
        test("createDate=gt=300", "{ \"createDate\" : { \"$gt\" : \"300\"}}");
    }


    @Test
    public void testGreaterThanOrEqualTo() {
        test("createDate=ge=300", "{ \"createDate\" : { \"$gte\" : \"300\"}}");
    }


    @Test
    public void testLessThan() {
        test("createDate=lt=300", "{ \"createDate\" : { \"$lt\" : \"300\"}}");
    }


    @Test
    public void testLessThanOrEqualTo() {
        test("createDate=le=300", "{ \"createDate\" : { \"$lte\" : \"300\"}}");
    }


    @Test
    public void testInListOfThings() {
        test("firstName=in=(billy,bob,joel)", "{ \"firstName\" : { \"$in\" : [ \"billy\" , \"bob\" , \"joel\"]}}");
    }

    @Test
    public void testNotInListOfThings() {
        test("firstName=out=(billy,bob,joel)", "{ \"firstName\" : { \"$nin\" : [ \"billy\" , \"bob\" , \"joel\"]}}");
    }


    // logical combos
    @Test
    public void testEqualityOfTwoAndedThings() {
        test("firstName!=joe;lastName==dummy", "{ \"$and\" : [ { \"firstName\" : { \"$ne\" : \"joe\"}} , { \"lastName\" : \"dummy\"}]}");
    }

    @Test
    public void testThingsThatAreOredTogether() {
        test("firstName!=joe,lastName==donovan","{ \"$or\" : [ { \"firstName\" : { \"$ne\" : \"joe\"}} , { \"lastName\" : \"donovan\"}]}");
    }

    @Test
    public void testAndingOfOringsOfAndings() {
        test("((firstName==joe;lastName==donovan),(firstName==aaron;lastName==carter));((age==21;height==90),(age==30;height==100))",
                "{ \"$and\" : [ { \"$or\" : [ { \"$and\" : [ { \"firstName\" : \"joe\"} , " +
                        "{ \"lastName\" : \"donovan\"}]} , { \"$and\" : [ { \"firstName\" : \"aaron\"} , " +
                        "{ \"lastName\" : \"carter\"}]}]} , { \"$or\" : [ { \"$and\" : [ { \"age\" : \"21\"} , " +
                        "{ \"height\" : \"90\"}]} , { \"$and\" : [ { \"age\" : \"30\"} , { \"height\" : \"100\"}]}]}]}");
    }



    private void test(String rsql, String mongo) {
        Node rootNode = new RSQLParser().parse(rsql);
        Criteria criteria = rootNode.accept(new CriteriaBuildingVisitor());
        Query query = Query.query(criteria);
        assertEquals(mongo, query.getQueryObject().toString());
    }

}
