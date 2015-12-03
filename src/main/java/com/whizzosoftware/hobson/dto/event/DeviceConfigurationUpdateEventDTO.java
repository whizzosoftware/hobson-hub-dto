/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.event;

import com.whizzosoftware.hobson.api.event.DeviceConfigurationUpdateEvent;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A DTO for device configuration update events.
 *
 * @author Dan Noguerol
 */
public class DeviceConfigurationUpdateEventDTO extends HobsonEventDTO {
    private String pluginId;
    private String deviceId;
    private String containerClassId;
    private Map<String,Object> configuration;

    public DeviceConfigurationUpdateEventDTO(DeviceConfigurationUpdateEvent event) {
        super(event.getEventId(), event.getTimestamp());
        this.pluginId = event.getPluginId();
        this.deviceId = event.getDeviceId();
        this.containerClassId = event.getConfigurationClassId();
        this.configuration = event.getConfiguration().getPropertyValues();
    }

    public DeviceConfigurationUpdateEventDTO(JSONObject json) {
        super(json);
    }

    public String getPluginId() {
        return pluginId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getContainerClassId() {
        return containerClassId;
    }

    public Map<String, Object> getConfiguration() {
        return configuration;
    }

    @Override
    protected JSONObject createProperties() {
        JSONObject json = new JSONObject();
        json.put(JSONAttributes.PLUGIN_ID, pluginId);
        json.put(JSONAttributes.DEVICE_ID, deviceId);
        json.put(JSONAttributes.CONTAINER_CLASS_ID, containerClassId);
        json.put(JSONAttributes.CONFIGURATION, configuration);
        return json;
    }

    @Override
    protected void readProperties(JSONObject json) {
        this.pluginId = json.getString(JSONAttributes.PLUGIN_ID);
        this.deviceId = json.getString(JSONAttributes.DEVICE_ID);
        this.containerClassId = json.getString(JSONAttributes.CONTAINER_CLASS_ID);
        JSONObject c = json.getJSONObject(JSONAttributes.CONFIGURATION);
        this.configuration = new HashMap<>();
        for (Object o : c.keySet()) {
            String key = (String)o;
            configuration.put(key, c.get(key));
        }
    }
}
