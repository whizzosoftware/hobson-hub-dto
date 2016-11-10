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

import com.whizzosoftware.hobson.api.property.PropertyContainerClass;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.dto.EntityDTO;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PropertyContainerClassDTO extends EntityDTO {
    private List<TypedPropertyDTO> supportedProperties;

    protected PropertyContainerClassDTO(String id) {
        super(id);
    }

    protected PropertyContainerClassDTO(JSONObject json) {
        super(json);
    }

    @Override
    public String getMediaType() {
        return MediaTypes.PROPERTY_CONTAINER_CLASS;
    }

    @Override
    public String getJSONMediaType() {
        return null;
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
        protected PropertyContainerClassDTO dto;

        public Builder(String id) {
            dto = createDTO(id);
        }

        public Builder(String id, PropertyContainerClass pcc, boolean showDetails) {
            dto = createDTO(id);
            if (showDetails && pcc != null) {
                if (pcc.hasSupportedProperties()) {
                    dto.supportedProperties = new ArrayList<>();
                    for (TypedProperty tp : pcc.getSupportedProperties()) {
                        dto.supportedProperties.add(new TypedPropertyDTO.Builder(tp).build());
                    }
                }
            }
        }

        public Builder(JSONObject json) {
            dto = new PropertyContainerClassDTO(json);
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

        protected PropertyContainerClassDTO createDTO(String id) {
            return new PropertyContainerClassDTO(id);
        }

        public PropertyContainerClassDTO build() {
            return dto;
        }
    }
}
