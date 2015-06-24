/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.json;

import com.whizzosoftware.hobson.api.HobsonInvalidRequestException;
import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TypedPropertyValueSerializer {

    static public Object createValueObject(TypedProperty.Type type, Object jsonValue, DeviceContextProvider dcp) {
        switch (type) {
            case NUMBER:
                if (jsonValue instanceof Double || jsonValue instanceof Integer) {
                    return jsonValue;
                } else {
                    throw new HobsonInvalidRequestException("Number property is not a valid JSON number: " + jsonValue);
                }
            case STRING:
                if (jsonValue instanceof String) {
                    return jsonValue;
                } else if (jsonValue instanceof Integer || jsonValue instanceof Double || jsonValue instanceof Boolean) {
                    return jsonValue.toString();
                } else {
                    throw new HobsonInvalidRequestException("String property is not a valid JSON string: " + jsonValue);
                }
            case DEVICE:
                if (jsonValue instanceof JSONObject) {
                    return createDeviceValueObject((JSONObject)jsonValue, dcp);
                } else {
                    throw new HobsonInvalidRequestException("Device property is not a JSON object: " + jsonValue);
                }
            case DEVICES:
                if (jsonValue instanceof JSONArray) {
                    return createDevicesValueObject((JSONArray)jsonValue, dcp);
                } else {
                    throw new HobsonInvalidRequestException("Devices property is not a JSON array: " + jsonValue);
                }
            default:
                return jsonValue.toString();
        }
    }

    static public Object createDevicesValueObject(JSONArray a, DeviceContextProvider dcp) {
        List<DeviceContext> results = new ArrayList<>();
        for (int i=0; i < a.length(); i++) {
            Object o = a.get(i);
            if (o instanceof JSONObject) {
                JSONObject json = (JSONObject)o;
                if (json.has(JSONAttributes.AID)) {
                    if (dcp != null) {
                        results.add(dcp.createDeviceContext(json.getString(JSONAttributes.AID)));
                    }
                } else {
                    throw new HobsonInvalidRequestException("Device list object must contain @id attribute");
                }
            } else {
                throw new HobsonInvalidRequestException("Device list must be an array of objects");
            }
        }
        return results;
    }

    static public Object createDeviceValueObject(JSONObject json, DeviceContextProvider dcp) {
        if (json.has(JSONAttributes.AID)) {
            if (dcp != null) {
                return dcp.createDeviceContext(json.getString(JSONAttributes.AID));
            } else {
                return json.getString(JSONAttributes.AID);
            }
        } else {
            throw new HobsonInvalidRequestException("Device object must contain @id attribute");
        }
    }

    public interface DeviceContextProvider {
        DeviceContext createDeviceContext(String id);
    }
}
