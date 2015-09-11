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

import java.util.Calendar;

import static org.junit.Assert.assertNotNull;

public class PersonCustomTypeMappingConversionsTest extends BaseIntegrationTest<Person> {

    @Test
    public void testCustomConvertersCanBeRegisteredToConvertStringsToCalendars() {
        check("dateOfBirth==2001-07-04T12:08:56.235-0700", query -> {
            Calendar calendar = (Calendar) query.get("dateOfBirth");
            assertNotNull(calendar);
        });
    }

}
