/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.telemetry;

import com.whizzosoftware.hobson.api.telemetry.TelemetryInterval;
import com.whizzosoftware.hobson.api.telemetry.TemporalValueSet;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Map;

public class DataStreamDataDTO extends ThingDTO {
    private long endTime;
    private TelemetryInterval interval;
    private JSONArray data;

    public DataStreamDataDTO(String id, long endTime, TelemetryInterval interval, Collection<TemporalValueSet> data) {
        super(id);

        this.endTime = endTime;
        this.interval = interval;
        this.data = new JSONArray();

        for (TemporalValueSet tvs : data) {
            JSONObject jtvs = new JSONObject();
            jtvs.put(JSONAttributes.TIMESTAMP, tvs.getTime());
            Map<String,Object> values = tvs.getValues();
            for (String key : values.keySet()) {
                jtvs.put(key, values.get(key));
            }
            this.data.put(jtvs);
        }
    }

    @Override
    public String getMediaType() {
        return MediaTypes.DATA_STREAM_DATA;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.END_TIME, endTime);
        json.put(JSONAttributes.INTERVAL, interval);
        json.put(JSONAttributes.DATA, data);
        return json;
    }
}
