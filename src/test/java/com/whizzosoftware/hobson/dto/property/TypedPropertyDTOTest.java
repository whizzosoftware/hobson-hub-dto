/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.property;

import com.whizzosoftware.hobson.api.property.TypedProperty;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class TypedPropertyDTOTest {
    @Test
    public void testToJSON() {
        TypedPropertyDTO dto = new TypedPropertyDTO.Builder("link").name("name").description("desc").type(TypedProperty.Type.SECURE_STRING).build();
        JSONObject json = dto.toJSON();
        assertEquals("link", json.getString("@id"));
        assertEquals("name", json.getString("name"));
        assertEquals("desc", json.getString("description"));
        assertEquals("SECURE_STRING", json.getString("type"));
    }
}
