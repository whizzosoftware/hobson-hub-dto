/*******************************************************************************
 * Copyright (c) 2014 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.event;

import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.variable.VariableConstants;
import com.whizzosoftware.hobson.dto.device.HobsonDeviceDTO;
import com.whizzosoftware.hobson.dto.variable.HobsonVariableDTO;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SnapshotEventDTOTest {
    @Test
    public void testJSONConstructor() {
        JSONObject json = new JSONObject(new JSONTokener("{\"timestamp\":1447796857593,\"eventId\":\"snapshot\",\"properties\":{\"devices\":[{\"cclass\":{\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:camera:configurationClass\"},\"lastCheckIn\":1447796855122,\"lastUpdate\":1447796825158,\"telemetry\":{},\"name\":\"Security Camera\",\"available\":true,\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:camera\",\"configuration\":{},\"type\":\"CAMERA\",\"variables\":{\"numberOfItems\":1,\"itemListElement\":[{\"item\":{\"lastUpdate\":1447796825158,\"name\":\"imageStatusUrl\",\"value\":\"http://hobson-automation.com/img/security-example.jpg\",\"mask\":\"READ_ONLY\",\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:camera:variablesimageStatusUrl\"}}],\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:camera:variables\"},\"preferredVariable\":{\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:camera:variablesimageStatusUrl\"}},{\"cclass\":{\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:bulb:configurationClass\"},\"lastCheckIn\":1447796855122,\"lastUpdate\":1447796825136,\"telemetry\":{},\"name\":\"Color LED Bulb\",\"available\":true,\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:bulb\",\"configuration\":{},\"type\":\"LIGHTBULB\",\"variables\":{\"numberOfItems\":3,\"itemListElement\":[{\"item\":{\"lastUpdate\":1447796825130,\"name\":\"level\",\"value\":100,\"mask\":\"READ_WRITE\",\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:bulb:variableslevel\"}},{\"item\":{\"lastUpdate\":1447796825121,\"name\":\"color\",\"value\":\"#0000ff\",\"mask\":\"READ_WRITE\",\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:bulb:variablescolor\"}},{\"item\":{\"lastUpdate\":1447796825136,\"name\":\"on\",\"value\":true,\"mask\":\"READ_WRITE\",\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:bulb:variableson\"}}],\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:bulb:variables\"},\"preferredVariable\":{\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:bulb:variableson\"}},{\"cclass\":{\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:switch:configurationClass\"},\"lastCheckIn\":1447796855122,\"lastUpdate\":1447796825148,\"telemetry\":{},\"name\":\"Switchable Outlet\",\"available\":true,\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:switch\",\"configuration\":{},\"type\":\"SWITCH\",\"variables\":{\"numberOfItems\":1,\"itemListElement\":[{\"item\":{\"lastUpdate\":1447796825148,\"name\":\"on\",\"value\":false,\"mask\":\"READ_WRITE\",\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:switch:variableson\"}}],\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:switch:variables\"},\"preferredVariable\":{\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:switch:variableson\"}},{\"cclass\":{\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:wstation:configurationClass\"},\"lastCheckIn\":1447796855122,\"lastUpdate\":1447796825208,\"telemetry\":{},\"name\":\"Weather Station\",\"available\":true,\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:wstation\",\"configuration\":{},\"type\":\"WEATHER_STATION\",\"variables\":{\"numberOfItems\":6,\"itemListElement\":[{\"item\":{\"lastUpdate\":1447796825200,\"name\":\"windSpdMph\",\"value\":2,\"mask\":\"READ_ONLY\",\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:wstation:variableswindSpdMph\"}},{\"item\":{\"lastUpdate\":1447796825190,\"name\":\"outRh\",\"value\":30,\"mask\":\"READ_ONLY\",\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:wstation:variablesoutRh\"}},{\"item\":{\"lastUpdate\":1447796825177,\"name\":\"inTempF\",\"value\":76,\"mask\":\"READ_ONLY\",\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:wstation:variablesinTempF\"}},{\"item\":{\"lastUpdate\":1447796825208,\"name\":\"windDirDeg\",\"value\":270,\"mask\":\"READ_ONLY\",\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:wstation:variableswindDirDeg\"}},{\"item\":{\"lastUpdate\":1447796825168,\"name\":\"outTempF\",\"value\":74,\"mask\":\"READ_ONLY\",\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:wstation:variablesoutTempF\"}},{\"item\":{\"lastUpdate\":1447796825185,\"name\":\"inRh\",\"value\":32,\"mask\":\"READ_ONLY\",\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:wstation:variablesinRh\"}}],\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:wstation:variables\"},\"preferredVariable\":{\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:wstation:variablesoutTempF\"}},{\"cclass\":{\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:thermostat:configurationClass\"},\"lastCheckIn\":1447796855122,\"lastUpdate\":1447796825226,\"telemetry\":{},\"name\":\"Thermostat\",\"available\":true,\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:thermostat\",\"configuration\":{},\"type\":\"THERMOSTAT\",\"variables\":{\"numberOfItems\":2,\"itemListElement\":[{\"item\":{\"lastUpdate\":1447796825220,\"name\":\"inTempF\",\"value\":73,\"mask\":\"READ_ONLY\",\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:thermostat:variablesinTempF\"}},{\"item\":{\"lastUpdate\":1447796825226,\"name\":\"targetTempF\",\"value\":74,\"mask\":\"READ_WRITE\",\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:thermostat:variablestargetTempF\"}}],\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:thermostat:variables\"},\"preferredVariable\":{\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-sample:thermostat:variablesinTempF\"}}],\"variables\":[{\"lastUpdate\":1447796824822,\"name\":\"sunrise\",\"mask\":\"READ_ONLY\",\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-scheduler:globalVariables:sunrise\"},{\"lastUpdate\":1447796824835,\"name\":\"sunset\",\"mask\":\"READ_ONLY\",\"@id\":\"local:local:com.whizzosoftware.hobson.hub.hobson-hub-scheduler:globalVariables:sunset\"}]}}"));
        SnapshotEventDTO dto = new SnapshotEventDTO.Builder(json).build();

        assertNotNull(dto.getDevices());
        assertEquals(5, dto.getDevices().size());

        Map<String,HobsonDeviceDTO> deviceMap = new HashMap<>();
        for (HobsonDeviceDTO hd : dto.getDevices()) {
            deviceMap.put(hd.getName(), hd);
        }

        HobsonDeviceDTO hd = deviceMap.get("Security Camera");
        assertNotNull(hd);
        assertEquals("Security Camera", hd.getName());
        assertEquals(DeviceType.CAMERA, hd.getType());
        assertEquals(1, (int)hd.getVariables().getNumberOfItems());
        HobsonVariableDTO vd = (HobsonVariableDTO)hd.getVariables().getItemListElement().get(0).getItem();
        assertEquals(VariableConstants.IMAGE_STATUS_URL, vd.getName());
        assertEquals("http://hobson-automation.com/img/security-example.jpg", vd.getValue());

        assertNotNull(dto.getVariables());

        assertEquals(2, dto.getVariables().size());
        HobsonVariableDTO hv = dto.getVariables().get(0);
        assertEquals("sunrise", hv.getName());
        assertNull(hv.getValue());

        hv = dto.getVariables().get(1);
        assertEquals("sunset", hv.getName());
        assertNull(hv.getValue());
    }
}
