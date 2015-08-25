/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.task;

import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import org.json.JSONObject;

public class TaskConditionClassDTO extends PropertyContainerClassDTO {
    private String type;

    private TaskConditionClassDTO(String id) {
        super(id);
    }

    private TaskConditionClassDTO(JSONObject json) {
        super(json);
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put("type", type);
        return json;
    }

    public static class Builder extends PropertyContainerClassDTO.Builder {
        private TaskConditionClassDTO dto;

        public Builder(String id) {
            super(id);
            dto = new TaskConditionClassDTO(id);
        }

        public Builder(JSONObject json) {
            super(json);
            dto = new TaskConditionClassDTO(json);
        }

        public Builder type(String type) {
            dto.type = type;
            return this;
        }

        protected PropertyContainerClassDTO getDto() {
            return dto;
        }
    }
}
