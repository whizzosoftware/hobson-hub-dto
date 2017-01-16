/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.property;

import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.persist.ContextPathIdProvider;
import com.whizzosoftware.hobson.api.plugin.PluginContext;
import com.whizzosoftware.hobson.api.property.*;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.context.ManagerDTOBuildContext;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Collections;

public class PropertyContainerSetDTOTest {
    @Test
    public void testObjectConstructorWithDetails() {
        HubContext hctx = HubContext.createLocal();
        DTOBuildContext ctx = new ManagerDTOBuildContext.Builder().idProvider(new ContextPathIdProvider()).build();
        PropertyContainerSet pcs = new PropertyContainerSet(
            "actionset1",
            Collections.singletonList(new PropertyContainer(
                PropertyContainerClassContext.create(PluginContext.create(hctx, "plugin1"), "cc1"),
                Collections.singletonMap("prop1", (Object)"bar")
            ))
        );
        PropertyContainerClassProvider pccp  = new PropertyContainerClassProvider() {
            @Override
            public PropertyContainerClass getPropertyContainerClass(PropertyContainerClassContext ctx) {
                return new PropertyContainerClass(ctx, PropertyContainerClassType.ACTION, Collections.singletonList(new TypedProperty.Builder("prop1", "prop1name", "prop1desc", TypedProperty.Type.STRING).build()));
            }
        };

        PropertyContainerSetDTO dto = new PropertyContainerSetDTO.Builder(ctx, hctx, pcs, PropertyContainerClassType.ACTION, pccp, true).build();
        assertNotNull(dto.getContainers());
        assertEquals(1, dto.getContainers().size());
        assertNotNull(dto.getContainers().get(0).getContainerClass());
        assertEquals("hubs:local:plugins:plugin1:containerClasses:cc1", dto.getContainers().get(0).getContainerClass().getId());
        assertNotNull(dto.getContainers().get(0).getValues());
        assertEquals(1, dto.getContainers().get(0).getValues().size());
        assertEquals("bar", dto.getContainers().get(0).getValues().get("prop1"));
    }
}
