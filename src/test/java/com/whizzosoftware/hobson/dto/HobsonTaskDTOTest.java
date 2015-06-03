/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.task.TaskContext;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Collections;

public class HobsonTaskDTOTest {
    @Test
    public void testToJSON() {
        TaskContext tc = TaskContext.createLocal("plugin1", "task1");
//        TaskConditionSet tcs = new TaskConditionSet(new TaskCondition(TaskConditionClassContext.createLocal("plugin1", "conditionclass1"), Collections.singletonMap("foo", (Object)"bar"), null), null);
//        TaskActionSet tas = new TaskActionSet(HubContext.createLocal(), "actionset1", null);
//        HobsonTask task = new HobsonTask(tc, "My Task", "Task Desc", null, tcs, tas);
//        HobsonTaskDTO dto = new HobsonTaskDTO(task, links);

//        JSONObject json = dto.toJSON(links);
//        assertEquals("taskLink", json.getString("@id"));
//        assertEquals("My Task", json.getString("name"));
//        assertTrue(json.has("plugin"));

//        assertTrue(json.has("conditionSet"));
//        JSONObject jcs = json.getJSONObject("conditionSet");
//        assertTrue(jcs.has("trigger"));
//        assertFalse(jcs.has("conditions"));
//        JSONObject jct = jcs.getJSONObject("trigger");
//        assertTrue(jct.has("conditionClass"));
//        assertEquals("conditionClassLink", jct.getJSONObject("conditionClass").getString("@id"));
//        assertTrue(jct.has("properties"));

//        assertTrue(json.has("actionSet"));
//        JSONObject jas = json.getJSONObject("actionSet");
//        assertFalse(jas.has("actions"));
//        assertTrue(jas.has("@id"));
//        assertEquals("actionSetLink", jas.getString("@id"));
    }

    @Test
    public void testJSONConstructor() {
        JSONObject json = new JSONObject(new JSONTokener("{\"name\":\"My Task\",\"conditionSet\":{\"trigger\":{\"cclass\":{\"@id\":\"/api/v1/users/local/hubs/local/plugins/plugin1/conditionClasses/conditionclass1\"},\"values\":{\"bar\":\"foo\"}}},\"actionSet\":{\"actions\":[{\"cclass\":{\"@id\":\"/api/v1/users/local/hubs/local/plugins/plugin1/actionClasses/actionclass1\"},\"values\":{\"foo\":\"bar\"}}]}}"));
        HobsonTaskDTO dto = new HobsonTaskDTO(json);
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
