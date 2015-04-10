/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.json.event;

import com.whizzosoftware.hobson.api.event.HobsonEvent;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that provides a way to marshal/unmarshal HobsonEvent objects. This is currently a manual process due to
 * the assumption that code size is less important than memory footprint. However, it makes sense to investigate
 * open-source library alternatives to make code maintenance easier.
 *
 * @author Dan Noguerol
 */
public class HobsonEventSerializationFactory {
    private final List<HobsonEventSerializer> serializers = new ArrayList<>();

    public HobsonEventSerializationFactory() {
        serializers.add(new VariableUpdateNotificationEventSerializer());
        serializers.add(new VariableUpdateRequestEventSerializer());
        serializers.add(new StateSnapshotEventSerializer());
    }

    /**
     * Marshals an event to JSON.
     *
     * @param event the HobsonEvent to marshal
     *
     * @return a JSONObject (or null if it can't be marshaled)
     */
    public JSONObject marshal(HobsonEvent event) {
        for (HobsonEventSerializer marshaller : serializers) {
            if (marshaller.canMarshal(event)) {
                return marshaller.marshal(event);
            }
        }
        return null;
    }

    /**
     * Unmarshals an event from JSON.
     *
     * @param json the JSON to unmarshal
     *
     * @return a HobsonEvent instance (or null if it can't be unmarshaled)
     */
    public HobsonEvent unmarshal(JSONObject json) {
        for (HobsonEventSerializer marshaller : serializers) {
            if (marshaller.canUnmarshal(json)) {
                return marshaller.unmarshal(json);
            }
        }
        return null;
    }
}
