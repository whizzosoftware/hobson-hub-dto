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

abstract public class ThingDTO implements JSONProducer {
    private String id;
    private String name;
    private String description;
    private Map<String,String> links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void addLink(String rel, String url) {
        if (links == null) {
            links = new HashMap<>();
        }
        links.put(rel, url);
    }

    @Override
    public JSONObject toJSON(LinkProvider lp) {
        JSONObject json = new JSONObject();
        if (id != null) {
            json.put("@id", id);
        }
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
}