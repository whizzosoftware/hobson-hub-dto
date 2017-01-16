/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.context;

import com.whizzosoftware.hobson.api.action.ActionClass;
import com.whizzosoftware.hobson.api.action.ActionManager;
import com.whizzosoftware.hobson.api.data.DataStream;
import com.whizzosoftware.hobson.api.device.*;
import com.whizzosoftware.hobson.api.hub.HobsonHub;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.hub.HubManager;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.plugin.HobsonLocalPluginDescriptor;
import com.whizzosoftware.hobson.api.plugin.HobsonPluginDescriptor;
import com.whizzosoftware.hobson.api.plugin.PluginContext;
import com.whizzosoftware.hobson.api.plugin.PluginManager;
import com.whizzosoftware.hobson.api.presence.PresenceEntity;
import com.whizzosoftware.hobson.api.presence.PresenceEntityContext;
import com.whizzosoftware.hobson.api.presence.PresenceLocation;
import com.whizzosoftware.hobson.api.presence.PresenceManager;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.PropertyContainerClass;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassContext;
import com.whizzosoftware.hobson.api.task.HobsonTask;
import com.whizzosoftware.hobson.api.task.TaskManager;
import com.whizzosoftware.hobson.api.task.condition.TaskConditionClass;
import com.whizzosoftware.hobson.api.data.DataStreamManager;
import com.whizzosoftware.hobson.api.variable.*;
import com.whizzosoftware.hobson.dto.ExpansionFields;

import java.io.IOException;
import java.util.*;

/**
 * A DTOBuildContext implementation that uses Hobson manager objects to obtain data.
 *
 * @author Dan Noguerol
 */
public class ManagerDTOBuildContext implements DTOBuildContext {
    ActionManager actionManager;
    DeviceManager deviceManager;
    HubManager hubManager;
    TaskManager taskManager;
    PluginManager pluginManager;
    PresenceManager presenceManager;
    ExpansionFields expansionFields;
    DataStreamManager dataStreamManager;
    IdProvider idProvider;
    String requestDomain;
    List<String> idTemplates = new ArrayList<>();

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

    @Override
    public PropertyContainerClass getDeviceConfigurationClass(DeviceContext dctx) {
        return (deviceManager != null) ? deviceManager.getDevice(dctx).getConfigurationClass() : null;
    }

    @Override
    public HobsonDeviceDescriptor getDevice(DeviceContext dctx) {
        return (deviceManager != null) ? deviceManager.getDevice(dctx) : null;
    }

    public Collection<HobsonDeviceDescriptor> getAllDevices(HubContext hctx) {
        return (deviceManager != null) ? deviceManager.getDevices(hctx) : null;
    }

    public Long getDeviceLastCheckIn(DeviceContext dctx) {
        return (deviceManager != null) ? deviceManager.getDeviceLastCheckin(dctx) : null;
    }

    @Override
    public DeviceVariableState getDeviceVariableState(DeviceVariableContext vctx) {
        return (deviceManager != null) ? deviceManager.getDeviceVariable(vctx) : null;
    }

    public DeviceVariableDescriptor getDeviceVariable(DeviceVariableContext ctx) {
        return (deviceManager != null) ? deviceManager.getDevice(ctx.getDeviceContext()).getVariable(ctx.getName()) : null;
    }

    public Collection<DeviceVariableDescriptor> getDeviceVariables(DeviceContext dctx) {
        return (deviceManager != null) ? deviceManager.getDevice(dctx).getVariables() : null;
    }

    @Override
    public boolean hasExpansionFields() {
        return (expansionFields != null);
    }

    public Collection<ActionClass> getAllActionClasses(HubContext hctx) {
        return (actionManager != null) ? actionManager.getActionClasses(hctx, false) : null;
    }

    public ActionClass getActionClass(PropertyContainerClassContext ctx) {
        return (actionManager != null) ? actionManager.getActionClass(ctx) : null;
    }

    public Collection<TaskConditionClass> getAllTaskConditionClasses(HubContext hctx) {
        return (taskManager != null) ? taskManager.getConditionClasses(hctx, null, false) : null;
    }

    public TaskConditionClass getTaskConditionClass(PropertyContainerClassContext ctx) {
        return (taskManager != null) ? taskManager.getConditionClass(ctx) : null;
    }

    public Collection<HobsonTask> getAllTasks(HubContext hctx) {
        return (taskManager != null) ? taskManager.getTasks(hctx) : null;
    }

    public PropertyContainer getLocalPluginConfiguration(PluginContext pctx) {
        return (pluginManager != null) ? pluginManager.getLocalPluginConfiguration(pctx) : null;
    }

    public Collection<HobsonLocalPluginDescriptor> getLocalPluginDescriptors(HubContext hctx) {
        return (pluginManager != null) ? pluginManager.getLocalPlugins(hctx) : null;
    }

    public Collection<HobsonPluginDescriptor> getRemotePluginDescriptors(HubContext hctx) {
        return (pluginManager != null) ? pluginManager.getRemotePlugins(hctx) : null;
    }

    public Collection<PresenceEntity> getAllPresenceEntities(HubContext hctx) {
        return (presenceManager != null) ? presenceManager.getAllPresenceEntities(hctx) : null;
    }

    public Collection<PresenceLocation> getAllPresenceLocations(HubContext hctx) {
        return (presenceManager != null) ? presenceManager.getAllPresenceLocations(hctx) : null;
    }

    @Override
    public Collection<DataStream> getDataStreams(HubContext hctx) {
        return (dataStreamManager != null) ? dataStreamManager.getDataStreams(hctx) : null;
    }

    @Override
    public boolean hasDataStreamManager(HubContext hctx) {
        return (dataStreamManager != null && !dataStreamManager.isStub());
    }

    @Override
    public String addIdTemplate(String template) {
        int ix = -1;
        if (template != null) {
            ix = idTemplates.indexOf(template);
            if (ix == -1) {
                ix = idTemplates.size();
                idTemplates.add(template);
            }
        }
        return Integer.toString(ix);
    }

    @Override
    public Map<String,String> getIdTemplateMap() {
        Map<String,String> results = new HashMap<>();
        for (int i=0; i < idTemplates.size(); i++) {
            results.put(Integer.toString(i), idTemplates.get(i));
        }
        return results;
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

    @Override
    public GlobalVariable getGlobalVariable(GlobalVariableContext gvctx) {
        return null;
    }

    @Override
    public Collection<GlobalVariable> getGlobalVariables(HubContext hctx) {
        return null;
    }

    @Override
    public IdProvider getIdProvider() {
        return idProvider;
    }

    @Override
    public String createURI(String protocol, int port, String path) throws IOException {
        String domain = requestDomain;
        if (domain == null) {
            domain = hubManager.getLocalManager().getNetworkInfo().getInetAddress().getHostName();
        }
        return protocol + "://" + domain + ":" + port + (path != null ? path : "");
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

        public Builder actionManager(ActionManager val) {
            ctx.actionManager = val;
            return this;
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

        public Builder dataStreamManager(DataStreamManager val) {
            ctx.dataStreamManager = val;
            return this;
        }

        public Builder requestDomain(String val) {
            ctx.requestDomain = val;
            return this;
        }

        public DTOBuildContext build() {
            return ctx;
        }
    }
}
