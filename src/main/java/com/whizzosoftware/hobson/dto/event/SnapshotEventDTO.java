/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.event;

import com.whizzosoftware.hobson.api.event.HobsonEvent;
import com.whizzosoftware.hobson.dto.device.HobsonDeviceDTO;
import com.whizzosoftware.hobson.dto.variable.HobsonVariableDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for snapshot events.
 *
 * @author Dan Noguerol
 */
public class SnapshotEventDTO extends HobsonEventDTO {
    private List<HobsonDeviceDTO> devices;
    private List<HobsonVariableDTO> variables;

    private SnapshotEventDTO(Long timestamp) {
        super("snapshot", timestamp);
        devices = new ArrayList<>();
        variables = new ArrayList<>();
    }

    private SnapshotEventDTO(JSONObject json) {
        super(json);
    }

    public List<HobsonDeviceDTO> getDevices() {
        return devices;
    }

    public List<HobsonVariableDTO> getVariables() {
        return variables;
    }

    @Override
    protected JSONObject createProperties() {
        JSONObject json = new JSONObject();

        JSONArray deviceArr = new JSONArray();
        for (HobsonDeviceDTO dto : devices) {
            deviceArr.put(dto.toJSON());
        }
        json.put("devices", deviceArr);

        JSONArray variableArr = new JSONArray();
        for (HobsonVariableDTO dto : variables) {
            variableArr.put(dto.toJSON());
        }
        json.put("variables", variableArr);

        return json;
    }

    @Override
    protected void readProperties(JSONObject json) {
        if (json.has("devices")) {
            devices = new ArrayList<>();
            JSONArray jdevices = json.getJSONArray("devices");
            for (int i = 0; i < jdevices.length(); i++) {
                JSONObject jd = jdevices.getJSONObject(i);
                devices.add(new HobsonDeviceDTO.Builder(jd).build());
            }
        }
        if (json.has("variables")) {
            variables = new ArrayList<>();
            JSONArray jvars = json.getJSONArray("variables");
            for (int i=0; i < jvars.length(); i++) {
                JSONObject jv = jvars.getJSONObject(i);
                variables.add(new HobsonVariableDTO.Builder(jv).build());
            }
        }
    }

    @Override
    public HobsonEvent createEvent() {
        return null;
    }

    public static class Builder {
        SnapshotEventDTO dto;

        public Builder(long timestamp) {
            dto = new SnapshotEventDTO(timestamp);
        }

        public Builder(JSONObject json) {
            dto = new SnapshotEventDTO(json);
        }

        public Builder device(HobsonDeviceDTO d) {
            dto.devices.add(d);
            return this;
        }

        public Builder variable(HobsonVariableDTO d) {
            dto.variables.add(d);
            return this;
        }

        public SnapshotEventDTO build() {
            return dto;
        }
    }
}
