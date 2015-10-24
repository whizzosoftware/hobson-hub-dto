/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.event;

import com.whizzosoftware.hobson.api.event.HobsonEvent;
import com.whizzosoftware.hobson.api.event.PresenceUpdateEvent;
import org.json.JSONObject;

/**
 * A DTO for PresenceUpdateEvent objects.
 *
 * @author Dan Noguerol
 */
public class PresenceUpdateEventDTO extends HobsonEventDTO {
    private String entityId;
    private String location;

    public PresenceUpdateEventDTO(PresenceUpdateEvent event) {
        super(event.getEventId(), event.getTimestamp());
        this.entityId = event.getEntityId();
        this.location = event.getLocation();
    }

    public PresenceUpdateEventDTO(JSONObject json) {
        super(json);
    }

    @Override
    protected JSONObject createProperties() {
        JSONObject json = new JSONObject();
        json.put("entityId", entityId);
        json.put("location", location);
        return json;
    }

    @Override
    protected void readProperties(JSONObject json) {
        this.entityId = json.getString("entityId");
        this.location = json.getString("location");
    }

    @Override
    public HobsonEvent createEvent() {
        return new PresenceUpdateEvent(this.timestamp, this.entityId, this.location);
    }
}
