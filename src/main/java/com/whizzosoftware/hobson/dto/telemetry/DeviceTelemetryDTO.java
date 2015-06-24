/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.telemetry;

import com.whizzosoftware.hobson.dto.ItemListDTO;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class DeviceTelemetryDTO extends ThingDTO {
    private Boolean capable;
    private Boolean enabled;
    private ItemListDTO datasets;

    private DeviceTelemetryDTO(String id) {
        super(id);
    }

    @Override
    public String getMediaType() {
        return MediaTypes.TELEMETRY;
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
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.CAPABLE, capable);
        json.put(JSONAttributes.ENABLED, enabled);
        if (datasets != null) {
            json.put(JSONAttributes.DATASETS, datasets.toJSON());
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
