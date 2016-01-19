/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.variable;

import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.api.variable.ImmutableHobsonVariable;
import com.whizzosoftware.hobson.api.variable.VariableContext;
import com.whizzosoftware.hobson.api.variable.VariableMediaType;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class HobsonVariableDTOTest {
    @Test
    public void testToJSON() {
        HobsonVariableDTO dto = new HobsonVariableDTO.Builder("varLink").name("varName").mask(HobsonVariable.Mask.READ_WRITE).lastUpdate(1000l).valueMediaType(VariableMediaType.VIDEO_MP4).build();
        JSONObject json = dto.toJSON();
        assertEquals("varLink", json.getString("@id"));
        assertEquals("varName", json.getString("name"));
        assertEquals("READ_WRITE", json.getString("mask"));
        assertEquals("VIDEO_MP4", json.getString(JSONAttributes.MEDIA_TYPE));
        assertEquals(1000, json.getDouble("lastUpdate"), 0);
    }

    @Test
    public void testHobsonVariableConstructor() {
        ImmutableHobsonVariable v = new ImmutableHobsonVariable(VariableContext.createLocal("plugin1", "device1", "name"), HobsonVariable.Mask.READ_ONLY, "foo", 0L, VariableMediaType.IMAGE_PNG);
        HobsonVariableDTO dto = new HobsonVariableDTO.Builder("id", v, true).build();
        assertEquals("name", dto.getName());
        assertEquals(HobsonVariable.Mask.READ_ONLY, dto.getMask());
        assertEquals(0L, (long)dto.getLastUpdate());
        assertEquals("foo", dto.getValue());
        assertEquals(VariableMediaType.IMAGE_PNG, dto.getValueMediaType());
    }
}
