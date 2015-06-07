/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.device.DeviceType;
import org.json.JSONObject;

public class HobsonDeviceDTO extends ThingDTO {
    private DeviceType type;
    private HobsonVariableDTO preferredVariable;
    private ItemListDTO variables;
    private PropertyContainerClassDTO configurationClass;
    private PropertyContainerDTO configuration;

    public HobsonDeviceDTO(String id) {
        this(id, null);
    }

    public HobsonDeviceDTO(String id, String name) {
        setId(id);
        setName(name);
    }

    public HobsonDeviceDTO setType(DeviceType type) {
        this.type = type;
        return this;
    }

    public HobsonDeviceDTO setPreferredVariable(HobsonVariableDTO preferredVariable) {
        this.preferredVariable = preferredVariable;
        return this;
    }

    public void setVariables(ItemListDTO variables) {
        this.variables = variables;
    }

    public HobsonDeviceDTO setConfigurationClass(PropertyContainerClassDTO configurationClass) {
        this.configurationClass = configurationClass;
        return this;
    }

    public HobsonDeviceDTO setConfiguration(PropertyContainerDTO configuration) {
        this.configuration = configuration;
        return this;
    }

    @Override
    public String getMediaType() {
        return "application/vnd.hobson.device";
    }

    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = super.toJSON(links);
        if (type != null) {
            json.put("type", type.toString());
        }
        if (configuration != null) {
            json.put("configuration", configuration.toJSON(links));
        }
        if (configurationClass != null) {
            json.put("configurationClass", configurationClass.toJSON(links));
        }
        if (preferredVariable != null) {
            json.put("preferredVariable", preferredVariable.toJSON(links));
        }
        if (variables != null) {
            json.put("variables", variables.toJSON(links));
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

        public HobsonDeviceDTO build() {
            return dto;
        }
    }
}
