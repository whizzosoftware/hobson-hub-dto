/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.json;

import com.whizzosoftware.hobson.api.HobsonInvalidRequestException;
import com.whizzosoftware.hobson.api.config.EmailConfiguration;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.hub.PasswordChange;
import com.whizzosoftware.hobson.api.task.*;
import com.whizzosoftware.hobson.api.task.condition.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JSONSerializationHelperTest {
//    @Test
//    public void testCreateHubLocation() {
//        // test text only
//        JSONObject json = new JSONObject();
//        json.put("text", "my_address");
//        HubLocation loc = com.whizzosoftware.hobson.json.JSONSerializationHelper.createHubLocation(json);
//        assertEquals("my_address", loc.getText());
//        assertNull(loc.getLatitude());
//        assertNull(loc.getLongitude());
//
//        // test lat/long only
//        json = new JSONObject();
//        json.put("latitude", "39.3722");
//        json.put("longitude", "-104.8561");
//        loc = JSONSerializationHelper.createHubLocation(json);
//        assertNull(loc.getText());
//        assertEquals(39.3722, loc.getLatitude(), 4);
//        assertEquals(-104.8561, loc.getLongitude(), 4);
//    }

    @Test
    public void testCreateEmailConfiguration() {
        JSONObject json = new JSONObject(new JSONTokener("{\"server\": null, \"secure\": null, \"username\": null, \"password\": null, \"senderAddress\": null}"));
        EmailConfiguration config = JSONSerializationHelper.createEmailConfiguration(json);
        assertNull(config.getServer());
        assertNull(config.getUsername());
        assertNull(config.getPassword());
        assertNull(config.getSenderAddress());
        assertNull(config.isSecure());
    }

    @Test
    public void testPasswordChange() {
        JSONObject json = new JSONObject(new JSONTokener("{\"currentPassword\":\"foo\", \"newPassword\":\"bar\"}"));
        PasswordChange pc = JSONSerializationHelper.createPasswordChange(json);
        assertEquals("foo", pc.getCurrentPassword());
        assertEquals("bar", pc.getNewPassword());

        try {
            json = new JSONObject(new JSONTokener("{\"currentPwd\":\"foo\", \"newPwd\":\"bar\"}"));
            JSONSerializationHelper.createPasswordChange(json);
            fail("Should have thrown exception");
        } catch (HobsonInvalidRequestException e) {
        }
    }

    @Test
    public void testCreateTask() {
//        MockTaskManager taskManager = new MockTaskManager();
//        taskManager.publishConditionClass(
//            TaskConditionClassContext.create(HubContext.createLocal(), "foo", "foo"),
//            "foo",
//            null
//        );
//
//        // test with no name
//        try {
//            JSONSerializationHelper.createTask(HubContext.createLocal(), taskManager, new JSONObject(new JSONTokener("{}")));
//            fail("Should have thrown exception");
//        } catch (HobsonInvalidRequestException ignored) {
//        }
//
//        // test with no trigger condition
//        try {
//            JSONSerializationHelper.createTask(HubContext.createLocal(), taskManager, new JSONObject(new JSONTokener("{\"name\":\"Foo\"}")));
//            fail("Should have thrown exception");
//        } catch (HobsonInvalidRequestException ignored) {
//        }
//
//        // test with no actions
//        try {
//            JSONSerializationHelper.createTask(HubContext.createLocal(), taskManager, new JSONObject(new JSONTokener("{\"name\":\"Foo\",\"triggerCondition\":{\"pluginId\":\"foo\",\"conditionClassId\":\"foo\"}}")));
//            fail("Should have thrown exception");
//        } catch (HobsonInvalidRequestException ignored) {
//        }
//
//        // test with empty actions array
//        try {
//            JSONSerializationHelper.createTask(HubContext.createLocal(), taskManager, new JSONObject(new JSONTokener("{\"name\":\"Foo\",\"triggerCondition\":{\"pluginId\":\"foo\",\"conditionClassId\":\"foo\",\"properties\": {}},\"actions\":[]}")));
//            fail("Should have thrown exception");
//        } catch (HobsonInvalidRequestException ignored) {
//        }
    }

    @Test
    public void testCreateTaskActions() {
//        TaskManager taskManager = new MockTaskManager();
//        taskManager.publishActionClass(new TaskActionClass() {
//            @Override
//            public TaskActionClassContext getContext() {
//                return TaskActionClassContext.create(HubContext.createLocal(), "plugin", "turnOn");
//            }
//
//            @Override
//            public String getId() {
//                return "turnOn";
//            }
//
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public boolean hasProperties() {
//                return true;
//            }
//
//            @Override
//            public List<TypedProperty> getProperties() {
//                return Collections.singletonList(new TypedProperty("devices", null, null, TypedProperty.Type.DEVICES));
//            }
//
//            @Override
//            public TaskAction createAction(String id, Map<String, Object> propertyValues) {
//                return null;
//            }
//        });
//
//        JSONArray a = new JSONArray(new JSONTokener("[{\"pluginId\":\"plugin\",\"actionClassId\":\"turnOn\",\"properties\":{\"devices\":{\"value\":[{\"pluginId\":\"plugin1\",\"deviceId\":\"device1\"},{\"pluginId\":\"plugin2\",\"deviceId\":\"device2\"}]}}}]"));
//        List<TaskActionDTO> actions = JSONSerializationHelper.createTaskActions(HubContext.createLocal(), taskManager, a);
//        assertEquals(1, actions.size());
//        TaskActionDTO action = actions.get(0);
//        assertNotNull(action.getPropertyValues());
//        assertEquals(1, action.getPropertyValues().size());
//        Object o = action.getPropertyValues().get("devices");
//        assertNotNull(o);
//        assertTrue(o instanceof List);
//        assertTrue(((List)o).get(0) instanceof DeviceContext);
    }

//    @Test
//    public void testCreateTaskJSONWithNoExpansion() {
//        HubContext ctx = HubContext.createLocal();
//        MockTaskManager taskManager = new MockTaskManager();
//        TaskCondition tc = new TaskCondition(TaskConditionClassContext.create(HubContext.createLocal(), "plugin1", "conditionclass1"), Collections.singletonMap("foo", (Object)"bar"), null);
//        TaskConditionSet cs = new TaskConditionSet(tc, null);
//        TaskActionSet tas = new TaskActionSet(ctx, "actionset1", null);
//        HobsonTask task = new HobsonTask(TaskContext.createLocal("plugin", "task1"), "Test Task", "Task Desc", null, cs, tas);
//
//        JSONObject json = JSONSerializationHelper.createTaskJSON(ctx, taskManager, task, null);
//        assertEquals("Test Task", json.getString("name"));
//
//        assertTrue(json.has("conditionSet"));
//        JSONObject jcs = json.getJSONObject("conditionSet");
//        assertTrue(jcs.has("trigger"));
//        assertFalse(jcs.has("conditions"));
//
//        JSONObject jtc = jcs.getJSONObject("trigger");
//        assertTrue(jtc.has("conditionClass"));
//        JSONObject jcc = jtc.getJSONObject("conditionClass");
//        assertEquals("conditionclass1", jcc.getString("@conditionClassId"));
//        assertTrue(jtc.has("properties"));
//        assertEquals("bar", jtc.getJSONObject("properties").getString("foo"));
//
////        assertTrue(json.has("actionSet"));
////        JSONObject jas = json.getJSONObject("actionSet");
////        assertEquals("actionset1", jas.getString("@actionSetId"));
//
//        assertFalse(json.has("properties"));
//    }

//    @Test
//    public void testCreateTaskJSONWithExpansion() {
//        HubContext ctx = HubContext.createLocal();
//        MockTaskManager taskManager = new MockTaskManager();
//
//        TaskActionClassContext tac = TaskActionClassContext.createLocal("plugin2", "actionclass1");
//        taskManager.publishActionClass(tac, "Test Action Class", null, null);
//
//        TaskActionSet tas = taskManager.publishActionSet(ctx, null, Arrays.asList((TaskAction)new TaskAction(tac, "actionset1", Collections.singletonMap("foo", (Object)"bar"), null)));
//
//        TaskCondition tc = new TaskCondition(TaskConditionClassContext.create(HubContext.createLocal(), "plugin1", "conditionclass1"), Collections.singletonMap("foo", (Object)"bar"), null);
//        TaskConditionSet cs = new TaskConditionSet(tc, null);
//        HobsonTask task = new HobsonTask(TaskContext.createLocal("plugin", "task1"), "Test Task", "Task Desc", null, cs, tas);
//
//        ExpansionFields expansionFields = new ExpansionFields("actionSet");
//        JSONObject json = JSONSerializationHelper.createTaskJSON(ctx, taskManager, task, expansionFields);
//        assertEquals("Test Task", json.getString("name"));
//
//        assertTrue(json.has("conditionSet"));
//        JSONObject jcs = json.getJSONObject("conditionSet");
//        assertTrue(jcs.has("trigger"));
//        assertFalse(jcs.has("conditions"));
//
//        JSONObject jtc = jcs.getJSONObject("trigger");
//        assertTrue(jtc.has("conditionClass"));
//        JSONObject jcc = jtc.getJSONObject("conditionClass");
//        assertEquals("conditionclass1", jcc.getString("@conditionClassId"));
//        assertTrue(jtc.has("properties"));
//        assertEquals("bar", jtc.getJSONObject("properties").getString("foo"));
//
////        assertTrue(json.has("actionSet"));
////        JSONObject jas = json.getJSONObject("actionSet");
////        assertEquals(tas.getId(), jas.getString("@actionSetId"));
////        assertTrue(jas.has("actions"));
////        JSONArray jac = jas.getJSONArray("actions");
////        assertEquals(1, jac.length());
//
////        JSONObject ja = jac.getJSONObject(0);
////        assertTrue(ja.has("plugin"));
////        assertEquals("plugin2", ja.getJSONObject("plugin").get("@pluginId"));
////        assertTrue(ja.has("actionClass"));
////        assertEquals("actionclass1", ja.getJSONObject("actionClass").get("@actionClassId"));
//
//        assertFalse(json.has("properties"));
//
//        System.out.println(json);
//    }
}
