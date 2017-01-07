/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.variable;

import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.api.variable.DeviceVariableDescriptor;
import com.whizzosoftware.hobson.api.variable.DeviceVariableState;
import com.whizzosoftware.hobson.api.variable.VariableMask;
import com.whizzosoftware.hobson.api.variable.VariableMediaType;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.context.TemplatedIdBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class HobsonVariableDTO extends ThingDTO {
    private Long lastUpdate;
    private VariableMask mask;
    private Object value;
    private VariableMediaType valueMediaType;

    private HobsonVariableDTO(TemplatedIdBuildContext ctx, TemplatedId id) {
        super(ctx, id);
    }

    private HobsonVariableDTO(JSONObject json) {
        super(json.getString(JSONAttributes.AID));

        if (json.has(JSONAttributes.NAME)) {
            setName(json.getString(JSONAttributes.NAME));
        }
        if (json.has(JSONAttributes.LAST_UPDATE)) {
            lastUpdate = json.getLong(JSONAttributes.LAST_UPDATE);
        }
        if (json.has(JSONAttributes.MASK)) {
            mask = VariableMask.valueOf(json.getString(JSONAttributes.MASK));
        }
        if (json.has(JSONAttributes.VALUE)) {
            value = json.get(JSONAttributes.VALUE);
        }
        if (json.has(JSONAttributes.MEDIA_TYPE)) {
            valueMediaType = VariableMediaType.valueOf(json.getString(JSONAttributes.MEDIA_TYPE));
        }
    }

    public String getMediaType() {
        return MediaTypes.VARIABLE;
    }

    public VariableMask getMask() {
        return mask;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public Object getValue() {
        return value;
    }

    public VariableMediaType getValueMediaType() {
        return valueMediaType;
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (lastUpdate != null) {
            json.put(JSONAttributes.LAST_UPDATE, lastUpdate);
        }
        if (mask != null) {
            json.put(JSONAttributes.MASK, mask.toString());
        }
        if (value != null) {
            json.put(JSONAttributes.VALUE, value);
        }
        if (valueMediaType != null) {
            json.put(JSONAttributes.MEDIA_TYPE, valueMediaType.toString());
        }
        return json;
    }

    static public class Builder {
        HobsonVariableDTO dto;

        public Builder(TemplatedIdBuildContext ctx, TemplatedId id) {
            dto = new HobsonVariableDTO(ctx, id);
        }

        public Builder(TemplatedIdBuildContext ctx, TemplatedId id, DeviceVariableDescriptor dv, DeviceVariableState state, boolean showDetails) {
            dto = new HobsonVariableDTO(ctx, id);
            if (showDetails) {
                if (dv != null) {
                    dto.setName(dv.getContext().getName());
                    dto.mask = dv.getMask();
                    dto.valueMediaType = dv.getMediaType();
                    dto.value = state != null ? state.getValue() : null;
                    dto.lastUpdate = state != null ? state.getLastUpdate() : null;
                }
            }
        }

        public Builder(DTOBuildContext ctx, TemplatedId id, DeviceVariableDescriptor dv, DeviceVariableState state, boolean showDetails) {
            dto = new HobsonVariableDTO(ctx, id);
            if (showDetails) {
                if (dv != null) {
                    dto.setName(dv.getContext().getName());
                    dto.mask = dv.getMask();
                    dto.valueMediaType = dv.getMediaType();
                    dto.value = state != null ? state.getValue() : null;
                    dto.lastUpdate = state != null ? state.getLastUpdate() : null;
                }
            }
        }

        public Builder(JSONObject json) {
            dto = new HobsonVariableDTO(json);
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder lastUpdate(Long lastUpdate) {
            dto.lastUpdate = lastUpdate;
            return this;
        }

        public Builder mask(VariableMask mask) {
            dto.mask = mask;
            return this;
        }

        public Builder value(Object value) {
            dto.value = value;
            return this;
        }

        public Builder valueMediaType(VariableMediaType mediaType) {
            dto.valueMediaType = mediaType;
            return this;
        }

        public HobsonVariableDTO build() {
            return dto;
        }
    }
}
