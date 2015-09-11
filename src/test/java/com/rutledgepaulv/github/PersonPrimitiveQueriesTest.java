package com.rutledgepaulv.github;

import org.junit.Test;
import com.rutledgepaulv.github.models.*;



public class PersonPrimitiveQueriesTest extends BaseIntegrationTest<Person> {

    // basic operators
    @Test
    public void testEquals() {
        check("firstName==joe", "{ \"firstName\" : \"joe\"}");
    }

    @Test
    public void testNotEquals() {
        check("firstName!=joe","{ \"firstName\" : { \"$ne\" : \"joe\"}}");
    }

    @Test
    public void testGreaterThan() {
        check("age=gt=300", "{ \"age\" : { \"$gt\" : 300}}");
    }

    @Test
    public void testGreaterThanOrEqualTo() {
        check("age=ge=300", "{ \"age\" : { \"$gte\" : 300}}");
    }

    @Test
    public void testLessThan() {
        check("age=lt=300", "{ \"age\" : { \"$lt\" : 300}}");
    }

    @Test
    public void testLessThanOrEqualTo() {
        check("age=le=300", "{ \"age\" : { \"$lte\" : 300}}");
    }

    @Test
    public void testInListOfThings() {
        check("firstName=in=(billy,bob,joel)", "{ \"firstName\" : { \"$in\" : [ \"billy\" , \"bob\" , \"joel\"]}}");
    }

    @Test
    public void testNotInListOfThings() {
        check("firstName=out=(billy,bob,joel)", "{ \"firstName\" : { \"$nin\" : [ \"billy\" , \"bob\" , \"joel\"]}}");
    }

}
