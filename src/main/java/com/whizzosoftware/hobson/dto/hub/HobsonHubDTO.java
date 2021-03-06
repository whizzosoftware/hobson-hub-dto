/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.hub;

import com.whizzosoftware.hobson.api.data.DataStream;
import com.whizzosoftware.hobson.api.device.HobsonDeviceDescriptor;
import com.whizzosoftware.hobson.api.hub.HobsonHub;
import com.whizzosoftware.hobson.api.hub.WebSocketInfo;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.api.plugin.HobsonPluginDescriptor;
import com.whizzosoftware.hobson.api.presence.PresenceEntity;
import com.whizzosoftware.hobson.api.presence.PresenceLocation;
import com.whizzosoftware.hobson.api.property.*;
import com.whizzosoftware.hobson.api.task.HobsonTask;
import com.whizzosoftware.hobson.dto.*;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.context.TemplatedIdBuildContext;
import com.whizzosoftware.hobson.dto.data.DataStreamDTO;
import com.whizzosoftware.hobson.dto.device.HobsonDeviceDTO;
import com.whizzosoftware.hobson.dto.plugin.HobsonPluginDTO;
import com.whizzosoftware.hobson.dto.presence.PresenceEntityDTO;
import com.whizzosoftware.hobson.dto.presence.PresenceLocationDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import com.whizzosoftware.hobson.dto.task.HobsonTaskDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * A DTO for HobsonHub model objects. This DTO allows for detailed in-line resource expansion.
 *
 * @author Dan Noguerol
 */
public class HobsonHubDTO extends ThingDTO {
    private static final Logger logger = LoggerFactory.getLogger(HobsonHubDTO.class);

    private ItemListDTO actionClasses;
    private String apiKey;
    private ItemListDTO conditionClasses;
    private PropertyContainerClassDTO configurationClass;
    private PropertyContainerDTO configuration;
    private ItemListDTO dataStreams;
    private ItemListDTO devices;
    private ItemListDTO globalVariables;
    private ItemListDTO localPlugins;
    private HubLogDTO log;
    private ItemListDTO presenceEntities;
    private ItemListDTO presenceLocations;
    private ItemListDTO remotePlugins;
    private ItemListDTO repositories;
    private ItemListDTO serialPorts;
    private ItemListDTO tasks;
    private String version;
    private boolean away;

    private HobsonHubDTO(TemplatedIdBuildContext ctx, TemplatedId id) {
        super(ctx, id);
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
            json.put(JSONAttributes.CCLASS, configurationClass.toJSON());
        }
        if (configuration != null) {
            json.put(JSONAttributes.CONFIGURATION, configuration.toJSON());
        }
        if (dataStreams != null) {
            json.put(JSONAttributes.DATA_STREAMS, dataStreams.toJSON());
        }
        if (devices != null) {
            json.put(JSONAttributes.DEVICES, devices.toJSON());
        }
        if (globalVariables != null) {
            json.put(JSONAttributes.GLOBAL_VARIABLES, globalVariables.toJSON());
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
        if (repositories != null) {
            json.put(JSONAttributes.REPOSITORIES, repositories.toJSON());
        }
        if (tasks != null) {
            json.put(JSONAttributes.TASKS, tasks.toJSON());
        }
        if (presenceEntities != null) {
            json.put(JSONAttributes.PRESENCE_ENTITIES, presenceEntities.toJSON());
        }
        if (presenceLocations != null) {
            json.put(JSONAttributes.PRESENCE_LOCATIONS, presenceLocations.toJSON());
        }
        if (serialPorts != null) {
            json.put(JSONAttributes.SERIAL_PORTS, serialPorts.toJSON());
        }
        json.put(JSONAttributes.VERSION, version);
        json.put(JSONAttributes.API_KEY, apiKey);

        return json;
    }

    public static class Builder {
        private HobsonHubDTO dto;

        public Builder(TemplatedIdBuildContext ctx, TemplatedId id) {
            dto = new HobsonHubDTO(ctx, id);
        }

