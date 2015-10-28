/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.device;

import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.device.HobsonDevice;
import com.whizzosoftware.hobson.dto.*;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import com.whizzosoftware.hobson.dto.telemetry.DeviceTelemetryDTO;
import com.whizzosoftware.hobson.dto.variable.HobsonVariableDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class HobsonDeviceDTO extends ThingDTO {
    private DeviceType type;
    private Long checkInTime;
    private Boolean available;
    private HobsonVariableDTO preferredVariable;
    private ItemListDTO variables;
    private PropertyContainerClassDTO configurationClass;
    private PropertyContainerDTO configuration;
    private DeviceTelemetryDTO telemetry;
    private Long lastVariableUpdate;

    private HobsonDeviceDTO(String id) {
        super(id);
    }

    private HobsonDeviceDTO(String id, HobsonDevice device) {
        super(id);
        setName(device.getName());
        this.type = device.getType();
        this.checkInTime = device.getLastCheckIn();
        this.available = device.isAvailable();
    }

    @Override
    public String getMediaType() {
        return MediaTypes.DEVICE;
    }

    public DeviceType getType() {
        return type;
    }

    public boolean isAvailable() {
        return (available != null && available);
    }

    public Long getCheckInTime() {
        return checkInTime;
    }

    public Long getLastVariableUpdate() {
        return lastVariableUpdate;
    }

    public HobsonVariableDTO getPreferredVariable() {
        return preferredVariable;
    }

    public ItemListDTO getVariables() {
        return variables;
    }

    public PropertyContainerClassDTO getConfigurationClass() {
        return configurationClass;
    }

    public PropertyContainerDTO getConfiguration() {
        return configuration;
    }

    public DeviceTelemetryDTO getTelemetry() {
        return telemetry;
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (available != null) {
            json.put(JSONAttributes.AVAILABLE, available);
        }
        if (checkInTime != null) {
            json.put(JSONAttributes.LAST_CHECK_IN, checkInTime);
        }
        if (lastVariableUpdate != null) {
            json.put(JSONAttributes.LAST_UPDATE, lastVariableUpdate);
        }
        if (type != null) {
            json.put(JSONAttributes.TYPE, type.toString());
        }
        if (configuration != null) {
            json.put(JSONAttributes.CONFIGURATION, configuration.toJSON());
        }
        if (configurationClass != null) {
            json.put(JSONAttributes.CONFIGURATION_CLASS, configurationClass.toJSON());
        }
        if (preferredVariable != null) {
            json.put(JSONAttributes.PREFERRED_VARIABLE, preferredVariable.toJSON());
        }
        if (variables != null) {
            json.put(JSONAttributes.VARIABLES, variables.toJSON());
        }
        if (telemetry != null) {
            json.put(JSONAttributes.TELEMETRY, telemetry.toJSON());
        }
        return json;
    }

    static public class Builder {
        private HobsonDeviceDTO dto;

        public Builder(String id) {
            dto = new HobsonDeviceDTO(id);
        }

        public Builder(String id, HobsonDevice device) {
            dto = new HobsonDeviceDTO(id, device);
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder type(DeviceType type) {
            dto.type = type;
            return this;
        }

        public Builder available(boolean available) {
            dto.available = available;
            return this;
        }

        public Builder checkInTime(Long checkInTime) {
            dto.checkInTime = checkInTime;
            return this;
        }

        public Builder lastUpdate(Long lastUpdate) {
            dto.lastVariableUpdate = lastUpdate;
            return this;
        }

        public Builder preferredVariable(HobsonVariableDTO preferredVariable) {
            dto.preferredVariable = preferredVariable;
            return this;
        }

        public Builder variables(ItemListDTO variables) {
            dto.variables = variables;
            return this;
        }

        public Builder configurationClass(PropertyContainerClassDTO configurationClass) {
            dto.configurationClass = configurationClass;
            return this;
        }

        public Builder configuration(PropertyContainerDTO configuration) {
            dto.configuration = configuration;
            return this;
        }

        public Builder telemetry(DeviceTelemetryDTO telemetry) {
            dto.telemetry = telemetry;
            return this;
        }

        public HobsonDeviceDTO build() {
            return dto;
        }
    }
}
