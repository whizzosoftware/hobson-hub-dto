/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PropertyContainerSetDTO extends ThingDTO {
    private PropertyContainerDTO primaryContainer;
    private List<PropertyContainerDTO> containers;

    public PropertyContainerSetDTO(String id, PropertyContainerDTO primaryContainer, List<PropertyContainerDTO> containers) {
        super(id);
        this.primaryContainer = primaryContainer;
        this.containers = containers;
    }

    public PropertyContainerSetDTO(JSONObject json, PropertyContainerMappingContext context) {
        String primaryPropertyName = context.getPrimaryContainerName();
        if (primaryPropertyName == null) {
            primaryPropertyName = "primaryContainer";
        }
        String propertyListName = context.getContainersName();
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

    public JSONObject toJSON(PropertyContainerMappingContext context) {
        JSONObject json = super.toJSON(null);

        if (primaryContainer != null) {
            json.put(context.getPrimaryContainerName(), primaryContainer.toJSON(null));
        }

        if (containers != null && containers.size() > 0) {
            JSONArray a = new JSONArray();
            for (PropertyContainerDTO d : containers) {
                a.put(d);
            }
            json.put(context.getContainersName(), a);
        }

        return json;
    }
}
