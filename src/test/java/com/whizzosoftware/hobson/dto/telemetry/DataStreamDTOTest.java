/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.telemetry;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.persist.ContextPathIdProvider;
import com.whizzosoftware.hobson.api.telemetry.DataStream;
import com.whizzosoftware.hobson.api.variable.VariableContext;
import com.whizzosoftware.hobson.dto.ExpansionFields;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.context.ManagerDTOBuildContext;
import com.whizzosoftware.hobson.dto.variable.HobsonVariableDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class DataStreamDTOTest {
    @Test
    public void testJSONConstructor() {
        JSONObject json = new JSONObject(new JSONTokener("{\"name\":\"My Data Stream\",\"variables\":[{\"@id\":\"/api/v1/users/local/hubs/local/plugins/com.whizzosoftware.hobson.hub.hobson-hub-sample/devices/wstation/variables/inRh\"},{\"@id\":\"/api/v1/users/local/hubs/local/plugins/com.whizzosoftware.hobson.hub.hobson-hub-sample/devices/bulb/variables/color\"}]}"));
        DataStreamDTO dto = new DataStreamDTO.Builder(json).build();
        assertEquals("My Data Stream", dto.getName());
        assertEquals(2, dto.getVariables().size());

        for (HobsonVariableDTO hv : dto.getVariables()) {
            assertTrue("/api/v1/users/local/hubs/local/plugins/com.whizzosoftware.hobson.hub.hobson-hub-sample/devices/wstation/variables/inRh".equals(hv.getId()) || "/api/v1/users/local/hubs/local/plugins/com.whizzosoftware.hobson.hub.hobson-hub-sample/devices/bulb/variables/color".equals(hv.getId()));
        }
    }

    @Test
    public void testDataStreamConstructorNoDetails() {
        DataStreamDTO dto = createDTO(new ManagerDTOBuildContext.Builder().idProvider(new ContextPathIdProvider()).build(), false);
        assertEquals("dataStreams:ds1", dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getVariables());
        assertNull(dto.getLinks());
    }

    @Test
    public void testDataStreamConstructorDetails() {
        DataStreamDTO dto = createDTO(new ManagerDTOBuildContext.Builder().idProvider(new ContextPathIdProvider()).build(), true);
        assertNotNull(dto.getVariables());
        assertEquals(1, dto.getVariables().size());
        assertEquals("dataStreams:ds1:data", dto.getLinks().get(JSONAttributes.DATA));
    }

    @Test
    public void testDataStreamConstructorDetailsAndDataExpansion() {
        ExpansionFields ef = new ExpansionFields(JSONAttributes.DATA);
        DataStreamDTO dto = createDTO(new ManagerDTOBuildContext.Builder().idProvider(new ContextPathIdProvider()).expansionFields(ef).build(), true);
        assertNotNull(dto.getVariables());
        assertEquals(1, dto.getVariables().size());
        assertEquals("dataStreams:ds1:data", dto.getLinks().get(JSONAttributes.DATA));
    }

    private DataStreamDTO createDTO(DTOBuildContext ctx, boolean showDetails) {
        Collection<VariableContext> vars = new ArrayList<>();
        vars.add(VariableContext.create(DeviceContext.createLocal("plugin1", "device1"), "var1"));
        DataStream ds = new DataStream("ds1", "My DS", vars);
        DataStreamDTO dto = new DataStreamDTO.Builder(ctx, ds, showDetails).build();
        assertEquals("dataStreams:ds1", dto.getId());
        if (showDetails) {
            assertEquals("My DS", dto.getName());
        } else {
            assertNull(dto.getName());
        }
        return dto;
    }
}
