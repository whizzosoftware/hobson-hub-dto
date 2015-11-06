/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.event;

import com.whizzosoftware.hobson.api.event.HobsonEvent;
import com.whizzosoftware.hobson.json.JSONProducer;
import org.json.JSONObject;

/**
 * An abstract base class for all HobsonEvent DTOs.
 *
 * @author Dan Noguerol
 */
abstract public class HobsonEventDTO implements JSONProducer {
    String eventId;
    Long timestamp;

    public HobsonEventDTO(String eventId, Long timestamp) {
        this.eventId = eventId;
        this.timestamp = timestamp;
    }

    public HobsonEventDTO(JSONObject json) {
        this.eventId = json.getString("eventId");
        this.timestamp = json.getLong("timestamp");
        readProperties(json);
    }

    @Override
    public String getMediaType() {
        return "";
    }

    @Override
    public String getJSONMediaType() {
        return getMediaType() + "+json";
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("eventId", eventId);
        json.put("timestamp", timestamp);
        json.put("properties", createProperties());
        return json;
    }

    abstract protected JSONObject createProperties();
    abstract protected void readProperties(JSONObject json);
    abstract public HobsonEvent createEvent();
}
