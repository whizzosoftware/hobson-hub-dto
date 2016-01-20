/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.telemetry;

import com.whizzosoftware.hobson.dto.variable.HobsonVariableDTO;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
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
}
