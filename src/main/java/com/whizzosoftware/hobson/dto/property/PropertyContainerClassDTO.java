/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.property;

import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PropertyContainerClassDTO extends ThingDTO {
    private List<TypedPropertyDTO> supportedProperties;

    private PropertyContainerClassDTO(String id) {
        super(id);
    }

    private PropertyContainerClassDTO(JSONObject json) {
        super(json);
    }

    @Override
    public String getMediaType() {
        return MediaTypes.PROPERTY_CONTAINER_CLASS;
    }

    public List<TypedPropertyDTO> getSupportedProperties() {
        return supportedProperties;
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (supportedProperties != null) {
            JSONArray array = new JSONArray();
            for (TypedPropertyDTO p : supportedProperties) {
                array.put(p.toJSON());
            }
            json.put(JSONAttributes.SUPPORTED_PROPERTIES, array);
        }
        return json;
    }

    public static class Builder {
        PropertyContainerClassDTO dto;

        public Builder(String id) {
            dto = new PropertyContainerClassDTO(id);
        }

        public Builder(JSONObject json) {
            dto = new PropertyContainerClassDTO(json);
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder supportedProperties(List<TypedPropertyDTO> supportedProperties) {
            dto.supportedProperties = supportedProperties;
            return this;
        }

        public Builder supportedProperty(TypedPropertyDTO supportedProperty) {
            if (dto.supportedProperties == null) {
                dto.supportedProperties = new ArrayList<>();
            }
            dto.supportedProperties.add(supportedProperty);
            return this;
        }

        public PropertyContainerClassDTO build() {
            return dto;
        }
    }
}
