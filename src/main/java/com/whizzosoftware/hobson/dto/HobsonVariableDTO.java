/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An implementation of HobsonVariable for purposes of data transfer.
 *
 * @author Dan Noguerol
 */
public class HobsonVariableDTO implements HobsonVariable {
    private String pluginId;
    private String deviceId;
    private String name;
    private Object value;
    private Mask mask;
    private Long lastUpdate;
    private String proxyType;

    private HobsonVariableDTO(String pluginId, String deviceId) {
        this.pluginId = pluginId;
        this.deviceId = deviceId;
    }

    @Override
    public String getPluginId() {
        return pluginId;
    }

    @Override
    public boolean hasProxyType() {
        return (proxyType != null);
    }

    @Override
    public String getProxyType() {
        return proxyType;
    }

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Mask getMask() {
        return mask;
    }

    @Override
    public Long getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public boolean isGlobal() {
        return (deviceId == null);
    }

    public String toString() {
        return new ToStringBuilder(this).
            append("name", name).
            append("value", value).
            append("mask", mask).
            append("lastUpdate", lastUpdate).
            toString();
    }

    public static class Builder {
        private HobsonVariableDTO dto;

        public Builder(String pluginId, String deviceId) {
            dto = new HobsonVariableDTO(pluginId, deviceId);
        }

        public Builder(String pluginId) {
            dto = new HobsonVariableDTO(pluginId, null);
        }

        public Builder setName(String name) {
            dto.name = name;
            return this;
        }

        public Builder setValue(Object value) {
            dto.value = value;
            return this;
        }

        public Builder setMask(Mask mask) {
            dto.mask = mask;
            return this;
        }

        public Builder setLastUpdate(Long lastUpdate) {
            dto.lastUpdate = lastUpdate;
            return this;
        }

        public Builder setProxyType(String proxyType) {
            dto.proxyType = proxyType;
            return this;
        }

        public HobsonVariableDTO build() {
            return dto;
        }
    }
}
