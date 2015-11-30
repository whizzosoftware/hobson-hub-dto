/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.event;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import static org.junit.Assert.*;

public class VariableUpdateNotificationEventDTOTest {
    @Test
    public void testJSONConstructor() {
        JSONObject json = new JSONObject(new JSONTokener("{\"timestamp\":1448468996911,\"eventId\":\"varUpdateNotify\",\"properties\":{\"updates\":[{\"timestamp\":1448468996911,\"pluginId\":\"com.whizzosoftware.hobson.hub.hobson-hub-sample\",\"name\":\"on\",\"value\":true,\"deviceId\":\"bulb\"}]}}"));
        VariableUpdateNotificationEventDTO dto = new VariableUpdateNotificationEventDTO(json);
        assertNotNull(dto.getUpdates());
        assertEquals(1, dto.getUpdates().size());
        JSONObject j = dto.getUpdates().get(0);
        assertEquals("com.whizzosoftware.hobson.hub.hobson-hub-sample", j.getString("pluginId"));
        assertEquals("bulb", j.getString("deviceId"));
        assertEquals("on", j.getString("name"));
        assertTrue((boolean)j.get("value"));
    }

    @Test
    public void testJSONConstructor2() {
        JSONObject json = new JSONObject(new JSONTokener("{\"timestamp\":1448469575400,\"eventId\":\"varUpdateNotify\",\"properties\":{\"updates\":[{\"timestamp\":1448469575400,\"pluginId\":\"com.whizzosoftware.hobson.hub.hobson-hub-sample\",\"name\":\"inTempF\",\"value\":72,\"deviceId\":\"thermostat\"}]}}"));
        VariableUpdateNotificationEventDTO dto = new VariableUpdateNotificationEventDTO(json);
        assertNotNull(dto.getUpdates());
        assertEquals(1, dto.getUpdates().size());
        JSONObject j = dto.getUpdates().get(0);
        assertEquals("com.whizzosoftware.hobson.hub.hobson-hub-sample", j.getString("pluginId"));
        assertEquals("thermostat", j.getString("deviceId"));
        assertEquals("inTempF", j.getString("name"));
        assertEquals(72, (int)j.get("value"));
    }
}
