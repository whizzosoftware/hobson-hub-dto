/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.HobsonInvalidRequestException;
import com.whizzosoftware.hobson.api.task.HobsonTask;
import org.json.JSONObject;

import java.util.Map;

public class HobsonTaskDTO extends ThingDTO {
    private PropertyContainerSetDTO conditionSet;
    private PropertyContainerSetDTO actionSet;
    private Map<String,Object> properties;

    public HobsonTaskDTO() {}

    public HobsonTaskDTO(String id, HobsonTask task, LinkProvider links) {
        setId(id);
        setName(task.getName());
        this.conditionSet = new PropertyContainerSetDTO(task.getConditionSet());
        this.actionSet = new PropertyContainerSetDTO(task.getActionSet());
        this.properties = task.getProperties();
    }

    public HobsonTaskDTO(JSONObject json) {
        if (json.has("name")) {
            setName(json.getString("name"));
        }
        if (json.has("conditionSet")) {
            this.conditionSet = new PropertyContainerSetDTO(json.getJSONObject("conditionSet"), "trigger", "conditions");
        }
        if (json.has("actionSet")) {
            this.actionSet = new PropertyContainerSetDTO(json.getJSONObject("actionSet"), null, "actions");
        }
    }

    public PropertyContainerSetDTO getConditionSet() {
        return conditionSet;
    }

    public PropertyContainerSetDTO getActionSet() {
        return actionSet;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public String getMediaType() {
        return "application/vnd.hobson.task";
    }

    public void validate() {
        if (getName() == null) {
            throw new HobsonInvalidRequestException("Name is required");
        }
        if (conditionSet != null) {
            conditionSet.validate();
        } else {
            throw new HobsonInvalidRequestException("Condition set is required");
        }
        if (actionSet != null) {
            actionSet.validate();
        } else {
            throw new HobsonInvalidRequestException("Action set is required");
        }
    }

    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = super.toJSON(links);
        json.put("conditionSet", conditionSet.toJSON(links));
        json.put("actionSet", actionSet.toJSON(links));
        return json;
    }
}
