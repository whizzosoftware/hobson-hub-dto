/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.config.ConfigurationPropertyMetaData;
import com.whizzosoftware.hobson.api.device.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;

/**
 * An implementation of HobsonDevice for purposes of data transfer.
 *
 * @author Dan Noguerol
 */
public class HobsonDeviceDTO implements HobsonDevice {
    private DeviceContext ctx;
    private String name;
    private DeviceType type;
    private DeviceError error;
    private String preferredVariableName;
    private Collection<ConfigurationPropertyMetaData> metaData;
    private boolean telemetryCapable = false;

    private HobsonDeviceDTO(DeviceContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public DeviceContext getContext() {
        return ctx;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DeviceType getType() {
        return type;
    }

    @Override
    public boolean hasError() {
        return (error != null);
    }

    @Override
    public DeviceError getError() {
        return error;
    }

    @Override
    public String getPreferredVariableName() {
        return preferredVariableName;
    }

    @Override
    public Collection<ConfigurationPropertyMetaData> getConfigurationPropertyMetaData() {
        return metaData;
    }

    @Override
    public boolean isTelemetryCapable() {
        return telemetryCapable;
    }

    @Override
    public HobsonDeviceRuntime getRuntime() {
        return null;
    }

    public String toString() {
        return new ToStringBuilder(this).
            append("ctx", ctx).
            append("name", name).
            append("type", type).
            append("preferredVariableName", preferredVariableName).
            append("telemetryCapable", telemetryCapable).
            append("metaData", metaData).
            append("error", error).
            toString();
    }

    public static class Builder {
        private HobsonDeviceDTO dto;

        public Builder(DeviceContext ctx) {
            dto = new HobsonDeviceDTO(ctx);
        }

        public Builder setName(String name) {
            dto.name = name;
            return this;
        }

        public Builder setType(DeviceType type) {
            dto.type = type;
            return this;
        }

        public Builder setPreferredVariableName(String preferredVariableName) {
            dto.preferredVariableName = preferredVariableName;
            return this;
        }

        public Builder setConfigurationPropertyMetaData(Collection<ConfigurationPropertyMetaData> metaData) {
            dto.metaData = metaData;
            return this;
        }

        public Builder setTelemetryCapable(boolean telemetryCapable) {
            dto.telemetryCapable = telemetryCapable;
            return this;
        }

        public Builder setError(DeviceError error) {
            dto.error = error;
            return this;
        }

        public HobsonDeviceDTO build() {
            return dto;
        }
    }
}
