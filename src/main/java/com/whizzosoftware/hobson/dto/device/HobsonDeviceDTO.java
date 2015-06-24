/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.device;

import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.dto.*;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import com.whizzosoftware.hobson.dto.telemetry.DeviceTelemetryDTO;
import com.whizzosoftware.hobson.dto.variable.HobsonVariableDTO;
import org.json.JSONObject;

public class HobsonDeviceDTO extends ThingDTO {
    private DeviceType type;
    private HobsonVariableDTO preferredVariable;
    private ItemListDTO variables;
    private PropertyContainerClassDTO configurationClass;
    private PropertyContainerDTO configuration;
    private DeviceTelemetryDTO telemetry;

    private HobsonDeviceDTO(String id) {
        super(id);
    }

    @Override
    public String getMediaType() {
        return MediaTypes.DEVICE;
    }

    public DeviceType getType() {
        return type;
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
        if (type != null) {
            json.put("type", type.toString());
        }
        if (configuration != null) {
            json.put("configuration", configuration.toJSON());
        }
        if (configurationClass != null) {
            json.put("configurationClass", configurationClass.toJSON());
        }
        if (preferredVariable != null) {
            json.put("preferredVariable", preferredVariable.toJSON());
        }
        if (variables != null) {
            json.put("variables", variables.toJSON());
        }
        if (telemetry != null) {
            json.put("telemetry", telemetry.toJSON());
        }
        return json;
    }

    static public class Builder {
        private HobsonDeviceDTO dto;

        public Builder(String id) {
            dto = new HobsonDeviceDTO(id);
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder type(DeviceType type) {
            dto.type = type;
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
