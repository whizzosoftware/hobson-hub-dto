/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.plugin;

import com.whizzosoftware.hobson.api.action.ActionClass;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.api.plugin.*;
import com.whizzosoftware.hobson.api.property.PropertyContainerClass;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassContext;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassProvider;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassType;
import com.whizzosoftware.hobson.dto.*;
import com.whizzosoftware.hobson.dto.action.ActionClassDTO;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.context.TemplatedIdBuildContext;
import com.whizzosoftware.hobson.dto.image.ImageDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class HobsonPluginDTO extends ThingDTO {
    private ItemListDTO actionClasses;
    private String pluginId;
    private String version;
    private PluginType type;
    private PluginStatus status;
    private Boolean configurable;
    private PropertyContainerClassDTO configurationClass;
    private PropertyContainerDTO configuration;
    private ImageDTO image;

    private HobsonPluginDTO(TemplatedIdBuildContext ctx, TemplatedId id) {
        super(ctx, id);
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
        if (actionClasses != null) {
            json.put(JSONAttributes.ACTION_CLASSES, actionClasses.toJSON());
        }
        return json;
    }

    public static class Builder {
        private HobsonPluginDTO dto;

        public Builder(TemplatedIdBuildContext ctx, TemplatedId id) {
            dto = new HobsonPluginDTO(ctx, id);
        }

        public Builder(DTOBuildContext ctx, HubContext hubContext, final HobsonPluginDescriptor plugin, String description, String remoteVersion, boolean showDetails) {
            PluginContext pctx = PluginContext.create(hubContext, plugin.getId());
            dto = new HobsonPluginDTO(ctx, remoteVersion != null ? ctx.getIdProvider().createRemotePluginId(hubContext, plugin.getId(), remoteVersion) : ctx.getIdProvider().createLocalPluginId(pctx));
            IdProvider idProvider = ctx.getIdProvider();
            ExpansionFields expansions = ctx.getExpansionFields();
            if (showDetails) {
                dto.setName(plugin.getName());
                dto.setDescription(description);
                dto.pluginId = plugin.getId();
                dto.type = plugin.getType();
                dto.version = plugin.getVersion();
                dto.status = plugin.getStatus();
                dto.configurable = plugin.isConfigurable();
                if (plugin.getImageUrl() != null) {
                    dto.image = new ImageDTO.Builder(plugin.getImageUrl()).build();
                }
                if (plugin instanceof HobsonLocalPluginDescriptor) {
                    final HobsonLocalPluginDescriptor lpd = (HobsonLocalPluginDescriptor)plugin;
                    if (dto.configurable) {
                        dto.configurationClass = new PropertyContainerClassDTO.Builder(ctx, ctx.getIdProvider().createLocalPluginConfigurationClassId(pctx), lpd.getConfigurationClass(), expansions != null && expansions.has(JSONAttributes.CCLASS)).build();
                        dto.configuration = new PropertyContainerDTO.Builder(
                                ctx,
                                ctx.getLocalPluginConfiguration(pctx),
                                new PropertyContainerClassProvider() {
                                    @Override
                                    public PropertyContainerClass getPropertyContainerClass(PropertyContainerClassContext ctx) {
                                        return lpd.getConfigurationClass();
                                    }
                                },
                                PropertyContainerClassType.PLUGIN_CONFIG,
                                expansions != null && expansions.has(JSONAttributes.CONFIGURATION)
                        ).build();
                    }
                    if (remoteVersion == null) {
                        dto.image = new ImageDTO.Builder(ctx.getIdProvider().createLocalPluginIconId(pctx).getId()).build();
                    }
                    // add action classes
                    boolean expand = expansions.has(JSONAttributes.ACTION_CLASSES);
                    if (lpd.hasActionClasses()) {
                        dto.actionClasses = new ItemListDTO(idProvider.createLocalPluginActionClassesId(pctx).getId(), expand);
                        if (expand) {
                            expansions.pushContext(JSONAttributes.ACTION_CLASSES);
                            for (ActionClass ac : lpd.getActionClasses()) {
                                dto.actionClasses.add(new ActionClassDTO.Builder(ctx, idProvider.createActionClassId(ac.getContext()), ac, expansions.has(JSONAttributes.ITEM)).build());
                            }
                            expansions.popContext();
                        }
                    }
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

        public Builder actionClasses(ItemListDTO actionClasses) {
            dto.actionClasses = actionClasses;
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
