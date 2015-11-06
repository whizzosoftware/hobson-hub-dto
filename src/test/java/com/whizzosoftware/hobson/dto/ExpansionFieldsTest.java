/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.junit.Test;
import static org.junit.Assert.*;

public class ExpansionFieldsTest {
    @Test
    public void testPushContext() {
        ExpansionFields ef = new ExpansionFields(null);
        assertFalse(ef.has("foo"));

        ef = new ExpansionFields("foo,c1.bar,c2.bat");
        assertTrue(ef.has("foo"));
        assertFalse(ef.has("bar"));
        assertFalse(ef.has("bat"));

        ef.pushContext("c1");
        assertFalse(ef.has("foo"));
        assertTrue(ef.has("bar"));
        assertFalse(ef.has("bat"));

        ef.popContext();
        assertTrue(ef.has("foo"));
        assertFalse(ef.has("bar"));
        assertFalse(ef.has("bat"));

        ef.pushContext("c2");
        assertFalse(ef.has("foo"));
        assertFalse(ef.has("bar"));
        assertTrue(ef.has("bat"));
    }

    @Test
    public void testAddWithExistingFields() {
        ExpansionFields ef = new ExpansionFields("foo,bar");
        ef.add("bat");
        assertTrue(ef.has("foo"));
        assertTrue(ef.has("bar"));
        assertTrue(ef.has("bat"));
    }

    @Test
    public void testHasWithNoNestedFields() {
        ExpansionFields ef = new ExpansionFields("foo,bar");
        assertTrue(ef.has("foo"));
        assertTrue(ef.has("bar"));
    }

    @Test
    public void testHasWithNestedFields() {
        ExpansionFields ef = new ExpansionFields("devices.item");
        assertTrue(ef.has("devices"));
        assertFalse(ef.has("item"));

        ef.pushContext("devices");
        assertTrue(ef.has("item"));

        ef = new ExpansionFields("devices.item.preferredVariable");
        assertTrue(ef.has("devices"));
        assertFalse(ef.has("item"));
        assertFalse(ef.has("preferredVariable"));

        ef.pushContext("devices");
        assertTrue(ef.has("item"));
        assertFalse(ef.has("preferredVariable"));

        ef.pushContext("item");
        assertTrue(ef.has("preferredVariable"));

        ef = new ExpansionFields("configurationClass.item");
        assertTrue(ef.has("configurationClass"));
        assertFalse(ef.has("configuration"));
    }
}
