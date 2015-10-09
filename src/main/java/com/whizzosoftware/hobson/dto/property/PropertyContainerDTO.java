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
import com.whizzosoftware.hobson.json.JSONProducer;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PropertyContainerDTO extends ThingDTO {
    private PropertyContainerClassDTO containerClass;
    private Map<String,Object> values;

    private PropertyContainerDTO(String id) {
        super(id);
    }

    public PropertyContainerDTO(JSONObject json) {
        if (json.has(JSONAttributes.CCLASS)) {
            containerClass = new PropertyContainerClassDTO.Builder(json.getJSONObject(JSONAttributes.CCLASS)).build();
        }
        if (json.has(JSONAttributes.VALUES)) {
            values = new HashMap<>();
            JSONObject jp = json.getJSONObject(JSONAttributes.VALUES);
            for (Object o : jp.keySet()) {
                String key = o.toString();
                values.put(key, jp.get(key));
            }
        }
    }

    @Override
    public String getMediaType() {
        return MediaTypes.PROPERTY_CONTAINER;
    }

    public PropertyContainerClassDTO getContainerClass() {
        return containerClass;
    }

    public boolean hasPropertyValues() {
        return (values != null && values.size() > 0);
    }

    public Map<String, Object> getValues() {
        return values;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (containerClass != null) {
            json.put(JSONAttributes.CCLASS, containerClass.toJSON());
        }
        if (values != null) {
            JSONObject p = new JSONObject();
            for (String k : values.keySet()) {
                Object v = values.get(k);
                if (v instanceof JSONProducer) {
                    p.put(k, ((JSONProducer)v).toJSON());
                } else {
                    p.put(k, v);
                }
            }
            json.put(JSONAttributes.VALUES, p);
        }
        return json;
    }

    public static class Builder {
        PropertyContainerDTO dto;

        public Builder() {
            dto = new PropertyContainerDTO((String)null);
        }

        public Builder(String id) {
            dto = new PropertyContainerDTO(id);
        }

        public Builder(JSONObject json) {
            dto = new PropertyContainerDTO(json);
        }

        public Builder containerClass(PropertyContainerClassDTO containerClass) {
            dto.containerClass = containerClass;
            return this;
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder values(Map<String,Object> values) {
            dto.values = values;
            return this;
        }

        public PropertyContainerDTO build() {
            return dto;
        }
    }
}
