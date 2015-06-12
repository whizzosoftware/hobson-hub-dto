/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.variable;

import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class HobsonVariableDTOTest {
    @Test
    public void testToJSON() {
        HobsonVariableDTO dto = new HobsonVariableDTO.Builder("varLink").name("varName").mask(HobsonVariable.Mask.READ_WRITE).lastUpdate(1000l).build();
        JSONObject json = dto.toJSON();
        assertEquals("varLink", json.getString("@id"));
        assertEquals("varName", json.getString("name"));
        assertEquals("READ_WRITE", json.getString("mask"));
        assertEquals(1000, json.getDouble("lastUpdate"), 0);
    }
}
