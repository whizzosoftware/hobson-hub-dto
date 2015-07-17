/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.property;

import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.property.TypedPropertyConstraint;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

import java.util.Map;

public class TypedPropertyDTO extends ThingDTO {
    public String id;
    public String name;
    public String description;
    public TypedProperty.Type type;
    public Map<TypedPropertyConstraint,String> constraints;

    private TypedPropertyDTO(String id) {
        super(id);
    }

    @Override
    public String getMediaType() {
        return MediaTypes.PROPERTY;
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (id != null) {
            json.put(JSONAttributes.ID, id);
        }
        if (name != null) {
            json.put(JSONAttributes.NAME, name);
        }
        if (description != null) {
            json.put(JSONAttributes.DESCRIPTION, description);
        }
        if (type != null) {
            json.put(JSONAttributes.TYPE, type.toString());
        }
        if (constraints != null) {
            json.put(JSONAttributes.CONSTRAINTS, constraints);
        }
        return json;
    }

    public static class Builder {
        private TypedPropertyDTO dto;

        public Builder(String id) {
            dto = new TypedPropertyDTO(id);
        }

        public Builder description(String description) {
            dto.setDescription(description);
            return this;
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder type(TypedProperty.Type type) {
            dto.type = type;
            return this;
        }

        public Builder constraints(Map<TypedPropertyConstraint,String> constraints) {
            dto.constraints = constraints;
            return this;
        }

        public TypedPropertyDTO build() {
            return dto;
        }
    }
}
