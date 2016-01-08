/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.context;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.device.HobsonDevice;
import com.whizzosoftware.hobson.api.hub.HobsonHub;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.plugin.PluginContext;
import com.whizzosoftware.hobson.api.plugin.PluginDescriptor;
import com.whizzosoftware.hobson.api.presence.PresenceEntity;
import com.whizzosoftware.hobson.api.presence.PresenceEntityContext;
import com.whizzosoftware.hobson.api.presence.PresenceLocation;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassContext;
import com.whizzosoftware.hobson.api.task.HobsonTask;
import com.whizzosoftware.hobson.api.task.action.TaskActionClass;
import com.whizzosoftware.hobson.api.task.condition.TaskConditionClass;
import com.whizzosoftware.hobson.api.variable.*;
import com.whizzosoftware.hobson.dto.ExpansionFields;

import java.util.Collection;

/**
 * A context that is used when creating DTOs to allow them to obtain classes needed to perform mappings and in-line
 * resource expansion. This represents every piece of information a DTO graph might need to populate itself with data.
 *
 * @author Dan Noguerol
 */
public interface DTOBuildContext {
    Collection<HobsonHub> getHubs(String userId);
    PropertyContainer getHubConfiguration(HubContext hctx);
    PropertyContainer getDeviceConfiguration(DeviceContext dctx);
    Collection<HobsonDevice> getAllDevices(HubContext hctx);
    Long getDeviceLastCheckIn(DeviceContext dctx);
    Collection<HobsonVariable> getGlobalVariables(HubContext hctx);
    Collection<HobsonVariable> getDeviceVariables(DeviceContext dctx);
    HobsonVariable getDeviceVariable(DeviceContext dctx, String name);
    Collection<TaskActionClass> getAllTaskActionClasses(HubContext hctx);
    TaskActionClass getTaskActionClass(PropertyContainerClassContext ctx);
    Collection<TaskConditionClass> getAllTaskConditionClasses(HubContext hctx);
    TaskConditionClass getTaskConditionClass(PropertyContainerClassContext ctx);
    Collection<HobsonTask> getAllTasks(HubContext hctx);
    PropertyContainer getLocalPluginConfiguration(PluginContext pctx);
    Collection<PluginDescriptor> getLocalPluginDescriptors(HubContext hctx);
    Collection<PluginDescriptor> getRemotePluginDescriptors(HubContext hctx);
    Collection<PresenceEntity> getAllPresenceEntities(HubContext hctx);
    Collection<PresenceLocation> getAllPresenceLocations(HubContext hctx);
    PresenceLocation getPresenceEntityLocation(PresenceEntityContext pctx);
    ExpansionFields getExpansionFields();
    IdProvider getIdProvider();
    boolean isDeviceAvailable(DeviceContext dctx);
    boolean isDeviceTelemetryEnabled(DeviceContext dctx);
}
