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
import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.dto.context.TemplatedIdBuildContext;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class ActionClassDTO extends PropertyContainerClassDTO {
    private String name;
    private String descriptionTemplate;
    private Boolean taskAction;

    protected ActionClassDTO(TemplatedIdBuildContext ctx, TemplatedId id) {
        super(ctx, id);
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
        public Builder(TemplatedIdBuildContext ctx, TemplatedId id, ActionClass actionClass, boolean showDetails) {
            super(ctx, id, actionClass, showDetails);
            if (showDetails) {
                name(actionClass.getName());
                descriptionTemplate(actionClass.getDescription());
                taskAction(actionClass.isTaskAction());
            }
        }

        public Builder(JSONObject json) {
            super(json);
            name(json.getString(JSONAttributes.NAME));
            descriptionTemplate(json.getString(JSONAttributes.DESCRIPTION_TEMPLATE));
            taskAction(json.getBoolean(JSONAttributes.DESCRIPTION_TEMPLATE));
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

        protected PropertyContainerClassDTO createDTO(TemplatedIdBuildContext ctx, TemplatedId id) {
            return new ActionClassDTO(ctx, id);
        }
    }
}
