/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.hub;

import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class RepositoryDTO extends ThingDTO {
    private String id;
    private String uri;

    public RepositoryDTO(String id, String uri) {
        super(id);
        this.uri = uri;
    }

    public RepositoryDTO(JSONObject json) {
        if (json.has("id")) {
            this.id = json.getString("id");
        }
        this.uri = json.getString("uri");
    }

    public String getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String getMediaType() {
        return MediaTypes.REPOSITORY;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put(JSONAttributes.ID, id);
        json.put(JSONAttributes.URI, uri);
        return json;
    }
}
