/*
 *******************************************************************************
 * Copyright (c) 2017 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.device;

import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.dto.EntityDTO;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.context.TemplatedIdBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class TagsDTO extends EntityDTO {
    private Set<String> tags;

    public TagsDTO(TemplatedIdBuildContext ctx, TemplatedId id, Set<String> tags) {
        super(ctx, id);
        this.tags = tags;
    }

    public TagsDTO(JSONObject json) {
        JSONArray ja = json.getJSONArray(JSONAttributes.VALUES);
        if (ja != null && ja.length() > 0) {
            tags = new HashSet<>();
            for (int i = 0; i < ja.length(); i++) {
                tags.add(ja.getString(i));
            }
        }
    }

    public Set<String> getTags() {
        return tags;
    }

    @Override
    public String getMediaType() {
        return MediaTypes.TAGS;
    }

    @Override
    public String getJSONMediaType() {
        return getMediaType() + "+json";
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.VALUES, tags);
        return json;
    }
}
