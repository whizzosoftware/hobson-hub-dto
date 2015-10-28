/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.presence;

import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class PresenceLocationDTOTest {
    @Test
    public void testBuildWithMapLocation() {
        PresenceLocationDTO.Builder builder = new PresenceLocationDTO.Builder("foo");
        builder.name("name").latitude(1.0).longitude(2.0).radius(3.0);
        PresenceLocationDTO dto = builder.build();
        assertEquals("foo", dto.getId());
        assertEquals("name", dto.getName());
        assertEquals(1.0, dto.getLatitude(), 0.0);
        assertEquals(2.0, dto.getLongitude(), 0.0);
        assertEquals(3.0, dto.getRadius(), 3.0);
        assertNull(dto.getBeaconMajor());
        assertNull(dto.getBeaconMinor());
    }

    @Test
    public void testBuildWithBeaconLocation() {
        PresenceLocationDTO.Builder builder = new PresenceLocationDTO.Builder("bar");
        builder.name("beacon").beaconMajor(1).beaconMinor(2);
        PresenceLocationDTO dto = builder.build();
        assertEquals("beacon", dto.getName());
        assertEquals((Integer)1, dto.getBeaconMajor());
        assertEquals((Integer)2, dto.getBeaconMinor());
        assertNull(dto.getLatitude());
        assertNull(dto.getLongitude());
        assertNull(dto.getRadius());
    }

    @Test
    public void testBuildWithEmptyLocation() {
        PresenceLocationDTO.Builder builder = new PresenceLocationDTO.Builder((String)null);
        PresenceLocationDTO dto = builder.build();
        assertNull(dto.getId());
    }

    @Test
    public void testBuildWithEmptyLocationJSON() {
        JSONObject json = new JSONObject();
        json.put("location", new JSONObject());
        PresenceLocationDTO.Builder builder = new PresenceLocationDTO.Builder(json);
        PresenceLocationDTO dto = builder.build();
        assertNull(dto.getId());
    }
}
