/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.device;

import com.whizzosoftware.hobson.api.device.*;
import com.whizzosoftware.hobson.api.event.MockEventManager;
import com.whizzosoftware.hobson.api.persist.ContextPathIdProvider;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.plugin.MockHobsonPlugin;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.variable.DeviceProxyVariable;
import com.whizzosoftware.hobson.api.variable.DeviceVariableContext;
import com.whizzosoftware.hobson.api.variable.VariableMask;
import com.whizzosoftware.hobson.dto.ExpansionFields;
import com.whizzosoftware.hobson.dto.context.ManagerDTOBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import io.netty.util.concurrent.Future;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import static org.junit.Assert.*;

public class HobsonDeviceDTOTest {
    @Test
    public void testConstructorWithJustId() throws Exception {
        DeviceManager deviceManager = new MockDeviceManager();
        MockHobsonPlugin plugin = new MockHobsonPlugin("plugin1", "1.0.0", "");
        plugin.setDeviceManager(deviceManager);
        DeviceContext dctx = DeviceContext.create(plugin.getContext(), "device1");
        MockDeviceProxy proxy = new MockDeviceProxy(plugin, dctx.getDeviceId(), DeviceType.LIGHTBULB);
        Future f = deviceManager.publishDevice(proxy, null, null).await();
        assertTrue(f.isSuccess());
        IdProvider idProvider = new ContextPathIdProvider();
        HobsonDeviceDTO dto = new HobsonDeviceDTO.Builder(
            new ManagerDTOBuildContext.Builder().
                deviceManager(deviceManager).
                idProvider(idProvider).
                build(),
            dctx,
            false
        ).build();
        assertEquals("hubs:local:devices:plugin1:device1", dto.getId());
    }

    @Test
    public void testConstructorWithDetailsAndNoExpansions() throws Exception {
        final MockDeviceManager deviceManager = new MockDeviceManager();
        MockHobsonPlugin plugin = new MockHobsonPlugin("plugin1", "1.0.0", "");
        plugin.setDeviceManager(deviceManager);
        final DeviceContext dctx = DeviceContext.create(plugin.getContext(), "device1");
        MockDeviceProxy proxy = new MockDeviceProxy(plugin, dctx.getDeviceId(), DeviceType.LIGHTBULB);
        proxy.setManufacturerName("Mfg");
        proxy.setManufacturerVersion("1.0");
        proxy.setModelName("model");
        proxy.setLastCheckin(100L);
        Future f = deviceManager.publishDevice(proxy, null, null).await();
        assertTrue(f.isSuccess());

        IdProvider idProvider = new ContextPathIdProvider();
        HobsonDeviceDTO dto = new HobsonDeviceDTO.Builder(
                new ManagerDTOBuildContext.Builder().
                        deviceManager(deviceManager).
                        idProvider(idProvider).
                        build(),
                dctx,
                true
        ).build();

        assertEquals("hubs:local:devices:plugin1:device1", dto.getId());
//        assertEquals("deviceName", dto.getName());
        assertEquals("Mfg", dto.getManufacturerName());
        assertEquals("1.0", dto.getManufacturerVersion());
        assertEquals("model", dto.getModelName());
        assertEquals("LIGHTBULB", dto.getType().toString());
        assertNotNull(dto.getLastCheckIn());
        assertEquals(100, (long)dto.getLastCheckIn());
        assertEquals("hubs:local:variables:plugin1:device1", dto.getVariables().getId());
    }

