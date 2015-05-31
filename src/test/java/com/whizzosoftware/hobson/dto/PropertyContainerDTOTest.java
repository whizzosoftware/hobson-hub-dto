/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import static org.junit.Assert.*;

public class PropertyContainerDTOTest {
    @Test
    public void testJSONConstructor() {
        JSONObject json = new JSONObject(new JSONTokener("{\"class\":{\"@id\":\"/api/v1/users/local/hubs/local/plugins/plugin1/actionClasses/actionclass1\"},\"values\":{\"foo\":\"bar\"}}"));
        PropertyContainerDTO dto = new PropertyContainerDTO(json);
        assertEquals("/api/v1/users/local/hubs/local/plugins/plugin1/actionClasses/actionclass1", dto.getContainerClass().getId());
        assertTrue(dto.hasPropertyValues());
        assertEquals(1, dto.getPropertyValues().size());
        assertEquals("bar", dto.getPropertyValues().get("foo"));
    }
}
