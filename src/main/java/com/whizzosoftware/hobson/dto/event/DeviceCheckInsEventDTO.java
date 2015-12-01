/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.event;

import com.whizzosoftware.hobson.api.event.HobsonEvent;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for a list of device check-in events.
 *
 * @author Dan Noguerol
 */
public class DeviceCheckInsEventDTO extends HobsonEventDTO {
    public static final String ID = "deviceCheckIns";

    private List<DeviceCheckInEventDTO> events;

    public DeviceCheckInsEventDTO(Long timestamp) {
        super(ID, timestamp);
    }

    public DeviceCheckInsEventDTO(JSONObject json) {
        super(json);
    }

    public void addCheckIn(DeviceCheckInEventDTO dto) {
        if (events == null) {
            events = new ArrayList<>();
        }
        events.add(dto);
    }

    public List<DeviceCheckInEventDTO> getEvents() {
        return events;
    }

    @Override
    protected JSONObject createProperties() {
        JSONObject json = new JSONObject();
        JSONArray ja = new JSONArray();
        if (events != null) {
            for (DeviceCheckInEventDTO dto : events) {
                ja.put(dto.toJSON());
            }
        }
        json.put(JSONAttributes.EVENTS, ja);
        return json;
    }

    @Override
    protected void readProperties(JSONObject json) {
        if (json.has(JSONAttributes.EVENTS)) {
            events = new ArrayList<>();
            JSONArray ja = json.getJSONArray(JSONAttributes.EVENTS);
            for (int i=0; i < ja.length(); i++) {
                events.add(new DeviceCheckInEventDTO(ja.getJSONObject(i)));
            }
        }
    }

    @Override
    public HobsonEvent createEvent() {
        return null;
    }
}
