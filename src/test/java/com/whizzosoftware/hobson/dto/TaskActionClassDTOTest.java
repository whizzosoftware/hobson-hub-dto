/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.property.TypedProperty;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TaskActionClassDTOTest {
//    @Test
//    public void testConstructorWithClass() {
//        TaskActionClass tac = new TaskActionClass(
//            TaskActionClassContext.createLocal("plugin1", "actionclass1"),
//            "cc",
//            Collections.singletonList(new TypedProperty("message", "Message", "A message", TypedProperty.Type.STRING)),
//            null
//        );
//
//        TaskActionClassDTO dto = new TaskActionClassDTO(tac);
//
//        assertNotNull(dto.getContext());
//        assertEquals("local", dto.getContext().getUserId());
//        assertEquals("local", dto.getContext().getHubId());
//        assertEquals("plugin1", dto.getContext().getPluginId());
//        assertEquals("actionclass1", dto.getContext().getActionClassId());
//
//        assertEquals("cc", dto.getName());
//
//        assertNotNull(dto.getSupportedProperties());
//        assertEquals(1, dto.getSupportedProperties().size());
//        assertEquals("message", dto.getSupportedProperties().get(0).getId());
//        assertEquals("Message", dto.getSupportedProperties().get(0).getName());
//        assertEquals("A message", dto.getSupportedProperties().get(0).getDescription());
//        assertEquals(TypedProperty.Type.STRING, dto.getSupportedProperties().get(0).getType());
//    }
//
//    @Test
//    public void testToJSON() {
//        TaskActionClass tac = new TaskActionClass(
//            TaskActionClassContext.createLocal("plugin1", "actionclass1"),
//            "cc",
//            Collections.singletonList(new TypedProperty("message", "Message", "A message", TypedProperty.Type.STRING)),
//            null
//        );
//
//        TaskActionClassDTO dto = new TaskActionClassDTO(tac);
//
//        JSONObject json = dto.toJSON(null);
//        assertTrue(json.has("plugin"));
//        assertEquals("cc", json.getString("name"));
//
//        assertTrue(json.has("propertyDescriptors"));
//        JSONArray a = json.getJSONArray("propertyDescriptors");
//        assertEquals(1, a.length());
//        assertEquals("STRING", a.getJSONObject(0).getString("type"));
//        assertEquals("Message", a.getJSONObject(0).getString("name"));
//        assertEquals("A message", a.getJSONObject(0).getString("description"));
//    }
}
