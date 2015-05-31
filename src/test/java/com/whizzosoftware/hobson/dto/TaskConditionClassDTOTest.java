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

import static org.junit.Assert.*;

public class TaskConditionClassDTOTest {
//    @Test
//    public void testConstructorWithClass() {
//        TaskConditionClass tcc = new TaskConditionClass(
//            TaskConditionClassContext.createLocal("plugin1", "conditionclass1"),
//            "cc",
//            Collections.singletonList(new TypedProperty("device", "Device", "A device", TypedProperty.Type.DEVICE))
//        );
//
//        ResourceClassDTO dto = new ResourceClassDTO(tcc);
//
//        assertNotNull(dto.getContext());
//        assertEquals("local", dto.getContext().getUserId());
//        assertEquals("local", dto.getContext().getHubId());
//        assertEquals("plugin1", dto.getContext().getPluginId());
//        assertEquals("conditionclass1", dto.getContext().getConditionClassId());
//
//        assertEquals("cc", dto.getName());
//
//        assertNotNull(dto.getProperties());
//        assertEquals(1, dto.getProperties().size());
//        assertEquals("device", dto.getProperties().get(0).getId());
//        assertEquals("Device", dto.getProperties().get(0).getName());
//        assertEquals("A device", dto.getProperties().get(0).getDescription());
//    }
//
//    @Test
//    public void testToJSON() {
//        TaskConditionClass tcc = new TaskConditionClass(
//                TaskConditionClassContext.createLocal("plugin1", "conditionclass1"),
//                "cc",
//                Collections.singletonList(new TypedProperty("device", "Device", "A device", TypedProperty.Type.DEVICE))
//        );
//        TaskConditionClassDTO dto = new TaskConditionClassDTO(tcc);
//
//        JSONObject json = dto.toJSON(null);
//        assertTrue(json.has("plugin"));
//        assertEquals("cc", json.getString("name"));
//
//        assertTrue(json.has("propertyDescriptors"));
//        JSONArray a = json.getJSONArray("propertyDescriptors");
//        assertEquals(1, a.length());
//        assertEquals("DEVICE", a.getJSONObject(0).getString("type"));
//        assertEquals("Device", a.getJSONObject(0).getString("name"));
//        assertEquals("A device", a.getJSONObject(0).getString("description"));
//    }

}
