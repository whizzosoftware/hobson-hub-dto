/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.hub.HobsonHub;
import com.whizzosoftware.hobson.api.hub.HubContext;
import org.json.JSONObject;

public class HobsonHubDTO extends ThingDTO {
    private String version;
    private PropertyContainerClassDTO configurationClass;
    private PropertyContainerDTO configuration;
    private ItemListDTO plugins;
    private ItemListDTO devices;
    private ItemListDTO conditionClasses;
    private ItemListDTO actionClasses;
    private ItemListDTO tasks;

    public HobsonHubDTO(HobsonHub hub, LinkProvider links) {
        setId(links.createHubLink(hub.getContext()));
        setName(hub.getName());
        this.version = hub.getVersion();
    }

    public HobsonHubDTO(HubContext context, String name, LinkProvider links) {
        setId(links.createHubLink(context));
        setName(name);
    }

    public PropertyContainerClassDTO getConfigurationClass() {
        return configurationClass;
    }

    public void setConfigurationClass(PropertyContainerClassDTO configurationClass) {
        this.configurationClass = configurationClass;
    }

    public ItemListDTO getPlugins() {
        return plugins;
    }

    public void setPlugins(ItemListDTO plugins) {
        this.plugins = plugins;
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

    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = super.toJSON(links);
        if (version != null) {
            json.put("version", version);
        }
        if (configurationClass != null) {
            json.put("configurationClass", configurationClass.toJSON(links));
        }
        if (configuration != null) {
            json.put("configuration", configuration.toJSON(links));
        }
        if (plugins != null) {
            json.put("plugins", plugins.toJSON(links));
        }
        if (devices != null) {
            json.put("devices", devices.toJSON(links));
        }
        if (conditionClasses != null) {
            json.put("conditionClasses", conditionClasses.toJSON(links));
        }
        if (actionClasses != null) {
            json.put("actionClasses", actionClasses.toJSON(links));
        }
        if (tasks != null) {
            json.put("tasks", tasks.toJSON(links));
        }
        return json;
    }
}
