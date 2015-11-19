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
import com.whizzosoftware.hobson.api.event.VariableUpdateNotificationEvent;
import com.whizzosoftware.hobson.api.event.VariableUpdateRequestEvent;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

/**
 * A helper class for marshaling HobsonEvents to/from DTOs.
 *
 * @author Dan Noguerol
 */
public class EventMarshaller {

    public JSONObject marshal(HobsonEvent event, IdProvider idProvider) {
        if (event instanceof VariableUpdateNotificationEvent) {
            return new VariableUpdateNotificationEventDTO((VariableUpdateNotificationEvent)event).toJSON();
        } else if (event instanceof VariableUpdateRequestEvent) {
            return new VariableUpdateRequestEventDTO((VariableUpdateRequestEvent)event, idProvider).toJSON();
        } else if (event instanceof PresenceUpdateNotificationEvent) {
            return new PresenceUpdateNotificationEventDTO((PresenceUpdateNotificationEvent)event).toJSON();
        }
        return null;
    }

    public HobsonEventDTO unmarshal(JSONObject json, IdProvider idProvider, boolean convertToLocal) {
        switch (json.getString(JSONAttributes.EVENT_ID)) {
            case VariableUpdateNotificationEvent.ID:
                return new VariableUpdateNotificationEventDTO(json);
            case VariableUpdateRequestEvent.ID:
                return new VariableUpdateRequestEventDTO(json, idProvider, convertToLocal);
            case PresenceUpdateNotificationEvent.ID:
                return new PresenceUpdateNotificationEventDTO(json);
            default:
                return null;
        }
    }
}
