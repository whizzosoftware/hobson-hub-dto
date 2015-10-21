/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.hub;

import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class HobsonHubDTOTest {
    @Test
    public void testJSONConstructor() {
        JSONObject json = new JSONObject();
        json.put("name", "Test Hub");
        HobsonHubDTO dto = new HobsonHubDTO.Builder(json).build();
        assertEquals("Test Hub", dto.getName());
    }

    @Test
    public void testToJSON() {
        HobsonHubDTO dto = new HobsonHubDTO.Builder("hubLink").name("hubName").version("hubVersion").build();
        JSONObject json = dto.toJSON();
        assertEquals("hubLink", json.getString("@id"));
        assertEquals("hubName", json.getString("name"));
        assertEquals("hubVersion", json.getString("version"));
    }
}
