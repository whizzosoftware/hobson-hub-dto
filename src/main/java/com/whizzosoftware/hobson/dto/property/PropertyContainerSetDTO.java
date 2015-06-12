/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.property;

import com.whizzosoftware.hobson.dto.ThingDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PropertyContainerSetDTO extends ThingDTO {
    private List<PropertyContainerDTO> containers;
    private PropertyContainerDTO primaryContainer;

    private PropertyContainerSetDTO(String id) {
        super(id);
    }

    private PropertyContainerSetDTO(JSONObject json, PropertyContainerMappingContext context) {
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

    @Override
    public String getMediaType() {
        return "application/vnd.hobson.propertyContainerSet";
    }

    public JSONObject toJSON(PropertyContainerMappingContext context) {
        JSONObject json = super.toJSON();

        if (primaryContainer != null) {
            json.put(context.getPrimaryContainerName(), primaryContainer.toJSON());
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

    public static class Builder {
        public PropertyContainerSetDTO dto;

        public Builder(String id) {
            dto = new PropertyContainerSetDTO(id);
        }

        public Builder(JSONObject json, PropertyContainerMappingContext ctx) {
            dto = new PropertyContainerSetDTO(json, ctx);
        }

        public Builder containers(List<PropertyContainerDTO> containers) {
            dto.containers = containers;
            return this;
        }

        public Builder primaryContainer(PropertyContainerDTO primaryContainer) {
            dto.primaryContainer = primaryContainer;
            return this;
        }

        public PropertyContainerSetDTO build() {
            return dto;
        }
    }
}
