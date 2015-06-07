/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.json;

import com.whizzosoftware.hobson.api.HobsonInvalidRequestException;
import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.plugin.PluginContext;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassContext;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.task.TaskContext;
import com.whizzosoftware.hobson.dto.LinkProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class TypedPropertyValueSerializerTest {
    @Test
    public void testCreateNumberObject() {
        // test that passing in a JSON array will fail
        try {
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.NUMBER, new JSONArray(new JSONTokener("[]")), null);
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a JSON string will fail
        JSONObject json = null;
        try {
            json = new JSONObject(new JSONTokener("{\"value\": \"test\"}"));
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.NUMBER, json.get("value"), null);
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a JSON integer number will succeed
        json = new JSONObject(new JSONTokener("{\"value\": 21}"));
        Object o = TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.NUMBER, json.get("value"), null);
        assertTrue(o instanceof Integer);
        assertEquals(21, o);

        // test that passing in a JSON double number will succeed
        json = new JSONObject(new JSONTokener("{\"value\": 21.5}"));
        o = TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.NUMBER, json.get("value"), null);
        assertTrue(o instanceof Double);
        assertEquals(21.5, o);
    }

    @Test
    public void testCreateStringObject() {
        // test that passing in a JSON array will fail
        try {
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.STRING, new JSONArray(new JSONTokener("[]")), null);
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a JSON string will succeed
        JSONObject json = new JSONObject(new JSONTokener("{\"value\": \"test\"}"));
        Object o = TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.STRING, json.get("value"), null);
        assertTrue(o instanceof String);
        assertEquals("test", o);

        // test that passing in a JSON number will succeed
        json = new JSONObject(new JSONTokener("{\"value\": 21}"));
        o = TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.STRING, json.get("value"), null);
        assertTrue(o instanceof String);
        assertEquals("21", o);

        // test that passing in a JSON boolean will succeed
        json = new JSONObject(new JSONTokener("{\"value\": true}"));
        o = TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.STRING, json.get("value"), null);
        assertTrue(o instanceof String);
        assertEquals("true", o);
    }

    @Test
    public void testCreateDeviceObject() {
        // test that passing in a JSON array will fail
        try {
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.DEVICE, new JSONArray(new JSONTokener("[{\"pluginId\":\"plugin1\",\"deviceId\":\"device1\"}]")), null);
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in an object containing invalid properties fails
        try {
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.DEVICE, new JSONObject(new JSONTokener("{\"d\":\"device1\"}")), null);
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a valid JSON object succeeds
        JSONObject json = new JSONObject(new JSONTokener("{\"@id\":\"device1\"}"));
        Object o = TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.DEVICE, json, new MockLinkProvider());
        assertTrue(o instanceof DeviceContext);
        assertEquals("plugin1", ((DeviceContext)o).getPluginId());
        assertEquals("device1", ((DeviceContext)o).getDeviceId());
    }

    @Test
    public void testCreateDevicesObject() {
        // test that passing in a regular JSON object will fail
        try {
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.DEVICES, new JSONObject(new JSONTokener("{\"pluginId\":\"plugin1\",\"deviceId\":\"device1\"}")), null);
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in an array of arrays fails
        try {
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.DEVICES, new JSONArray(new JSONTokener("[[{\"pluginId\":\"plugin1\",\"deviceId\":\"device1\"}]]")), new MockLinkProvider());
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in an array containing objects with invalid properties fails
        try {
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.DEVICES, new JSONArray(new JSONTokener("[{\"d\":\"device1\"}]")), new MockLinkProvider());
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a JSON array succeeds
        JSONArray json = new JSONArray(new JSONTokener("[{\"@id\":\"device1\"},{\"@id\":\"device2\"}]"));
        Object o = TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.DEVICES, json, new MockLinkProvider());
        assertTrue(o instanceof List);
        assertEquals(2, ((List)o).size());

        DeviceContext ctx = (DeviceContext)((List)o).get(0);
        assertEquals("plugin1", ctx.getPluginId());
        assertEquals("device1", ctx.getDeviceId());

        ctx = (DeviceContext)((List)o).get(1);
        assertEquals("plugin1", ctx.getPluginId());
        assertEquals("device2", ctx.getDeviceId());
    }

    public class MockLinkProvider implements LinkProvider {

        @Override
        public HubContext createHubContext(String link) {
            return null;
        }

        @Override
        public String createTaskLink(TaskContext ctx) {
            return null;
        }

        @Override
        public TaskContext createTaskContext(String link) {
            return null;
        }

        @Override
        public String createTaskConditionClassesLink(HubContext ctx) {
            return null;
        }

        @Override
        public String createTaskConditionClassLink(PropertyContainerClassContext ctx) {
            return null;
        }

        @Override
        public PropertyContainerClassContext createTaskConditionClassContext(String link) {
            return null;
        }

        @Override
        public String createTaskActionClassesLink(HubContext ctx) {
            return null;
        }

        @Override
        public String createTaskActionClassLink(PropertyContainerClassContext ctx) {
            return null;
        }

        @Override
        public PropertyContainerClassContext createTaskActionClassContext(String link) {
            return null;
        }

        @Override
        public String createTaskActionSetLink(HubContext ctx, String actionSetId) {
            return null;
        }

        @Override
        public PluginContext createPluginContext(String link) {
            return null;
        }

        @Override
        public String createUserLink(String id) {
            return null;
        }

        @Override
        public String createHubLink(HubContext context) {
            return null;
        }

        @Override
        public String createHubsLink(String userId) {
            return null;
        }

        @Override
        public String createHubConfigurationClassLink(HubContext context) {
            return null;
        }

        @Override
        public String createHubConfigurationLink(HubContext context) {
            return null;
        }

        @Override
        public String createPropertyContainerLink(HubContext context, int type) {
            return null;
        }

        @Override
        public String createPropertyContainerClassLink(int type, PropertyContainerClassContext pccc) {
            return null;
        }

        @Override
        public String createTasksLink(HubContext context) {
            return null;
        }

        @Override
        public String createDevicesLink(HubContext context) {
            return null;
        }

        @Override
        public String createLocalPluginLink(PluginContext context) {
            return null;
        }

        @Override
        public String createLocalPluginsLink(HubContext context) {
            return null;
        }

        @Override
        public String createRemotePluginLink(PluginContext context) {
            return null;
        }

        @Override
        public String createRemotePluginsLink(HubContext context) {
            return null;
        }

        @Override
        public String createDeviceLink(DeviceContext context) {
            return null;
        }

        @Override
        public String createDeviceVariableLink(DeviceContext context, String preferredVariableName) {
            return null;
        }

        @Override
        public String createDeviceConfigurationLink(DeviceContext context) {
            return null;
        }

        @Override
        public String createDeviceConfigurationClassLink(DeviceContext context) {
            return null;
        }

        @Override
        public String createLocalPluginConfigurationLink(PluginContext pctx) {
            return null;
        }

        @Override
        public String createLocalPluginConfigurationClassLink(PluginContext pctx) {
            return null;
        }

        @Override
        public String createDeviceVariablesLink(DeviceContext context) {
            return null;
        }

        @Override
        public String createRemotePluginInstallLink(PluginContext pctx, String version) {
            return null;
        }

        @Override
        public String createTaskActionSetsLink(HubContext hubContext) {
            return null;
        }

        @Override
        public DeviceContext createDeviceContext(String deviceId) {
            return DeviceContext.createLocal("plugin1", deviceId);
        }
    }
}
