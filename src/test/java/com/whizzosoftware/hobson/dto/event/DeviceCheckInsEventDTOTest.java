/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.event;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.event.DeviceCheckInEvent;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeviceCheckInsEventDTOTest {
    @Test
    public void testJSONConstructor() {
        long now = System.currentTimeMillis();

        JSONObject json = new JSONObject();
        json.put(JSONAttributes.EVENT_ID, DeviceCheckInsEventDTO.ID);
        json.put(JSONAttributes.TIMESTAMP, System.currentTimeMillis());
        JSONObject jprops = new JSONObject();
        JSONArray ja = new JSONArray();
        ja.put(new DeviceCheckInEventDTO(new DeviceCheckInEvent(DeviceContext.createLocal("plugin1", "device1"), now)).toJSON());
        jprops.put(JSONAttributes.EVENTS, ja);
        json.put(JSONAttributes.PROPERTIES, jprops);

        DeviceCheckInsEventDTO dto = new DeviceCheckInsEventDTO(json);
        assertEquals(1, dto.getEvents().size());
        DeviceCheckInEventDTO dcie = dto.getEvents().get(0);
        assertEquals("plugin1", dcie.getPluginId());
        assertEquals("device1", dcie.getDeviceId());
        assertEquals(now, (long)dcie.getCheckInTime());
    }
}
