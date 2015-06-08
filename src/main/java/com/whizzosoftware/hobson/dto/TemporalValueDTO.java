/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONObject;

public class TemporalValueDTO extends ThingDTO {
    private Long time;
    private Object value;

    public TemporalValueDTO(Long time, Object value) {
        super();
        this.time = time;
        this.value = value;
    }

    @Override
    public String getMediaType() {
        return "application/vnd.hobson.temporalValue";
    }

    @Override
    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = super.toJSON(links);
        if (time != null) {
            json.put("time", time);
        }
        if (value != null) {
            json.put("value", value);
        }
        return json;
    }
}
