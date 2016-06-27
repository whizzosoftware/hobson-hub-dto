/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.device;

import com.whizzosoftware.hobson.api.device.*;
import com.whizzosoftware.hobson.api.persist.ContextPathIdProvider;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.plugin.MockHobsonPlugin;
import com.whizzosoftware.hobson.api.variable.VariableContext;
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.api.variable.MockVariableManager;
import com.whizzosoftware.hobson.api.variable.VariableManager;
import com.whizzosoftware.hobson.dto.ExpansionFields;
import com.whizzosoftware.hobson.dto.MockIdProvider;
import com.whizzosoftware.hobson.dto.context.ManagerDTOBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import static org.junit.Assert.*;

public class HobsonDeviceDTOTest {
    @Test
    public void testConstructorWithJustId() {
        DeviceManager deviceManager = new MockDeviceManager();
        MockHobsonPlugin plugin = new MockHobsonPlugin("plugin1");
        plugin.setDeviceManager(deviceManager);
        HobsonDevice device = new MockHobsonDevice(plugin, "device1");
        VariableManager varManager = new MockVariableManager();
        IdProvider idProvider = new ContextPathIdProvider();
        HobsonDeviceDTO dto = new HobsonDeviceDTO.Builder(
            new ManagerDTOBuildContext.Builder().
                deviceManager(deviceManager).
                variableManager(varManager).
                idProvider(idProvider).
                build(),
            device,
            false
        ).build();
        assertEquals("hubs:local:devices:plugin1:device1", dto.getId());
    }

    @Test
    public void testConstructorWithDetailsAndNoExpansions() {
        DeviceManager deviceManager = new MockDeviceManager();
        MockHobsonPlugin plugin = new MockHobsonPlugin("plugin1");
        plugin.setDeviceManager(deviceManager);
        MockHobsonDevice device = new MockHobsonDevice(plugin, "device1");
        device.setDefaultName("deviceName");
        device.setType(DeviceType.LIGHTBULB);
        device.setManufacturerName("Mfg");
        device.setManufacturerVersion("1.0");
        device.setModelName("model");
        device.setDeviceAvailability(true, 100L);

        VariableManager varManager = new MockVariableManager();

        IdProvider idProvider = new ContextPathIdProvider();

        HobsonDeviceDTO dto = new HobsonDeviceDTO.Builder(
            new ManagerDTOBuildContext.Builder().
                deviceManager(deviceManager).
                variableManager(varManager).
                idProvider(idProvider).
                build(),
            device,
            true
        ).build();

        assertEquals("hubs:local:devices:plugin1:device1", dto.getId());
        assertEquals("deviceName", dto.getName());
        assertEquals("Mfg", dto.getManufacturerName());
        assertEquals("1.0", dto.getManufacturerVersion());
        assertEquals("model", dto.getModelName());
        assertEquals("LIGHTBULB", dto.getType().toString());
        assertEquals(100, (long)dto.getLastCheckIn());
        assertEquals("hubs:local:variables:plugin1:device1", dto.getVariables().getId());
    }

    @Test
    public void testConstructorWithDetailsAndVariablesExpansions() {
        DeviceManager deviceManager = new MockDeviceManager();
        MockHobsonPlugin plugin = new MockHobsonPlugin("plugin1");
        plugin.setDeviceManager(deviceManager);
        MockHobsonDevice device = new MockHobsonDevice(plugin, "device1");
        device.setDefaultName("deviceName");
        device.setType(DeviceType.LIGHTBULB);
        device.setDeviceAvailability(true, 100L);


        VariableManager varManager = new MockVariableManager();
        varManager.publishVariable(VariableContext.create(device.getContext(), "foo"), "bar", HobsonVariable.Mask.READ_ONLY, null);

        IdProvider idProvider = new ContextPathIdProvider();

        HobsonDeviceDTO dto = new HobsonDeviceDTO.Builder(
            new ManagerDTOBuildContext.Builder().
                deviceManager(deviceManager).
                variableManager(varManager).
                idProvider(idProvider).
                expansionFields(new ExpansionFields(JSONAttributes.VARIABLES)).
                build(),
            device,
            true
        ).build();

        assertEquals("hubs:local:devices:plugin1:device1", dto.getId());
        assertEquals("deviceName", dto.getName());
        assertEquals("LIGHTBULB", dto.getType().toString());
        assertEquals(100, (long)dto.getLastCheckIn());
        assertEquals("hubs:local:variables:plugin1:device1", dto.getVariables().getId());
        assertEquals(1, (int)dto.getVariables().getNumberOfItems());
        assertEquals("hubs:local:variables:plugin1:device1:foo", dto.getVariables().getItemListElement().get(0).getItem().getId());
    }

    @Test
    public void testJSONConstructor() {
        JSONObject json = new JSONObject(new JSONTokener("{\"cclass\":{},\"lastUpdate\":1448403617684,\"telemetry\":{},\"available\":true,\"manufacturerVersion\":\"1.0\",\"type\":\"WEATHER_STATION\",\"variables\":{\"numberOfItems\":6,\"itemListElement\":[{\"item\":{\"lastUpdate\":1448403617675,\"name\":\"windSpdMph\",\"value\":2,\"mask\":\"READ_ONLY\",\"@id\":\"local:hubs:local:variables:device:windSpdMph\"}},{\"item\":{\"lastUpdate\":1448403617665,\"name\":\"outRh\",\"value\":30,\"mask\":\"READ_ONLY\",\"@id\":\"local:hubs:local:variables:device:outRh\"}},{\"item\":{\"lastUpdate\":1448403617650,\"name\":\"inTempF\",\"value\":76,\"mask\":\"READ_ONLY\",\"@id\":\"local:hubs:local:variables:device:inTempF\"}},{\"item\":{\"lastUpdate\":1448403617684,\"name\":\"windDirDeg\",\"value\":270,\"mask\":\"READ_ONLY\",\"@id\":\"local:hubs:local:variables:device:windDirDeg\"}},{\"item\":{\"lastUpdate\":1448403617643,\"name\":\"outTempF\",\"value\":74,\"mask\":\"READ_ONLY\",\"@id\":\"local:hubs:local:variables:device:outTempF\"}},{\"item\":{\"lastUpdate\":1448403617659,\"name\":\"inRh\",\"value\":32,\"mask\":\"READ_ONLY\",\"@id\":\"local:hubs:local:variables:device:inRh\"}}],\"@id\":\"local:hubs:local:devices:com.whizzosoftware.hobson.hub.hobson-hub-sample:wstation:variables\"},\"preferredVariable\":{\"@id\":\"local:hubs:local:variables:device:outTempF\"},\"modelName\":\"Sample Weather Station\",\"lastCheckIn\":1448403617736,\"name\":\"Weather Station\",\"manufacturerName\":\"Whizzo Software LLC\",\"@id\":\"local:hubs:local:devices:com.whizzosoftware.hobson.hub.hobson-hub-sample:wstation\",\"configuration\":{}}"));
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
