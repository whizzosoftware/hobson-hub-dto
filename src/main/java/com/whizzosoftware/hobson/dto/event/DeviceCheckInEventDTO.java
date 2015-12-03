/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.event;

import com.whizzosoftware.hobson.api.event.DeviceCheckInEvent;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

/**
 * A DTO for device check-in events.
 *
 * @author Dan Noguerol
 */
public class DeviceCheckInEventDTO extends HobsonEventDTO {
    private String pluginId;
    private String deviceId;
    private Long checkInTime;

    public DeviceCheckInEventDTO(DeviceCheckInEvent event) {
        super(event.getEventId(), event.getTimestamp());
        this.pluginId = event.getPluginId();
        this.deviceId = event.getDeviceId();
        this.checkInTime = event.getCheckInTime();
    }

    public DeviceCheckInEventDTO(JSONObject json) {
        super(json);
    }

    public String getPluginId() {
        return pluginId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Long getCheckInTime() {
        return checkInTime;
    }

    @Override
    protected JSONObject createProperties() {
        JSONObject json = new JSONObject();
        json.put(JSONAttributes.PLUGIN_ID, pluginId);
        json.put(JSONAttributes.DEVICE_ID, deviceId);
        json.put(JSONAttributes.LAST_CHECK_IN, checkInTime);
        return json;
    }

    @Override
    protected void readProperties(JSONObject json) {
        this.pluginId = json.getString(JSONAttributes.PLUGIN_ID);
        this.deviceId = json.getString(JSONAttributes.DEVICE_ID);
        this.checkInTime = json.getLong(JSONAttributes.LAST_CHECK_IN);
    }
}
