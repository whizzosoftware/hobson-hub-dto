/*******************************************************************************
 * Copyright (c) 2014 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.variable;

import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.variable.VariableUpdate;
import com.whizzosoftware.hobson.json.JSONAttributes;
import com.whizzosoftware.hobson.json.JSONProducer;
import org.json.JSONObject;

public class VariableUpdateDTO implements JSONProducer {
    private String id;
    private String name;
    private Object value;
    private long timestamp;

    public VariableUpdateDTO(VariableUpdate vu, IdProvider idProvider) {
        this.id = idProvider.createDeviceId(vu.getContext().getDeviceContext());
        this.name = vu.getName();
        this.value = vu.getValue();
        this.timestamp = vu.getTimestamp();
    }

    public VariableUpdateDTO(JSONObject json) {
        this.id = json.getString(JSONAttributes.AID);
        this.name = json.getString(JSONAttributes.NAME);
        this.value = json.get(JSONAttributes.VALUE);
        this.timestamp = json.getLong(JSONAttributes.TIMESTAMP);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getMediaType() {
        return null;
    }

    @Override
    public String getJSONMediaType() {
        return null;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put(JSONAttributes.AID, id);
        json.put(JSONAttributes.NAME, name);
        json.put(JSONAttributes.VALUE, value);
        json.put(JSONAttributes.TIMESTAMP, timestamp);
        return json;
    }
}
