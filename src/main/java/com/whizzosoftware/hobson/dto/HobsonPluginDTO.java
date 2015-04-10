/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.config.ConfigurationPropertyMetaData;
import com.whizzosoftware.hobson.api.plugin.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An implementation of HobsonPlugin for purposes of data transfer.
 *
 * @author Dan Noguerol
 */
public class HobsonPluginDTO implements HobsonPlugin {
    private PluginContext ctx;
    private String name;
    private String version;
    private PluginType type;
    private PluginStatus status;
    private boolean configurable = false;
    private Collection<ConfigurationPropertyMetaData> metaData = new ArrayList<>();

    private HobsonPluginDTO(PluginContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public PluginContext getContext() {
        return ctx;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public PluginType getType() {
        return type;
    }

    @Override
    public PluginStatus getStatus() {
        return status;
    }

    @Override
    public boolean isConfigurable() {
        return configurable;
    }

    @Override
    public Collection<ConfigurationPropertyMetaData> getConfigurationPropertyMetaData() {
        return metaData;
    }

    @Override
    public HobsonPluginRuntime getRuntime() {
        return null;
    }

    public String toString() {
        return new ToStringBuilder(this).
            append("userId", ctx.getUserId()).
            append("hubId", ctx.getHubId()).
            append("pluginId", ctx.getPluginId()).
            append("name", name).
            append("version", version).
            append("type", type).
            append("status", status.getStatus()).
            toString();
    }

    public static class Builder {
        private HobsonPluginDTO dto;

        public Builder(PluginContext ctx) {
            dto = new HobsonPluginDTO(ctx);
        }

        public Builder setName(String name) {
            dto.name = name;
            return this;
        }

        public Builder setVersion(String version) {
            dto.version = version;
            return this;
        }

        public Builder setType(PluginType type) {
            dto.type = type;
            return this;
        }

        public Builder setStatus(PluginStatus status) {
            dto.status = status;
            return this;
        }

        public Builder setConfigurable(boolean configurable) {
            dto.configurable = configurable;
            return this;
        }

        public Builder setConfigurationPropertyMetaData(Collection<ConfigurationPropertyMetaData> metaData) {
            dto.metaData = metaData;
            return this;
        }

        public HobsonPluginDTO build() {
            return dto;
        }
    }
}
