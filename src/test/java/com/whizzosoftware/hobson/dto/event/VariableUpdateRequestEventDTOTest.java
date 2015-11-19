/*******************************************************************************
 * Copyright (c) 2014 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.event;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.event.HobsonEvent;
import com.whizzosoftware.hobson.api.event.VariableUpdateRequestEvent;
import com.whizzosoftware.hobson.api.persist.ContextPathIdProvider;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.variable.VariableConstants;
import com.whizzosoftware.hobson.api.variable.VariableUpdate;
import com.whizzosoftware.hobson.dto.variable.VariableUpdateDTO;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import static org.junit.Assert.*;

public class VariableUpdateRequestEventDTOTest {
    @Test
    public void testEventConstructor() {
        long now = System.currentTimeMillis();

        DeviceContext dctx = DeviceContext.createLocal("plugin1", "device1");
        VariableUpdateRequestEvent event = new VariableUpdateRequestEvent(
            now,
            new VariableUpdate(
                dctx,
                VariableConstants.ON,
                true,
                now
            )
        );

        IdProvider idProvider = new ContextPathIdProvider();
        VariableUpdateRequestEventDTO dto = new VariableUpdateRequestEventDTO(event, idProvider);

        assertEquals(1, dto.getUpdates().size());
        VariableUpdateDTO vud = dto.getUpdates().get(0);
        assertEquals("local:hubs:local:devices:plugin1:device1", vud.getId());
        assertEquals(VariableConstants.ON, vud.getName());
        assertTrue((Boolean)vud.getValue());
        assertEquals(now, vud.getTimestamp());
    }

    @Test
    public void testJSONConstructor() {
        IdProvider idProvider = new ContextPathIdProvider();
        JSONObject json = new JSONObject(new JSONTokener("{\"eventId\":\"varUpdateReq\",\"properties\":{\"updates\":[{\"name\":\"on\",\"@id\":\"local:hubs:local:devices:plugin1:device1\",\"value\":true,\"timestamp\":1447887115419}]},\"timestamp\":1447887115419}"));

        VariableUpdateRequestEventDTO dto = new VariableUpdateRequestEventDTO(json, idProvider);
        assertNotNull(dto.getUpdates());
        assertEquals(1, dto.getUpdates().size());

        VariableUpdateDTO vud = dto.getUpdates().get(0);
        assertEquals("local:hubs:local:devices:plugin1:device1", vud.getId());
        assertEquals(VariableConstants.ON, vud.getName());
        assertTrue((Boolean)vud.getValue());
    }

    @Test
    public void testCreateEvent() {
        IdProvider idProvider = new ContextPathIdProvider();
        JSONObject json = new JSONObject(new JSONTokener("{\"eventId\":\"varUpdateReq\",\"properties\":{\"updates\":[{\"name\":\"on\",\"@id\":\"user1:hubs:hub1:devices:plugin1:device1\",\"value\":true,\"timestamp\":1447887115419}]},\"timestamp\":1447887115419}"));
        VariableUpdateRequestEventDTO dto = new VariableUpdateRequestEventDTO(json, idProvider);
        HobsonEvent event = dto.createEvent();
        assertTrue(event instanceof VariableUpdateRequestEvent);
        VariableUpdateRequestEvent e = (VariableUpdateRequestEvent)event;
        assertEquals(VariableUpdateRequestEvent.ID, e.getEventId());
        assertNotNull(e.getUpdates());
        assertEquals(1, e.getUpdates().size());
        VariableUpdate vu = e.getUpdates().get(0);
        assertEquals("hub1", vu.getDeviceContext().getHubId());
        assertEquals("user1", vu.getDeviceContext().getUserId());
        assertEquals("plugin1", vu.getDeviceContext().getPluginId());
        assertEquals("device1", vu.getDeviceContext().getDeviceId());
        assertEquals(VariableConstants.ON, vu.getName());
        assertTrue((Boolean)vu.getValue());
    }

    @Test
    public void testCreateLocalEvent() {
        IdProvider idProvider = new ContextPathIdProvider();
        JSONObject json = new JSONObject(new JSONTokener("{\"eventId\":\"varUpdateReq\",\"properties\":{\"updates\":[{\"name\":\"on\",\"@id\":\"user1:hubs:hub1:devices:plugin1:device1\",\"value\":true,\"timestamp\":1447887115419}]},\"timestamp\":1447887115419}"));
        VariableUpdateRequestEventDTO dto = new VariableUpdateRequestEventDTO(json, idProvider, true);
        HobsonEvent event = dto.createEvent();
        assertTrue(event instanceof VariableUpdateRequestEvent);
        VariableUpdateRequestEvent e = (VariableUpdateRequestEvent)event;
        assertEquals(VariableUpdateRequestEvent.ID, e.getEventId());
        assertNotNull(e.getUpdates());
        assertEquals(1, e.getUpdates().size());
        VariableUpdate vu = e.getUpdates().get(0);
        assertEquals("local", vu.getDeviceContext().getHubId());
        assertEquals("local", vu.getDeviceContext().getUserId());
        assertEquals("plugin1", vu.getDeviceContext().getPluginId());
        assertEquals("device1", vu.getDeviceContext().getDeviceId());
        assertEquals(VariableConstants.ON, vu.getName());
        assertTrue((Boolean)vu.getValue());
    }
}
