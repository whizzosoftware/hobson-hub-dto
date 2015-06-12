/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.task;

import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerSetDTO;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class HobsonTaskDTOTest {
    @Test
    public void testToJSON() {
        HobsonTaskDTO dto = new HobsonTaskDTO.Builder("taskLink")
            .name("My Task")
            .description("Task Desc")
            .conditionSet(new PropertyContainerSetDTO.Builder("conditionClassLink")
                            .primaryContainer(
                                    new PropertyContainerDTO.Builder("conditionclass1")
                                            .containerClass(new PropertyContainerClassDTO.Builder("conditionClassLink").build())
                                            .values(Collections.singletonMap("foo", (Object) "bar"))
                                            .build()
                            )
                            .build()
            )
            .actionSet(new PropertyContainerSetDTO.Builder("actionSetLink")
                            .build()
            )
            .build();

        JSONObject json = dto.toJSON();
        assertEquals("taskLink", json.getString("@id"));
        assertEquals("My Task", json.getString("name"));

        assertTrue(json.has("conditionSet"));
        JSONObject jcs = json.getJSONObject("conditionSet");
        assertTrue(jcs.has("trigger"));
        assertFalse(jcs.has("conditions"));
        JSONObject jct = jcs.getJSONObject("trigger");
        assertTrue(jct.has("cclass"));
        assertEquals("conditionClassLink", jct.getJSONObject("cclass").getString("@id"));
        assertTrue(jct.has("values"));

        assertTrue(json.has("actionSet"));
        JSONObject jas = json.getJSONObject("actionSet");
        assertFalse(jas.has("actions"));
        assertTrue(jas.has("@id"));
        assertEquals("actionSetLink", jas.getString("@id"));
    }

    @Test
    public void testJSONConstructor() {
        JSONObject json = new JSONObject(new JSONTokener("{\"name\":\"My Task\",\"conditionSet\":{\"trigger\":{\"cclass\":{\"@id\":\"/api/v1/users/local/hubs/local/plugins/plugin1/conditionClasses/conditionclass1\"},\"values\":{\"bar\":\"foo\"}}},\"actionSet\":{\"actions\":[{\"cclass\":{\"@id\":\"/api/v1/users/local/hubs/local/plugins/plugin1/actionClasses/actionclass1\"},\"values\":{\"foo\":\"bar\"}}]}}"));
        HobsonTaskDTO dto = new HobsonTaskDTO.Builder(json).build();
        assertEquals("My Task", dto.getName());

        assertNotNull(dto.getConditionSet());
        PropertyContainerSetDTO csdto = dto.getConditionSet();
        assertTrue(csdto.hasPrimaryContainer());
        assertFalse(csdto.hasContainers());

        assertNotNull(dto.getActionSet());
        csdto = dto.getActionSet();
        assertFalse(csdto.hasPrimaryContainer());
        assertTrue(csdto.hasContainers());
    }
}
