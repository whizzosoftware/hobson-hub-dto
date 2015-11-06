/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.event;

import com.whizzosoftware.hobson.api.event.HobsonEvent;
import com.whizzosoftware.hobson.api.event.PresenceUpdateNotificationEvent;
import com.whizzosoftware.hobson.api.presence.PresenceEntityContext;
import com.whizzosoftware.hobson.api.presence.PresenceLocationContext;
import org.json.JSONObject;

/**
 * A DTO for PresenceUpdateEvent objects.
 *
 * @author Dan Noguerol
 */
public class PresenceUpdateNotificationEventDTO extends HobsonEventDTO {
    private PresenceEntityContext entityContext;
    private PresenceLocationContext oldLocationContext;
    private PresenceLocationContext newLocationContext;

    public PresenceUpdateNotificationEventDTO(PresenceUpdateNotificationEvent event) {
        super(event.getEventId(), event.getTimestamp());
        this.entityContext = event.getEntityContext();
        this.oldLocationContext = event.getOldLocation();
        this.newLocationContext = event.getNewLocation();
    }

    public PresenceUpdateNotificationEventDTO(JSONObject json) {
        super(json);
    }

    @Override
    protected JSONObject createProperties() {
        JSONObject json = new JSONObject();
        json.put("entity", entityContext.toString());
        json.put("newLocation", newLocationContext.toString());
        json.put("oldLocation", newLocationContext.toString());
        return json;
    }

    @Override
    protected void readProperties(JSONObject json) {
        this.entityContext = PresenceEntityContext.create(json.getString("entity"));
        this.oldLocationContext = PresenceLocationContext.create(json.getString("oldLocation"));
        this.newLocationContext = PresenceLocationContext.create(json.getString("newLocation"));
    }

    @Override
    public HobsonEvent createEvent() {
        return new PresenceUpdateNotificationEvent(this.timestamp, this.entityContext, this.oldLocationContext, this.newLocationContext);
    }
}
