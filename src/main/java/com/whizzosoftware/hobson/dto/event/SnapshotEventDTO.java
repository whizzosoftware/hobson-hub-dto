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
    private List<HobsonDeviceDTO> devices = new ArrayList<>();
    private List<HobsonVariableDTO> variables = new ArrayList<>();

    public SnapshotEventDTO(Long timestamp) {
        super("snapshot", timestamp);
    }

    public void addDevice(HobsonDeviceDTO device) {
        devices.add(device);
    }

    public void addVariable(HobsonVariableDTO variable) {
        variables.add(variable);
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

    }

    @Override
    public HobsonEvent createEvent() {
        return null;
    }
}