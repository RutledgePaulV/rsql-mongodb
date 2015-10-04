package com.rutledgepaulv.github;

import com.rutledgepaulv.github.models.Person;
import org.junit.Test;

public class PersonLogicalComboQueriesTest extends BaseIntegrationTest<Person>{

    @Test
    public void testEqualityOfTwoAndedThings() {
        check("firstName!=joe;lastName==dummy", "{ \"$and\" : [ { \"firstName\" : { \"$ne\" : \"joe\"}} , { \"lastName\" : \"dummy\"}]}");
    }

    @Test
    public void testThingsThatAreOredTogether() {
        check("firstName!=john,lastName==doe","{ \"$or\" : [ { \"firstName\" : { \"$ne\" : \"john\"}} , { \"lastName\" : \"doe\"}]}");
    }

    @Test
    public void testAndingOfOringsOfAndings() {
        check("((firstName==john;lastName==doe),(firstName==aaron;lastName==carter));((age==21;height==90),(age==30;height==100))",
                "{ \"$and\" : [ { \"$or\" : [ { \"$and\" : [ { \"firstName\" : \"john\"} , " +
                        "{ \"lastName\" : \"doe\"}]} , { \"$and\" : [ { \"firstName\" : \"aaron\"} , " +
                        "{ \"lastName\" : \"carter\"}]}]} , { \"$or\" : [ { \"$and\" : [ { \"age\" : 21} , " +
                        "{ \"height\" : 90}]} , { \"$and\" : [ { \"age\" : 30} , { \"height\" : 100}]}]}]}");
    }
    

}
