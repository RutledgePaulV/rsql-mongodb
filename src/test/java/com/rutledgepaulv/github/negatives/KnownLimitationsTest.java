/*
 * Copyright (c) 2015. Vodori, Inc.  All rights reserved.
 *
 * This software is not open source and license for its use and
 * modification can only be granted through an agreement with
 * Vodori, Inc.
 *
 * http://www.vodori.com
 */
package com.rutledgepaulv.github.negatives;

import com.rutledgepaulv.github.BaseIntegrationTest;
import com.rutledgepaulv.github.models.Person;
import org.junit.Test;
import org.springframework.data.mapping.model.MappingException;

public class KnownLimitationsTest extends BaseIntegrationTest<Person> {

    @Test(expected = MappingException.class)
    public void testUsingFieldNameFailsToResolveRealPropertyPath() {
        check("aGoodFieldName==1", "");
    }

}
