/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.json.event;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.event.HobsonEvent;
import com.whizzosoftware.hobson.api.event.VariableUpdateRequestEvent;
import com.whizzosoftware.hobson.api.variable.VariableUpdate;
import com.whizzosoftware.hobson.json.JSONSerializationHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VariableUpdateRequestEventSerializer implements HobsonEventSerializer {
    @Override
    public boolean canMarshal(HobsonEvent event) {
        return (event instanceof VariableUpdateRequestEvent);
    }

    @Override
    public JSONObject marshal(HobsonEvent event) {
        VariableUpdateRequestEvent vure = (VariableUpdateRequestEvent)event;
        JSONObject json = new JSONObject();
        json.put(PROP_EVENT, vure.getEventId());
        JSONArray updates = new JSONArray();
        for (VariableUpdate update : vure.getUpdates()) {
            updates.put(JSONSerializationHelper.createVariableUpdateJSON(update));
        }
        json.put("updates", updates);
        return json;
    }

    @Override
    public boolean canUnmarshal(JSONObject json) {
        return (json.has(PROP_EVENT) && json.getString(PROP_EVENT).equals(VariableUpdateRequestEvent.ID));
    }

    @Override
    public HobsonEvent unmarshal(JSONObject json) {
        List<VariableUpdate> updates = new ArrayList<>();
        if (json.has("updates")) {
            JSONArray jups = json.getJSONArray("updates");
            for (int i=0; i < jups.length(); i++) {
                JSONObject jup = jups.getJSONObject(i);
                // determine if it's a device variable update or a global variable update
                if (jup.has("device")) {
                    updates.add(new VariableUpdate(DeviceContext.createLocal(jup.getString("plugin"), jup.getString("device")), jup.getString("name"), jup.get("value")));
                } else {
                    updates.add(new VariableUpdate(DeviceContext.createLocalGlobal(jup.getString("plugin")), jup.getString("name"), jup.get("value")));
                }
            }
        }
        return new VariableUpdateRequestEvent(System.currentTimeMillis(), updates);
    }
}
