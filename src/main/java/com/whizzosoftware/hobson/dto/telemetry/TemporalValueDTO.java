/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.telemetry;

import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class TemporalValueDTO extends ThingDTO {
    private Long time;
    private Object value;

    public TemporalValueDTO(Long time, Object value) {
        super();
        this.time = time;
        this.value = value;
    }

    @Override
    public String getMediaType() {
        return MediaTypes.TEMPORAL_VALUE;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (time != null) {
            json.put(JSONAttributes.TIME, time);
        }
        if (value != null) {
            json.put(JSONAttributes.VALUE, value);
        }
        return json;
    }
}
