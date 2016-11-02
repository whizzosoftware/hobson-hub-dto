/*
 *******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.json.JSONProducer;
import org.json.JSONObject;

/**
 * Base class for all other identifiable data transfer objects.
 *
 * @author Dan Noguerol
 */
abstract public class EntityDTO implements JSONProducer {
    private String id;

    public EntityDTO() {}

    public EntityDTO(String id) {
        this.id = id;
    }

    public EntityDTO(JSONObject json) {
        this.id = json.has("@id") ? json.getString("@id") : null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        if (id != null) {
            json.put("@id", id);
        }
        return json;
    }
}
