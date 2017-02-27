/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.device;

import com.whizzosoftware.hobson.api.HobsonNotFoundException;
import com.whizzosoftware.hobson.api.action.ActionClass;
import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.device.HobsonDeviceDescriptor;
import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.api.property.*;
import com.whizzosoftware.hobson.api.variable.DeviceVariableContext;
import com.whizzosoftware.hobson.api.variable.DeviceVariableDescriptor;
import com.whizzosoftware.hobson.api.variable.DeviceVariableState;
import com.whizzosoftware.hobson.dto.*;
import com.whizzosoftware.hobson.dto.action.ActionClassDTO;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.context.TemplatedIdBuildContext;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import com.whizzosoftware.hobson.dto.variable.HobsonVariableDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class HobsonDeviceDTO extends ThingDTO {
    private ItemListDTO actionClasses;
    private Boolean available;
    private PropertyContainerDTO configuration;
    private PropertyContainerClassDTO configurationClass;
    private Long lastVariableUpdate;
    private Map<String,String> links;
    private String manufacturerName;
    private String manufacturerVersion;
    private String modelName;
    private Long lastCheckIn;
    private HobsonVariableDTO preferredVariable;
    private TagsDTO tags;
    private DeviceType type;
    private ItemListDTO variables;

    private HobsonDeviceDTO(TemplatedIdBuildContext ctx, TemplatedId id) {
        super(ctx, id);
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
        if (links != null) {
            json.put(JSONAttributes.LINKS, links);
        }
        if (actionClasses != null) {
            json.put(JSONAttributes.ACTION_CLASSES, actionClasses.toJSON());
        }
        if (tags != null) {
            json.put(JSONAttributes.TAGS, tags.toJSON());
        }
        return json;
    }

    static public class Builder {
        private HobsonDeviceDTO dto;

        public Builder(JSONObject json) {
            dto = new HobsonDeviceDTO(json);
        }

        public Builder(final DTOBuildContext bctx, DeviceContext dctx, boolean showDetails) {
            TemplatedId tid = bctx.getIdProvider().createDeviceId(dctx);
            dto = new HobsonDeviceDTO(bctx, tid);
            if (showDetails) {
                final HobsonDeviceDescriptor dd = bctx.getDevice(dctx);
                ExpansionFields expansions = bctx.getExpansionFields();

                dto.setName(dd.getName());
                dto.type = dd.getType();
                dto.manufacturerName = dd.getManufacturerName();
                dto.manufacturerVersion = dd.getManufacturerVersion();
                dto.modelName = dd.getModelName();

                try {
                    dto.available = bctx.isDeviceAvailable(dd.getContext());
                    dto.lastCheckIn = bctx.getDeviceLastCheckIn(dd.getContext());
                } catch (HobsonNotFoundException ignored) {}

                // preferred variable
                if (dd.hasPreferredVariableName()) {
                    DeviceVariableContext vctx = DeviceVariableContext.create(dd.getContext(), dd.getPreferredVariableName());
                    DeviceVariableState state = null;
                    try {
                        state = bctx.getDeviceVariableState(vctx);
                    } catch (HobsonNotFoundException ignored) {}
                    dto.preferredVariable = new HobsonVariableDTO.Builder(
                        bctx,
                        bctx.getIdProvider().createDeviceVariableId(vctx),
                        bctx.getDeviceVariable(vctx),
                        state,
                        expansions.has(JSONAttributes.PREFERRED_VARIABLE)
                    ).build();
                }

                // variables
                boolean expand = expansions.has(JSONAttributes.VARIABLES);
                dto.variables = new ItemListDTO(bctx, bctx.getIdProvider().createDeviceVariablesId(dd.getContext()), expand);
                if (expand) {
                    expansions.pushContext(JSONAttributes.VARIABLES);
                    boolean expandItem = expansions.has(JSONAttributes.ITEM);
                    Collection<DeviceVariableDescriptor> hvc = bctx.getDeviceVariables(dd.getContext());
                    if (hvc != null) {
                        for (DeviceVariableDescriptor v : hvc) {
                            DeviceVariableState state = null;
                            try {
                                state = bctx.getDeviceVariableState(v.getContext());
                            } catch (HobsonNotFoundException ignored) {}
                            dto.variables.add(new HobsonVariableDTO.Builder(
                                bctx,
                                bctx.getIdProvider().createDeviceVariableId(v.getContext()),
                                v,
                                state,
                                expandItem
                            ).build());
                            if (state != null && state.getLastUpdate() != null) {
                                dto.lastVariableUpdate = dto.lastVariableUpdate != null ? Math.max(dto.lastVariableUpdate, state.getLastUpdate()) : state.getLastUpdate();
                            }
                        }
                    }
                    expansions.popContext();
                }

                // configurationClass
                dto.configurationClass = new PropertyContainerClassDTO.Builder(bctx, bctx.getIdProvider().createDeviceConfigurationClassId(dd.getContext()), bctx.getDeviceConfigurationClass(dd.getContext()), expansions.has(JSONAttributes.CCLASS)).build();

                // configuration
                PropertyContainer pc = bctx.getDeviceConfiguration(dd.getContext());
                if (pc != null) {
                    showDetails = expansions.has(JSONAttributes.CONFIGURATION);
                    expansions.pushContext(JSONAttributes.CONFIGURATION);
                    dto.configuration = new PropertyContainerDTO.Builder(
                            bctx,
                            bctx.getDeviceConfiguration(dd.getContext()),
                            new PropertyContainerClassProvider() {
                                @Override
                                public PropertyContainerClass getPropertyContainerClass(PropertyContainerClassContext ctx) {
                                    return bctx.getDeviceConfigurationClass(dd.getContext());
                                }
                            },
                            PropertyContainerClassType.DEVICE_CONFIG,
                            showDetails
                    ).build();
                    expansions.popContext();
                } else {
                    dto.configuration = new PropertyContainerDTO.Builder(bctx.getIdProvider().createDeviceConfigurationId(dd.getContext()).getId()).build();
                }

                // links
                dto.links = Collections.singletonMap("setName", bctx.getIdProvider().createDeviceNameId(dd.getContext()).getId());

                // add action classes
                expand = expansions.has(JSONAttributes.ACTION_CLASSES);
                dto.actionClasses = new ItemListDTO(bctx, bctx.getIdProvider().createDeviceActionClassesId(dctx), expand);
                if (expand) {
                    if (dd.hasActionClasses()) {
                        expansions.pushContext(JSONAttributes.ACTION_CLASSES);
                        boolean expandItem = expansions.has(JSONAttributes.ITEM);
                        for (ActionClass ac : dd.getActionClasses()) {
                            dto.actionClasses.add(new ActionClassDTO.Builder(bctx, bctx.getIdProvider().createActionClassId(ac.getContext()), ac, expandItem).build());
                        }
                        expansions.popContext();
                    }
                }

                // tags
                dto.tags = new TagsDTO(bctx, bctx.getIdProvider().createDeviceTagsId(dd.getContext()), dd.getTags());
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

        public Builder links(Map<String,String> links) {
            dto.links = links;
            return this;
        }

        public Builder actionClasses(ItemListDTO actionClasses) {
            dto.actionClasses = actionClasses;
            return this;
        }

        public Builder tags(TagsDTO tags) {
            dto.tags = tags;
            return this;
        }

        public HobsonDeviceDTO build() {
            return dto;
        }
    }
}
