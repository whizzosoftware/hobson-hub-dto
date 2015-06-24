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

public class TelemetryDatasetDTO extends ThingDTO {
    private ItemListDTO data;

    private TelemetryDatasetDTO(String id) {
        super(id);
    }

    public ItemListDTO getData() {
        return data;
    }

    @Override
    public String getMediaType() {
        return MediaTypes.TELEMETRY_DATASET;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (data != null) {
            json.put(JSONAttributes.DATA, data.toJSON());
        }
        return json;
    }

    static public class Builder {
        private TelemetryDatasetDTO dto;

        public Builder(String id) {
            dto = new TelemetryDatasetDTO(id);
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder data(ItemListDTO data) {
            dto.data = data;
            return this;
        }

        public TelemetryDatasetDTO build() {
            return dto;
        }
    }
}
