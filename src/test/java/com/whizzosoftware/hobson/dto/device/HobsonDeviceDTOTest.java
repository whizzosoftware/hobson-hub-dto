/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.device;

import com.whizzosoftware.hobson.api.device.*;
import com.whizzosoftware.hobson.api.plugin.MockHobsonPlugin;
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.api.variable.MockVariableManager;
import com.whizzosoftware.hobson.api.variable.VariableManager;
import com.whizzosoftware.hobson.dto.DTOBuildContext;
import com.whizzosoftware.hobson.dto.ExpansionFields;
import com.whizzosoftware.hobson.dto.MockIdProvider;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.junit.Test;
import static org.junit.Assert.*;

public class HobsonDeviceDTOTest {
    @Test
    public void testConstructorWithJustId() {
        HobsonDevice device = new MockHobsonDevice(new MockHobsonPlugin("plugin1"), "device1");
        DeviceManager deviceManager = new MockDeviceManager();
        VariableManager varManager = new MockVariableManager();
        MockIdProvider idProvider = new MockIdProvider();
        idProvider.setDeviceId("device1Link");
        HobsonDeviceDTO dto = new HobsonDeviceDTO.Builder(
            new DTOBuildContext.Builder().
                deviceManager(deviceManager).
                variableManager(varManager).
                idProvider(idProvider).
                build(),
            device,
            false
        ).build();
        assertEquals("device1Link", dto.getId());
    }

    @Test
    public void testConstructorWithDetailsAndNoExpansions() {
        MockHobsonDevice device = new MockHobsonDevice(new MockHobsonPlugin("plugin1"), "device1");
        device.setDefaultName("deviceName");
        device.setType(DeviceType.LIGHTBULB);
        device.checkInDevice(100l);

        DeviceManager deviceManager = new MockDeviceManager();
        VariableManager varManager = new MockVariableManager();

        MockIdProvider idProvider = new MockIdProvider();
        idProvider.setDeviceId("device1Link");
        idProvider.setDeviceVariablesId("deviceVariablesLink");

        HobsonDeviceDTO dto = new HobsonDeviceDTO.Builder(
            new DTOBuildContext.Builder().
                deviceManager(deviceManager).
                variableManager(varManager).
                idProvider(idProvider).
                build(),
            device,
            true
        ).build();

        assertEquals("device1Link", dto.getId());
        assertEquals("deviceName", dto.getName());
        assertEquals("LIGHTBULB", dto.getType().toString());
        assertEquals(100, (long) dto.getCheckInTime());
        assertEquals("deviceVariablesLink", dto.getVariables().getId());
    }

    @Test
    public void testConstructorWithDetailsAndVariablesExpansions() {
        MockHobsonDevice device = new MockHobsonDevice(new MockHobsonPlugin("plugin1"), "device1");
        device.setDefaultName("deviceName");
        device.setType(DeviceType.LIGHTBULB);
        device.checkInDevice(100l);

        DeviceManager deviceManager = new MockDeviceManager();

        VariableManager varManager = new MockVariableManager();
        varManager.publishDeviceVariable(device.getContext(), "foo", "bar", HobsonVariable.Mask.READ_ONLY);

        MockIdProvider idProvider = new MockIdProvider();
        idProvider.setDeviceId("device1Link");
        idProvider.setDeviceVariablesId("deviceVariablesLink");
        idProvider.setDeviceVariableId("deviceVariableLink");

        HobsonDeviceDTO dto = new HobsonDeviceDTO.Builder(
            new DTOBuildContext.Builder().
                deviceManager(deviceManager).
                variableManager(varManager).
                idProvider(idProvider).
                expansionFields(new ExpansionFields(JSONAttributes.VARIABLES)).
                build(),
            device,
            true
        ).build();

        assertEquals("device1Link", dto.getId());
        assertEquals("deviceName", dto.getName());
        assertEquals("LIGHTBULB", dto.getType().toString());
        assertEquals(100, (long)dto.getCheckInTime());
        assertEquals("deviceVariablesLink", dto.getVariables().getId());
        assertEquals(1, (int)dto.getVariables().getNumberOfItems());
        assertEquals("deviceVariableLink", dto.getVariables().getItemListElement().get(0).getItem().getId());
    }
}
