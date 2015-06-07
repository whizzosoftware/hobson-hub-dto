/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PropertyContainerClassDTO extends ThingDTO {
    private List<TypedPropertyDTO> supportedProperties;

    public PropertyContainerClassDTO(String id) {
        super(id);
    }

    public PropertyContainerClassDTO(JSONObject json) {
        super(json);
    }

    public PropertyContainerClassDTO(String id, String name, List<TypedPropertyDTO> supportedProperties) {
        super(id, name);
        this.supportedProperties = supportedProperties;
    }

    @Override
    public String getMediaType() {
        return "application/vnd.hobson.propertyContainerClass";
    }

    public List<TypedPropertyDTO> getSupportedProperties() {
        return supportedProperties;
    }

    public void setSupportedProperties(List<TypedPropertyDTO> supportedProperties) {
        this.supportedProperties = supportedProperties;
    }

    public void addSupportedProperty(TypedPropertyDTO property) {
        if (supportedProperties == null) {
            supportedProperties = new ArrayList<>();
        }
        supportedProperties.add(property);
    }

    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = super.toJSON(links);
        if (supportedProperties != null) {
            JSONArray array = new JSONArray();
            for (TypedPropertyDTO p : supportedProperties) {
                array.put(p.toJSON(links));
            }
            json.put("supportedProperties", array);
        }
        return json;
    }
}
