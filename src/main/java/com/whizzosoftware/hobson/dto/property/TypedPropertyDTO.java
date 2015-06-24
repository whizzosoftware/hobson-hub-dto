/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.property;

import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import org.json.JSONObject;

public class TypedPropertyDTO extends ThingDTO {
    public String id;
    public String name;
    public String description;
    public TypedProperty.Type type;

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
            json.put("id", id);
        }
        if (name != null) {
            json.put("name", name);
        }
        if (description != null) {
            json.put("description", description);
        }
        if (type != null) {
            json.put("type", type.toString());
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

        public TypedPropertyDTO build() {
            return dto;
        }
    }
}
