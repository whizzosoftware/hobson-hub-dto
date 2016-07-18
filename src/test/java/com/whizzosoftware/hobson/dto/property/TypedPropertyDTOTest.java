/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.property;

import com.whizzosoftware.hobson.api.property.PropertyConstraintType;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.property.TypedPropertyConstraint;
import com.whizzosoftware.hobson.api.variable.VariableConstants;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TypedPropertyDTOTest {
    @Test
    public void testToJSON() {
        List<TypedPropertyConstraint> constraints = new ArrayList<>();
        constraints.add(new TypedPropertyConstraint(PropertyConstraintType.deviceVariable, VariableConstants.ON));
        TypedPropertyDTO dto = new TypedPropertyDTO.Builder("link").name("name").description("desc").type(TypedProperty.Type.SECURE_STRING).constraints(constraints).build();
        JSONObject json = dto.toJSON();
        assertEquals("link", json.getString("@id"));
        assertEquals("name", json.getString("name"));
        assertEquals("desc", json.getString("description"));
        assertEquals("on", json.getJSONObject("constraints").getString("deviceVariable"));
        assertEquals("SECURE_STRING", json.getString("type"));
    }
}
