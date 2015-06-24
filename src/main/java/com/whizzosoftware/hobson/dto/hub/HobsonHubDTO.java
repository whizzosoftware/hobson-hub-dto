/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.hub;

import com.whizzosoftware.hobson.dto.ItemListDTO;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import com.whizzosoftware.hobson.dto.ThingDTO;
import org.json.JSONObject;

public class HobsonHubDTO extends ThingDTO {
    private ItemListDTO actionClasses;
    private ItemListDTO conditionClasses;
    private PropertyContainerClassDTO configurationClass;
    private PropertyContainerDTO configuration;
    private ItemListDTO devices;
    private ItemListDTO localPlugins;
    private HubLogDTO log;
    private ItemListDTO remotePlugins;
    private ItemListDTO tasks;
    private String version;

    private HobsonHubDTO(String id) {
        super(id);
    }

    private HobsonHubDTO(JSONObject json) {

    }

    @Override
    public String getMediaType() {
        return MediaTypes.HUB;
    }

    public ItemListDTO getActionClasses() {
        return actionClasses;
    }

    public PropertyContainerDTO getConfiguration() {
        return configuration;
    }

    public PropertyContainerClassDTO getConfigurationClass() {
        return configurationClass;
    }

    public ItemListDTO getConditionClasses() {
        return conditionClasses;
    }

    public ItemListDTO getDevices() {
        return devices;
    }

    public ItemListDTO getLocalPlugins() {
        return localPlugins;
    }

    public HubLogDTO getLog() {
        return log;
    }

    public ItemListDTO getRemotePlugins() {
        return remotePlugins;
    }

    public ItemListDTO getTasks() {
        return tasks;
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (actionClasses != null) {
            json.put("actionClasses", actionClasses.toJSON());
        }
        if (conditionClasses != null) {
            json.put("conditionClasses", conditionClasses.toJSON());
        }
        if (configurationClass != null) {
            json.put("configurationClass", configurationClass.toJSON());
        }
        if (configuration != null) {
            json.put("configuration", configuration.toJSON());
        }
        if (devices != null) {
            json.put("devices", devices.toJSON());
        }
        if (log != null) {
            json.put("log", log.toJSON());
        }
        if (localPlugins != null) {
            json.put("localPlugins", localPlugins.toJSON());
        }
        if (remotePlugins != null) {
            json.put("remotePlugins", remotePlugins.toJSON());
        }
        if (tasks != null) {
            json.put("tasks", tasks.toJSON());
        }
        if (version != null) {
            json.put("version", version);
        }
        return json;
    }

    public static class Builder {
        private HobsonHubDTO dto;

        public Builder(String id) {
            dto = new HobsonHubDTO(id);
        }

        public Builder(JSONObject json) {
            dto = new HobsonHubDTO(json);
        }

        public Builder actionClasses(ItemListDTO actionClasses) {
            dto.actionClasses = actionClasses;
            return this;
        }

        public Builder conditionClasses(ItemListDTO conditionClasses) {
            dto.conditionClasses = conditionClasses;
            return this;
        }

        public Builder configurationClass(PropertyContainerClassDTO configurationClass) {
            dto.configurationClass = configurationClass;
            return this;
        }

        public Builder configuration(PropertyContainerDTO configuration) {
            dto.configuration = configuration;
            return this;
        }

        public Builder devices(ItemListDTO devices) {
            dto.devices = devices;
            return this;
        }

        public Builder localPlugins(ItemListDTO localPlugins) {
            dto.localPlugins = localPlugins;
            return this;
        }

        public Builder log(HubLogDTO log) {
            dto.log = log;
            return this;
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder remotePlugins(ItemListDTO remotePlugins) {
            dto.remotePlugins = remotePlugins;
            return this;
        }

        public Builder tasks(ItemListDTO tasks) {
            dto.tasks = tasks;
            return this;
        }

        public Builder version(String version) {
            dto.version = version;
            return this;
        }

        public HobsonHubDTO build() {
            return dto;
        }
    }
}
