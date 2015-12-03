/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.plugin;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.plugin.*;
import com.whizzosoftware.hobson.api.property.*;
import com.whizzosoftware.hobson.dto.ExpansionFields;
import com.whizzosoftware.hobson.dto.MockIdProvider;
import com.whizzosoftware.hobson.dto.context.ManagerDTOBuildContext;
import com.whizzosoftware.hobson.dto.device.HobsonDeviceDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class HobsonPluginDTOTest {
    @Test
    public void testToJSON() {
        HobsonPluginDTO dto = new HobsonPluginDTO.Builder("pluginLink")
            .name("pluginName")
            .description("pluginDesc")
            .configurable(true)
            .status(PluginStatus.running())
            .type(PluginType.PLUGIN)
            .addLink("foo", "bar")
            .configuration(new PropertyContainerDTO.Builder("configLink").build())
            .configurationClass(new PropertyContainerClassDTO.Builder("cclassLink").build())
            .build();

        JSONObject json = dto.toJSON();
        assertEquals("pluginLink", json.getString(JSONAttributes.AID));
        assertEquals("pluginName", json.getString(JSONAttributes.NAME));
        assertEquals("pluginDesc", json.getString(JSONAttributes.DESCRIPTION));
        assertEquals("RUNNING", json.getJSONObject(JSONAttributes.STATUS).getString("code"));
        assertEquals("PLUGIN", json.getString(JSONAttributes.TYPE));
        assertEquals(true, json.getBoolean(JSONAttributes.CONFIGURABLE));
        assertEquals("bar", json.getJSONObject(JSONAttributes.LINKS).getString("foo"));
        assertEquals("configLink", json.getJSONObject(JSONAttributes.CONFIGURATION).getString("@id"));
        assertEquals("cclassLink", json.getJSONObject(JSONAttributes.CCLASS).getString("@id"));
    }

    @Test
    public void testMapLocalFrameworkPlugin() {
        PluginDescriptor pd = new PluginDescriptor("plugin1", "Plugin", "Description", PluginType.FRAMEWORK, PluginStatus.running(), "1.0.0");
        MockIdProvider idProvider = new MockIdProvider();
        idProvider.setLocalPluginId("/api/v1/users/local/hubs/local/plugins/local/plugin1");
        HobsonPluginDTO dto = new HobsonPluginDTO.Builder(new ManagerDTOBuildContext.Builder().idProvider(idProvider).build(), new PluginDescriptorAdapter(pd, null), pd.getDescription(), null, true).build();
        assertEquals("/api/v1/users/local/hubs/local/plugins/local/plugin1", dto.getId());
        assertEquals("Plugin", dto.getName());
        assertEquals("Description", dto.getDescription());
        assertEquals("1.0.0", dto.getVersion());
        assertEquals(PluginStatus.running().toString(), dto.getStatus().toString());
    }

    @Test
    public void testMapRemotePlugin() {
        PluginDescriptor pd = new PluginDescriptor("plugin1", "Plugin", "Description", PluginType.PLUGIN, PluginStatus.notInstalled(), "1.0.0");
        MockIdProvider idProvider = new MockIdProvider();
        idProvider.setRemotePluginId("/api/v1/users/local/hubs/local/plugins/remote/plugin1/1.0.0");
        HobsonPluginDTO dto = new HobsonPluginDTO.Builder(new ManagerDTOBuildContext.Builder().idProvider(idProvider).build(), new PluginDescriptorAdapter(pd, null), pd.getDescription(), pd.getVersionString(), true).build();
        assertEquals("/api/v1/users/local/hubs/local/plugins/remote/plugin1/1.0.0", dto.getId());
        assertEquals("Plugin", dto.getName());
        assertEquals("Description", dto.getDescription());
        assertEquals("1.0.0", dto.getVersion());
    }

    @Test
    public void testMapLocalNonFrameworkPlugin() {
        HobsonPlugin plugin = new HobsonPlugin() {
            @Override
            public PluginContext getContext() {
                return PluginContext.createLocal("plugin1");
            }

            @Override
            public String getName() {
                return "My Plugin";
            }

            @Override
            public PropertyContainerClass getConfigurationClass() {
                return new PropertyContainerClass(
                        PropertyContainerClassContext.create(PluginContext.createLocal("plugin2"), "configurationClass"),
                        "name",
                        PropertyContainerClassType.PLUGIN_CONFIG,
                        "template",
                        null
                );
            }

            @Override
            public HobsonPluginRuntime getRuntime() {
                return null;
            }

            @Override
            public PluginStatus getStatus() {
                return PluginStatus.running();
            }

            @Override
            public PluginType getType() {
                return PluginType.CORE;
            }

            @Override
            public String getVersion() {
                return "1.0.0";
            }

            @Override
            public boolean isConfigurable() {
                return true;
            }
        };

        // create configuration metadata
        PluginContext pctx = PluginContext.createLocal("plugin1");
        PropertyContainerClassContext pccc = PropertyContainerClassContext.create(pctx, "configurationClass");
        final PropertyContainerClass pcc = new PropertyContainerClass(pccc, "name", PropertyContainerClassType.PLUGIN_CONFIG, null, Collections.singletonList(new TypedProperty.Builder("device", "device", "device", TypedProperty.Type.DEVICE).build()));
        PropertyContainer config = new PropertyContainer(pccc, Collections.singletonMap("device", (Object) DeviceContext.createLocal("plugin3", "device1")));

        MockPluginManager pluginManager = new MockPluginManager();
        pluginManager.setLocalPluginConfiguration(pctx, config);

        MockIdProvider idProvider = new MockIdProvider();
        idProvider.setLocalPluginId("/api/v1/users/local/hubs/local/plugins/local/plugin1");
        idProvider.setLocalPluginConfigurationClassId("/api/v1/users/local/hubs/local/plugins/local/plugin2/configurationClass");
        idProvider.setLocalPluginConfigurationId("/api/v1/users/local/hubs/local/plugins/local/plugin1/configuration");
        idProvider.setDeviceId("/api/v1/users/local/hubs/local/plugins/plugin3/devices/device1");
        idProvider.setPropertyContainerId("/api/v1/users/local/hubs/local/plugins/local/plugin1/configuration");

        // test with no expansions
        HobsonPluginDTO dto = new HobsonPluginDTO.Builder(new ManagerDTOBuildContext.Builder().pluginManager(pluginManager).idProvider(idProvider).build(), plugin, "Description", null, false).build();
        assertEquals("/api/v1/users/local/hubs/local/plugins/local/plugin1", dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getDescription());
        assertNull(dto.getVersion());
        assertNull(dto.getType());
        assertNull(dto.isConfigurable());
        assertNull(dto.getStatus());
        assertNull(dto.getConfigurationClass());
        assertNull(dto.getConfiguration());

        // test with details
        dto = new HobsonPluginDTO.Builder(new ManagerDTOBuildContext.Builder().pluginManager(pluginManager).idProvider(idProvider).build(), plugin, "Description", null, true).build();
        assertEquals("/api/v1/users/local/hubs/local/plugins/local/plugin1", dto.getId());
        assertEquals("My Plugin", dto.getName());
        assertEquals("Description", dto.getDescription());
        assertEquals("1.0.0", dto.getVersion());
        assertEquals("plugin1", dto.getPluginId());
        assertNotNull(dto.getImage());
        assertEquals(PluginType.CORE, dto.getType());
        assertEquals(true, dto.isConfigurable());
        assertEquals(PluginStatus.Code.RUNNING, dto.getStatus().getCode());
        assertNotNull(dto.getConfigurationClass());
        assertEquals("/api/v1/users/local/hubs/local/plugins/local/plugin2/configurationClass", dto.getConfigurationClass().getId());
        assertNull(dto.getConfigurationClass().getName());
        assertNotNull(dto.getConfiguration());
        assertEquals("/api/v1/users/local/hubs/local/plugins/local/plugin1/configuration", dto.getConfiguration().getId());
        assertNull(dto.getConfiguration().getContainerClass());

        // test with configuration class expansion
        dto = new HobsonPluginDTO.Builder(new ManagerDTOBuildContext.Builder().idProvider(idProvider).pluginManager(pluginManager).expansionFields(new ExpansionFields(JSONAttributes.CCLASS)).build(), plugin, "Description", null, true).build();
        assertEquals("/api/v1/users/local/hubs/local/plugins/local/plugin2/configurationClass", dto.getConfigurationClass().getId());
        assertEquals("name", dto.getConfigurationClass().getName());

        // test with configuration expansion
        dto = new HobsonPluginDTO.Builder(new ManagerDTOBuildContext.Builder().idProvider(idProvider).pluginManager(pluginManager).expansionFields(new ExpansionFields(JSONAttributes.CONFIGURATION)).build(), plugin, "Description", null, true).build();
        assertEquals("/api/v1/users/local/hubs/local/plugins/local/plugin2/configurationClass", dto.getConfigurationClass().getId());
        assertNotNull(dto.getConfiguration());
        assertNotNull(dto.getConfiguration().getValues());
        HobsonDeviceDTO hdd = (HobsonDeviceDTO)dto.getConfiguration().getValues().get("device");
        assertEquals("/api/v1/users/local/hubs/local/plugins/plugin3/devices/device1", hdd.getId());
    }
}
