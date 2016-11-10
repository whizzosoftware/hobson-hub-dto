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

import com.whizzosoftware.hobson.api.action.ActionClass;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class ActionClassDTO extends PropertyContainerClassDTO {
    private String name;
    private String descriptionTemplate;
    private boolean statusProvider;
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
        json.put(JSONAttributes.STATUS_PROVIDER, statusProvider);
        return json;
    }

    public static class Builder extends PropertyContainerClassDTO.Builder {
        public Builder(String id, ActionClass actionClass, boolean showDetails) {
            super(id, actionClass, showDetails);
            name(actionClass.getName());
            descriptionTemplate(actionClass.getDescription());
            taskAction(actionClass.isTaskAction());
            statusProvider(actionClass.isStatusProvider());
        }

        public Builder(JSONObject json) {
            super(json);
            name(json.getString(JSONAttributes.NAME));
            descriptionTemplate(json.getString(JSONAttributes.DESCRIPTION_TEMPLATE));
            taskAction(json.getBoolean(JSONAttributes.DESCRIPTION_TEMPLATE));
            statusProvider(json.getBoolean(JSONAttributes.DESCRIPTION_TEMPLATE));
        }

        public ActionClassDTO.Builder name(String name) {
            ((ActionClassDTO)dto).name = name;
            return this;
        }

        public ActionClassDTO.Builder descriptionTemplate(String descriptionTemplate) {
            ((ActionClassDTO)dto).descriptionTemplate = descriptionTemplate;
            return this;
        }

        public ActionClassDTO.Builder taskAction(boolean taskAction) {
            ((ActionClassDTO)dto).taskAction = taskAction;
            return this;
        }

        public ActionClassDTO.Builder statusProvider(boolean statusProvider) {
            ((ActionClassDTO)dto).statusProvider = statusProvider;
            return this;
        }

        protected PropertyContainerClassDTO createDTO(String id) {
            return new ActionClassDTO(id);
        }
    }
}
