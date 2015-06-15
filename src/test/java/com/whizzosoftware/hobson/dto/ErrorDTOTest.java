/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class ErrorDTOTest {
    @Test
    public void testToJSON() {
        ErrorsDTO dto = new ErrorsDTO.Builder(400, "Foo").build();
        JSONObject json = dto.toJSON();
        assertTrue(json.has("errors"));
        JSONArray a = json.getJSONArray("errors");
        assertEquals(1, a.length());
        JSONObject o = a.getJSONObject(0);
        assertEquals(400, o.getInt("code"));
        assertEquals("Foo", o.getString("message"));
    }
}
