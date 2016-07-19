/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.data;

import com.whizzosoftware.hobson.api.data.DataStreamField;
import com.whizzosoftware.hobson.api.data.DataStreamInterval;
import com.whizzosoftware.hobson.api.data.DataStreamValueSet;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Map;

public class DataStreamDataDTO extends ThingDTO {
    private long endTime;
    private DataStreamInterval interval;
    private Collection<DataStreamField> fields;
    private Collection<DataStreamValueSet> data;

    public DataStreamDataDTO(String id, long endTime, DataStreamInterval interval) {
        super(id);
        this.endTime = endTime;
        this.interval = interval;
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

        // build field dictionary
        JSONObject f = new JSONObject();
        for (DataStreamField dsf : fields) {
            f.put(dsf.getId(), dsf.getName());
        }
        json.put(JSONAttributes.FIELDS, f);

        // build data set
        JSONArray a = new JSONArray();
        for (DataStreamValueSet tvs : data) {
            JSONObject jtvs = new JSONObject();
            jtvs.put(JSONAttributes.TIMESTAMP, tvs.getTime());
            Map<String,Object> values = tvs.getValues();
            for (String key : values.keySet()) {
                jtvs.put(key, values.get(key));
            }
            a.put(jtvs);
        }
        json.put(JSONAttributes.DATA, a);
        return json;
    }

    static public class Builder {
        DataStreamDataDTO dto;

        public Builder(DTOBuildContext ctx, String dataStreamId, long endTime, DataStreamInterval inr) {
            dto = new DataStreamDataDTO(ctx.getIdProvider().createDataStreamDataId(dataStreamId), endTime, inr);
        }

        public DataStreamDataDTO.Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public DataStreamDataDTO.Builder fields(Collection<DataStreamField> fields) {
            dto.fields = fields;
            return this;
        }

        public DataStreamDataDTO.Builder data(Collection<DataStreamValueSet> data) {
            dto.data = data;
            return this;
        }

        public DataStreamDataDTO build() {
            return dto;
        }
    }
}
