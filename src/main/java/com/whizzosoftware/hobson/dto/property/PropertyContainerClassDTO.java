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
    private String descriptionTemplate;
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

    public String getDescriptionTemplate() {
        return descriptionTemplate;
    }

    public List<TypedPropertyDTO> getSupportedProperties() {
        return supportedProperties;
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.DESCRIPTION_TEMPLATE, descriptionTemplate);
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
        private PropertyContainerClassDTO dto;

        public Builder(String id) {
            dto = new PropertyContainerClassDTO(id);
        }

        public Builder(JSONObject json) {
            dto = new PropertyContainerClassDTO(json);
        }

        protected PropertyContainerClassDTO getDto() {
            return dto;
        }

        public Builder name(String name) {
            getDto().setName(name);
            return this;
        }

        public Builder descriptionTemplate(String descriptionTemplate) {
            getDto().descriptionTemplate = descriptionTemplate;
            return this;
        }

        public Builder supportedProperties(List<TypedPropertyDTO> supportedProperties) {
            getDto().supportedProperties = supportedProperties;
            return this;
        }

        public Builder supportedProperty(TypedPropertyDTO supportedProperty) {
            if (getDto().supportedProperties == null) {
                getDto().supportedProperties = new ArrayList<>();
            }
            getDto().supportedProperties.add(supportedProperty);
            return this;
        }

        public PropertyContainerClassDTO build() {
            return getDto();
        }
    }
}
