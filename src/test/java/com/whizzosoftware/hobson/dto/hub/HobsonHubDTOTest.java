/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.hub;

import com.whizzosoftware.hobson.api.device.DeviceManager;
import com.whizzosoftware.hobson.api.device.MockDeviceManager;
import com.whizzosoftware.hobson.api.device.MockHobsonDevice;
import com.whizzosoftware.hobson.api.hub.HobsonHub;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.hub.HubManager;
import com.whizzosoftware.hobson.api.hub.MockHubManager;
import com.whizzosoftware.hobson.api.plugin.MockHobsonPlugin;
import com.whizzosoftware.hobson.api.plugin.MockPluginManager;
import com.whizzosoftware.hobson.api.plugin.PluginManager;
import com.whizzosoftware.hobson.api.presence.MockPresenceManager;
import com.whizzosoftware.hobson.api.presence.PresenceManager;
import com.whizzosoftware.hobson.api.task.MockTaskManager;
import com.whizzosoftware.hobson.api.task.TaskManager;
import com.whizzosoftware.hobson.api.variable.MockVariableManager;
import com.whizzosoftware.hobson.api.variable.VariableManager;
import com.whizzosoftware.hobson.dto.DTOBuildContext;
import com.whizzosoftware.hobson.dto.ExpansionFields;
import com.whizzosoftware.hobson.dto.MockIdProvider;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class HobsonHubDTOTest {
    @Test
    public void testJSONConstructor() {
        JSONObject json = new JSONObject();
        json.put("name", "Test Hub");
        HobsonHubDTO dto = new HobsonHubDTO.Builder(json).build();
        assertEquals("Test Hub", dto.getName());
    }

    @Test
    public void testToJSON() {
        HobsonHubDTO dto = new HobsonHubDTO.Builder("hubLink").name("hubName").version("hubVersion").build();
        JSONObject json = dto.toJSON();
        assertEquals("hubLink", json.getString("@id"));
        assertEquals("hubName", json.getString("name"));
        assertEquals("hubVersion", json.getString("version"));
    }

    @Test
    public void testHubConstructor() {
        HobsonHub hub = new HobsonHub(HubContext.createLocal());
        HubManager hubManager = new MockHubManager();
        PluginManager pluginManager = new MockPluginManager();
        DeviceManager deviceManager = new MockDeviceManager();
        VariableManager varManager = new MockVariableManager();
        TaskManager taskManager = new MockTaskManager();
        PresenceManager presenceManager = new MockPresenceManager();

        MockIdProvider idProvider = new MockIdProvider();
        idProvider.setHubId("local");
        idProvider.setActionClassesId("actionClasses");
        idProvider.setConditionClassesId("conditionClasses");
        idProvider.setConfigurationId("configuration");
        idProvider.setConfigurationClassId("configurationClass");
        idProvider.setDeviceId("device");
        idProvider.setDevicesId("devices");
        idProvider.setLocalPluginsId("localPlugins");
        idProvider.setLogId("log");
        idProvider.setPresenceEntitiesId("presenceEntities");
        idProvider.setRemotePluginsId("remotePlugins");
        idProvider.setTasksId("tasks");
        idProvider.setPropertyContainerId("configuration");

        // test with just ID
        HobsonHubDTO dto = new HobsonHubDTO.Builder(
            new DTOBuildContext.Builder().
                hubManager(hubManager).
                pluginManager(pluginManager).
                deviceManager(deviceManager).
                variableManager(varManager).
                taskManager(taskManager).
                presenceManager(presenceManager).
                idProvider(idProvider).
                build(),
            hub,
            false
        ).build();
        assertEquals("local", dto.getId());
        assertNull(dto.getActionClasses());
        assertNull(dto.getConditionClasses());
        assertNull(dto.getConfiguration());
        assertNull(dto.getConfigurationClass());
        assertNull(dto.getDevices());
        assertNull(dto.getLocalPlugins());
        assertNull(dto.getLog());
        assertNull(dto.getPresenceEntities());
        assertNull(dto.getRemotePlugins());
        assertNull(dto.getTasks());

        // test with top-level expansion
        dto = new HobsonHubDTO.Builder(
            new DTOBuildContext.Builder().
                hubManager(hubManager).
                pluginManager(pluginManager).
                deviceManager(deviceManager).
                variableManager(varManager).
                taskManager(taskManager).
                presenceManager(presenceManager).
                idProvider(idProvider).
                build(),
            hub,
            true
        ).build();

        assertEquals("local", dto.getId());
        assertEquals("actionClasses", dto.getActionClasses().getId());
        assertEquals("conditionClasses", dto.getConditionClasses().getId());
        assertEquals("configuration", dto.getConfiguration().getId());
        assertEquals("configurationClass", dto.getConfigurationClass().getId());
        assertEquals("devices", dto.getDevices().getId());
        assertEquals("localPlugins", dto.getLocalPlugins().getId());
        assertEquals("log", dto.getLog().getId());
        assertEquals("presenceEntities", dto.getPresenceEntities().getId());
        assertEquals("remotePlugins", dto.getRemotePlugins().getId());
        assertEquals("tasks", dto.getTasks().getId());

        // test with top-level and devices expansion
        MockHobsonPlugin plugin = new MockHobsonPlugin("plugin1");
        plugin.setDeviceManager(new MockDeviceManager());
        deviceManager.publishDevice(new MockHobsonDevice(plugin, "device1"));
        dto = new HobsonHubDTO.Builder(
            new DTOBuildContext.Builder().
                hubManager(hubManager).
                pluginManager(pluginManager).
                deviceManager(deviceManager).
                variableManager(varManager).
                taskManager(taskManager).
                presenceManager(presenceManager).
                expansionFields(new ExpansionFields(JSONAttributes.DEVICES)).
                idProvider(idProvider).
                build(),
            hub,
            true
        ).build();
        assertEquals("local", dto.getId());
        assertEquals("devices", dto.getDevices().getId());
        assertEquals(1, (int)dto.getDevices().getNumberOfItems());
        assertEquals("device", dto.getDevices().getItemListElement().get(0).getItem().getId());
    }
}
