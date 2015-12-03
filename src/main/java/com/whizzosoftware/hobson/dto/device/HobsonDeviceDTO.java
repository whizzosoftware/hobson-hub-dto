/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.device;

import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.device.HobsonDevice;
import com.whizzosoftware.hobson.api.property.*;
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.dto.*;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import com.whizzosoftware.hobson.dto.telemetry.DeviceTelemetryDTO;
import com.whizzosoftware.hobson.dto.variable.HobsonVariableDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

public class HobsonDeviceDTO extends ThingDTO {
    private DeviceType type;
    private String manufacturerName;
    private String modelName;
    private String manufacturerVersion;
    private Long lastCheckIn;
    private HobsonVariableDTO preferredVariable;
    private ItemListDTO variables;
    private PropertyContainerClassDTO configurationClass;
    private PropertyContainerDTO configuration;
    private DeviceTelemetryDTO telemetry;
    private Long lastVariableUpdate;

    private HobsonDeviceDTO(String id) {
        super(id);
    }

    private HobsonDeviceDTO(JSONObject json) {
        super(json.getString(JSONAttributes.AID));

        setName(json.getString(JSONAttributes.NAME));
        if (json.has(JSONAttributes.TYPE)) {
            type = DeviceType.valueOf(json.getString(JSONAttributes.TYPE));
        }
        if (json.has(JSONAttributes.MANUFACTURER_NAME)) {
            this.manufacturerName = json.getString(JSONAttributes.MANUFACTURER_NAME);
        }
        if (json.has(JSONAttributes.MODEL_NAME)) {
            this.modelName = json.getString(JSONAttributes.MODEL_NAME);
        }
        if (json.has(JSONAttributes.MANUFACTURER_VERSION)) {
            this.manufacturerVersion = json.getString(JSONAttributes.MANUFACTURER_VERSION);
        }
        if (json.has(JSONAttributes.LAST_CHECK_IN)) {
            this.lastCheckIn = json.getLong(JSONAttributes.LAST_CHECK_IN);
        }
        if (json.has(JSONAttributes.PREFERRED_VARIABLE)) {
            this.preferredVariable = new HobsonVariableDTO.Builder(json.getJSONObject(JSONAttributes.PREFERRED_VARIABLE)).build();
        }

        if (json.has(JSONAttributes.VARIABLES)) {
            JSONObject jvaril = json.getJSONObject(JSONAttributes.VARIABLES);
            variables = new ItemListDTO(jvaril.getString(JSONAttributes.AID));
            if (jvaril.has(JSONAttributes.ITEM_LIST_ELEMENT)) {
                JSONArray jvarile = jvaril.getJSONArray(JSONAttributes.ITEM_LIST_ELEMENT);
                for (int i=0; i < jvarile.length(); i++) {
                    JSONObject jvar = jvarile.getJSONObject(i);
                    variables.add(new HobsonVariableDTO.Builder(jvar.getJSONObject(JSONAttributes.ITEM)).build());
                }
            }
        }
    }

    @Override
    public String getMediaType() {
        return MediaTypes.DEVICE;
    }

    public DeviceType getType() {
        return type;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getManufacturerVersion() {
        return manufacturerVersion;
    }

    public Long getLastCheckIn() {
        return lastCheckIn;
    }

    public Long getLastVariableUpdate() {
        return lastVariableUpdate;
    }

    public HobsonVariableDTO getPreferredVariable() {
        return preferredVariable;
    }

    public ItemListDTO getVariables() {
        return variables;
    }

    public PropertyContainerClassDTO getConfigurationClass() {
        return configurationClass;
    }

    public PropertyContainerDTO getConfiguration() {
        return configuration;
    }

    public DeviceTelemetryDTO getTelemetry() {
        return telemetry;
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.LAST_CHECK_IN, lastCheckIn);
        json.put(JSONAttributes.LAST_UPDATE, lastVariableUpdate);
        json.put(JSONAttributes.MANUFACTURER_NAME, manufacturerName);
        json.put(JSONAttributes.MANUFACTURER_VERSION, manufacturerVersion);
        json.put(JSONAttributes.MODEL_NAME, modelName);
        if (type != null) {
            json.put(JSONAttributes.TYPE, type.toString());
        }
        if (configuration != null) {
            json.put(JSONAttributes.CONFIGURATION, configuration.toJSON());
        }
        if (configurationClass != null) {
            json.put(JSONAttributes.CCLASS, configurationClass.toJSON());
        }
        if (preferredVariable != null) {
            json.put(JSONAttributes.PREFERRED_VARIABLE, preferredVariable.toJSON());
        }
        if (variables != null) {
            json.put(JSONAttributes.VARIABLES, variables.toJSON());
        }
        if (telemetry != null) {
            json.put(JSONAttributes.TELEMETRY, telemetry.toJSON());
        }
        return json;
    }

