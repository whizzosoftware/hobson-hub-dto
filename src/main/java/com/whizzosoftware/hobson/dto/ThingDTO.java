/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for named, described and linkable data transfer objects.
 *
 * @author Dan Noguerol
 */
abstract public class ThingDTO extends EntityDTO {
    private String name;
    private String description;
    private Map<String,String> links;

    public ThingDTO() {}

    public ThingDTO(JSONObject json) {
        this(json.has("@id") ? json.getString("@id") : null, json.has("name") ? json.getString("name") : null);
    }

    public ThingDTO(String id) {
        this(id, null);
    }

    public ThingDTO(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void addLink(String rel, String url) {
        if (links == null) {
            links = new HashMap<>();
        }
        links.put(rel, url);
    }

    public String getJSONMediaType() {
        return getMediaType() + "+json";
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (name != null) {
            json.put("name", name);
        }
        if (description != null) {
            json.put("description", description);
        }
        if (links != null && links.size() > 0) {
            json.put("links", links);
        }
        return json;
    }

    public String toString() {
        return toJSON().toString();
    }
}
