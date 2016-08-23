/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.device;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.device.DeviceDescription;
import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.property.*;
import com.whizzosoftware.hobson.api.variable.DeviceVariable;
import com.whizzosoftware.hobson.api.variable.DeviceVariableContext;
import com.whizzosoftware.hobson.dto.*;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import com.whizzosoftware.hobson.dto.variable.HobsonVariableDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

public class HobsonDeviceDTO extends ThingDTO {
    private DeviceType type;
    private String manufacturerName;
    private String modelName;
    private String manufacturerVersion;
    private Boolean available;
    private Long lastCheckIn;
    private HobsonVariableDTO preferredVariable;
    private ItemListDTO variables;
    private PropertyContainerClassDTO configurationClass;
    private PropertyContainerDTO configuration;
    private Long lastVariableUpdate;

    private HobsonDeviceDTO(String id) {
        super(id);
    }

    private HobsonDeviceDTO(JSONObject json) {
        super(json.has(JSONAttributes.AID) ? json.getString(JSONAttributes.AID) : null);

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
        if (json.has(JSONAttributes.AVAILABLE)) {
            this.available = json.getBoolean(JSONAttributes.AVAILABLE);
        }
        if (json.has(JSONAttributes.LAST_CHECK_IN)) {
            this.lastCheckIn = json.getLong(JSONAttributes.LAST_CHECK_IN);
        }
        if (json.has(JSONAttributes.PREFERRED_VARIABLE)) {
            this.preferredVariable = new HobsonVariableDTO.Builder(json.getJSONObject(JSONAttributes.PREFERRED_VARIABLE)).build();
        }
        if (json.has(JSONAttributes.CONFIGURATION)) {
            configuration = new PropertyContainerDTO.Builder(json.getJSONObject(JSONAttributes.CONFIGURATION)).build();
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

    public Boolean getAvailable() {
        return available;
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

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.AVAILABLE, available);
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

        public Builder(final DTOBuildContext bctx, DeviceContext dctx, boolean showDetails) {
            final DeviceDescription dd = bctx.getDeviceDescription(dctx);
            dto = new HobsonDeviceDTO(bctx.getIdProvider().createDeviceId(dd.getContext()));
            if (showDetails) {
                dto.setName(dd.getName());
                dto.type = dd.getDeviceType();
                dto.manufacturerName = dd.getManufacturerName();
                dto.manufacturerVersion = dd.getManufacturerVersion();
                dto.modelName = dd.getModelName();
                dto.available = bctx.isDeviceAvailable(dd.getContext());
                dto.lastCheckIn = bctx.getDeviceLastCheckIn(dd.getContext());

                // preferred variable
                if (dd.hasPreferredVariableName()) {
                    DeviceVariableContext vctx = DeviceVariableContext.create(dd.getContext(), dd.getPreferredVariableName());
                    dto.preferredVariable = new HobsonVariableDTO.Builder(
                        bctx.getIdProvider().createDeviceVariableId(vctx),
                        bctx.getDeviceVariable(vctx),
                        bctx.getExpansionFields().has(JSONAttributes.PREFERRED_VARIABLE)
                    ).build();
                }

                // variables
                boolean expand = bctx.getExpansionFields().has(JSONAttributes.VARIABLES);
                dto.variables = new ItemListDTO(bctx.getIdProvider().createDeviceVariablesId(dd.getContext()), expand);
                if (expand) {
                    bctx.getExpansionFields().pushContext(JSONAttributes.VARIABLES);
                    boolean expandItem = bctx.getExpansionFields().has(JSONAttributes.ITEM);
                    Collection<DeviceVariable> hvc = bctx.getDeviceVariables(dd.getContext());
                    if (hvc != null) {
                        for (DeviceVariable v : hvc) {
                            dto.variables.add(new HobsonVariableDTO.Builder(
                                bctx.getIdProvider().createDeviceVariableId(v.getContext()),
                                v,
                                expandItem
                            ).build());
                            if (v.getLastUpdate() != null) {
                                dto.lastVariableUpdate = dto.lastVariableUpdate != null ? Math.max(dto.lastVariableUpdate, v.getLastUpdate()) : v.getLastUpdate();
                            }
                        }
                    }
                    bctx.getExpansionFields().popContext();
                }

                // configurationClass
                dto.configurationClass = new PropertyContainerClassDTO.Builder(bctx.getIdProvider().createDeviceConfigurationClassId(dd.getContext()), bctx.getDeviceConfigurationClass(dd.getContext()), bctx.getExpansionFields().has(JSONAttributes.CCLASS)).build();

                // configuration
                PropertyContainer pc = bctx.getDeviceConfiguration(dd.getContext());
                if (pc != null) {
                    dto.configuration = new PropertyContainerDTO.Builder(
                            bctx.getDeviceConfiguration(dd.getContext()),
                            new PropertyContainerClassProvider() {
                                @Override
                                public PropertyContainerClass getPropertyContainerClass(PropertyContainerClassContext ctx) {
                                    return bctx.getDeviceConfigurationClass(dd.getContext());
                                }
                            },
                            PropertyContainerClassType.DEVICE_CONFIG,
                            bctx.getExpansionFields().has(JSONAttributes.CONFIGURATION),
                            bctx.getExpansionFields().pushContext(JSONAttributes.CONFIGURATION),
                            bctx.getIdProvider()
                    ).build();
                    bctx.getExpansionFields().popContext();
                } else {
                    dto.configuration = new PropertyContainerDTO.Builder(bctx.getIdProvider().createDeviceConfigurationId(dd.getContext())).build();
                }
            }
        }

//        public Builder(DTOBuildContext ctx, final HobsonDevice device, boolean showDetails) {
//            dto = new HobsonDeviceDTO(ctx.getIdProvider().createDeviceId(device.getContext()));
//            if (showDetails) {
//                dto.setName(device.getName());
//                dto.type = device.getType();
//                dto.manufacturerName = device.getManufacturerName();
//                dto.manufacturerVersion = device.getManufacturerVersion();
//                dto.modelName = device.getModelName();
//                dto.available = ctx.isDeviceAvailable(device.getContext());
//                dto.lastCheckIn = ctx.getDeviceLastCheckIn(device.getContext());
//
//                // preferred variable
//                if (device.hasPreferredVariableName()) {
//                    dto.preferredVariable = new HobsonVariableDTO.Builder(ctx.getIdProvider().createVariableId(VariableContext.create(device.getContext(), device.getPreferredVariableName())), ctx.getDeviceVariable(device.getContext(), device.getPreferredVariableName()), ctx.getExpansionFields().has(JSONAttributes.PREFERRED_VARIABLE)).build();
//                }
//
//                // variables
//                boolean expand = ctx.getExpansionFields().has(JSONAttributes.VARIABLES);
//                dto.variables = new ItemListDTO(ctx.getIdProvider().createDeviceVariablesId(device.getContext()), expand);
//                if (expand) {
//                    ctx.getExpansionFields().pushContext(JSONAttributes.VARIABLES);
//                    boolean expandItem = ctx.getExpansionFields().has(JSONAttributes.ITEM);
//                    Collection<HobsonVariable> hvc = ctx.getDeviceVariables(device.getContext());
//                    if (hvc != null) {
//                        for (HobsonVariable v : hvc) {
//                            dto.variables.add(new HobsonVariableDTO.Builder(ctx.getIdProvider().createVariableId(v.getContext()), v, expandItem).build());
//                            if (v.getLastUpdate() != null) {
//                                dto.lastVariableUpdate = dto.lastVariableUpdate != null ? Math.max(dto.lastVariableUpdate, v.getLastUpdate()) : v.getLastUpdate();
//                            }
//                        }
//                    }
//                    ctx.getExpansionFields().popContext();
//                }
//
//                // configurationClass
//                dto.configurationClass = new PropertyContainerClassDTO.Builder(ctx.getIdProvider().createDeviceConfigurationClassId(device.getContext()), device.getConfigurationClass(), ctx.getExpansionFields().has(JSONAttributes.CCLASS)).build();
//
//                // configuration
//                PropertyContainer pc = ctx.getDeviceConfiguration(device.getContext());
//                if (pc != null) {
//                    dto.configuration = new PropertyContainerDTO.Builder(
//                        ctx.getDeviceConfiguration(device.getContext()),
//                        new PropertyContainerClassProvider() {
//                            @Override
//                            public PropertyContainerClass getPropertyContainerClass(PropertyContainerClassContext ctx) {
//                                return device.getConfigurationClass();
//                            }
//                        },
//                        PropertyContainerClassType.DEVICE_CONFIG,
//                        ctx.getExpansionFields().has(JSONAttributes.CONFIGURATION),
//                        ctx.getExpansionFields().pushContext(JSONAttributes.CONFIGURATION),
//                        ctx.getIdProvider()
//                    ).build();
//                    ctx.getExpansionFields().popContext();
//                } else {
//                    dto.configuration = new PropertyContainerDTO.Builder(ctx.getIdProvider().createDeviceConfigurationId(device.getContext())).build();
//                }
//            }
//        }

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

        public Builder available(Boolean available) {
            dto.available = available;
            return this;
        }

        public Builder lastCheckIn(Long checkInTime) {
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

        public HobsonDeviceDTO build() {
            return dto;
        }
    }
}
