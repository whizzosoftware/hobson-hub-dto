/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class HobsonUserDTOTest {
    @Test
    public void testToJson() {
        HobsonUserDTO dto = new HobsonUserDTO.Builder("personLink").familyName("Doe").givenName("John").hubs(new ItemListDTO("hubsLink")).build();
        JSONObject json = dto.toJSON();
        assertEquals("personLink", json.getString("@id"));
        assertEquals("John", json.getString("givenName"));
        assertEquals("Doe", json.getString("familyName"));
        assertEquals("John Doe", json.getString("name"));
        assertEquals("hubsLink", json.getJSONObject("hubs").getString("@id"));
    }
}
