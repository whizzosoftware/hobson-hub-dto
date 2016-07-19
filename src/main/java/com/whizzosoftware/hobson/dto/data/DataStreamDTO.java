/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.data;

import com.whizzosoftware.hobson.api.data.DataStream;
import com.whizzosoftware.hobson.api.data.DataStreamField;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class DataStreamDTO extends ThingDTO {
    private Collection<DataStreamFieldDTO> fields;
    private Set<String> tags;

    private DataStreamDTO(String id) {
        super(id);
    }

    private DataStreamDTO(JSONObject json) {
        super(json);

        if (json.has(JSONAttributes.FIELDS)) {
            JSONArray jca = json.getJSONArray(JSONAttributes.FIELDS);
            this.fields = new ArrayList<>();
            for (int i=0; i < jca.length(); i++) {
                fields.add(new DataStreamFieldDTO.Builder(jca.getJSONObject(i)).build());
            }
        }
    }

    @Override
    public String getMediaType() {
        return MediaTypes.DATA_STREAM;
    }

    public boolean hasFields() {
        return (fields != null && fields.size() > 0);
    }

    public Collection<DataStreamFieldDTO> getFields() {
        return fields;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (fields != null) {
            JSONArray vars = new JSONArray();
            for (DataStreamFieldDTO f : fields) {
                vars.put(f.toJSON());
            }
            json.put(JSONAttributes.FIELDS, vars);
        }
        if (tags != null) {
            json.put(JSONAttributes.TAGS, tags);
        }
        return json;
    }

    static public class Builder {
        DataStreamDTO dto;

        public Builder(DTOBuildContext ctx, DataStream ds, boolean showDetails) {
            dto = new DataStreamDTO(ctx.getIdProvider().createDataStreamId(ds.getId()));
            if (showDetails) {
                dto.setName(ds.getName());
                if (ds.hasFields()) {
                    dto.fields = new ArrayList<>();
                    for (DataStreamField f : ds.getFields()) {
                        dto.fields.add(new DataStreamFieldDTO.Builder(ctx, ds.getId(), f, true).build());
                    }
                }
                dto.tags = ds.getTags();
                dto.addLink(JSONAttributes.DATA, ctx.getIdProvider().createDataStreamDataId(ds.getId()));
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
