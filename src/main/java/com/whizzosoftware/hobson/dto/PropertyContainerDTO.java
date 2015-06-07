/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PropertyContainerDTO extends ThingDTO {
    private PropertyContainerClassDTO containerClass;
    private Map<String,Object> propertyValues;

    public PropertyContainerDTO(String containerId) {
        this(containerId, null, null, null);
    }

    public PropertyContainerDTO(String containerId, String containerClassId, Map<String,Object> propertyValues) {
        this(containerId, null, new PropertyContainerClassDTO(containerClassId), propertyValues);
    }

    public PropertyContainerDTO(String containerId, String name, PropertyContainerClassDTO containerClass, Map<String,Object> propertyValues) {
        super(containerId, name);
        this.containerClass = containerClass;
        this.propertyValues = propertyValues;
    }

    public PropertyContainerDTO(JSONObject json) {
        if (json.has("cclass")) {
            containerClass = new PropertyContainerClassDTO(json.getJSONObject("cclass"));
        }
        if (json.has("values")) {
            propertyValues = new HashMap<>();
            JSONObject jp = json.getJSONObject("values");
            for (Object o : jp.keySet()) {
                String key = o.toString();
                propertyValues.put(key, jp.get(key));
            }
        }
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