    @Test
    public void testConstructorWithDetailsAndVariablesExpansions() throws Exception {
        final MockDeviceManager deviceManager = new MockDeviceManager();
        MockHobsonPlugin plugin = new MockHobsonPlugin("plugin1", "1.0.0", "");
        plugin.setDeviceManager(deviceManager);
        plugin.setEventManager(new MockEventManager());

        final DeviceContext dctx = DeviceContext.create(plugin.getContext(), "device1");
        final DeviceVariableContext dvctx = DeviceVariableContext.create(dctx, "foo");
        MockDeviceProxy proxy = new MockDeviceProxy(plugin, dctx.getDeviceId(), DeviceType.LIGHTBULB) {
            public void onStartup(String name, PropertyContainer config) {
                publishVariables(new DeviceProxyVariable(dvctx, VariableMask.READ_WRITE));
            }
        };
        proxy.setLastCheckin(100L);
        Future f = deviceManager.publishDevice(proxy, null, null).await();
        assertTrue(f.isSuccess());
        deviceManager.setDeviceVariable(dvctx, "bar");

        IdProvider idProvider = new ContextPathIdProvider();
        HobsonDeviceDTO dto = new HobsonDeviceDTO.Builder(
                new ManagerDTOBuildContext.Builder().
                    deviceManager(deviceManager).
                    idProvider(idProvider).
                    expansionFields(new ExpansionFields(JSONAttributes.VARIABLES)).
                    build(),
                dctx,
                true
        ).build();

        assertEquals("hubs:local:devices:plugin1:device1", dto.getId());
//                        assertEquals("deviceName", dto.getName());
        assertEquals("LIGHTBULB", dto.getType().toString());
        assertNotNull(dto.getLastCheckIn());
        assertEquals(100, (long)dto.getLastCheckIn());
        assertEquals("hubs:local:variables:plugin1:device1", dto.getVariables().getId());
        assertEquals(1, (int)dto.getVariables().getNumberOfItems());
        assertEquals("hubs:local:variables:plugin1:device1:foo", dto.getVariables().getItemListElement().get(0).getItem().getId());
    }

    @Test
    public void testJSONConstructor() {
        JSONObject json = new JSONObject(new JSONTokener("{\"cclass\":{},\"lastUpdate\":1448403617684,\"available\":true,\"manufacturerVersion\":\"1.0\",\"type\":\"WEATHER_STATION\",\"variables\":{\"numberOfItems\":6,\"itemListElement\":[{\"item\":{\"lastUpdate\":1448403617675,\"name\":\"windSpdMph\",\"value\":2,\"mask\":\"READ_ONLY\",\"@id\":\"local:hubs:local:variables:device:windSpdMph\"}},{\"item\":{\"lastUpdate\":1448403617665,\"name\":\"outRh\",\"value\":30,\"mask\":\"READ_ONLY\",\"@id\":\"local:hubs:local:variables:device:outRh\"}},{\"item\":{\"lastUpdate\":1448403617650,\"name\":\"inTempF\",\"value\":76,\"mask\":\"READ_ONLY\",\"@id\":\"local:hubs:local:variables:device:inTempF\"}},{\"item\":{\"lastUpdate\":1448403617684,\"name\":\"windDirDeg\",\"value\":270,\"mask\":\"READ_ONLY\",\"@id\":\"local:hubs:local:variables:device:windDirDeg\"}},{\"item\":{\"lastUpdate\":1448403617643,\"name\":\"outTempF\",\"value\":74,\"mask\":\"READ_ONLY\",\"@id\":\"local:hubs:local:variables:device:outTempF\"}},{\"item\":{\"lastUpdate\":1448403617659,\"name\":\"inRh\",\"value\":32,\"mask\":\"READ_ONLY\",\"@id\":\"local:hubs:local:variables:device:inRh\"}}],\"@id\":\"local:hubs:local:devices:com.whizzosoftware.hobson.hub.hobson-hub-sample:wstation:variables\"},\"preferredVariable\":{\"@id\":\"local:hubs:local:variables:device:outTempF\"},\"modelName\":\"Sample Weather Station\",\"lastCheckIn\":1448403617736,\"name\":\"Weather Station\",\"manufacturerName\":\"Whizzo Software LLC\",\"@id\":\"local:hubs:local:devices:com.whizzosoftware.hobson.hub.hobson-hub-sample:wstation\",\"configuration\":{}}"));
        HobsonDeviceDTO dto = new HobsonDeviceDTO.Builder(json).build();
        assertEquals("Weather Station", dto.getName());
        assertEquals(DeviceType.WEATHER_STATION, dto.getType());
        assertEquals("Whizzo Software LLC", dto.getManufacturerName());
        assertEquals("Sample Weather Station", dto.getModelName());
        assertEquals("1.0", dto.getManufacturerVersion());
        assertNotNull(dto.getPreferredVariable());
        assertEquals("local:hubs:local:variables:device:outTempF", dto.getPreferredVariable().getId());
    }
}
