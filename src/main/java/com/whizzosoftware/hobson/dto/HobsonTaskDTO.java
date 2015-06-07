/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.HobsonInvalidRequestException;
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

        if (json.has("conditionSet")) {
            this.conditionSet = new PropertyContainerSetDTO(json.getJSONObject("conditionSet"), new PropertyContainerMappingContext() {
                @Override
                public String getPrimaryContainerName() {
                    return "trigger";
                }

                @Override
                public String getContainersName() {
                    return "conditions";
                }
            });
        }

        if (json.has("actionSet")) {
            this.actionSet = new PropertyContainerSetDTO(json.getJSONObject("actionSet"), new PropertyContainerMappingContext() {
                @Override
                public String getPrimaryContainerName() {
                    return null;
                }

                @Override
                public String getContainersName() {
                    return "actions";
                }
            });
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
            json.put("actionSet", actionSet.toJSON(links));
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
