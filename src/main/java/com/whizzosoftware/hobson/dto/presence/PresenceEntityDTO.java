/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.presence;

import com.whizzosoftware.hobson.api.presence.PresenceEntity;
import com.whizzosoftware.hobson.api.presence.PresenceManager;
import com.whizzosoftware.hobson.dto.ExpansionFields;
import com.whizzosoftware.hobson.dto.IdProvider;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class PresenceEntityDTO extends ThingDTO {
    private PresenceLocationDTO location;
    private Long lastUpdate;

    private PresenceEntityDTO(String id) {
        super(id);
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

        public Builder(PresenceEntity entity, PresenceManager manager, boolean showDetails, ExpansionFields expansions, IdProvider idProvider) {
            dto = new PresenceEntityDTO(idProvider.createPresenceEntityId(entity.getContext()));
            if (showDetails) {
                dto.setName(entity.getName());
                dto.lastUpdate = entity.getLastUpdate();
                dto.location = new PresenceLocationDTO.Builder(manager.getEntityLocation(entity.getContext()), idProvider, expansions.has(JSONAttributes.LOCATION)).build();
            }
        }

        public Builder(String id) {
            dto = new PresenceEntityDTO(id);
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
