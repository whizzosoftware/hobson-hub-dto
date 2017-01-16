/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.dto.context.ManagerDTOBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemListDTOTest {
    @Test
    public void testToJsonWithIdOnly() {
        ItemListDTO dto = new ItemListDTO("listLink");
        JSONObject json = dto.toJSON();
        assertEquals("listLink", json.getString(JSONAttributes.AID));
        assertFalse(json.has("numberOfItems"));
        assertFalse(json.has("itemListElement"));
    }

    @Test
    public void testToJsonWithForcedDetails() {
        ItemListDTO dto = new ItemListDTO("listLink2", true);
        JSONObject json = dto.toJSON();
        assertEquals(0, json.getInt("numberOfItems"));
        assertEquals(0, json.getJSONArray("itemListElement").length());
    }

    @Test
    public void testToJsonWithOneItem() {
        ManagerDTOBuildContext bctx = new ManagerDTOBuildContext();
        ItemListDTO dto = new ItemListDTO("listLink");
        dto.add(new HobsonUserDTO.Builder(bctx, new TemplatedId("personLink", null)).build());
        JSONObject json = dto.toJSON();
        assertEquals(1, json.getInt("numberOfItems"));
        JSONArray a = json.getJSONArray("itemListElement");
        assertEquals(1, a.length());
        JSONObject a1 = a.getJSONObject(0);
        assertTrue(a1.has("item"));
    }
}
