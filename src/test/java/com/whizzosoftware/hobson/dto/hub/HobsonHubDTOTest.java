/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.hub;

import com.whizzosoftware.hobson.api.device.*;
import com.whizzosoftware.hobson.api.hub.HobsonHub;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.hub.HubManager;
import com.whizzosoftware.hobson.api.hub.MockHubManager;
import com.whizzosoftware.hobson.api.persist.ContextPathIdProvider;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.api.plugin.MockHobsonPlugin;
import com.whizzosoftware.hobson.api.plugin.MockPluginManager;
import com.whizzosoftware.hobson.api.plugin.PluginManager;
import com.whizzosoftware.hobson.api.presence.MockPresenceManager;
import com.whizzosoftware.hobson.api.presence.PresenceManager;
import com.whizzosoftware.hobson.api.task.MockTaskManager;
import com.whizzosoftware.hobson.api.task.TaskManager;
import com.whizzosoftware.hobson.dto.ExpansionFields;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.context.ManagerDTOBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import io.netty.util.concurrent.Future;
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
        DTOBuildContext ctx = new ManagerDTOBuildContext();
        HobsonHubDTO dto = new HobsonHubDTO.Builder(ctx, new TemplatedId("hubLink", null)).name("hubName").version("hubVersion").build();
        JSONObject json = dto.toJSON();
        assertEquals("hubLink", json.getString("@id"));
        assertEquals("hubName", json.getString("name"));
        assertEquals("hubVersion", json.getString("version"));
    }

    @Test
    public void testHubConstructor() throws Exception {
        HubContext hctx = HubContext.createLocal();
        final HobsonHub hub = new HobsonHub(hctx);
        final HubManager hubManager = new MockHubManager();
        final PluginManager pluginManager = new MockPluginManager();
        final DeviceManager deviceManager = new MockDeviceManager();
        final TaskManager taskManager = new MockTaskManager();
        final PresenceManager presenceManager = new MockPresenceManager();
        final IdProvider idProvider = new ContextPathIdProvider();

        // test with just ID
        HobsonHubDTO dto = new HobsonHubDTO.Builder(
            new ManagerDTOBuildContext.Builder().
                hubManager(hubManager).
                pluginManager(pluginManager).
                deviceManager(deviceManager).
                taskManager(taskManager).
                presenceManager(presenceManager).
                idProvider(idProvider).
                build(),
            hub,
            false
        ).build();
        assertEquals("hubs:local", dto.getId());
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
            new ManagerDTOBuildContext.Builder().
                hubManager(hubManager).
                pluginManager(pluginManager).
                deviceManager(deviceManager).
                taskManager(taskManager).
                presenceManager(presenceManager).
                idProvider(idProvider).
                build(),
            hub,
            true
        ).build();

        assertEquals("hubs:local", dto.getId());
        assertEquals("hubs:local:actionClasses", dto.getActionClasses().getId());
        assertEquals("hubs:local:conditionClasses", dto.getConditionClasses().getId());
        assertEquals("hubs:local:configuration", dto.getConfiguration().getId());
        assertEquals("hubs:local:configurationClass", dto.getConfigurationClass().getId());
        assertEquals("hubs:local:devices", dto.getDevices().getId());
        assertEquals("hubs:local:localPlugins", dto.getLocalPlugins().getId());
        assertEquals("hubs:local:log", dto.getLog().getId());
        assertEquals("hubs:local:presenceEntities", dto.getPresenceEntities().getId());
        assertEquals("hubs:local:remotePlugins", dto.getRemotePlugins().getId());
        assertEquals("hubs:local:tasks", dto.getTasks().getId());

        // test with top-level and devices expansion
        MockHobsonPlugin plugin = new MockHobsonPlugin("plugin1", "1.0.0", "");
        plugin.setDeviceManager(new MockDeviceManager());
        Future f = deviceManager.publishDevice(new MockDeviceProxy(plugin, "device1", DeviceType.LIGHTBULB), null, null).await();
        assertTrue(f.isSuccess());
        dto = new HobsonHubDTO.Builder(
                new ManagerDTOBuildContext.Builder().
                        hubManager(hubManager).
                        pluginManager(pluginManager).
                        deviceManager(deviceManager).
                        taskManager(taskManager).
                        presenceManager(presenceManager).
                        expansionFields(new ExpansionFields(JSONAttributes.DEVICES)).
                        idProvider(idProvider).
                        build(),
                hub,
                true
        ).build();
        assertEquals("hubs:local", dto.getId());
        assertEquals("hubs:local:devices", dto.getDevices().getId());
        assertEquals(1, (int)dto.getDevices().getNumberOfItems());
        assertEquals("hubs:local:devices:plugin1:device1", dto.getDevices().getItemListElement().get(0).getItem().getId());
    }
}
