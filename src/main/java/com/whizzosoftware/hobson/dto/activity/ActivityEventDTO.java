/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.activity;

import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import org.json.JSONObject;

public class ActivityEventDTO extends ThingDTO {
    private Long timestamp;

    public ActivityEventDTO(String name, Long timestamp) {
        setName(name);
        this.timestamp = timestamp;
    }

    @Override
    public String getMediaType() {
        return MediaTypes.EVENT;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (timestamp != null) {
            json.put("timestamp", timestamp);
        }
        return json;
    }
}
