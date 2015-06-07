/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.plugin.PluginContext;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassContext;
import com.whizzosoftware.hobson.api.task.TaskContext;

public interface LinkProvider {
    public static final int ACTION_CONTAINER = 0;
    public static final int CONDITION_CONTAINER = 1;
    public static final int HUB_CONFIG_CONTAINER = 2;

    public HubContext createHubContext(String link);
    public String createTaskLink(TaskContext ctx);
    public TaskContext createTaskContext(String link);
    public String createTaskConditionClassesLink(HubContext ctx);
    public String createTaskConditionClassLink(PropertyContainerClassContext ctx);
    public PropertyContainerClassContext createTaskConditionClassContext(String link);
    public String createTaskActionClassesLink(HubContext ctx);
    public String createTaskActionClassLink(PropertyContainerClassContext ctx);
    public PropertyContainerClassContext createTaskActionClassContext(String link);
    public String createTaskActionSetLink(HubContext ctx, String actionSetId);
    public PluginContext createPluginContext(String link);
    public String createUserLink(String id);
    public String createHubLink(HubContext context);
    public String createHubsLink(String userId);
    public String createHubConfigurationClassLink(HubContext context);
    public String createHubConfigurationLink(HubContext context);
    public String createPropertyContainerLink(HubContext context, int type);
    public String createPropertyContainerClassLink(int type, PropertyContainerClassContext pccc);
    public String createTasksLink(HubContext context);
    public String createDevicesLink(HubContext context);
    public String createLocalPluginLink(PluginContext context);
    public String createLocalPluginsLink(HubContext context);
    public String createRemotePluginLink(PluginContext context);
    public String createRemotePluginsLink(HubContext context);
    public String createDeviceLink(DeviceContext context);
    public String createDeviceVariableLink(DeviceContext context, String preferredVariableName);
    public String createDeviceConfigurationLink(DeviceContext context);
    public String createDeviceConfigurationClassLink(DeviceContext context);
    public String createLocalPluginConfigurationLink(PluginContext pctx);
    public String createLocalPluginConfigurationClassLink(PluginContext pctx);
    public String createDeviceVariablesLink(DeviceContext context);
    public String createRemotePluginInstallLink(PluginContext pctx, String version);
    public String createTaskActionSetsLink(HubContext hubContext);
    public DeviceContext createDeviceContext(String deviceId);
}
