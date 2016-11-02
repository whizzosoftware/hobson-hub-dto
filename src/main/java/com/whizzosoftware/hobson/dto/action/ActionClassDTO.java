/*
 *******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.action;

import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.TypedPropertyDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

import java.util.List;

public class ActionClassDTO extends PropertyContainerClassDTO {
    private String name;
    private String descriptionTemplate;
    private boolean taskAction;

    private ActionClassDTO(String id) {
        super(id);
    }

    private ActionClassDTO(JSONObject json) {
        super(json);
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.NAME, name);
        json.put(JSONAttributes.DESCRIPTION_TEMPLATE, descriptionTemplate);
        json.put(JSONAttributes.TASK_ACTION, taskAction);
        return json;
    }

    public static class Builder extends PropertyContainerClassDTO.Builder {
        private ActionClassDTO dto;

        public Builder(String id, List<TypedPropertyDTO> props) {
            super(id);
            dto = new ActionClassDTO(id);
            supportedProperties(props);
        }

        public Builder(JSONObject json) {
            super(json);
            dto = new ActionClassDTO(json);
        }

        public ActionClassDTO.Builder name(String name) {
            dto.name = name;
            return this;
        }

        public ActionClassDTO.Builder descriptionTemplate(String descriptionTemplate) {
            dto.descriptionTemplate = descriptionTemplate;
            return this;
        }

        public ActionClassDTO.Builder taskAction(boolean taskAction) {
            dto.taskAction = taskAction;
            return this;
        }

        protected PropertyContainerClassDTO getDto() {
            return dto;
        }
    }
}
