/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.property;

import com.whizzosoftware.hobson.api.HobsonRuntimeException;
import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassProvider;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassType;
import com.whizzosoftware.hobson.dto.ExpansionFields;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.dto.device.HobsonDeviceDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import com.whizzosoftware.hobson.json.JSONProducer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.*;

public class PropertyContainerDTO extends ThingDTO {
    private PropertyContainerClassDTO containerClass;
    private Map<String,Object> values;

    private PropertyContainerDTO(String id) {
        super(id);
    }

    private PropertyContainerDTO(JSONObject json) {
        if (json.has(JSONAttributes.CCLASS)) {
            containerClass = new PropertyContainerClassDTO.Builder(json.getJSONObject(JSONAttributes.CCLASS)).build();
        }
        if (json.has(JSONAttributes.VALUES)) {
            values = new HashMap<>();
            JSONObject jp = json.getJSONObject(JSONAttributes.VALUES);
            for (Object o : jp.keySet()) {
                String key = o.toString();
                Object v = jp.get(key);
                if (JSONObject.NULL.equals(v)) {
                    values.put(key, null);
                } else if (v instanceof Serializable || v instanceof JSONArray || v instanceof JSONObject) {
                    values.put(key, v);
                } else {
                    throw new HobsonRuntimeException("Invalid property value for " + key + ": " + v);
                }
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
                p.put(k, createJSONObject(values.get(k)));
            }
            json.put(JSONAttributes.VALUES, p);
        }
        return json;
    }

    private Object createJSONObject(Object v) {
        if (v instanceof JSONProducer) {
            return ((JSONProducer)v).toJSON();
        } else if (v instanceof Collection) {
            JSONArray a = new JSONArray();
            for (Object o : ((Collection)v)) {
                a.put(createJSONObject(o));
            }
            return a;
        } else {
            return v;
        }
    }

    public static class Builder {
        PropertyContainerDTO dto;

        public Builder() {
            dto = new PropertyContainerDTO((String)null);
        }

        public Builder(String id) {
            dto = new PropertyContainerDTO(id);
        }

        public Builder(PropertyContainer pc, PropertyContainerClassProvider pccp, PropertyContainerClassType type, boolean showDetails, ExpansionFields expansions, IdProvider idProvider) {
            dto = new PropertyContainerDTO(idProvider.createPropertyContainerId(pc != null ? pc.getId() : null, pccp.getPropertyContainerClass(pc != null ? pc.getContainerClassContext() : null)));
            if (showDetails && pc != null) {
                dto.setName(pc.getName());
                if (pc.getContainerClassContext() != null) {
                    dto.containerClass = new PropertyContainerClassDTO.Builder(idProvider.createPropertyContainerClassId(pc.getContainerClassContext(), type), pccp.getPropertyContainerClass(pc.getContainerClassContext()), expansions != null && expansions.has(JSONAttributes.CCLASS)).build();
                }
                if (pc.hasPropertyValues()) {
                    dto.values = new HashMap<>();
                    for (String key : pc.getPropertyValues().keySet()) {
                        dto.values.put(key, mapDTOValueObject(pc.getPropertyValue(key), idProvider));
                    }
                }
            }
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

        Object mapDTOValueObject(Object value, IdProvider idProvider) {
            if (value instanceof DeviceContext) {
                DeviceContext dctx = (DeviceContext) value;
                return new HobsonDeviceDTO.Builder(idProvider.createDeviceId(dctx)).build();
            } else if (value instanceof List) {
                List<Object> mappedList = new ArrayList<>();
                for (Object o : ((List)value)) {
                    mappedList.add(mapDTOValueObject(o, idProvider));
                }
                return mappedList;
            } else {
                return value;
            }
        }

        public PropertyContainerDTO build() {
            return dto;
        }
    }
}
