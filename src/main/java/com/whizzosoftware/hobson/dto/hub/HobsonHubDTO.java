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
import com.whizzosoftware.hobson.json.JSONAttributes;
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
    private ItemListDTO presenceEntities;
    private String version;
    private String apiKey;

    private HobsonHubDTO(String id) {
        super(id);
    }

    private HobsonHubDTO(JSONObject json) {
        super(json);
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

    public ItemListDTO getPresenceEntities() {
        return presenceEntities;
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (actionClasses != null) {
            json.put(JSONAttributes.ACTION_CLASSES, actionClasses.toJSON());
        }
        if (conditionClasses != null) {
            json.put(JSONAttributes.CONDITION_CLASSES, conditionClasses.toJSON());
        }
        if (configurationClass != null) {
            json.put(JSONAttributes.CONFIGURATION_CLASS, configurationClass.toJSON());
        }
        if (configuration != null) {
            json.put(JSONAttributes.CONFIGURATION, configuration.toJSON());
        }
        if (devices != null) {
            json.put(JSONAttributes.DEVICES, devices.toJSON());
        }
        if (log != null) {
            json.put(JSONAttributes.LOG, log.toJSON());
        }
        if (localPlugins != null) {
            json.put(JSONAttributes.LOCAL_PLUGINS, localPlugins.toJSON());
        }
        if (remotePlugins != null) {
            json.put(JSONAttributes.REMOTE_PLUGINS, remotePlugins.toJSON());
        }
        if (tasks != null) {
            json.put(JSONAttributes.TASKS, tasks.toJSON());
        }
        if (presenceEntities != null) {
            json.put(JSONAttributes.PRESENCE_ENTITIES, presenceEntities.toJSON());
        }
        if (version != null) {
            json.put(JSONAttributes.VERSION, version);
        }
        if (apiKey != null) {
            json.put(JSONAttributes.API_KEY, apiKey);
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

        public Builder presenceEntities(ItemListDTO presenceEntities) {
            dto.presenceEntities = presenceEntities;
            return this;
        }

        public Builder version(String version) {
            dto.version = version;
            return this;
        }

        public Builder apiKey(String apiKey) {
            dto.apiKey = apiKey;
            return this;
        }

        public Builder link(String rel, String url) {
            dto.addLink(rel, url);
            return this;
        }

        public HobsonHubDTO build() {
            return dto;
        }
    }
}
