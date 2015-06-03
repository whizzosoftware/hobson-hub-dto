/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.plugin.PluginContext;
import com.whizzosoftware.hobson.api.plugin.PluginDescriptor;
import org.json.JSONObject;

public class PluginDTO extends ThingDTO {
    private String version;

    public PluginDTO(JSONObject json) {
        setId(json.getString("@id"));
    }

    public PluginDTO(String id) {
        setId(id);
    }

    public PluginDTO(String id, PluginContext context) {
        setId(id);
    }

    public PluginDTO(String id, PluginDescriptor pd) {
        setId(id);
        setName(pd.getName());
        setDescription(pd.getDescription());
        this.version = pd.getVersionString();
    }

    @Override
    public String getMediaType() {
        return "application/vnd.hobson.plugin";
    }

    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = super.toJSON(links);
        json.put("version", version != null ? version : null);
        return json;
    }
}
