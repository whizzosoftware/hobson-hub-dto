/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONObject;

public class DeviceTelemetryDTO extends ThingDTO {
    private Boolean capable;
    private Boolean enabled;
    private ItemListDTO datasets;

    private DeviceTelemetryDTO(String id) {
        setId(id);
    }

    @Override
    public String getMediaType() {
        return "application/vnd.hobson.deviceTelemetry";
    }

    public Boolean getCapable() {
        return capable;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public ItemListDTO getDatasets() {
        return datasets;
    }

    @Override
    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = super.toJSON(links);
        json.put("capable", capable);
        json.put("enabled", enabled);
        if (datasets != null) {
            json.put("datasets", datasets.toJSON(links));
        }
        return json;
    }

    static public class Builder {
        private DeviceTelemetryDTO dto;

        public Builder(String id) {
            dto = new DeviceTelemetryDTO(id);
        }

        public Builder capable(Boolean capable) {
            dto.capable = capable;
            return this;
        }

        public Builder enabled(Boolean enabled) {
            dto.enabled = enabled;
            return this;
        }

        public Builder datasets(ItemListDTO data) {
            dto.datasets = data;
            return this;
        }

        public DeviceTelemetryDTO build() {
            return dto;
        }
    }
}
