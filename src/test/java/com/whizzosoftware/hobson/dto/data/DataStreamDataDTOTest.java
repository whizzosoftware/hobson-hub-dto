/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.data;

import com.whizzosoftware.hobson.api.data.DataStreamField;
import com.whizzosoftware.hobson.api.data.DataStreamInterval;
import com.whizzosoftware.hobson.api.data.DataStreamValueSet;
import com.whizzosoftware.hobson.api.persist.ContextPathIdProvider;
import com.whizzosoftware.hobson.api.variable.DeviceVariableContext;
import com.whizzosoftware.hobson.dto.context.ManagerDTOBuildContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class DataStreamDataDTOTest {
    @Test
    public void testToJSON() {
        DataStreamDataDTO dto = new DataStreamDataDTO.Builder(new ManagerDTOBuildContext.Builder().idProvider(new ContextPathIdProvider()).build(), "ds1", 2000, DataStreamInterval.HOURS_1).
            fields(Collections.singletonList(new DataStreamField("field1", "fieldName1", DeviceVariableContext.createLocal("plugin1", "device1", "var1")))).
            data(Collections.singletonList(new DataStreamValueSet(1000, Collections.singletonMap("field1", (Object)100)))).
            build();

        JSONObject json = dto.toJSON();
        assertEquals(2000, json.getInt("endTime"));
        JSONObject f = json.getJSONObject("fields");
        assertEquals("fieldName1", f.getString("field1"));
        JSONArray a = json.getJSONArray("data");
        assertEquals(1, a.length());
        assertEquals(1000, a.getJSONObject(0).getInt("timestamp"));
        assertEquals(100, a.getJSONObject(0).getInt("field1"));
    }
}
