/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.device.DeviceManager;
import com.whizzosoftware.hobson.api.hub.HubManager;
import com.whizzosoftware.hobson.api.plugin.PluginManager;
import com.whizzosoftware.hobson.api.presence.PresenceManager;
import com.whizzosoftware.hobson.api.task.TaskManager;
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.api.variable.VariableManager;
import com.whizzosoftware.hobson.api.variable.VariableProxyType;
import com.whizzosoftware.hobson.api.variable.VariableProxyValueProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * A context that is passed to DTO builders to obtain classes needed to perform the model to DTO mappings.
 *
 * @author Dan Noguerol
 */
public class DTOBuildContext {
    private DeviceManager deviceManager;
    private ExpansionFields expansionFields;
    private HubManager hubManager;
    private IdProvider idProvider;
    private TaskManager taskManager;
    private PluginManager pluginManager;
    private PresenceManager presenceManager;
    private VariableManager variableManager;
    private Map<VariableProxyType,VariableProxyValueProvider> proxyValueProviders;

    public DeviceManager getDeviceManager() {
        return deviceManager;
    }

    public ExpansionFields getExpansionFields() {
        if (expansionFields == null) {
            expansionFields = new ExpansionFields(null);
        }
        return expansionFields;
    }

    public HubManager getHubManager() {
        return hubManager;
    }

    public IdProvider getIdProvider() {
        return idProvider;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public PresenceManager getPresenceManager() {
        return presenceManager;
    }

    public VariableManager getVariableManager() {
        return variableManager;
    }

    public Object getProxyValue(HobsonVariable v) {
        if (v.hasProxyType() && proxyValueProviders != null) {
            VariableProxyValueProvider vpvp = proxyValueProviders.get(v.getProxyType());
            if (vpvp != null) {
                return vpvp.getProxyValue(v);
            }
        }
        return v.getValue();
    }

    public static final class Builder {
        DTOBuildContext ctx;

        public Builder() {
            ctx = new DTOBuildContext();
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

        public Builder addProxyValueProvider(VariableProxyValueProvider provider) {
            if (ctx.proxyValueProviders == null) {
                ctx.proxyValueProviders = new HashMap<>();
            }
            ctx.proxyValueProviders.put(provider.getProxyType(), provider);
            return this;
        }

        public DTOBuildContext build() {
            return ctx;
        }
    }
}
