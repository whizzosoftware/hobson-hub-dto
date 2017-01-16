/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.variable;

import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.api.variable.*;
import com.whizzosoftware.hobson.dto.context.ManagerDTOBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class HobsonVariableDTOTest {
    @Test
    public void testToJSON() {
        HobsonVariableDTO dto = new HobsonVariableDTO.Builder(new ManagerDTOBuildContext(), new TemplatedId("varLink", null)).name("varName").mask(VariableMask.READ_WRITE).lastUpdate(1000L).valueMediaType(VariableMediaType.VIDEO_MP4).build();
        JSONObject json = dto.toJSON();
        assertEquals("varLink", json.getString("@id"));
        assertEquals("varName", json.getString("name"));
        assertEquals("READ_WRITE", json.getString("mask"));
        assertEquals("VIDEO_MP4", json.getString(JSONAttributes.MEDIA_TYPE));
        assertEquals(1000, json.getDouble("lastUpdate"), 0);
    }

    @Test
    public void testHobsonVariableConstructor() {
        DeviceVariableContext vctx = DeviceVariableContext.createLocal("plugin1", "device1", "name");
        DeviceProxyVariable vv = new DeviceProxyVariable(vctx, VariableMask.READ_ONLY, VariableMediaType.IMAGE_PNG, "foo", 0L);
        HobsonVariableDTO dto = new HobsonVariableDTO.Builder(new ManagerDTOBuildContext(), new TemplatedId("id", null), vv.getDescriptor(), vv.getState(), true).build();
        assertEquals("name", dto.getName());
        assertEquals(VariableMask.READ_ONLY, dto.getMask());
        assertEquals(0L, (long)dto.getLastUpdate());
        assertEquals("foo", dto.getValue());
        assertEquals(VariableMediaType.IMAGE_PNG, dto.getValueMediaType());
    }
}
