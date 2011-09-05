/*
 * Copyright (c) 2007-2011 Sonatype, Inc. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */

package org.sonatype.sisu.jacksbee.runtime;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Verifies {@link DateTimeAdapter} parsing.
 */
public class DateTimeAdapterTest
{
    @Test
    public void parseIso8601JacksonString() {
        String Iso8601StringFromJackson = "2011-02-03T19:25:53.656+0000";
        //String StringFromCalendar = "2011-02-03T14:25:38.649-05:00";
        DateTimeAdapter.parse(Iso8601StringFromJackson);
    }
    
    /**
     * Expect that the parsing test verifies accepted string formats that
     * the generator will produce.  If that passes then checking that output
     * formats can be parsed as input is acceptable verification of generator.
     */
    @Test
    public void generatedFormatIsParsable() {
        Date originalDate = new Date();
        String dateAsString = DateTimeAdapter.print(originalDate);
        Date parsedDate = DateTimeAdapter.parse(dateAsString);
        assertThat( parsedDate, equalTo( originalDate ) );
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidFormat() {
        DateTimeAdapter.parse("no a valid format");
    }
}
