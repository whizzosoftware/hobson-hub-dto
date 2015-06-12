/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.task;

import com.whizzosoftware.hobson.api.HobsonInvalidRequestException;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerMappingContext;
import com.whizzosoftware.hobson.dto.property.PropertyContainerSetDTO;
import org.json.JSONObject;

import java.util.Map;

public class HobsonTaskDTO extends ThingDTO {
    private PropertyContainerSetDTO conditionSet;
    private PropertyContainerSetDTO actionSet;
    private Map<String,Object> properties;

    private HobsonTaskDTO(String id) {
        super(id);
    }

    private HobsonTaskDTO(JSONObject json) {
        if (json.has("name")) {
            setName(json.getString("name"));
        }

        if (json.has("description")) {
            setDescription(json.getString("description"));
        }

        if (json.has("conditionSet")) {
            this.conditionSet = new PropertyContainerSetDTO.Builder(json.getJSONObject("conditionSet"), new PropertyContainerMappingContext() {
                @Override
                public String getPrimaryContainerName() {
                    return "trigger";
                }

                @Override
                public String getContainersName() {
                    return "conditions";
                }
            }).build();
        }

        if (json.has("actionSet")) {
            this.actionSet = new PropertyContainerSetDTO.Builder(json.getJSONObject("actionSet"), new PropertyContainerMappingContext() {
                @Override
                public String getPrimaryContainerName() {
                    return null;
                }

                @Override
                public String getContainersName() {
                    return "actions";
                }
            }).build();
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
        if (conditionSet == null) {
            throw new HobsonInvalidRequestException("Condition set is required");
        }
        if (actionSet == null) {
            throw new HobsonInvalidRequestException("Action set is required");
        }
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();

        if (conditionSet != null) {
            json.put("conditionSet", conditionSet.toJSON(new PropertyContainerMappingContext() {
                @Override
                public String getPrimaryContainerName() {
                    return "trigger";
                }

                @Override
                public String getContainersName() {
                    return "conditions";
                }
            }));
        }

        if (actionSet != null) {
            json.put("actionSet", actionSet.toJSON());
        }

        return json;
    }

    static public class Builder {
        HobsonTaskDTO dto;

        public Builder(String id) {
            dto = new HobsonTaskDTO(id);
        }

        public Builder(JSONObject json) {
            dto = new HobsonTaskDTO(json);
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder description(String description) {
            dto.setDescription(description);
            return this;
        }

        public Builder conditionSet(PropertyContainerSetDTO conditionSet) {
            dto.conditionSet = conditionSet;
            return this;
        }

        public Builder actionSet(PropertyContainerSetDTO actionSet) {
            dto.actionSet = actionSet;
            return this;
        }

        public Builder properties(Map<String,Object> properties) {
            dto.properties = properties;
            return this;
        }

        public HobsonTaskDTO build() {
            return dto;
        }
    }
}