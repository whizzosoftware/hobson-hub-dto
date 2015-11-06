/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.property;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.plugin.PluginContext;
import com.whizzosoftware.hobson.api.property.*;
import com.whizzosoftware.hobson.dto.MockIdProvider;
import com.whizzosoftware.hobson.dto.device.HobsonDeviceDTO;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PropertyContainerDTOTest {
    @Test
    public void testJSONConstructor() {
        JSONObject json = new JSONObject(new JSONTokener("{\"cclass\":{\"@id\":\"/api/v1/users/local/hubs/local/plugins/plugin1/actionClasses/actionclass1\"},\"values\":{\"foo\":\"bar\"}}"));
        PropertyContainerDTO dto = new PropertyContainerDTO.Builder(json).build();
        assertEquals("/api/v1/users/local/hubs/local/plugins/plugin1/actionClasses/actionclass1", dto.getContainerClass().getId());
        assertTrue(dto.hasPropertyValues());
        assertEquals(1, dto.getValues().size());
        assertEquals("bar", dto.getValues().get("foo"));
    }

    @Test
    public void testPropertyContainerConstructor() {
        List<TypedProperty> props = new ArrayList<>();
        props.add(new TypedProperty.Builder("prop1id", "prop1name", "prop1desc", TypedProperty.Type.STRING).build());

        PropertyContainerClass pcc = new PropertyContainerClass(
                PropertyContainerClassContext.create(PluginContext.createLocal("plugin1"), "configurationClass"),
                "config",
                PropertyContainerClassType.PLUGIN_CONFIG,
                "template",
                props
        );

        // test with no details
        PropertyContainerClassDTO dto = new PropertyContainerClassDTO.Builder("/api/v1/users/local/hubs/local/plugins/local/plugin1/configurationClass", pcc, false).build();
        assertEquals("/api/v1/users/local/hubs/local/plugins/local/plugin1/configurationClass", dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getDescriptionTemplate());
        assertNull(dto.getSupportedProperties());

        // test with details
        dto = new PropertyContainerClassDTO.Builder("/api/v1/users/local/hubs/local/plugins/local/plugin1/configurationClass", pcc, true).build();
        assertEquals("/api/v1/users/local/hubs/local/plugins/local/plugin1/configurationClass", dto.getId());
        assertEquals("config", dto.getName());
        assertEquals("template", dto.getDescriptionTemplate());
        assertNotNull(dto.getSupportedProperties());
        assertEquals(1, dto.getSupportedProperties().size());
        TypedPropertyDTO tpd = dto.getSupportedProperties().get(0);
        assertEquals("prop1id", tpd.getId());
        assertEquals("prop1name", tpd.getName());
        assertEquals("prop1desc", tpd.getDescription());
    }

    @Test
    public void testMapPropertyContainerWithHubConfigContainerClass() {
        List<TypedProperty> props = new ArrayList<>();
        props.add(new TypedProperty.Builder("name", "name", "name", TypedProperty.Type.STRING).build());
        final PropertyContainerClass pcc = new PropertyContainerClass(PropertyContainerClassContext.create(HubContext.createLocal(), "configuration"), "name", PropertyContainerClassType.HUB_CONFIG, "", props);

        MockIdProvider idProvider = new MockIdProvider();
        idProvider.setPropertyContainerClassId("/api/v1/users/local/hubs/local/configurationClass");

        Map<String,Object> values = new HashMap<>();
        values.put("name", "My Name");
        values.put("device", DeviceContext.create(HubContext.createLocal(), "plugin2", "device2"));
        PropertyContainer pc = new PropertyContainer(PropertyContainerClassContext.create(PluginContext.createLocal("plugin1"), "ccid"), values);

        PropertyContainerDTO dto = new PropertyContainerDTO.Builder(
                pc,
                new PropertyContainerClassProvider() {
                    @Override
                    public PropertyContainerClass getPropertyContainerClass(PropertyContainerClassContext ctx) {
                        return pcc;
                    }
                },
                PropertyContainerClassType.HUB_CONFIG,
                true,
                null,
                idProvider
        ).build();
        assertEquals("/api/v1/users/local/hubs/local/configurationClass", dto.getContainerClass().getId());
        assertEquals("My Name", dto.getValues().get("name"));
    }

    @Test
    public void testMapPropertyContainerWithPluginConfigContainerClass() {
        List<TypedProperty> props = new ArrayList<>();
        props.add(new TypedProperty.Builder("name", "name", "name", TypedProperty.Type.STRING).build());
        props.add(new TypedProperty.Builder("device", "device", "device", TypedProperty.Type.DEVICE).build());
        final PropertyContainerClass pcc = new PropertyContainerClass(PropertyContainerClassContext.create(PluginContext.createLocal("plugin1"), "configuration"), "name", PropertyContainerClassType.PLUGIN_CONFIG, "", props);

        Map<String,Object> values = new HashMap<>();
        values.put("name", "My Name");
        values.put("device", DeviceContext.create(HubContext.createLocal(), "plugin2", "device2"));
        PropertyContainer pc = new PropertyContainer(PropertyContainerClassContext.create(PluginContext.createLocal("plugin1"), "ccid"), values);

        MockIdProvider idProvider = new MockIdProvider();
        idProvider.setDeviceId("/api/v1/users/local/hubs/local/plugins/plugin2/devices/device2");
        idProvider.setPropertyContainerId("/api/v1/users/local/hubs/local/plugins/local/plugin1/configuration");
        idProvider.setPropertyContainerClassId("/api/v1/users/local/hubs/local/plugins/local/plugin1/configurationClass");

        PropertyContainerDTO dto = new PropertyContainerDTO.Builder(
            pc,
            new PropertyContainerClassProvider() {
                @Override
                public PropertyContainerClass getPropertyContainerClass(PropertyContainerClassContext ctx) {
                    return pcc;
                }
            },
            PropertyContainerClassType.HUB_CONFIG,
            true,
            null,
            idProvider
        ).build();
        assertEquals("/api/v1/users/local/hubs/local/plugins/local/plugin1/configuration", dto.getId());
        assertEquals("/api/v1/users/local/hubs/local/plugins/local/plugin1/configurationClass", dto.getContainerClass().getId());
        assertEquals("My Name", dto.getValues().get("name"));
        assertTrue(dto.getValues().get("device") instanceof HobsonDeviceDTO);
        assertEquals("/api/v1/users/local/hubs/local/plugins/plugin2/devices/device2", ((HobsonDeviceDTO)dto.getValues().get("device")).getId());
    }
}
