/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.variable;

import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.dto.ThingDTO;
import org.json.JSONObject;

public class HobsonVariableDTO extends ThingDTO {
    private Long lastUpdate;
    private HobsonVariable.Mask mask;
    private Object value;

    private HobsonVariableDTO(String id) {
        super(id);
    }

    @Override
    public String getMediaType() {
        return "application/vnd.hobson.variable";
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (lastUpdate != null) {
            json.put("lastUpdate", lastUpdate);
        }
        if (mask != null) {
            json.put("mask", mask.toString());
        }
        if (value != null) {
            json.put("value", value);
        }
        return json;
    }

    static public class Builder {
        HobsonVariableDTO dto;

        public Builder(String id) {
            dto = new HobsonVariableDTO(id);
        }

        public Builder id(String id) {
            dto.setId(id);
            return this;
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
