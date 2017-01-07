/*
 *******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.dto.context.TemplatedIdBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import com.whizzosoftware.hobson.json.JSONProducer;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for all other identifiable data transfer objects.
 *
 * @author Dan Noguerol
 */
abstract public class EntityDTO implements JSONProducer {
    private String id;
    private String idTemplate;
    private Map<String,Map<String,String>> contexts;

    public EntityDTO() {}

    public EntityDTO(TemplatedIdBuildContext ctx, TemplatedId id) {
        this.id = id.getId();
        this.idTemplate = ctx.addIdTemplate(id.getIdTemplate());
    }

    public EntityDTO(String id) {
        this.id = id;
    }

    public EntityDTO(JSONObject json) {
        this.id = json.has(JSONAttributes.AID) ? json.getString(JSONAttributes.AID) : null;
        this.idTemplate = json.has(JSONAttributes.AIDT) ? json.getString(JSONAttributes.AIDT) : null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(String idTemplate) {
        this.idTemplate = idTemplate;
    }

    public void addContext(String name, Map<String,String> values) {
        if (contexts == null) {
            contexts = new HashMap<>();
        }
        contexts.put(name, values);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        if (contexts != null) {
            JSONObject contexts = new JSONObject();
            for (String key : this.contexts.keySet()) {
                contexts.put(key, this.contexts.get(key));
            }
            json.put(JSONAttributes.ACONTEXT, contexts);
        }
        if (id != null) {
            json.put(JSONAttributes.AID, id);
        }
        if (idTemplate != null) {
            json.put(JSONAttributes.AIDT, idTemplate);
        }
        return json;
    }
}
