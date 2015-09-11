/*
 * Copyright (c) 2015. Vodori, Inc.  All rights reserved.
 *
 * This software is not open source and license for its use and
 * modification can only be granted through an agreement with
 * Vodori, Inc.
 *
 * http://www.vodori.com
 */
package com.rutledgepaulv.github;

import com.rutledgepaulv.github.models.Person;
import org.junit.Test;

public class PersonLogicalComboQueries extends BaseIntegrationTest<Person>{

    @Test
    public void testEqualityOfTwoAndedThings() {
        check("firstName!=joe;lastName==dummy", "{ \"$and\" : [ { \"firstName\" : { \"$ne\" : \"joe\"}} , { \"lastName\" : \"dummy\"}]}");
    }

    @Test
    public void testThingsThatAreOredTogether() {
        check("firstName!=joe,lastName==donovan","{ \"$or\" : [ { \"firstName\" : { \"$ne\" : \"joe\"}} , { \"lastName\" : \"donovan\"}]}");
    }

    @Test
    public void testAndingOfOringsOfAndings() {
        check("((firstName==joe;lastName==donovan),(firstName==aaron;lastName==carter));((age==21;height==90),(age==30;height==100))",
                "{ \"$and\" : [ { \"$or\" : [ { \"$and\" : [ { \"firstName\" : \"joe\"} , " +
                        "{ \"lastName\" : \"donovan\"}]} , { \"$and\" : [ { \"firstName\" : \"aaron\"} , " +
                        "{ \"lastName\" : \"carter\"}]}]} , { \"$or\" : [ { \"$and\" : [ { \"age\" : 21} , " +
                        "{ \"height\" : 90}]} , { \"$and\" : [ { \"age\" : 30} , { \"height\" : 100}]}]}]}");
    }
    

}
