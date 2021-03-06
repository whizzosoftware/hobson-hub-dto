/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.presence;

import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.api.presence.PresenceEntity;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.dto.context.TemplatedIdBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class PresenceEntityDTO extends ThingDTO {
    private PresenceLocationDTO location;
    private Long lastUpdate;

    private PresenceEntityDTO(TemplatedIdBuildContext ctx, TemplatedId id) {
        super(ctx, id);
    }

    @Override
    public String getMediaType() {
        return MediaTypes.PRESENCE_ENTITY;
    }

    public PresenceLocationDTO getLocation() {
        return location;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (location != null) {
            json.put(JSONAttributes.LOCATION, location.toJSON());
        }
        if (lastUpdate != null) {
            json.put(JSONAttributes.LAST_UPDATE, lastUpdate);
        }
        return json;
    }

    public static class Builder {
        private PresenceEntityDTO dto;

        public Builder(DTOBuildContext ctx, PresenceEntity entity, boolean showDetails) {
            dto = new PresenceEntityDTO(ctx, ctx.getIdProvider().createPresenceEntityId(entity.getContext()));
            if (showDetails) {
                dto.setName(entity.getName());
                dto.lastUpdate = entity.getLastUpdate();
                dto.location = new PresenceLocationDTO.Builder(ctx, ctx.getPresenceEntityLocation(entity.getContext()), ctx.getIdProvider(), ctx.getExpansionFields().has(JSONAttributes.LOCATION)).build();
            }
        }

        public Builder(TemplatedIdBuildContext ctx, TemplatedId id) {
            dto = new PresenceEntityDTO(ctx, id);
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder location(PresenceLocationDTO pldto) {
            dto.location = pldto;
            return this;
        }

        public Builder lastUpdate(Long lastUpdate) {
            dto.lastUpdate = lastUpdate;
            return this;
        }

        public PresenceEntityDTO build() {
            return dto;
        }
    }
}
