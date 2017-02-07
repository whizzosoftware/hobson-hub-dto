/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.plugin;

import com.whizzosoftware.hobson.api.action.MockActionManager;
import com.whizzosoftware.hobson.api.config.MockConfigurationManager;
import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.event.MockEventManager;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.persist.ContextPathIdProvider;
import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.api.plugin.*;
import com.whizzosoftware.hobson.api.property.*;
import com.whizzosoftware.hobson.dto.ExpansionFields;
import com.whizzosoftware.hobson.dto.MockIdProvider;
import com.whizzosoftware.hobson.dto.context.ManagerDTOBuildContext;
import com.whizzosoftware.hobson.dto.device.HobsonDeviceDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import com.whizzosoftware.hobson.dto.property.TypedPropertyDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class HobsonPluginDTOTest {
    @Test
    public void testToJSON() {
        HobsonPluginDTO dto = new HobsonPluginDTO.Builder(new ManagerDTOBuildContext(), new TemplatedId("pluginLink", null))
            .name("pluginName")
            .description("pluginDesc")
            .configurable(true)
            .status(PluginStatus.running())
            .type(PluginType.PLUGIN)
            .addLink("foo", "bar")
            .configuration(new PropertyContainerDTO.Builder("configLink").build())
            .configurationClass(new PropertyContainerClassDTO.Builder(new ManagerDTOBuildContext(), new TemplatedId("cclassLink", null)).build())
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
        HubContext hctx = HubContext.createLocal();
        HobsonPluginDescriptor pd = new HobsonPluginDescriptor("plugin1", PluginType.FRAMEWORK, "Plugin", "Description", "1.0.0", PluginStatus.running());
        MockIdProvider idProvider = new MockIdProvider();
        idProvider.setLocalPluginId("/api/v1/users/local/hubs/local/plugins/local/plugin1");
        HobsonPluginDTO dto = new HobsonPluginDTO.Builder(new ManagerDTOBuildContext.Builder().idProvider(idProvider).build(), hctx, pd, pd.getDescription(), null, true).build();
        assertEquals("/api/v1/users/local/hubs/local/plugins/local/plugin1", dto.getId());
        assertEquals("Plugin", dto.getName());
        assertEquals("Description", dto.getDescription());
        assertEquals("1.0.0", dto.getVersion());
        assertEquals(PluginStatus.running().toString(), dto.getStatus().toString());
    }

    @Test
    public void testMapRemotePlugin() {
        HubContext hctx = HubContext.createLocal();
        HobsonPluginDescriptor pd = new HobsonPluginDescriptor("plugin1", PluginType.PLUGIN, "Plugin", "Description", "1.0.0", PluginStatus.notInstalled());
        MockIdProvider idProvider = new MockIdProvider();
        idProvider.setRemotePluginId("/api/v1/users/local/hubs/local/plugins/remote/plugin1/1.0.0");
        HobsonPluginDTO dto = new HobsonPluginDTO.Builder(new ManagerDTOBuildContext.Builder().idProvider(idProvider).build(), hctx, pd, pd.getDescription(), pd.getVersion(), true).build();
        assertEquals("/api/v1/users/local/hubs/local/plugins/remote/plugin1/1.0.0", dto.getId());
        assertEquals("Plugin", dto.getName());
        assertEquals("Description", dto.getDescription());
        assertEquals("1.0.0", dto.getVersion());
    }

    @Test
    public void testMapLocalNonFrameworkPlugin() {
        HobsonLocalPluginDescriptor plugin = new HobsonLocalPluginDescriptor(PluginContext.createLocal("plugin1"), PluginType.CORE, "My Plugin", "Description", "1.0.0", PluginStatus.running());
        plugin.setConfigurationClass(new PropertyContainerClass(
            PropertyContainerClassContext.create(PluginContext.createLocal("plugin2"), "configurationClass"),
            PropertyContainerClassType.PLUGIN_CONFIG,
            Collections.singletonList(new TypedProperty.Builder("id", "name", "description", TypedProperty.Type.STRING).build())
        ));

        // create configuration metadata
        PluginContext pctx = PluginContext.createLocal("plugin1");

        MockPluginManager pluginManager = new MockPluginManager();
        pluginManager.setConfigurationManager(new MockConfigurationManager());
        pluginManager.setEventManager(new MockEventManager());
        MockHobsonPlugin hp = new MockHobsonPlugin("plugin1", "0.0.1", "");
        MockActionManager am = new MockActionManager();
        hp.setActionManager(am);
        pluginManager.addLocalPlugin(hp);
        pluginManager.setLocalPluginConfiguration(pctx, Collections.singletonMap("device", (Object)DeviceContext.createLocal("plugin3", "device1")));

        MockIdProvider idProvider = new MockIdProvider();
        idProvider.setLocalPluginId("/api/v1/users/local/hubs/local/plugins/local/plugin1");
        idProvider.setLocalPluginConfigurationClassId("/api/v1/users/local/hubs/local/plugins/local/plugin2/configurationClass");
        idProvider.setLocalPluginConfigurationId("/api/v1/users/local/hubs/local/plugins/local/plugin1/configuration");
        idProvider.setDeviceId("/api/v1/users/local/hubs/local/plugins/plugin3/devices/device1");
        idProvider.setPropertyContainerId("/api/v1/users/local/hubs/local/plugins/local/plugin1/configuration");

        // test with no expansions
        HobsonPluginDTO dto = new HobsonPluginDTO.Builder(new ManagerDTOBuildContext.Builder().pluginManager(pluginManager).idProvider(idProvider).build(), pctx.getHubContext(), plugin, "Description", null, false).build();
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
        dto = new HobsonPluginDTO.Builder(new ManagerDTOBuildContext.Builder().pluginManager(pluginManager).idProvider(idProvider).build(), pctx.getHubContext(), plugin, "Description", null, true).build();
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
        assertNotNull(dto.getConfiguration());
        assertEquals("/api/v1/users/local/hubs/local/plugins/local/plugin1/configuration", dto.getConfiguration().getId());
        assertNull(dto.getConfiguration().getContainerClass());

        // test with configuration class expansion
        dto = new HobsonPluginDTO.Builder(new ManagerDTOBuildContext.Builder().idProvider(idProvider).pluginManager(pluginManager).expansionFields(new ExpansionFields(JSONAttributes.CCLASS)).build(), pctx.getHubContext(), plugin, "Description", null, true).build();
        assertEquals("/api/v1/users/local/hubs/local/plugins/local/plugin2/configurationClass", dto.getConfigurationClass().getId());

        // test with configuration expansion
        dto = new HobsonPluginDTO.Builder(new ManagerDTOBuildContext.Builder().idProvider(idProvider).pluginManager(pluginManager).expansionFields(new ExpansionFields(JSONAttributes.CONFIGURATION)).build(), pctx.getHubContext(), plugin, "Description", null, true).build();
        assertEquals("/api/v1/users/local/hubs/local/plugins/local/plugin2/configurationClass", dto.getConfigurationClass().getId());
        assertNotNull(dto.getConfiguration());
        assertNotNull(dto.getConfiguration().getValues());
        HobsonDeviceDTO hdd = (HobsonDeviceDTO)dto.getConfiguration().getValues().get("device");
        assertEquals("/api/v1/users/local/hubs/local/plugins/plugin3/devices/device1", hdd.getId());
    }

    @Test
    public void testConfigurationPropertyOrder() {
        MockActionManager am = new MockActionManager();
        MockConfigurationManager cm = new MockConfigurationManager();
        MockPluginManager pluginManager = new MockPluginManager();

        MockIdProvider idProvider = new MockIdProvider();

        MockHobsonPlugin plugin = new MockHobsonPlugin("plugin1", "1.0.0", "Description") {
            @Override
            protected TypedProperty[] getConfigurationPropertyTypes() {
                return new TypedProperty[] {
                    new TypedProperty.Builder("c", "c", "description", TypedProperty.Type.STRING).build(),
                    new TypedProperty.Builder("b", "b", "description", TypedProperty.Type.STRING).build(),
                    new TypedProperty.Builder("a", "a", "description", TypedProperty.Type.STRING).build(),
                    new TypedProperty.Builder("d", "d", "description", TypedProperty.Type.STRING).build()
                };
            }
        };
        pluginManager.addLocalPlugin(plugin);
        pluginManager.setConfigurationManager(cm);
        plugin.setActionManager(am);

        HobsonPluginDTO dto = new HobsonPluginDTO.Builder(new ManagerDTOBuildContext.Builder().idProvider(idProvider).pluginManager(pluginManager).expansionFields(new ExpansionFields(JSONAttributes.CCLASS)).build(), HubContext.createLocal(), plugin.getDescriptor(), "Description", null, true).build();
        List<TypedPropertyDTO> p = dto.getConfigurationClass().getSupportedProperties();
        assertEquals("c", p.get(0).getId());
        assertEquals("b", p.get(1).getId());
        assertEquals("a", p.get(2).getId());
        assertEquals("d", p.get(3).getId());

    }
}
