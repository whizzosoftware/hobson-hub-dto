/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.json;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.event.HobsonEvent;
import com.whizzosoftware.hobson.api.event.StateSnapshotEvent;
import com.whizzosoftware.hobson.api.hub.HobsonHub;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.plugin.HobsonPlugin;
import com.whizzosoftware.hobson.api.plugin.PluginContext;
import com.whizzosoftware.hobson.api.plugin.PluginStatus;
import com.whizzosoftware.hobson.api.plugin.PluginType;
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.dto.HobsonDeviceDTO;
import com.whizzosoftware.hobson.dto.HobsonPluginDTO;
import com.whizzosoftware.hobson.dto.HobsonVariableDTO;
import com.whizzosoftware.hobson.json.event.StateSnapshotEventSerializer;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import static org.junit.Assert.*;

public class StateSnapshotEventSerializerTest {
    @Test
    public void testUnmarshal() {
        String json = "{\"event\":\"stateSnapshot\",\"hub\":{\"userId\":\"local\",\"hubId\":\"local\",\"name\":\"Test Hub\",\"version\":\"0.5.0-SNAPSHOT\",\"logLevel\":\"INFO\",\"setupComplete\":true,\"location\":{\"text\":\"My House\",\"latitude\":0.1234,\"longitude\":-0.1234},\"email\":{\"server\":\"localhost\",\"secure\":false,\"username\":\"user\",\"senderAddress\":\"foo@bar.com\"},\"plugins\":{\"com.whizzosoftware.hobson.hub.hobson-hub-sample\":{\"name\":\"Sample Plugin\",\"version\":\"0.5.0-SNAPSHOT\",\"type\":\"PLUGIN\",\"status\":\"RUNNING\",\"devices\":{\"thermostat\":{\"name\":\"Thermostat\",\"variables\":{\"tempF\":{\"value\":72.0,\"mask\":\"READ_ONLY\",\"lastUpdate\":1000}}},\"camera\":{\"name\":\"Security Camera\"},\"switch\":{\"name\":\"Switchable Outlet\",\"variables\":{\"on\":{\"value\":false,\"mask\":\"READ_WRITE\",\"lastUpdate\":1000}}},\"bulb\":{\"name\":\"Color LED Bulb\",\"variables\":{\"on\":{\"value\":true,\"mask\":\"READ_WRITE\",\"lastUpdate\":1000},\"level\":{\"value\":100,\"mask\":\"READ_WRITE\",\"lastUpdate\":1000}}}}}}}}";
        StateSnapshotEventSerializer sses = new StateSnapshotEventSerializer();
        HobsonEvent event = sses.unmarshal(new JSONObject(new JSONTokener(json)));

        assertTrue(event instanceof StateSnapshotEvent);
        StateSnapshotEvent sse = (StateSnapshotEvent)event;

        assertTrue(sse.hasHub());

        assertTrue(sse.hasPlugins());
        assertEquals(1, sse.getPlugins().size());
        HobsonPlugin plugin = sse.getPlugins().iterator().next();
        assertEquals("com.whizzosoftware.hobson.hub.hobson-hub-sample", plugin.getContext().getPluginId());
        assertEquals("Sample Plugin", plugin.getName());
        assertEquals("0.5.0-SNAPSHOT", plugin.getVersion());
        assertEquals(PluginStatus.Status.RUNNING, plugin.getStatus().getStatus());
        assertNull(plugin.getStatus().getMessage());

        assertTrue(sse.hasDevices("com.whizzosoftware.hobson.hub.hobson-hub-sample"));
        assertEquals(4, sse.getDevices("com.whizzosoftware.hobson.hub.hobson-hub-sample").size());
    }

    @Test
    public void testMarshal() {
        long now = System.currentTimeMillis();
        StateSnapshotEvent sse = new StateSnapshotEvent(System.currentTimeMillis());
        HubContext hubContext = HubContext.createLocal();
        sse.setHub(new HobsonHub(hubContext));
        PluginContext pluginContext = PluginContext.create(hubContext, "pid");
        sse.addPlugin(new HobsonPluginDTO.Builder(pluginContext).
            setName("Plugin").
            setVersion("0.0.1").
            setType(PluginType.PLUGIN).
            setStatus(new PluginStatus(PluginStatus.Status.RUNNING)).
            build()
        );
        sse.addDevice(new HobsonDeviceDTO.Builder(DeviceContext.create(pluginContext, "did")).
            setName("Device").
            setType(DeviceType.LIGHTBULB).
            build()
        );
        sse.addVariable(new HobsonVariableDTO.Builder(DeviceContext.createLocal("pid", "did")).
            setName("on").
            setValue(true).
            setMask(HobsonVariable.Mask.READ_ONLY).
            setLastUpdate(now).
            build()
        );

        StateSnapshotEventSerializer sses = new StateSnapshotEventSerializer();
        JSONObject json = sses.marshal(sse);

        assertEquals(StateSnapshotEvent.ID, json.get("event"));
        assertTrue(json.has("hub"));
        JSONObject hub = (JSONObject)json.get("hub");
        assertEquals("local", hub.getString("hubId"));
        assertTrue(hub.has("plugins"));
        JSONObject plugins = (JSONObject)hub.get("plugins");
        assertTrue(plugins.has("pid"));
        JSONObject plugin = (JSONObject)plugins.get("pid");
        assertEquals("Plugin", plugin.getString("name"));
        assertEquals("PLUGIN", plugin.getString("type"));
        assertEquals("0.0.1", plugin.getString("version"));
        assertTrue(plugin.has("devices"));
        JSONObject devices = (JSONObject)plugin.get("devices");
        assertTrue(devices.has("did"));
        JSONObject device = (JSONObject)devices.get("did");
        assertEquals("Device", device.getString("name"));
        assertEquals("LIGHTBULB", device.getString("type"));
        assertTrue(device.has("variables"));
        JSONObject variables = (JSONObject)device.get("variables");
        assertTrue(variables.has("on"));
        JSONObject variable = (JSONObject)variables.get("on");
        assertEquals(true, variable.get("value"));
        assertEquals("READ_ONLY", variable.getString("mask"));
        assertEquals(now, variable.getLong("lastUpdate"));
    }
}
