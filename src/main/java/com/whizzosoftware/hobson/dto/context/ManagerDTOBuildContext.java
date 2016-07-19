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
import com.whizzosoftware.hobson.api.device.DevicePassport;
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
import com.whizzosoftware.hobson.api.data.TelemetryManager;
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
    TelemetryManager telemetryManager;
    IdProvider idProvider;

    @Override
    public HobsonHub getHub(HubContext hctx) {
        return (hubManager != null) ? hubManager.getHub(hctx) : null;
    }

    public PropertyContainer getHubConfiguration(HubContext hctx) {
        return (hubManager != null) ? hubManager.getConfiguration(hctx) : null;
    }

    public PropertyContainer getDeviceConfiguration(DeviceContext dctx) {
        return (deviceManager != null) ? deviceManager.getDeviceConfiguration(dctx) : null;
    }

    public Collection<HobsonDevice> getAllDevices(HubContext hctx) {
        return (deviceManager != null) ? deviceManager.getAllDevices(hctx) : null;
    }

    public Long getDeviceLastCheckIn(DeviceContext dctx) {
        return (deviceManager != null) ? deviceManager.getDeviceLastCheckIn(dctx) : null;
    }

    @Override
    public Collection<DevicePassport> getDevicePassports(HubContext hctx) {
        return (deviceManager != null) ? deviceManager.getDevicePassports(hctx) : null;
    }

    public Collection<HobsonVariable> getGlobalVariables(HubContext hctx) {
        return (variableManager != null) ? variableManager.getGlobalVariables(hctx) : null;
    }

    public Collection<HobsonVariable> getDeviceVariables(DeviceContext dctx) {
        return (variableManager != null) ? variableManager.getDeviceVariables(dctx) : null;
    }

    public HobsonVariable getDeviceVariable(DeviceContext dctx, String name) {
        return (variableManager != null) ? variableManager.getVariable(VariableContext.create(dctx, name)) : null;
    }

    public Collection<TaskActionClass> getAllTaskActionClasses(HubContext hctx) {
        return (taskManager != null) ? taskManager.getAllActionClasses(hctx, false) : null;
    }

    public TaskActionClass getTaskActionClass(PropertyContainerClassContext ctx) {
        return (taskManager != null) ? taskManager.getActionClass(ctx) : null;
    }

    public Collection<TaskConditionClass> getAllTaskConditionClasses(HubContext hctx) {
        return (taskManager != null) ? taskManager.getAllConditionClasses(hctx, null, false) : null;
    }

    public TaskConditionClass getTaskConditionClass(PropertyContainerClassContext ctx) {
        return (taskManager != null) ? taskManager.getConditionClass(ctx) : null;
    }

    public Collection<HobsonTask> getAllTasks(HubContext hctx) {
        return (taskManager != null) ? taskManager.getAllTasks(hctx) : null;
    }

    public PropertyContainer getLocalPluginConfiguration(PluginContext pctx) {
        return (pluginManager != null) ? pluginManager.getLocalPluginConfiguration(pctx) : null;
    }

    public Collection<PluginDescriptor> getLocalPluginDescriptors(HubContext hctx) {
        return (pluginManager != null) ? pluginManager.getLocalPluginDescriptors(hctx) : null;
    }

    public Collection<PluginDescriptor> getRemotePluginDescriptors(HubContext hctx) {
        return (pluginManager != null) ? pluginManager.getRemotePluginDescriptors(hctx) : null;
    }

    public Collection<PresenceEntity> getAllPresenceEntities(HubContext hctx) {
        return (presenceManager != null) ? presenceManager.getAllPresenceEntities(hctx) : null;
    }

    public Collection<PresenceLocation> getAllPresenceLocations(HubContext hctx) {
        return (presenceManager != null) ? presenceManager.getAllPresenceLocations(hctx) : null;
    }

    @Override
    public boolean hasTelemetryManager(HubContext hctx) {
        return (telemetryManager != null);
    }

    public PresenceLocation getPresenceEntityLocation(PresenceEntityContext pctx) {
        return (presenceManager != null) ? presenceManager.getPresenceEntityLocation(pctx) : null;
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

    @Override
    public boolean isDeviceAvailable(DeviceContext dctx) {
        return (deviceManager != null && deviceManager.isDeviceAvailable(dctx));
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

        public Builder telemetryManager(TelemetryManager val) {
            ctx.telemetryManager = val;
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
