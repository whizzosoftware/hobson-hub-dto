/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.task;

import com.whizzosoftware.hobson.api.HobsonInvalidRequestException;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.property.*;
import com.whizzosoftware.hobson.api.task.HobsonTask;
import com.whizzosoftware.hobson.api.task.TaskManager;
import com.whizzosoftware.hobson.dto.ExpansionFields;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerMappingContext;
import com.whizzosoftware.hobson.dto.property.PropertyContainerSetDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HobsonTaskDTO extends ThingDTO {
    private List<PropertyContainerDTO> conditions;
    private PropertyContainerSetDTO actionSet;
    private Map<String,Object> properties;

    private HobsonTaskDTO(String id) {
        super(id);
    }

    private HobsonTaskDTO(JSONObject json) {
        if (json.has(JSONAttributes.NAME)) {
            setName(json.getString(JSONAttributes.NAME));
        }

        if (json.has(JSONAttributes.DESCRIPTION)) {
            setDescription(json.getString(JSONAttributes.DESCRIPTION));
        }

        if (json.has(JSONAttributes.CONDITIONS)) {
            JSONArray jca = json.getJSONArray(JSONAttributes.CONDITIONS);
            this.conditions = new ArrayList<>();
            for (int i=0; i < jca.length(); i++) {
                this.conditions.add(new PropertyContainerDTO.Builder(jca.getJSONObject(i)).build());
            }
        }

        if (json.has(JSONAttributes.ACTION_SET)) {
            this.actionSet = new PropertyContainerSetDTO.Builder(json.getJSONObject(JSONAttributes.ACTION_SET), new PropertyContainerMappingContext() {
                @Override
                public String getContainersName() {
                    return JSONAttributes.ACTIONS;
                }
            }).build();
        }
    }

    public List<PropertyContainerDTO> getConditions() {
        return conditions;
    }

    public PropertyContainerSetDTO getActionSet() {
        return actionSet;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public String getMediaType() {
        return MediaTypes.TASK;
    }

    public void validate() {
        if (getName() == null) {
            throw new HobsonInvalidRequestException("Name is required");
        }
        if (conditions == null) {
            throw new HobsonInvalidRequestException("Conditions are required");
        }
        if (actionSet == null) {
            throw new HobsonInvalidRequestException("Action set is required");
        }
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();

        if (conditions != null) {
            JSONArray ja = new JSONArray();
            for (PropertyContainerDTO dto : conditions) {
                ja.put(dto.toJSON());
            }
            json.put(JSONAttributes.CONDITIONS, ja);
        }

        if (actionSet != null) {
            json.put(JSONAttributes.ACTION_SET, actionSet.toJSON());
        }

        if (properties != null) {
            json.put(JSONAttributes.PROPERTIES, properties);
        }

        return json;
    }

    static public class Builder {
        HobsonTaskDTO dto;

        public Builder(String id) {
            dto = new HobsonTaskDTO(id);
        }

        public Builder(HobsonTask task, final TaskManager taskManager, boolean showDetails, ExpansionFields expansions, IdProvider idProvider) {
            dto = new HobsonTaskDTO(idProvider.createTaskId(task.getContext()));

            if (showDetails) {
                dto.setName(task.getName());
                dto.setDescription(task.getDescription());

                if (task.hasConditions()) {
                    dto.conditions = new ArrayList<>();
                    boolean showConditionDetails = expansions.has(JSONAttributes.CONDITIONS);
                    expansions.pushContext(JSONAttributes.CONDITIONS);
                    for (PropertyContainer pc : task.getConditions()) {
                        dto.conditions.add(new PropertyContainerDTO.Builder(
                                pc,
                                new PropertyContainerClassProvider() {
                                    @Override
                                    public PropertyContainerClass getPropertyContainerClass(PropertyContainerClassContext ctx) {
                                        return taskManager.getConditionClass(ctx);
                                    }
                                },
                                PropertyContainerClassType.CONDITION,
                                showConditionDetails,
                                expansions,
                                idProvider
                        ).build());
                    }
                    expansions.popContext();
                }

                if (task.getActionSet() != null) {
                    expansions.pushContext(JSONAttributes.ACTION_SET);
                    dto.actionSet = new PropertyContainerSetDTO.Builder(
                            task.getActionSet(),
                            task.getContext().getHubContext(),
                            PropertyContainerClassType.ACTION,
                            expansions != null && expansions.has(JSONAttributes.ACTION_SET),
                            new PropertyContainerClassProvider() {
                                @Override
                                public PropertyContainerClass getPropertyContainerClass(PropertyContainerClassContext ctx) {
                                    return taskManager.getActionClass(ctx);
                                }
                            },
                            expansions,
                            idProvider
                    ).build();
                    expansions.popContext();
                }

                dto.properties = task.getProperties();
            }
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

        public Builder conditions(List<PropertyContainerDTO> conditions) {
            dto.conditions = conditions;
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
