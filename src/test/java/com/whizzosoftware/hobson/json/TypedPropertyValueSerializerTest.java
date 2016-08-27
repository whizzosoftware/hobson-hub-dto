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
import com.whizzosoftware.hobson.api.presence.PresenceEntityContext;
import com.whizzosoftware.hobson.api.presence.PresenceLocationContext;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import org.json.JSONArray;
import org.json.JSONException;
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
            TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.NUMBER, new JSONArray(new JSONTokener("[]")), null);
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a JSON string will fail
        JSONObject json;
        try {
            json = new JSONObject(new JSONTokener("{\"value\": \"test\"}"));
            TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.NUMBER, json.get("value"), null);
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a JSON integer number will succeed
        json = new JSONObject(new JSONTokener("{\"value\": 21}"));
        Object o = TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.NUMBER, json.get("value"), null);
        assertTrue(o instanceof Integer);
        assertEquals(21, o);

        // test that passing in a JSON double number will succeed
        json = new JSONObject(new JSONTokener("{\"value\": 21.5}"));
        o = TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.NUMBER, json.get("value"), null);
        assertTrue(o instanceof Double);
        assertEquals(21.5, o);
    }

    @Test
    public void testCreateBooleanObject() {
        // test that passing in a JSON array will fail
        try {
            TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.BOOLEAN, new JSONArray(new JSONTokener("[]")), null);
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a JSON boolean will succeed
        JSONObject json = new JSONObject(new JSONTokener("{\"value\": true}"));
        Object o = TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.BOOLEAN, json.get("value"), null);
        assertTrue(o instanceof Boolean);
        assertTrue((Boolean)o);

        // test that passing in a JSON boolean will succeed
        json = new JSONObject(new JSONTokener("{\"value\": false}"));
        o = TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.BOOLEAN, json.get("value"), null);
        assertTrue(o instanceof Boolean);
        assertFalse((Boolean)o);

        // test that passing in a JSON string with a boolean value will succeed
        json = new JSONObject(new JSONTokener("{\"value\": \"true\"}"));
        o = TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.BOOLEAN, json.get("value"), null);
        assertTrue(o instanceof Boolean);
        assertTrue((Boolean)o);

        // test that passing in a JSON string with a boolean value will succeed
        json = new JSONObject(new JSONTokener("{\"value\": \"false\"}"));
        o = TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.BOOLEAN, json.get("value"), null);
        assertTrue(o instanceof Boolean);
        assertFalse((Boolean)o);

        // test that passing in a JSON string with a randon value will not succeed
        try {
            json = new JSONObject(new JSONTokener("{\"value\": \"foo\"}"));
            TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.BOOLEAN, json.get("value"), null);
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {}
    }

    @Test
    public void testCreateStringObject() {
        // test that passing in a JSON array will fail
        try {
            TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.STRING, new JSONArray(new JSONTokener("[]")), null);
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a JSON string will succeed
        JSONObject json = new JSONObject(new JSONTokener("{\"value\": \"test\"}"));
        Object o = TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.STRING, json.get("value"), null);
        assertTrue(o instanceof String);
        assertEquals("test", o);

        // test that passing in a JSON number will succeed
        json = new JSONObject(new JSONTokener("{\"value\": 21}"));
        o = TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.STRING, json.get("value"), null);
        assertTrue(o instanceof String);
        assertEquals("21", o);

        // test that passing in a JSON boolean will succeed
        json = new JSONObject(new JSONTokener("{\"value\": true}"));
        o = TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.STRING, json.get("value"), null);
        assertTrue(o instanceof String);
        assertEquals("true", o);

        // test that passing in a JSON null will success
        json = new JSONObject(new JSONTokener("{\"value\": null}"));
        o = TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.STRING, json.get("value"), null);
        assertNull(o);
    }

    @Test
    public void testCreateDeviceObject() {
        // test that passing in a JSON array will fail
        try {
            TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.DEVICE, new JSONArray(new JSONTokener("[{\"pluginId\":\"plugin1\",\"deviceId\":\"device1\"}]")), null);
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in an object containing invalid properties fails
        try {
            TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.DEVICE, new JSONObject(new JSONTokener("{\"d\":\"device1\"}")), null);
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a valid JSON object succeeds
        JSONObject json = new JSONObject(new JSONTokener("{\"@id\":\"device1\"}"));
        Object o = TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.DEVICE, json, new TypedPropertyValueSerializer.PropertyContextProvider() {
            @Override
            public DeviceContext createDeviceContext(String id) {
                return DeviceContext.createLocal("pid", id);
            }

            @Override
            public PresenceEntityContext createPresenceEntityContext(String id) {
                return null;
            }

            @Override
            public PresenceLocationContext createPresenceLocationContext(String id) {
                return null;
            }
        });
        assertTrue(o instanceof DeviceContext);
        assertEquals("pid", ((DeviceContext)o).getPluginId());
        assertEquals("device1", ((DeviceContext)o).getDeviceId());
    }

    @Test
    public void testCreateDevicesObject() {
        // test that passing in a regular JSON object will fail
        try {
            TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.DEVICES, new JSONObject(new JSONTokener("{\"pluginId\":\"plugin1\",\"deviceId\":\"device1\"}")), null);
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in an array of arrays fails
        try {
            TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.DEVICES, new JSONArray(new JSONTokener("[[{\"pluginId\":\"plugin1\",\"deviceId\":\"device1\"}]]")), new TypedPropertyValueSerializer.PropertyContextProvider() {
                @Override
                public DeviceContext createDeviceContext(String id) {
                    return DeviceContext.createLocal("pid", "did");
                }

                @Override
                public PresenceEntityContext createPresenceEntityContext(String id) {
                    return null;
                }

                @Override
                public PresenceLocationContext createPresenceLocationContext(String id) {
                    return null;
                }
            });
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in an array containing objects with invalid properties fails
        try {
            TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.DEVICES, new JSONArray(new JSONTokener("[{\"d\":\"device1\"}]")), new TypedPropertyValueSerializer.PropertyContextProvider() {
                @Override
                public DeviceContext createDeviceContext(String id) {
                    return DeviceContext.createLocal("pid", "did");
                }

                @Override
                public PresenceEntityContext createPresenceEntityContext(String id) {
                    return null;
                }

                @Override
                public PresenceLocationContext createPresenceLocationContext(String id) {
                    return null;
                }
            });
            fail("Should have thrown an exception");
        } catch (HobsonInvalidRequestException ignored) {
        }

        // test that passing in a JSON array succeeds
        JSONArray json = new JSONArray(new JSONTokener("[{\"@id\":\"device1\"},{\"@id\":\"device2\"}]"));
        Object o = TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.DEVICES, json, new TypedPropertyValueSerializer.PropertyContextProvider() {
            @Override
            public DeviceContext createDeviceContext(String id) {
                return DeviceContext.createLocal("plugin1", id);
            }

            @Override
            public PresenceEntityContext createPresenceEntityContext(String id) {
                return null;
            }

            @Override
            public PresenceLocationContext createPresenceLocationContext(String id) {
                return null;
            }
        });
        assertTrue(o instanceof List);
        assertEquals(2, ((List)o).size());

        DeviceContext ctx = (DeviceContext)((List)o).get(0);
        assertEquals("plugin1", ctx.getPluginId());
        assertEquals("device1", ctx.getDeviceId());

        ctx = (DeviceContext)((List)o).get(1);
        assertEquals("plugin1", ctx.getPluginId());
        assertEquals("device2", ctx.getDeviceId());
    }

    @Test
    public void testPresenceEntityObject() {
        JSONObject json = new JSONObject();
        json.put("@id", "/api/v1/hubs/local/presence/entities/foo");
        Object o = TypedPropertyValueSerializer.createValueObject(TypedProperty.Type.PRESENCE_ENTITY, json, new TypedPropertyValueSerializer.PropertyContextProvider() {
            @Override
            public DeviceContext createDeviceContext(String id) {
                return DeviceContext.createLocal("p1", "d1");
            }

            @Override
            public PresenceEntityContext createPresenceEntityContext(String id) {
                return PresenceEntityContext.createLocal("foo");
            }

            @Override
            public PresenceLocationContext createPresenceLocationContext(String id) {
                return PresenceLocationContext.createLocal("bar");
            }
        });
        assertTrue(o instanceof PresenceEntityContext);
        PresenceEntityContext pec = (PresenceEntityContext)o;
        assertEquals("local", pec.getHubId());
        assertEquals("foo", pec.getEntityId());
    }
}
