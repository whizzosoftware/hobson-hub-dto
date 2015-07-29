/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.plugin;

import org.json.JSONObject;

public class EnableRemoteRepositoryDTO {
    private String url;
    private boolean enabled;

    public EnableRemoteRepositoryDTO(JSONObject json) {
        this.url = json.getString("url");
        this.enabled = json.getBoolean("enabled");
    }

    public String getUrl() {
        return url;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
