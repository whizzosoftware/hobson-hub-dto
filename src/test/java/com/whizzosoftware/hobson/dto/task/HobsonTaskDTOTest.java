/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.task;

import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.persist.ContextPathIdProvider;
import com.whizzosoftware.hobson.api.plugin.PluginContext;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassContext;
import com.whizzosoftware.hobson.api.property.PropertyContainerSet;
import com.whizzosoftware.hobson.api.task.HobsonTask;
import com.whizzosoftware.hobson.api.task.MockTaskManager;
import com.whizzosoftware.hobson.api.task.TaskContext;
import com.whizzosoftware.hobson.dto.ExpansionFields;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.context.ManagerDTOBuildContext;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerSetDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class HobsonTaskDTOTest {
    @Test
    public void testToJSON() {
        HobsonTaskDTO dto = new HobsonTaskDTO.Builder("taskLink")
            .name("My Task")
            .description("Task Desc")
            .conditions(
                    Collections.singletonList(
                            new PropertyContainerDTO.Builder("conditionclass1")
                                    .containerClass(new PropertyContainerClassDTO.Builder("conditionClassLink").build())
                                    .values(Collections.singletonMap("foo", (Object) "bar"))
                                    .build()
                    )
            )
            .actionSet(new PropertyContainerSetDTO.Builder("actionSetLink").
                containers(Collections.singletonList(
                    new PropertyContainerDTO.Builder("pc1").
                        build())
                ).
                build()
            )
            .build();

        JSONObject json = dto.toJSON();
        assertEquals("taskLink", json.getString("@id"));
        assertEquals("My Task", json.getString("name"));

        assertTrue(json.has("conditions"));
        JSONArray jcs = json.getJSONArray("conditions");
        assertEquals(1, jcs.length());
        JSONObject jct = jcs.getJSONObject(0);
        assertTrue(jct.has("cclass"));
        assertEquals("conditionClassLink", jct.getJSONObject("cclass").getString("@id"));
        assertTrue(jct.has("values"));

        assertTrue(json.has("actionSet"));
        JSONObject jas = json.getJSONObject("actionSet");
        assertTrue(jas.has("@id"));
        assertEquals("actionSetLink", jas.getString("@id"));
        assertTrue(jas.has("actions"));
    }

    @Test
    public void testJSONConstructor() {
        JSONObject json = new JSONObject(new JSONTokener("{\"name\":\"My Task\",\"conditions\":[{\"cclass\":{\"@id\":\"/api/v1/users/local/hubs/local/plugins/plugin1/conditionClasses/conditionclass1\"},\"values\":{\"bar\":\"foo\"}}],\"actionSet\":{\"actions\":[{\"cclass\":{\"@id\":\"/api/v1/users/local/hubs/local/plugins/plugin1/actionClasses/actionclass1\"},\"values\":{\"foo\":\"bar\"}}]}}"));
        HobsonTaskDTO dto = new HobsonTaskDTO.Builder(json).build();
        assertEquals("My Task", dto.getName());

        assertNotNull(dto.getConditions());
        List<PropertyContainerDTO> csdto = dto.getConditions();
        assertEquals(1, csdto.size());

        assertNotNull(dto.getActionSet());
    }

    @Test
    public void testJSONConstructor2() {
        JSONObject json = new JSONObject(new JSONTokener("{\"name\":\"Turn on outlet\",\"conditions\":[{\"cclass\":{\"@id\":\"/api/v1/hubs/local/plugins/com.whizzosoftware.hobson.hub.hobson-hub-rules/conditionClasses/turnOn\"},\"values\":{\"devices\":[{\"@id\":\"/api/v1/hubs/local/plugins/com.whizzosoftware.hobson.hub.hobson-hub-sample/devices/bulb\"}]}}],\"actionSet\":{\"actions\":[{\"cclass\":{\"@id\":\"/api/v1/hubs/local/plugins/com.whizzosoftware.hobson.hub.hobson-hub-actions/actionClasses/turnOn\"},\"values\":{\"devices\":[{\"@id\":\"/api/v1/hubs/local/plugins/com.whizzosoftware.hobson.hub.hobson-hub-sample/devices/switch\"}]}}]}}"));
        HobsonTaskDTO dto = new HobsonTaskDTO.Builder(json).build();
    }

    @Test
    public void testExpandActionSet() {
        HubContext hctx = HubContext.createLocal();
        ExpansionFields expansions = new ExpansionFields("actionSet");

        MockTaskManager tm = new MockTaskManager();
        List<PropertyContainer> actions = new ArrayList<>();
        actions.add(new PropertyContainer(PropertyContainerClassContext.create(PluginContext.createLocal("plugin1"), "cc1"), Collections.singletonMap("foo", (Object)"bar")));
        String actionSetId = tm.publishActionSet(hctx, null, actions).getId();

        DTOBuildContext ctx = new ManagerDTOBuildContext.Builder().expansionFields(expansions).idProvider(new ContextPathIdProvider()).build();
        TaskContext tctx = TaskContext.createLocal("task1");
        List<PropertyContainer> conditions = new ArrayList<>();
        HobsonTask task = new HobsonTask(tctx, "Test", null, null, conditions, new PropertyContainerSet(actionSetId, actions));
        HobsonTaskDTO dto = new HobsonTaskDTO.Builder(ctx, task, true).build();
        assertEquals("Test", dto.getName());
        assertTrue(dto.getActionSet().getId().startsWith("hubs:local:actionSets:"));
        assertNotNull(dto.getActionSet().getContainers());
        assertEquals(1, dto.getActionSet().getContainers().size());
        assertEquals("hubs:local:plugins:plugin1:containerClasses:cc1", dto.getActionSet().getContainers().get(0).getContainerClass().getId());
        assertEquals(1, dto.getActionSet().getContainers().get(0).getValues().size());
        assertEquals("bar", dto.getActionSet().getContainers().get(0).getValues().get("foo"));
    }
}
