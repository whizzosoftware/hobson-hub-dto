/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.PropertyContainerSet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PropertyContainerSetDTO extends ThingDTO {
    private PropertyContainerDTO primaryContainer;
    private List<PropertyContainerDTO> containers;

    public PropertyContainerSetDTO(PropertyContainerSet pcs) {
        setId(pcs.getId());

        if (pcs.hasProperties()) {
            containers = new ArrayList<>();
            for (PropertyContainer pc : pcs.getProperties()) {
                containers.add(new PropertyContainerDTO(null, pc));
            }
        }
    }

    public PropertyContainerSetDTO(JSONObject json) {
        this(json, null, null);
    }

    public PropertyContainerSetDTO(JSONObject json, String primaryPropertyName, String propertyListName) {
        if (primaryPropertyName == null) {
            primaryPropertyName = "primaryContainer";
        }
        if (propertyListName == null) {
            propertyListName = "containers";
        }

        if (json.has("@id")) {
            setId(json.getString("@id"));
        }
        if (json.has(primaryPropertyName)) {
            primaryContainer = new PropertyContainerDTO(json.getJSONObject(primaryPropertyName));
        }
        if (json.has(propertyListName)) {
            containers = new ArrayList<>();
            JSONArray ja = json.getJSONArray(propertyListName);
            for (int i=0; i < ja.length(); i++) {
                containers.add(new PropertyContainerDTO(ja.getJSONObject(i)));
            }
        }
    }

    public boolean hasPrimaryContainer() {
        return (primaryContainer != null);
    }

    public PropertyContainerDTO getPrimaryContainer() {
        return primaryContainer;
    }

    public boolean hasContainers() {
        return (containers != null);
    }

    public List<PropertyContainerDTO> getContainers() {
        return containers;
    }

    public void validate() {
    }

    @Override
    public String getMediaType() {
        return "application/vnd.hobson.propertyContainerSet";
    }
}
