/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.event;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.event.DeviceConfigurationUpdateEvent;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DeviceConfigurationUpdateEventDTOTest {
    @Test
    public void testJSONConstructor() {
        JSONObject json = new JSONObject();
        json.put(JSONAttributes.EVENT_ID, DeviceConfigurationUpdateEvent.ID);
        json.put(JSONAttributes.TIMESTAMP, System.currentTimeMillis());
        JSONObject jprops = new JSONObject();
        jprops.put(JSONAttributes.PLUGIN_ID, "plugin1");
        jprops.put(JSONAttributes.DEVICE_ID, "device1");
        jprops.put(JSONAttributes.CONTAINER_CLASS_ID, "container1");
        Map<String,Object> config = new HashMap<>();
        config.put("foo", "bar");
        jprops.put(JSONAttributes.CONFIGURATION, config);
        json.put(JSONAttributes.PROPERTIES, jprops);

        DeviceConfigurationUpdateEventDTO dto = new DeviceConfigurationUpdateEventDTO(json);
        assertEquals("plugin1", dto.getPluginId());
        assertEquals("device1", dto.getDeviceId());
        assertEquals("container1", dto.getContainerClassId());
        assertEquals(1, dto.getConfiguration().size());
        assertEquals("bar", dto.getConfiguration().get("foo"));
    }

    @Test
    public void testEventConstructor() {
        long now = System.currentTimeMillis();
        PropertyContainerClassContext ctx = PropertyContainerClassContext.create(DeviceContext.createLocal("plugin1", "device1"), "container1");
        Map<String,Object> map = new HashMap<>();
        map.put("foo", "bar");
        PropertyContainer config = new PropertyContainer(ctx, map);

        DeviceConfigurationUpdateEvent event = new DeviceConfigurationUpdateEvent(now, "plugin1", "device1", config);
        DeviceConfigurationUpdateEventDTO dto = new DeviceConfigurationUpdateEventDTO(event);

        assertEquals("plugin1", dto.getPluginId());
        assertEquals("device1", dto.getDeviceId());
        assertEquals("container1", dto.getContainerClassId());
        assertEquals(1, dto.getConfiguration().size());
        assertEquals("bar", dto.getConfiguration().get("foo"));
    }

    @Test
    public void testToJSON() {
        long now = System.currentTimeMillis();
        PropertyContainerClassContext ctx = PropertyContainerClassContext.create(DeviceContext.createLocal("plugin1", "device1"), "container1");
        Map<String,Object> map = new HashMap<>();
        map.put("foo", "bar");
        PropertyContainer config = new PropertyContainer(ctx, map);

        DeviceConfigurationUpdateEvent event = new DeviceConfigurationUpdateEvent(now, "plugin1", "device1", config);
        DeviceConfigurationUpdateEventDTO dto = new DeviceConfigurationUpdateEventDTO(event);
        JSONObject json = dto.toJSON();

        assertEquals(DeviceConfigurationUpdateEvent.ID, json.getString(JSONAttributes.EVENT_ID));
        assertTrue(json.has(JSONAttributes.PROPERTIES));
        JSONObject json2 = json.getJSONObject(JSONAttributes.PROPERTIES);
        assertEquals("plugin1", json2.getString(JSONAttributes.PLUGIN_ID));
        assertEquals("device1", json2.getString(JSONAttributes.DEVICE_ID));
        assertEquals("container1", json2.getString(JSONAttributes.CONTAINER_CLASS_ID));
        JSONObject jconfig = json2.getJSONObject(JSONAttributes.CONFIGURATION);
        assertEquals("bar", jconfig.getString("foo"));
    }
}
