/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.property;

import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.property.PropertyContainerClass;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassContext;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassType;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PropertyContainerClassDTOTest {
    @Test
    public void testBuilderWithPCC() {
        List<TypedProperty> props = new ArrayList<>();
        props.add(new TypedProperty.Builder("id1", "name1", "desc1", TypedProperty.Type.STRING).build());
        props.add(new TypedProperty.Builder("id2", "name2", "desc2", TypedProperty.Type.STRING).isPublic(false).build());

        PropertyContainerClass pcc = new PropertyContainerClass(
          PropertyContainerClassContext.create(HubContext.createLocal(), "cc1"),
          "name",
           PropertyContainerClassType.DEVICE_CONFIG,
           "desc",
           props
        );

        PropertyContainerClassDTO dto = new PropertyContainerClassDTO.Builder(
          "id3",
          pcc,
          true
        ).build();

        assertEquals("id3", dto.getId());
        assertEquals("name", dto.getName());
        assertEquals("desc", dto.getDescriptionTemplate());
        assertEquals(1, dto.getSupportedProperties().size());
        assertEquals("id1", dto.getSupportedProperties().get(0).getId());
        assertEquals("name1", dto.getSupportedProperties().get(0).getName());
        assertEquals("desc1", dto.getSupportedProperties().get(0).getDescription());
    }
}