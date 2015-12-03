/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.plugin;

import com.whizzosoftware.hobson.api.plugin.*;
import com.whizzosoftware.hobson.api.property.PropertyContainerClass;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassContext;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassProvider;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassType;
import com.whizzosoftware.hobson.dto.*;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.image.ImageDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class HobsonPluginDTO extends ThingDTO {
    private String pluginId;
    private String version;
    private PluginType type;
    private PluginStatus status;
    private Boolean configurable;
    private PropertyContainerClassDTO configurationClass;
    private PropertyContainerDTO configuration;
    private ImageDTO image;

    private HobsonPluginDTO(String id) {
        super(id, null);
    }

    @Override
    public String getMediaType() {
        return MediaTypes.PLUGIN;
    }

    public String getPluginId() {
        return pluginId;
    }

    public String getVersion() {
        return version;
    }

    public PluginType getType() {
        return type;
    }

    public PluginStatus getStatus() {
        return status;
    }

    public Boolean getConfigurable() {
        return configurable;
    }

    public PropertyContainerClassDTO getConfigurationClass() {
        return configurationClass;
    }

    public PropertyContainerDTO getConfiguration() {
        return configuration;
    }

    public ImageDTO getImage() {
        return image;
    }

    public Boolean isConfigurable() {
        return configurable;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.PLUGIN_ID, pluginId);
        json.put(JSONAttributes.VERSION, version != null ? version : null);
        json.put(JSONAttributes.TYPE, type != null ? type.toString() : null);
        json.put(JSONAttributes.CONFIGURABLE, configurable != null ? configurable : null);
        json.put(JSONAttributes.CCLASS, configurationClass != null ? configurationClass.toJSON() : null);
        json.put(JSONAttributes.CONFIGURATION, configuration != null ? configuration.toJSON() : null);
        json.put(JSONAttributes.IMAGE, image != null ? image.toJSON() : null);
        if (status != null) {
            JSONObject psjson = new JSONObject();
            psjson.put(JSONAttributes.CODE, status.getCode().toString());
            psjson.put(JSONAttributes.MESSAGE, status.getMessage());
            json.put(JSONAttributes.STATUS, psjson);
        }
        return json;
    }

    public static class Builder {
        private HobsonPluginDTO dto;

        public Builder(String id) {
            dto = new HobsonPluginDTO(id);
        }

        public Builder(DTOBuildContext ctx, final HobsonPlugin plugin, String description, String remoteVersion, boolean showDetails) {
            dto = new HobsonPluginDTO(remoteVersion != null ? ctx.getIdProvider().createRemotePluginId(plugin.getContext(), remoteVersion) : ctx.getIdProvider().createLocalPluginId(plugin.getContext()));
            ExpansionFields expansions = ctx.getExpansionFields();
            if (showDetails) {
                dto.setName(plugin.getName());
                dto.setDescription(description);
                dto.pluginId = plugin.getContext().getPluginId();
                dto.type = plugin.getType();
                dto.version = plugin.getVersion();
                dto.status = plugin.getStatus();
                dto.configurable = plugin.isConfigurable();
                if (dto.configurable) {
                    dto.configurationClass = new PropertyContainerClassDTO.Builder(ctx.getIdProvider().createLocalPluginConfigurationClassId(plugin.getContext()), plugin.getConfigurationClass(), expansions != null && expansions.has(JSONAttributes.CCLASS)).build();
                    dto.configuration = new PropertyContainerDTO.Builder(
                            ctx.getLocalPluginConfiguration(plugin.getContext()),
                            new PropertyContainerClassProvider() {
                                @Override
                                public PropertyContainerClass getPropertyContainerClass(PropertyContainerClassContext ctx) {
                                    return plugin.getConfigurationClass();
                                }
                            },
                            PropertyContainerClassType.PLUGIN_CONFIG,
                            expansions != null && expansions.has(JSONAttributes.CONFIGURATION),
                            expansions,
                            ctx.getIdProvider()
                    ).build();
                }
                if (remoteVersion == null) {
                    dto.image = new ImageDTO.Builder(ctx.getIdProvider().createLocalPluginIconId(plugin.getContext())).build();
                }

            }
        }

        public Builder pluginId(String pluginId) {
            dto.pluginId = pluginId;
            return this;
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder description(String description) {
            dto.setDescription(description);
            return this;
        }

        public Builder version(String version) {
            dto.version = version;
            return this;
        }

        public Builder type(PluginType type) {
            dto.type = type;
            return this;
        }

        public Builder status(PluginStatus status) {
            dto.status = status;
            return this;
        }

        public Builder configurable(boolean configurable) {
            dto.configurable = configurable;
            return this;
        }

        public Builder configuration(PropertyContainerDTO configuration) {
            dto.configuration = configuration;
            return this;
        }

        public Builder configurationClass(PropertyContainerClassDTO configurationClass) {
            dto.configurationClass = configurationClass;
            return this;
        }

        public Builder image(ImageDTO image) {
            dto.image = image;
            return this;
        }

        public Builder addLink(String rel, String url) {
            dto.addLink(rel, url);
            return this;
        }

        public HobsonPluginDTO build() {
            return dto;
        }
    }
}
