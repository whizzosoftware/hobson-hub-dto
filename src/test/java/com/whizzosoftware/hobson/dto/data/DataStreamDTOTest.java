/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.data;

import com.whizzosoftware.hobson.api.data.DataStreamField;
import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.persist.ContextPathIdProvider;
import com.whizzosoftware.hobson.api.data.DataStream;
import com.whizzosoftware.hobson.api.variable.VariableContext;
import com.whizzosoftware.hobson.dto.ExpansionFields;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.context.ManagerDTOBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class DataStreamDTOTest {
    @Test
    public void testJSONConstructor() {
        JSONObject json = new JSONObject(new JSONTokener("{\"name\":\"My Data Stream\",\"fields\":[{\"@id\":\"id1\", \"name\":\"field1\",\"variable\":{\"@id\":\"/api/v1/users/local/hubs/local/plugins/com.whizzosoftware.hobson.hub.hobson-hub-sample/devices/wstation/variables/inRh\"}},{\"@id\":\"id1\", \"name\":\"field2\",\"variable\":{\"@id\":\"/api/v1/users/local/hubs/local/plugins/com.whizzosoftware.hobson.hub.hobson-hub-sample/devices/bulb/variables/color\"}}]}"));
        DataStreamDTO dto = new DataStreamDTO.Builder(json).build();
        assertEquals("My Data Stream", dto.getName());
        assertEquals(2, dto.getFields().size());

        for (DataStreamFieldDTO dsf : dto.getFields()) {
            assertTrue("/api/v1/users/local/hubs/local/plugins/com.whizzosoftware.hobson.hub.hobson-hub-sample/devices/wstation/variables/inRh".equals(dsf.getVariable().getId()) || "/api/v1/users/local/hubs/local/plugins/com.whizzosoftware.hobson.hub.hobson-hub-sample/devices/bulb/variables/color".equals(dsf.getVariable().getId()));
            assertTrue("id1".equals(dsf.getId()) || "id2".equals(dsf.getId()));
            assertTrue("field1".equals(dsf.getName()) || "field2".equals(dsf.getName()));
        }
    }

    @Test
    public void testDataStreamConstructorNoDetails() {
        DataStreamDTO dto = createDTO(new ManagerDTOBuildContext.Builder().idProvider(new ContextPathIdProvider()).build(), false);
        assertEquals("dataStreams:ds1", dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getFields());
        assertNull(dto.getLinks());
    }

    @Test
    public void testDataStreamConstructorDetails() {
        DataStreamDTO dto = createDTO(new ManagerDTOBuildContext.Builder().idProvider(new ContextPathIdProvider()).build(), true);
        assertNotNull(dto.getFields());
        assertEquals(1, dto.getFields().size());
        assertEquals("dataStreams:ds1:data", dto.getLinks().get(JSONAttributes.DATA));
    }

    @Test
    public void testDataStreamConstructorDetailsAndDataExpansion() {
        ExpansionFields ef = new ExpansionFields(JSONAttributes.DATA);
        DataStreamDTO dto = createDTO(new ManagerDTOBuildContext.Builder().idProvider(new ContextPathIdProvider()).expansionFields(ef).build(), true);
        assertNotNull(dto.getFields());
        assertEquals(1, dto.getFields().size());
        assertEquals("dataStreams:ds1:data", dto.getLinks().get(JSONAttributes.DATA));
    }

    @Test
    public void testToJSON() {
        ExpansionFields ef = new ExpansionFields(JSONAttributes.DATA);
        DataStreamDTO dto = createDTO(new ManagerDTOBuildContext.Builder().idProvider(new ContextPathIdProvider()).expansionFields(ef).build(), true);
        JSONObject json = dto.toJSON();
        assertEquals("dataStreams:ds1", json.getString("@id"));
        assertEquals("My DS", json.getString("name"));
        JSONArray fa = json.getJSONArray("fields");
        assertEquals(1, fa.length());
        JSONObject f = fa.getJSONObject(0);
        assertEquals("dataStreams:ds1:fields:field1", f.getString("@id"));
        assertEquals("test", f.getString("name"));
        assertNotNull(f.getJSONObject("variable"));
        assertEquals("hubs:local:variables:plugin1:device1:var1", f.getJSONObject("variable").getString("@id"));
    }

    private DataStreamDTO createDTO(DTOBuildContext ctx, boolean showDetails) {
        Collection<DataStreamField> vars = new ArrayList<>();
        vars.add(new DataStreamField("field1", "test", VariableContext.create(DeviceContext.createLocal("plugin1", "device1"), "var1")));
        DataStream ds = new DataStream("ds1", "My DS", vars, null);
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
