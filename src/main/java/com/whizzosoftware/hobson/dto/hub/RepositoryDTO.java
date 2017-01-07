/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.hub;

import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.dto.context.TemplatedIdBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class RepositoryDTO extends ThingDTO {
    private String uri;

    public RepositoryDTO(TemplatedIdBuildContext ctx, TemplatedId id, String uri) {
        super(ctx, id);
        this.uri = uri;
    }

    public RepositoryDTO(JSONObject json) {
        super(json);
        this.uri = json.getString("uri");
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String getMediaType() {
        return MediaTypes.REPOSITORY;
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.URI, uri);
        return json;
    }
}
