/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.event;

import com.whizzosoftware.hobson.api.event.HobsonEvent;
import com.whizzosoftware.hobson.json.JSONAttributes;
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
        this.eventId = json.getString(JSONAttributes.EVENT_ID);
        this.timestamp = json.getLong(JSONAttributes.TIMESTAMP);
        if (json.has(JSONAttributes.PROPERTIES)) {
            readProperties(json.getJSONObject(JSONAttributes.PROPERTIES));
        }
    }

    public Long getTimestamp() {
        return timestamp;
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
        json.put(JSONAttributes.EVENT_ID, eventId);
        json.put(JSONAttributes.TIMESTAMP, timestamp);
        json.put(JSONAttributes.PROPERTIES, createProperties());
        return json;
    }

    /**
     * Allows sub-classes to return their custom properties for inclusion.
     *
     * @return a JSONObject with any custom properties
     */
    abstract protected JSONObject createProperties();

    /**
     * Allows sub-classes to restore their custom properties from a JSONObject.
     *
     * @param json the JSONObject to populate from
     */
    abstract protected void readProperties(JSONObject json);
}