    static public class Builder {
        private HobsonDeviceDTO dto;

        public Builder(String id) {
            dto = new HobsonDeviceDTO(id);
        }

        public Builder(JSONObject json) {
            dto = new HobsonDeviceDTO(json);
        }

        public Builder(DTOBuildContext ctx, final HobsonDevice device, boolean showDetails) {
            dto = new HobsonDeviceDTO(ctx.getIdProvider().createDeviceId(device.getContext()));
            if (showDetails) {
                dto.setName(device.getName());
                dto.type = device.getType();
                dto.manufacturerName = device.getManufacturerName();
                dto.manufacturerVersion = device.getManufacturerVersion();
                dto.modelName = device.getModelName();
                dto.lastCheckIn = ctx.getDeviceLastCheckIn(device.getContext());

                // preferred variable
                if (device.hasPreferredVariableName()) {
                    dto.preferredVariable = new HobsonVariableDTO.Builder(ctx.getIdProvider().createDeviceVariableId(device.getContext(), device.getPreferredVariableName()), ctx.getDeviceVariable(device.getContext(), device.getPreferredVariableName()), ctx.getExpansionFields().has(JSONAttributes.PREFERRED_VARIABLE)).build();
                }

                // variables
                boolean expand = ctx.getExpansionFields().has(JSONAttributes.VARIABLES);
                dto.variables = new ItemListDTO(ctx.getIdProvider().createDeviceVariablesId(device.getContext()), expand);
                if (expand) {
                    ctx.getExpansionFields().pushContext(JSONAttributes.VARIABLES);
                    boolean expandItem = ctx.getExpansionFields().has(JSONAttributes.ITEM);
                    for (HobsonVariable v : ctx.getDeviceVariables(device.getContext()).getCollection()) {
                        dto.variables.add(new HobsonVariableDTO.Builder(ctx.getIdProvider().createDeviceVariableId(device.getContext(), v.getName()), v, expandItem).build());
                        if (v.getLastUpdate() != null) {
                            dto.lastVariableUpdate = dto.lastVariableUpdate != null ? Math.max(dto.lastVariableUpdate, v.getLastUpdate()) : v.getLastUpdate();
                        }
                    }
                    ctx.getExpansionFields().popContext();
                }

                // configurationClass
                dto.configurationClass = new PropertyContainerClassDTO.Builder(ctx.getIdProvider().createDeviceConfigurationClassId(device.getContext()), device.getConfigurationClass(), ctx.getExpansionFields().has(JSONAttributes.CCLASS)).build();

                // configuration
                PropertyContainer pc = ctx.getDeviceConfiguration(device.getContext());
                if (pc != null) {
                    dto.configuration = new PropertyContainerDTO.Builder(
                        ctx.getDeviceConfiguration(device.getContext()),
                        new PropertyContainerClassProvider() {
                            @Override
                            public PropertyContainerClass getPropertyContainerClass(PropertyContainerClassContext ctx) {
                                return device.getConfigurationClass();
                            }
                        },
                        PropertyContainerClassType.DEVICE_CONFIG,
                        ctx.getExpansionFields().has(JSONAttributes.CONFIGURATION),
                        ctx.getExpansionFields().pushContext(JSONAttributes.CONFIGURATION),
                        ctx.getIdProvider()
                    ).build();
                    ctx.getExpansionFields().popContext();
                } else {
                    dto.configuration = new PropertyContainerDTO.Builder(ctx.getIdProvider().createDeviceConfigurationId(device.getContext())).build();
                }

                // telemetry
                dto.telemetry = new DeviceTelemetryDTO.Builder(ctx.getIdProvider().createDeviceTelemetryId(device.getContext())).build();
            }
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder type(DeviceType type) {
            dto.type = type;
            return this;
        }

        public Builder manufacturerName(String manufacturerName) {
            dto.manufacturerName = manufacturerName;
            return this;
        }

        public Builder manufacturerVersion(String manufacturerVersion) {
            dto.manufacturerVersion = manufacturerVersion;
            return this;
        }

        public Builder modelName(String modelName) {
            dto.modelName = modelName;
            return this;
        }

        public Builder checkInTime(Long checkInTime) {
            dto.lastCheckIn = checkInTime;
            return this;
        }

        public Builder lastUpdate(Long lastUpdate) {
            dto.lastVariableUpdate = lastUpdate;
            return this;
        }

        public Builder preferredVariable(HobsonVariableDTO preferredVariable) {
            dto.preferredVariable = preferredVariable;
            return this;
        }

        public Builder variables(ItemListDTO variables) {
            dto.variables = variables;
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

        public Builder telemetry(DeviceTelemetryDTO telemetry) {
            dto.telemetry = telemetry;
            return this;
        }

        public HobsonDeviceDTO build() {
            return dto;
        }
    }
}
