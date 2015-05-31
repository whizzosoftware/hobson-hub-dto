/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.hub.HubContext;
import org.json.JSONObject;

public class HubContextDTO {
    private HubContext context;

    public HubContextDTO(JSONObject json, LinkProvider links) {
        this.context = links.createHubContext(json.getString("@id"));
    }

    public HubContextDTO(HubContext context) {
        this.context = context;
    }

    public HubContext getContext() {
        return context;
    }
}
