/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONObject;

public class HobsonHubDTO extends ThingDTO {
    private ItemListDTO actionClasses;
    private ItemListDTO conditionClasses;
    private PropertyContainerClassDTO configurationClass;
    private PropertyContainerDTO configuration;
    private ItemListDTO devices;
    private HubLogDTO log;
    private ItemListDTO localPlugins;
    private ItemListDTO remotePlugins;
    private ItemListDTO tasks;
    private String version;

    public HobsonHubDTO(String id) {
        setId(id);
    }

    public HobsonHubDTO(String id, String name, String version) {
        super(id, name);
        this.version = version;
    }

    public PropertyContainerClassDTO getConfigurationClass() {
        return configurationClass;
    }

    public void setConfigurationClass(PropertyContainerClassDTO configurationClass) {
        this.configurationClass = configurationClass;
    }

    public ItemListDTO getLocalPlugins() {
        return localPlugins;
    }

    public void setLocalPlugins(ItemListDTO localPlugins) {
        this.localPlugins = localPlugins;
    }

    public ItemListDTO getRemotePlugins() {
        return remotePlugins;
    }

    public void setRemotePlugins(ItemListDTO remotePlugins) {
        this.remotePlugins = remotePlugins;
    }

    public ItemListDTO getDevices() {
        return devices;
    }

    public void setDevices(ItemListDTO devices) {
        this.devices = devices;
    }

    public ItemListDTO getConditionClasses() {
        return conditionClasses;
    }

    public void setConditionClasses(ItemListDTO conditionClasses) {
        this.conditionClasses = conditionClasses;
    }

    public ItemListDTO getActionClasses() {
        return actionClasses;
    }

    public void setActionClasses(ItemListDTO actionClasses) {
        this.actionClasses = actionClasses;
    }

    public PropertyContainerDTO getConfiguration() {
        return configuration;
    }

    public void setConfiguration(PropertyContainerDTO configuration) {
        this.configuration = configuration;
    }

    public ItemListDTO getTasks() {
        return tasks;
    }

    public void setTasks(ItemListDTO tasks) {
        this.tasks = tasks;
    }

    public String getMediaType() {
        return "application/vnd.hobson.v1.hub";
    }

    public HubLogDTO getLog() {
        return log;
    }

    public void setLog(HubLogDTO log) {
        this.log = log;
    }

    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = super.toJSON(links);
        if (actionClasses != null) {
            json.put("actionClasses", actionClasses.toJSON(links));
        }
        if (conditionClasses != null) {
            json.put("conditionClasses", conditionClasses.toJSON(links));
        }
        if (configurationClass != null) {
            json.put("configurationClass", configurationClass.toJSON(links));
        }
        if (configuration != null) {
            json.put("configuration", configuration.toJSON(links));
        }
        if (devices != null) {
            json.put("devices", devices.toJSON(links));
        }
        if (log != null) {
            json.put("log", log.toJSON(links));
        }
        if (localPlugins != null) {
            json.put("localPlugins", localPlugins.toJSON(links));
        }
        if (remotePlugins != null) {
            json.put("remotePlugins", remotePlugins.toJSON(links));
        }
        if (tasks != null) {
            json.put("tasks", tasks.toJSON(links));
        }
        if (version != null) {
            json.put("version", version);
        }
        return json;
    }
}
