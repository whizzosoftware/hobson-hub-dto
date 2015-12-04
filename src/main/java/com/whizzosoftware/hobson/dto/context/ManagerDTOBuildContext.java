/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.context;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.device.DeviceManager;
import com.whizzosoftware.hobson.api.device.HobsonDevice;
import com.whizzosoftware.hobson.api.hub.HobsonHub;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.hub.HubManager;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.plugin.PluginContext;
import com.whizzosoftware.hobson.api.plugin.PluginDescriptor;
import com.whizzosoftware.hobson.api.plugin.PluginManager;
import com.whizzosoftware.hobson.api.presence.PresenceEntity;
import com.whizzosoftware.hobson.api.presence.PresenceEntityContext;
import com.whizzosoftware.hobson.api.presence.PresenceLocation;
import com.whizzosoftware.hobson.api.presence.PresenceManager;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassContext;
import com.whizzosoftware.hobson.api.task.HobsonTask;
import com.whizzosoftware.hobson.api.task.TaskManager;
import com.whizzosoftware.hobson.api.task.action.TaskActionClass;
import com.whizzosoftware.hobson.api.task.condition.TaskConditionClass;
import com.whizzosoftware.hobson.api.variable.*;
import com.whizzosoftware.hobson.dto.ExpansionFields;

import java.util.*;

/**
 * A DTOBuildContext implementation that uses Hobson manager objects to obtain data.
 *
 * @author Dan Noguerol
 */
public class ManagerDTOBuildContext implements DTOBuildContext {
    DeviceManager deviceManager;
    HubManager hubManager;
    TaskManager taskManager;
    PluginManager pluginManager;
    PresenceManager presenceManager;
    VariableManager variableManager;
    ExpansionFields expansionFields;
    IdProvider idProvider;

    public Collection<HobsonHub> getHubs(String userId) {
        return hubManager.getHubs(userId);
    }

    public PropertyContainer getHubConfiguration(HubContext hctx) {
        return hubManager.getConfiguration(hctx);
    }

    public PropertyContainer getDeviceConfiguration(DeviceContext dctx) {
        return deviceManager.getDeviceConfiguration(dctx);
    }

    public Collection<HobsonDevice> getAllDevices(HubContext hctx) {
        return deviceManager.getAllDevices(hctx);
    }

    public Long getDeviceLastCheckIn(DeviceContext dctx) {
        return deviceManager.getDeviceLastCheckIn(dctx);
    }

    public Collection<HobsonVariable> getGlobalVariables(HubContext hctx) {
        return variableManager.getGlobalVariables(hctx);
    }

    public HobsonVariableCollection getDeviceVariables(DeviceContext dctx) {
        return variableManager.getDeviceVariables(dctx);
    }

    public HobsonVariable getDeviceVariable(DeviceContext dctx, String name) {
        return variableManager.getDeviceVariable(dctx, name);
    }

    public Collection<TaskActionClass> getAllTaskActionClasses(HubContext hctx) {
        return taskManager.getAllActionClasses(hctx, false);
    }

    public TaskActionClass getTaskActionClass(PropertyContainerClassContext ctx) {
        return taskManager.getActionClass(ctx);
    }

    public Collection<TaskConditionClass> getAllTaskConditionClasses(HubContext hctx) {
        return taskManager.getAllConditionClasses(hctx, null, false);
    }

    public TaskConditionClass getTaskConditionClass(PropertyContainerClassContext ctx) {
        return taskManager.getConditionClass(ctx);
    }

    public Collection<HobsonTask> getAllTasks(HubContext hctx) {
        return taskManager.getAllTasks(hctx);
    }

    public PropertyContainer getLocalPluginConfiguration(PluginContext pctx) {
        return pluginManager.getLocalPluginConfiguration(pctx);
    }

    public Collection<PluginDescriptor> getLocalPluginDescriptors(HubContext hctx) {
        return pluginManager.getLocalPluginDescriptors(hctx);
    }

    public Collection<PluginDescriptor> getRemotePluginDescriptors(HubContext hctx) {
        return pluginManager.getRemotePluginDescriptors(hctx);
    }

    public Collection<PresenceEntity> getAllPresenceEntities(HubContext hctx) {
        return presenceManager.getAllPresenceEntities(hctx);
    }

    public Collection<PresenceLocation> getAllPresenceLocations(HubContext hctx) {
        return presenceManager.getAllPresenceLocations(hctx);
    }

    public PresenceLocation getPresenceEntityLocation(PresenceEntityContext pctx) {
        return presenceManager.getPresenceEntityLocation(pctx);
    }

    public ExpansionFields getExpansionFields() {
        if (expansionFields == null) {
            expansionFields = new ExpansionFields(null);
        }
        return expansionFields;
    }

    public IdProvider getIdProvider() {
        return idProvider;
    }

    public static class Builder {
        ManagerDTOBuildContext ctx;

        public Builder() {
            ctx = new ManagerDTOBuildContext();
        }

        public Builder deviceManager(DeviceManager val) {
            ctx.deviceManager = val;
            return this;
        }

        public Builder expansionFields(ExpansionFields val) {
            ctx.expansionFields = val;
            return this;
        }

        public Builder hubManager(HubManager val) {
            ctx.hubManager = val;
            return this;
        }

        public Builder idProvider(IdProvider val) {
            ctx.idProvider = val;
            return this;
        }

        public Builder taskManager(TaskManager val) {
            ctx.taskManager = val;
            return this;
        }

        public Builder pluginManager(PluginManager val) {
            ctx.pluginManager = val;
            return this;
        }

        public Builder presenceManager(PresenceManager val) {
            ctx.presenceManager = val;
            return this;
        }

        public Builder variableManager(VariableManager val) {
            ctx.variableManager = val;
            return this;
        }

        public DTOBuildContext build() {
            return ctx;
        }
    }
}
