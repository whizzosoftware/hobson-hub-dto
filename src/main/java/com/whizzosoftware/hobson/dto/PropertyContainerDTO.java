/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.property.PropertyContainer;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PropertyContainerDTO extends ThingDTO {
    private PropertyContainerClassDTO containerClass;
    private Map<String,Object> propertyValues;

    public PropertyContainerDTO(String containerId, PropertyContainer r) {
        setId(containerId);
        setName(r.getName());
        this.containerClass = new PropertyContainerClassDTO("containerclass1");
        this.propertyValues = r.getPropertyValues();
    }

    public PropertyContainerDTO(String containerId) {
        setId(containerId);
    }

    public PropertyContainerDTO(JSONObject json) {
        containerClass = new PropertyContainerClassDTO(json.getJSONObject("cclass"));
        if (json.has("values")) {
            propertyValues = new HashMap<>();
            JSONObject jp = json.getJSONObject("values");
            for (Object o : jp.keySet()) {
                String key = o.toString();
                propertyValues.put(key, jp.get(key));
            }
        }
    }

    public PropertyContainerDTO(String containerId, String containerClassId, Map<String,Object> propertyValues) {
        setId(containerId);
        this.containerClass = new PropertyContainerClassDTO(containerClassId);
        this.propertyValues = propertyValues;
    }

    @Override
    public String getMediaType() {
        return "application/vnd.hobson.propertyContainer";
    }

    public PropertyContainerClassDTO getContainerClass() {
        return containerClass;
    }

    public void setContainerClass(PropertyContainerClassDTO containerClass) {
        this.containerClass = containerClass;
    }

    public boolean hasPropertyValues() {
        return (propertyValues != null && propertyValues.size() > 0);
    }

    public Map<String, Object> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(Map<String, Object> propertyValues) {
        this.propertyValues = propertyValues;
    }

    @Override
    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = super.toJSON(links);
        if (containerClass != null) {
            json.put("cclass", containerClass.toJSON(links));
        }
        if (propertyValues != null) {
            JSONObject p = new JSONObject();
            for (String k : propertyValues.keySet()) {
                p.put(k, propertyValues.get(k));
            }
            json.put("values", p);
        }
        return json;
    }
}
