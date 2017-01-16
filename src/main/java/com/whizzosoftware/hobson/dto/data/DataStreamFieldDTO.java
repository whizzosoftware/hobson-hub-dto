/*
 *******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.data;

import com.whizzosoftware.hobson.api.data.DataStreamField;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.context.TemplatedIdBuildContext;
import com.whizzosoftware.hobson.dto.variable.HobsonVariableDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class DataStreamFieldDTO extends ThingDTO {
    private HobsonVariableDTO variable;

    public DataStreamFieldDTO(TemplatedIdBuildContext ctx, TemplatedId id) {
        super(ctx, id);
    }

    public DataStreamFieldDTO(JSONObject json) {
        super(json);
        variable = new HobsonVariableDTO.Builder(json.getJSONObject(JSONAttributes.VARIABLE)).build();
    }

    public HobsonVariableDTO getVariable() {
        return variable;
    }

    @Override
    public String getMediaType() {
        return MediaTypes.DATA_STREAM_FIELD;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.VARIABLE, variable.toJSON());
        return json;
    }

    static public class Builder {
        DataStreamFieldDTO dto;

        public Builder(DTOBuildContext ctx, HubContext hctx, String dataStreamId, DataStreamField df, boolean showDetails) {
            dto = new DataStreamFieldDTO(ctx, ctx.getIdProvider().createDataStreamFieldId(hctx, dataStreamId, df.getId()));
            if (showDetails) {
                dto.setName(df.getName());
                dto.variable = new HobsonVariableDTO.Builder(ctx, ctx.getIdProvider().createDeviceVariableId(df.getVariable())).build();
            }
        }

        public Builder(JSONObject json) {
            dto = new DataStreamFieldDTO(json);
        }

        public DataStreamFieldDTO.Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public DataStreamFieldDTO.Builder variable() {
            return this;
        }

        public DataStreamFieldDTO build() {
            return dto;
        }
    }
}
