/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.presence;

import com.whizzosoftware.hobson.dto.ThingDTO;
import org.json.JSONObject;

public class PresenceEntityDTO extends ThingDTO {
    private String location;
    private Long lastUpdate;

    private PresenceEntityDTO(String id) {
        super(id);
    }

    @Override
    public String getMediaType() {
        return "application/vnd.hobson.presenceEntity";
    }

    public String getLocation() {
        return location;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (location != null) {
            json.put("location", location);
        }
        if (lastUpdate != null) {
            json.put("lastUpdate", lastUpdate);
        }
        return json;
    }

    public static class Builder {
        private PresenceEntityDTO dto;

        public Builder(String id) {
            dto = new PresenceEntityDTO(id);
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder location(String location) {
            dto.location = location;
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
