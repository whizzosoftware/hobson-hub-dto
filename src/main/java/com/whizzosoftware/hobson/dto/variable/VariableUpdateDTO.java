/*******************************************************************************
 * Copyright (c) 2014 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.variable;

import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.variable.DeviceVariableUpdate;
import com.whizzosoftware.hobson.api.variable.GlobalVariableUpdate;
import com.whizzosoftware.hobson.json.JSONAttributes;
import com.whizzosoftware.hobson.json.JSONProducer;
import org.json.JSONObject;

public class VariableUpdateDTO implements JSONProducer {
    private String id;
    private String name;
    private Object newValue;
    private long timestamp;

    public VariableUpdateDTO(DeviceVariableUpdate vu, IdProvider idProvider) {
        this.id = idProvider.createDeviceVariableId(vu.getContext());
        this.name = vu.getName();
        this.newValue = vu.getNewValue();
        this.timestamp = vu.getTimestamp();
    }

    public VariableUpdateDTO(GlobalVariableUpdate vu, IdProvider idProvider) {
        this.id = idProvider.createGlobalVariableId(vu.getContext());
        this.name = vu.getName();
        this.newValue = vu.getNewValue();
        this.timestamp = vu.getTimestamp();
    }

    public VariableUpdateDTO(JSONObject json) {
        this.id = json.getString(JSONAttributes.AID);
        this.name = json.getString(JSONAttributes.NAME);
        this.newValue = json.get(JSONAttributes.VALUE);
        this.timestamp = json.getLong(JSONAttributes.TIMESTAMP);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Object getNewValue() {
        return newValue;
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
        json.put(JSONAttributes.VALUE, newValue);
        json.put(JSONAttributes.TIMESTAMP, timestamp);
        return json;
    }
}
