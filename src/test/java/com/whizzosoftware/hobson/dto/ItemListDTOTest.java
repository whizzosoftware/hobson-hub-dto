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

public class ItemListDTOTest {
    @Test
    public void testToJsonWithNoItems() {
        ItemListDTO dto = new ItemListDTO("listLink");
        JSONObject json = dto.toJSON();
        assertFalse(json.has("numberOfItems"));
    }

    @Test
    public void testToJsonWithOneItem() {
        ItemListDTO dto = new ItemListDTO("listLink");
        dto.add(new PersonDTO.Builder("personLink").build());
        JSONObject json = dto.toJSON();
        assertEquals(1, json.getDouble("numberOfItems"), 0);
        JSONArray a = json.getJSONArray("itemListElement");
        assertEquals(1, a.length());
        JSONObject a1 = a.getJSONObject(0);
        assertTrue(a1.has("item"));
    }
}
