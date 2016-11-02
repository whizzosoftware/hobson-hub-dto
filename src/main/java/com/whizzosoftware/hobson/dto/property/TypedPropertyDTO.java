/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.property;

import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.property.TypedPropertyConstraint;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Map;

public class TypedPropertyDTO extends ThingDTO {
    public String id;
    public TypedProperty.Type type;
    public Map<String,String> enumeration;
    public Collection<TypedPropertyConstraint> constraints;

    private TypedPropertyDTO(String id) {
        super(id);
    }

    @Override
    public String getMediaType() {
        return MediaTypes.PROPERTY;
    }

    public Map<String,String> getEnumeration() {
        return enumeration;
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (id != null) {
            json.put(JSONAttributes.ID, id);
        }
        if (type != null) {
            json.put(JSONAttributes.TYPE, type.toString());
        }
        if (enumeration != null) {
            json.put(JSONAttributes.ENUM, enumeration);
        }
        if (constraints != null) {
            JSONObject o = new JSONObject();
            for (TypedPropertyConstraint tpc : constraints) {
                o.put(tpc.getType().toString(), tpc.getArgument());
            }
            json.put(JSONAttributes.CONSTRAINTS, o);
        }
        return json;
    }

    public static class Builder {
        private TypedPropertyDTO dto;

        public Builder(String id) {
            dto = new TypedPropertyDTO(id);
        }

        public Builder(TypedProperty tp) {
            dto = new TypedPropertyDTO(tp.getId());
            dto.setName(tp.getName());
            dto.setDescription(tp.getDescription());
            dto.type = tp.getType();
            dto.enumeration = tp.getEnumeration();
            dto.constraints = tp.getConstraints();
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

        public Builder enumeration(Map<String,String> enumeration) {
            dto.enumeration = enumeration;
            return this;
        }

        public Builder constraints(Collection<TypedPropertyConstraint> constraints) {
            dto.constraints = constraints;
            return this;
        }

        public TypedPropertyDTO build() {
            return dto;
        }
    }
}
