package com.whizzosoftware.hobson.json;

import com.whizzosoftware.hobson.api.HobsonInvalidRequestException;
import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.dto.LinkProvider;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TypedPropertyValueSerializer {

    static public Object createValueObject(HubContext ctx, TypedProperty.Type type, Object jsonValue, LinkProvider links) {
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
                    return createDeviceValueObject(ctx, (JSONObject)jsonValue, links);
                } else {
                    throw new HobsonInvalidRequestException("Device property is not a JSON object: " + jsonValue);
                }
            case DEVICES:
                if (jsonValue instanceof JSONArray) {
                    return createDevicesValueObject(ctx, (JSONArray)jsonValue, links);
                } else {
                    throw new HobsonInvalidRequestException("Devices property is not a JSON array: " + jsonValue);
                }
            default:
                return jsonValue.toString();
        }
    }

    static public Object createDevicesValueObject(HubContext ctx, JSONArray a, LinkProvider links) {
        List<DeviceContext> results = new ArrayList<>();
        for (int i=0; i < a.length(); i++) {
            Object o = a.get(i);
            if (o instanceof JSONObject) {
                JSONObject json = (JSONObject)o;
                if (json.has("@id")) {
                    if (links != null) {
                        results.add(links.createDeviceContext(json.getString("@id")));
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

    static public Object createDeviceValueObject(HubContext ctx, JSONObject json, LinkProvider links) {
        if (json.has("@id")) {
            if (links != null) {
                return links.createDeviceContext(json.getString("@id"));
            } else {
                return json.getString("@id");
            }
        } else {
            throw new HobsonInvalidRequestException("Device object must contain @id attribute");
        }
    }
}
