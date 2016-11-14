/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.task;

import com.whizzosoftware.hobson.api.task.condition.TaskConditionClass;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class TaskConditionClassDTO extends PropertyContainerClassDTO {
    private String name;
    private String descriptionTemplate;
    private String type;

    private TaskConditionClassDTO(String id) {
        super(id);
    }

    private TaskConditionClassDTO(JSONObject json) {
        super(json);
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.NAME, name);
        json.put(JSONAttributes.DESCRIPTION_TEMPLATE, descriptionTemplate);
        json.put(JSONAttributes.TYPE, type);
        return json;
    }

    public static class Builder extends PropertyContainerClassDTO.Builder {
        public Builder(String id) {
            super(id);
        }

        public Builder(String id, TaskConditionClass tcc, boolean showDetails) {
            super(id, tcc, showDetails);
            name(tcc.getName());
            descriptionTemplate(tcc.getDescriptionTemplate());
            type(tcc.getConditionClassType().toString());
        }

        public Builder(JSONObject json) {
            super(json);
            name(json.getString(JSONAttributes.NAME));
            descriptionTemplate(json.getString(JSONAttributes.DESCRIPTION_TEMPLATE));
            type(json.getString(JSONAttributes.TYPE));
        }

        public TaskConditionClassDTO.Builder name(String name) {
            ((TaskConditionClassDTO)dto).name = name;
            return this;
        }

        public TaskConditionClassDTO.Builder descriptionTemplate(String descriptionTemplate) {
            ((TaskConditionClassDTO)dto).descriptionTemplate = descriptionTemplate;
            return this;
        }

        public Builder type(String type) {
            ((TaskConditionClassDTO)dto).type = type;
            return this;
        }

        protected PropertyContainerClassDTO createDTO(String id) {
            return new TaskConditionClassDTO(id);
        }
    }
}