        public Builder(DTOBuildContext ctx, final HobsonHub hub, boolean expandItem) {
            dto = new HobsonHubDTO(ctx, ctx.getIdProvider().createHubId(hub.getContext()));

            IdProvider idProvider = ctx.getIdProvider();
            ExpansionFields expansions = ctx.getExpansionFields();

            if (expandItem) {
                dto.setName(hub.getName());
                dto.apiKey = hub.getApiKey();
                dto.version = hub.getVersion();

                // add action classes
                boolean expand = expansions.has(JSONAttributes.ACTION_CLASSES);
                dto.actionClasses = new ItemListDTO(ctx, idProvider.createActionClassesId(hub.getContext()), expand);
                if (expand) {
                    expansions.pushContext(JSONAttributes.ACTION_CLASSES);
                    for (PropertyContainerClass tac : ctx.getAllActionClasses(hub.getContext())) {
                        dto.actionClasses.add(new PropertyContainerClassDTO.Builder(ctx, idProvider.createActionClassId(tac.getContext()), tac, expansions.has(JSONAttributes.ITEM)).build());
                    }
                    expansions.popContext();
                }

                // add conditionClasses
                expand = expansions.has(JSONAttributes.CONDITION_CLASSES);
                dto.conditionClasses = new ItemListDTO(ctx, idProvider.createTaskConditionClassesId(hub.getContext()), expand);
                if (expand) {
                    expansions.pushContext(JSONAttributes.CONDITION_CLASSES);
                    for (PropertyContainerClass tcc : ctx.getAllTaskConditionClasses(hub.getContext())) {
                        dto.conditionClasses.add(new PropertyContainerClassDTO.Builder(ctx, idProvider.createTaskConditionClassId(tcc.getContext()), tcc, expansions.has(JSONAttributes.ITEM)).build());
                    }
                    expansions.popContext();
                }

                // add configuration
                dto.configuration = new PropertyContainerDTO.Builder(
                        ctx,
                        ctx.getHubConfiguration(hub.getContext()),
                        new PropertyContainerClassProvider() {
                            @Override
                            public PropertyContainerClass getPropertyContainerClass(PropertyContainerClassContext ctx) {
                                return hub.getConfigurationClass();
                            }
                        },
                        PropertyContainerClassType.HUB_CONFIG,
                        expansions.has(JSONAttributes.CONFIGURATION)
                ).build();

                // add configurationClass
                dto.configurationClass = new PropertyContainerClassDTO.Builder(ctx, idProvider.createHubConfigurationClassId(hub.getContext()), hub.getConfigurationClass(), expansions.has(JSONAttributes.CCLASS)).build();

                // add data streams
                if (ctx.hasDataStreamManager(hub.getContext())) {
                    expand = expansions.has(JSONAttributes.DATA_STREAMS);
                    dto.dataStreams = new ItemListDTO(ctx, idProvider.createDataStreamsId(hub.getContext()), expand);
                    if (expand) {
                        expansions.pushContext(JSONAttributes.DATA_STREAMS);
                        boolean showDetails = expansions.has(JSONAttributes.ITEM);
                        expansions.pushContext(JSONAttributes.ITEM);
                        for (DataStream ds : ctx.getDataStreams(hub.getContext())) {
                            dto.dataStreams.add(new DataStreamDTO.Builder(ctx, hub.getContext(), ds, showDetails).build());
                        }
                        expansions.popContext();
                        expansions.popContext();
                    }
                }

                // add devices
                expand = expansions.has(JSONAttributes.DEVICES);
                dto.devices = new ItemListDTO(ctx, idProvider.createDevicesId(hub.getContext()), expand);
                if (expand) {
                    expansions.pushContext(JSONAttributes.DEVICES);
                    boolean showDetails = expansions.has(JSONAttributes.ITEM);
                    expansions.pushContext(JSONAttributes.ITEM);
                    for (HobsonDeviceDescriptor device : ctx.getAllDevices(hub.getContext())) {
                        dto.devices.add(new HobsonDeviceDTO.Builder(ctx, device.getContext(), showDetails).build());
                    }
                    expansions.popContext();
                    expansions.popContext();
                }

                // add globalVariables
                expand = expansions.has(JSONAttributes.GLOBAL_VARIABLES);
                dto.globalVariables = new ItemListDTO(ctx, idProvider.createGlobalVariablesId(hub.getContext()), expand);
                if (expand) {
                    expansions.pushContext(JSONAttributes.GLOBAL_VARIABLES);
                    boolean showDetails = expansions.has(JSONAttributes.ITEM);
                    expansions.pushContext(JSONAttributes.ITEM);
//                    for (HobsonVariable v : ctx.getGlobalVariables(hub.getContext())) {
//                        dto.globalVariables.add(new HobsonVariableDTO.Builder(idProvider.createGlobalVariableId(hub.getContext(), v.getName()), v, showDetails).build());
//                    }
                    expansions.popContext();
                    expansions.popContext();
                }

                // add log
                dto.log = new HubLogDTO(ctx, idProvider.createHubLogId(hub.getContext()));

                // add localPlugins
                expand = expansions.has(JSONAttributes.LOCAL_PLUGINS);
                dto.localPlugins = new ItemListDTO(ctx, idProvider.createLocalPluginsId(hub.getContext()), expand);
                if (expand) {
                    expansions.pushContext(JSONAttributes.LOCAL_PLUGINS);
                    boolean showDetails = expansions.has(JSONAttributes.ITEM);
                    expansions.pushContext(JSONAttributes.ITEM);
                    for (HobsonPluginDescriptor pd : ctx.getLocalPluginDescriptors(hub.getContext())) {
                        dto.localPlugins.add(new HobsonPluginDTO.Builder(ctx, hub.getContext(), pd, pd.getDescription(), null, showDetails).build());
                    }
                    expansions.popContext();
                    expansions.popContext();
                }

                // add presence entities
                expand = expansions.has(JSONAttributes.PRESENCE_ENTITIES);
                dto.presenceEntities = new ItemListDTO(ctx, idProvider.createPresenceEntitiesId(hub.getContext()), expand);
                if (expand) {
                    expansions.pushContext(JSONAttributes.PRESENCE_ENTITIES);
                    boolean showDetails = expansions.has(JSONAttributes.ITEM);
                    expansions.pushContext(JSONAttributes.ITEM);
                    for (PresenceEntity entity : ctx.getAllPresenceEntities(hub.getContext())) {
                        dto.presenceEntities.add(new PresenceEntityDTO.Builder(ctx, entity, showDetails).build());
                    }
                    expansions.popContext();
                    expansions.popContext();
                }

                // add presence locations
                expand = expansions.has(JSONAttributes.PRESENCE_LOCATIONS);
                dto.presenceLocations = new ItemListDTO(ctx, idProvider.createPresenceLocationsId(hub.getContext()), expand);
                if (expand) {
                    expansions.pushContext(JSONAttributes.PRESENCE_LOCATIONS);
                    boolean showDetails = expansions.has(JSONAttributes.ITEM);
                    expansions.pushContext(JSONAttributes.ITEM);
                    for (PresenceLocation loc : ctx.getAllPresenceLocations(hub.getContext())) {
                        dto.presenceLocations.add(new PresenceLocationDTO.Builder(ctx, loc, idProvider, showDetails).build());
                    }
                    expansions.popContext();
                    expansions.popContext();
                }

                // add remotePlugins
                expand = expansions.has(JSONAttributes.REMOTE_PLUGINS);
                dto.remotePlugins = new ItemListDTO(ctx, idProvider.createRemotePluginsId(hub.getContext()), expand);
                if (expand) {
                    expansions.pushContext(JSONAttributes.REMOTE_PLUGINS);
                    boolean showDetails = expansions.has(JSONAttributes.ITEM);
                    expansions.pushContext(JSONAttributes.ITEM);
                    for (HobsonPluginDescriptor pd : ctx.getRemotePluginDescriptors(hub.getContext())) {
                        dto.remotePlugins.add(new HobsonPluginDTO.Builder(ctx, hub.getContext(), pd, pd.getDescription(), pd.getVersion(), showDetails).build());
                    }
                    expansions.popContext();
                    expansions.popContext();
                }

                // add repositories
                expand = expansions.has(JSONAttributes.REPOSITORIES);
                dto.repositories = new ItemListDTO(ctx, idProvider.createRepositoriesId(hub.getContext()), expand);

                // add serial ports
                dto.serialPorts = new ItemListDTO(ctx, idProvider.createHubSerialPortsId(hub.getContext()), expand);

                // add tasks
                expand = expansions.has(JSONAttributes.TASKS);
                dto.tasks = new ItemListDTO(ctx, idProvider.createTasksId(hub.getContext()), expand);
                if (expand) {
                    expansions.pushContext(JSONAttributes.TASKS);
                    boolean showDetails = expansions.has(JSONAttributes.ITEM);
                    expansions.pushContext(JSONAttributes.ITEM);
                    for (HobsonTask task : ctx.getAllTasks(hub.getContext())) {
                        dto.tasks.add(new HobsonTaskDTO.Builder(
                            ctx,
                            task,
                            showDetails
                        ).build());
                    }
                    expansions.popContext();
                    expansions.popContext();
                }

                // add local links
                if (hub.isLocal()) {
                    dto.addLink("powerOff", idProvider.createShutdownId(hub.getContext()).getId());
                    dto.addLink("activityLog", idProvider.createActivityLogId(hub.getContext()).getId());
                    dto.addLink("sendTestEmail", idProvider.createSendTestEmailId(hub.getContext()).getId());
                    dto.addLink("password", idProvider.createHubPasswordId(hub.getContext()).getId());
                    if (hub.hasWebSocketInfo()) {
                        WebSocketInfo wsi = hub.getWebSocketInfo();
                        try {
                            dto.addLink("webSocket", ctx.createURI(wsi.getProtocol(), wsi.getPort(), wsi.getPath()));
                        } catch (IOException e) {
                            logger.error("Unable to create local URI", e);
                        }
                    }
                }
            }

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

        public Builder dataStreams(ItemListDTO dataStreams) {
            dto.dataStreams = dataStreams;
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

        public Builder presenceLocations(ItemListDTO presenceLocations) {
            dto.presenceLocations = presenceLocations;
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
