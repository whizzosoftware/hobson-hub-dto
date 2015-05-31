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
import com.whizzosoftware.hobson.api.property.TypedProperty;
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
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.NUMBER, new JSONArray(new JSONTokener("[]")));
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a JSON string will fail
        JSONObject json = null;
        try {
            json = new JSONObject(new JSONTokener("{\"value\": \"test\"}"));
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.NUMBER, json.get("value"));
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a JSON integer number will succeed
        json = new JSONObject(new JSONTokener("{\"value\": 21}"));
        Object o = TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.NUMBER, json.get("value"));
        assertTrue(o instanceof Integer);
        assertEquals(21, o);

        // test that passing in a JSON double number will succeed
        json = new JSONObject(new JSONTokener("{\"value\": 21.5}"));
        o = TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.NUMBER, json.get("value"));
        assertTrue(o instanceof Double);
        assertEquals(21.5, o);
    }

    @Test
    public void testCreateStringObject() {
        // test that passing in a JSON array will fail
        try {
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.STRING, new JSONArray(new JSONTokener("[]")));
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a JSON string will succeed
        JSONObject json = new JSONObject(new JSONTokener("{\"value\": \"test\"}"));
        Object o = TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.STRING, json.get("value"));
        assertTrue(o instanceof String);
        assertEquals("test", o);

        // test that passing in a JSON number will succeed
        json = new JSONObject(new JSONTokener("{\"value\": 21}"));
        o = TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.STRING, json.get("value"));
        assertTrue(o instanceof String);
        assertEquals("21", o);

        // test that passing in a JSON boolean will succeed
        json = new JSONObject(new JSONTokener("{\"value\": true}"));
        o = TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.STRING, json.get("value"));
        assertTrue(o instanceof String);
        assertEquals("true", o);
    }

    @Test
    public void testCreateDeviceObject() {
        // test that passing in a JSON array will fail
        try {
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.DEVICE, new JSONArray(new JSONTokener("[{\"pluginId\":\"plugin1\",\"deviceId\":\"device1\"}]")));
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in an object containing invalid properties fails
        try {
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.DEVICE, new JSONObject(new JSONTokener("{\"plugin\":\"plugin1\",\"device\":\"device1\"}")));
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a valid JSON object succeeds
        JSONObject json = new JSONObject(new JSONTokener("{\"pluginId\":\"plugin1\",\"deviceId\":\"device1\"}"));
        Object o = TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.DEVICE, json);
        assertTrue(o instanceof DeviceContext);
        assertEquals("plugin1", ((DeviceContext)o).getPluginId());
        assertEquals("device1", ((DeviceContext)o).getDeviceId());
    }

    @Test
    public void testCreateDevicesObject() {
        // test that passing in a regular JSON object will fail
        try {
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.DEVICES, new JSONObject(new JSONTokener("{\"pluginId\":\"plugin1\",\"deviceId\":\"device1\"}")));
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in an array of arrays fails
        try {
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.DEVICES, new JSONArray(new JSONTokener("[[{\"pluginId\":\"plugin1\",\"deviceId\":\"device1\"}]]")));
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in an array containing objects with invalid properties fails
        try {
            TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.DEVICES, new JSONArray(new JSONTokener("[{\"plugin\":\"plugin1\",\"device\":\"device1\"}]")));
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a JSON array succeeds
        JSONArray json = new JSONArray(new JSONTokener("[{\"pluginId\":\"plugin1\",\"deviceId\":\"device1\"},{\"pluginId\":\"plugin2\",\"deviceId\":\"device2\"}]"));
        Object o = TypedPropertyValueSerializer.createValueObject(HubContext.createLocal(), TypedProperty.Type.DEVICES, json);
        assertTrue(o instanceof List);
        assertEquals(2, ((List)o).size());

        DeviceContext ctx = (DeviceContext)((List)o).get(0);
        assertEquals("plugin1", ctx.getPluginId());
        assertEquals("device1", ctx.getDeviceId());

        ctx = (DeviceContext)((List)o).get(1);
        assertEquals("plugin2", ctx.getPluginId());
        assertEquals("device2", ctx.getDeviceId());
    }
}
