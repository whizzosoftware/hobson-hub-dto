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
import com.whizzosoftware.hobson.api.event.VariableUpdateNotificationEvent;
import org.json.JSONObject;

/**
 * A helper class for marshaling HobsonEvents to/from DTOs.
 *
 * @author Dan Noguerol
 */
public class EventMarshaller {

    public JSONObject marshal(HobsonEvent event) {
        if (event instanceof VariableUpdateNotificationEvent) {
            return new VariableUpdateNotificationEventDTO((VariableUpdateNotificationEvent)event).toJSON();
        } else if (event instanceof PresenceUpdateEvent) {
            return new PresenceUpdateEventDTO((PresenceUpdateEvent)event).toJSON();
        }
        return null;
    }

    public HobsonEventDTO unmarshal(JSONObject json) {
        switch (json.getString("eventId")) {
            case VariableUpdateNotificationEvent.ID:
                return new VariableUpdateNotificationEventDTO(json);
            case PresenceUpdateEvent.ID:
                return new PresenceUpdateEventDTO(json);
            default:
                return null;
        }
    }
}
