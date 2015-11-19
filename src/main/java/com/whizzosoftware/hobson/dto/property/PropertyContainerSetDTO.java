/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.property;

import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassProvider;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassType;
import com.whizzosoftware.hobson.api.property.PropertyContainerSet;
import com.whizzosoftware.hobson.dto.ExpansionFields;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PropertyContainerSetDTO extends ThingDTO {
    private List<PropertyContainerDTO> containers;

    private PropertyContainerSetDTO(String id) {
        super(id);
    }

    private PropertyContainerSetDTO(JSONObject json, PropertyContainerMappingContext context) {
        String propertyListName = context.getContainersName();
        if (propertyListName == null) {
            propertyListName = JSONAttributes.CONTAINERS;
        }

        if (json.has(JSONAttributes.AID)) {
            setId(json.getString(JSONAttributes.AID));
        }
        if (json.has(propertyListName)) {
            containers = new ArrayList<>();
            JSONArray ja = json.getJSONArray(propertyListName);
            for (int i=0; i < ja.length(); i++) {
                containers.add(new PropertyContainerDTO.Builder(ja.getJSONObject(i)).build());
            }
        }
    }

    public boolean hasContainers() {
        return (containers != null);
    }

    public List<PropertyContainerDTO> getContainers() {
        return containers;
    }

    @Override
    public String getMediaType() {
        return MediaTypes.PROPERTY_CONTAINER_SET;
    }

    public JSONObject toJSON(PropertyContainerMappingContext context) {
        JSONObject json = super.toJSON();

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

        public Builder(PropertyContainerSet pcs, HubContext hubContext, PropertyContainerClassType type, boolean showDetails, PropertyContainerClassProvider pccp, ExpansionFields expansions, IdProvider idProvider) {
            dto = new PropertyContainerSetDTO(idProvider.createTaskActionSetId(hubContext, pcs.getId()));

            if (showDetails) {
                dto.setName(pcs.getName());
                if (pcs.hasProperties()) {
                    for (PropertyContainer pc : pcs.getProperties()) {
                        dto.containers.add(new PropertyContainerDTO.Builder(
                            pc,
                            pccp,
                            type,
                            true,
                            expansions,
                            idProvider
                        ).build());
                    }
                }
            }

        }

        public Builder(JSONObject json, PropertyContainerMappingContext ctx) {
            dto = new PropertyContainerSetDTO(json, ctx);
        }

        public Builder containers(List<PropertyContainerDTO> containers) {
            dto.containers = containers;
            return this;
        }

        public PropertyContainerSetDTO build() {
            return dto;
        }
    }
}
