/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.variable;

import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.dto.DTOBuildContext;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class HobsonVariableDTO extends ThingDTO {
    private Long lastUpdate;
    private HobsonVariable.Mask mask;
    private Object value;

    private HobsonVariableDTO(String id) {
        super(id);
    }

    private HobsonVariableDTO(JSONObject json) {
        super(json.getString(JSONAttributes.AID));

        setName(json.getString(JSONAttributes.NAME));
        lastUpdate = json.getLong(JSONAttributes.LAST_UPDATE);
        mask = HobsonVariable.Mask.valueOf(json.getString(JSONAttributes.MASK));
        if (json.has(JSONAttributes.VALUE)) {
            value = json.get(JSONAttributes.VALUE);
        }
    }

    @Override
    public String getMediaType() {
        return MediaTypes.VARIABLE;
    }

    public HobsonVariable.Mask getMask() {
        return mask;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public Object getValue() {
        return value;
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
        return json;
    }

    static public class Builder {
        HobsonVariableDTO dto;

        public Builder(String id) {
            dto = new HobsonVariableDTO(id);
        }

        public Builder(DTOBuildContext ctx, String id, HobsonVariable var, boolean showDetails) {
            dto = new HobsonVariableDTO(id);
            if (showDetails) {
                dto.setName(var.getName());
                dto.mask = var.getMask();
                dto.value = ctx.getProxyValue(var);
                dto.lastUpdate = var.getLastUpdate();
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

        public Builder mask(HobsonVariable.Mask mask) {
            dto.mask = mask;
            return this;
        }

        public Builder value(Object value) {
            dto.value = value;
            return this;
        }

        public HobsonVariableDTO build() {
            return dto;
        }
    }
}
