/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.telemetry;

import com.whizzosoftware.hobson.api.telemetry.DataStream;
import com.whizzosoftware.hobson.api.variable.VariableContext;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.variable.HobsonVariableDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class DataStreamDTO extends ThingDTO {
    private Collection<HobsonVariableDTO> variables;

    private DataStreamDTO(String id) {
        super(id);
    }

    private DataStreamDTO(JSONObject json) {
        super(json);

        if (json.has(JSONAttributes.VARIABLES)) {
            JSONArray jca = json.getJSONArray(JSONAttributes.VARIABLES);
            this.variables = new ArrayList<>();
            for (int i=0; i < jca.length(); i++) {
                variables.add(new HobsonVariableDTO.Builder(jca.getJSONObject(i)).build());
            }
        }
    }

    @Override
    public String getMediaType() {
        return MediaTypes.DATA_STREAM;
    }

    public boolean hasVariables() {
        return (variables != null && variables.size() > 0);
    }

    public Collection<HobsonVariableDTO> getVariables() {
        return variables;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (variables != null) {
            JSONArray vars = new JSONArray();
            for (HobsonVariableDTO v : variables) {
                vars.put(v.toJSON());
            }
            json.put(JSONAttributes.VARIABLES, vars);
        }
        return json;
    }

    static public class Builder {
        DataStreamDTO dto;

        public Builder(DTOBuildContext ctx, DataStream ds, boolean showDetails) {
            dto = new DataStreamDTO(ctx.getIdProvider().createDataStreamId(ds.getUserId(), ds.getId()));
            if (showDetails) {
                dto.setName(ds.getName());
                if (ds.hasVariables()) {
                    dto.variables = new ArrayList<>();
                    for (VariableContext v : ds.getVariables()) {
                        dto.variables.add(new HobsonVariableDTO.Builder(ctx.getIdProvider().createVariableId(v)).build());
                    }
                }
            }
        }

        public Builder(JSONObject json) {
            dto = new DataStreamDTO(json);
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public DataStreamDTO build() {
            return dto;
        }
    }
}
