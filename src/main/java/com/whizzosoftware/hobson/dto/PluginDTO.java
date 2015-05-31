/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.plugin.PluginContext;
import org.json.JSONObject;

public class PluginDTO {
    private PluginContext context;

    public PluginDTO(JSONObject json, LinkProvider links) {
        this.context = links.createPluginContext(json.getString("@id"));
    }

    public PluginDTO(String link, LinkProvider links) {
        this.context = links.createPluginContext(link);
    }

    public PluginDTO(PluginContext context) {
        this.context = context;
    }

    public PluginContext getContext() {
        return context;
    }

    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = new JSONObject();
        if (links != null) {
            json.put("@id", links.createPluginLink(context));
        }
        return json;
    }
}
