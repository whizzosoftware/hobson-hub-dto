/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.event;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.event.HobsonEvent;
import com.whizzosoftware.hobson.api.event.VariableUpdateRequestEvent;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.variable.VariableUpdate;
import com.whizzosoftware.hobson.dto.variable.VariableUpdateDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VariableUpdateRequestEventDTO extends HobsonEventDTO {
    private List<VariableUpdateDTO> updates;
    private IdProvider idProvider;
    private boolean convertToLocal;

    public VariableUpdateRequestEventDTO(VariableUpdateRequestEvent event, IdProvider idProvider) {
        super(event.getEventId(), event.getTimestamp());

        this.idProvider = idProvider;

        // create update JSON
        if (event.getUpdates() != null) {
            updates = new ArrayList<>();
            for (VariableUpdate update : event.getUpdates()) {
                updates.add(new VariableUpdateDTO(update, idProvider));
            }
        }
    }

    public VariableUpdateRequestEventDTO(JSONObject json, IdProvider idProvider, boolean convertToLocal) {
        super(json);
        this.idProvider = idProvider;
        this.convertToLocal = convertToLocal;
    }

    public VariableUpdateRequestEventDTO(JSONObject json, IdProvider idProvider) {
        this(json, idProvider, false);
    }


    public List<VariableUpdateDTO> getUpdates() {
        return updates;
    }

    @Override
    protected JSONObject createProperties() {
        JSONObject json = new JSONObject();
        if (updates != null) {
            JSONArray arr = new JSONArray();
            for (VariableUpdateDTO dto : updates) {
                arr.put(dto.toJSON());
            }
            json.put(JSONAttributes.UPDATES, arr);
        }
        return json;
    }

    @Override
    protected void readProperties(JSONObject json) {
        if (json.has(JSONAttributes.UPDATES)) {
            updates = new ArrayList<>();
            JSONArray arr = json.getJSONArray(JSONAttributes.UPDATES);
            for (int i=0; i < arr.length(); i++) {
                JSONObject jo = arr.getJSONObject(i);
                updates.add(new VariableUpdateDTO(jo));
            }
        }
    }

    @Override
    public HobsonEvent createEvent() {
        List<VariableUpdate> u = new ArrayList<>();
        for (VariableUpdateDTO dto : updates) {
            DeviceContext dctx = convertToLocal ? idProvider.createDeviceContextWithHub(HubContext.createLocal(), dto.getId()) : idProvider.createDeviceContext(dto.getId());
            u.add(new VariableUpdate(
                dctx,
                dto.getName(),
                dto.getValue(),
                dto.getTimestamp()
            ));
        }
        return new VariableUpdateRequestEvent(this.timestamp, u);
    }
}
