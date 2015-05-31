/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.property.TypedProperty;
import org.json.JSONObject;

public class TypedPropertyDTO extends ThingDTO {
    public String id;
    public String name;
    public String description;
    public String type;

    public TypedPropertyDTO(TypedProperty p) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.type = p.getType().toString();
    }

    @Override
    public String getMediaType() {
        return "application/vnd.hobson.property";
    }

    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = super.toJSON(links);
        if (id != null) {
            json.put("id", id);
        }
        if (name != null) {
            json.put("name", name);
        }
        if (description != null) {
            json.put("description", description);
        }
        if (type != null) {
            json.put("type", type);
        }
        return json;
    }
}
