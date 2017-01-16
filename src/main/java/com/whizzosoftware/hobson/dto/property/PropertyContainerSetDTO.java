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

import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassProvider;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassType;
import com.whizzosoftware.hobson.api.property.PropertyContainerSet;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.context.TemplatedIdBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PropertyContainerSetDTO extends ThingDTO {
    private List<PropertyContainerDTO> containers;

    private PropertyContainerSetDTO(TemplatedIdBuildContext ctx, TemplatedId id) {
        super(ctx, id);
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
                a.put(d.toJSON());
            }
            json.put(context.getContainersName(), a);
        }

        return json;
    }

    public static class Builder {
        public PropertyContainerSetDTO dto;

        public Builder(TemplatedIdBuildContext ctx, TemplatedId id) {
            dto = new PropertyContainerSetDTO(ctx, id);
        }

        public Builder(DTOBuildContext ctx, HubContext hctx, PropertyContainerSet pcs, PropertyContainerClassType type, PropertyContainerClassProvider pccp, boolean showDetails) {
            dto = new PropertyContainerSetDTO(ctx, ctx.getIdProvider().createTaskActionSetId(hctx, pcs.getId()));

            if (showDetails) {
                dto.setName(pcs.getName());
                if (pcs.hasProperties()) {
                    dto.containers = new ArrayList<>();
                    for (PropertyContainer pc : pcs.getProperties()) {
                        dto.containers.add(new PropertyContainerDTO.Builder(
                            ctx,
                            pc,
                            pccp,
                            type,
                            true
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
