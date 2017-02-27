/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.api.plugin.PluginContext;
import com.whizzosoftware.hobson.api.presence.PresenceEntityContext;
import com.whizzosoftware.hobson.api.presence.PresenceLocationContext;
import com.whizzosoftware.hobson.api.property.PropertyContainerClass;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassContext;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassType;
import com.whizzosoftware.hobson.api.task.TaskContext;
import com.whizzosoftware.hobson.api.variable.DeviceVariableContext;
import com.whizzosoftware.hobson.api.variable.GlobalVariableContext;

public class MockIdProvider implements IdProvider {
    private String actionClassesId;
    private String conditionClassesId;
    private String configurationId;
    private String configurationClassId;
    private String devicesId;
    private String deviceId;
    private String deviceVariablesId;
    private String hubId;
    private String localPluginId;
    private String localPluginsId;
    private String localPluginConfigurationId;
    private String localPluginConfigurationClassId;
    private String logId;
    private String presenceEntitiesId;
    private String presenceEntityId;
    private String presenceLocationId;
    private String propertyContainerId;
    private String propertyContainerClassId;
    private String remotePluginId;
    private String remotePluginsId;
    private String tasksId;

    @Override
    public TemplatedId createTaskActionSetId(HubContext ctx, String id) {
        return null;
    }

    @Override
    public TemplatedId createTaskActionSetsId(HubContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createActionId(HubContext ctx, String actionId) {
        return null;
    }

    @Override
    public TemplatedId createActionSetId(HubContext ctx, String actionSetId) {
        return null;
    }

    @Override
    public TemplatedId createActionSetActionsId(HubContext ctx, String actionSetId) {
        return null;
    }

    @Override
    public TemplatedId createActionSetsId(HubContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createActionPropertiesId(HubContext ctx, String actionId) {
        return null;
    }

    @Override
    public TemplatedId createActivityLogId(HubContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createDataStreamsId(HubContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createDataStreamId(HubContext ctx, String dataStreamId) {
        return null;
    }

    @Override
    public TemplatedId createDataStreamDataId(HubContext ctx, String dataStreamId) {
        return null;
    }

    @Override
    public TemplatedId createDataStreamFieldsId(HubContext ctx, String dataStreamId) {
        return null;
    }

    @Override
    public TemplatedId createDataStreamTagsId(HubContext ctx, String dataStreamId) {
        return null;
    }

    @Override
    public TemplatedId createDataStreamFieldId(HubContext ctx, String dataStreamId, String fieldId) {
        return null;
    }

    @Override
    public TemplatedId createDevicesId(HubContext ctx) {
        return new TemplatedId(devicesId, null);
    }

    @Override
    public DeviceContext createDeviceContext(String deviceId) {
        return null;
    }

    @Override
    public DeviceContext createDeviceContextWithHub(HubContext ctx, String deviceId) {
        return null;
    }

    @Override
    public TemplatedId createDeviceActionClassId(DeviceContext ctx, String actionClassId) {
        return null;
    }

    @Override
    public TemplatedId createDeviceActionClassesId(DeviceContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createDeviceActionClassSupportedPropertiesId(DeviceContext dctx, String containerClassId) {
        return null;
    }

    @Override
    public TemplatedId createDeviceId(DeviceContext ctx) {
        return new TemplatedId(deviceId, null);
    }

    @Override
    public TemplatedId createDeviceConfigurationId(DeviceContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createDeviceNameId(DeviceContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createDeviceVariableDescriptionId(DeviceVariableContext vctx) {
        return null;
    }

    @Override
    public TemplatedId createDeviceVariableId(DeviceVariableContext vctx) {
        return null;
    }

    @Override
    public TemplatedId createDeviceConfigurationClassId(DeviceContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createDeviceTagsId(DeviceContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createDeviceTagNameId(HubContext ctx, String tag) {
        return null;
    }

    @Override
    public TemplatedId createPluginDeviceConfigurationClassesId(PluginContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createPluginDeviceConfigurationClassId(PluginContext pluginContext, String s) {
        return null;
    }

    @Override
    public TemplatedId createDeviceVariablesId(DeviceContext ctx) {
        return new TemplatedId(deviceVariablesId, null);
    }

    @Override
    public TemplatedId createGlobalVariableId(GlobalVariableContext globalVariableContext) {
        return null;
    }

    @Override
    public TemplatedId createGlobalVariablesId(HubContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createHubId(HubContext ctx) {
        return new TemplatedId(hubId, null);
    }

    @Override
    public TemplatedId createHubConfigurationClassId(HubContext ctx) {
        return new TemplatedId(configurationClassId, null);
    }

    @Override
    public TemplatedId createHubConfigurationId(HubContext ctx) {
        return new TemplatedId(configurationId, null);
    }

    @Override
    public TemplatedId createHubLogId(HubContext ctx) {
        return new TemplatedId(logId, null);
    }

    @Override
    public TemplatedId createHubPasswordId(HubContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createHubSerialPortsId(HubContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createHubSerialPortId(HubContext ctx, String name) {
        return null;
    }

    @Override
    public TemplatedId createUserHubsId(String userId) {
        return null;
    }

    @Override
    public TemplatedId createUsersId() {
        return null;
    }

    @Override
    public DeviceVariableContext createDeviceVariableContext(String variableId) {
        return null;
    }

    @Override
    public TemplatedId createJobId(HubContext ctx, String jobId) {
        return null;
    }

    @Override
    public TemplatedId createLocalPluginActionClassesId(PluginContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createLocalPluginConfigurationId(PluginContext ctx) {
        return new TemplatedId(localPluginConfigurationId, null);
    }

    @Override
    public TemplatedId createLocalPluginConfigurationClassId(PluginContext ctx) {
        return new TemplatedId(localPluginConfigurationClassId, null);
    }

    @Override
    public TemplatedId createLocalPluginId(PluginContext ctx) {
        return new TemplatedId(localPluginId, null);
    }

    @Override
    public TemplatedId createLocalPluginIconId(PluginContext ctx) {
        return new TemplatedId("", null);
    }

    @Override
    public TemplatedId createLocalPluginReloadId(PluginContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createLocalPluginsId(HubContext ctx) {
        return new TemplatedId(localPluginsId, null);
    }

    @Override
    public TemplatedId createPersonId(String userId) {
        return null;
    }

    @Override
    public PluginContext createPluginContext(String pluginId) {
        return null;
    }

    @Override
    public TemplatedId createPluginDevicesId(PluginContext pctx) {
        return null;
    }

    @Override
    public TemplatedId createPresenceEntitiesId(HubContext ctx) {
        return new TemplatedId(presenceEntitiesId, null);
    }

    @Override
    public PresenceEntityContext createPresenceEntityContext(String presenceEntityId) {
        return null;
    }

    @Override
    public TemplatedId createPresenceEntityId(PresenceEntityContext ctx) {
        return new TemplatedId(presenceEntityId, null);
    }

    @Override
    public TemplatedId createPresenceLocationsId(HubContext ctx) {
        return null;
    }

    @Override
    public PresenceLocationContext createPresenceLocationContext(String presenceLocationId) {
        return null;
    }

    @Override
    public TemplatedId createPresenceLocationId(PresenceLocationContext ctx) {
        return new TemplatedId(presenceLocationId, null);
    }

    @Override
    public TemplatedId createPropertyContainerId(String id, PropertyContainerClass pcc) {
        return new TemplatedId(propertyContainerId, null);
    }

    @Override
    public TemplatedId createPropertyContainerClassesId(PluginContext pctx) {
        return new TemplatedId(null, null);
    }

    @Override
    public TemplatedId createPropertyContainerClassId(PropertyContainerClassContext pccc, PropertyContainerClassType type) {
        return new TemplatedId(propertyContainerClassId, null);
    }

    @Override
    public TemplatedId createRemotePluginId(HubContext ctx, String pluginId, String version) {
        return new TemplatedId(remotePluginId, null);
    }

    @Override
    public TemplatedId createRemotePluginInstallId(HubContext ctx, String pluginId, String version) {
        return new TemplatedId(null, null);
    }

    @Override
    public TemplatedId createRemotePluginsId(HubContext ctx) {
        return new TemplatedId(remotePluginsId, null);
    }

    @Override
    public TemplatedId createRepositoriesId(HubContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createRepositoryId(HubContext ctx, String uri) {
        return new TemplatedId(null, null);
    }

    @Override
    public TemplatedId createSendTestEmailId(HubContext ctx) {
        return new TemplatedId(null, null);
    }

    @Override
    public TemplatedId createShutdownId(HubContext ctx) {
        return new TemplatedId(null, null);
    }

    @Override
    public TemplatedId createActionClassesId(HubContext ctx) {
        return new TemplatedId(actionClassesId, null);
    }

    @Override
    public TemplatedId createActionClassId(PropertyContainerClassContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createTaskConditionClassesId(HubContext ctx) {
        return new TemplatedId(conditionClassesId, null);
    }

    @Override
    public TemplatedId createTaskConditionClassId(PropertyContainerClassContext ctx) {
        return null;
    }

    @Override
    public TemplatedId createTaskConditionId(TaskContext ctx, String propertyContainerId) {
        return null;
    }

    @Override
    public TemplatedId createTaskConditionPropertiesId(TaskContext ctx, String propertyContainerId) {
        return null;
    }

    @Override
    public TemplatedId createTaskConditionsId(TaskContext ctx) {
        return new TemplatedId(null, null);
    }

    @Override
    public TemplatedId createTaskId(TaskContext ctx) {
        return new TemplatedId(null, null);
    }

    @Override
    public TemplatedId createTaskPropertiesId(TaskContext ctx) {
        return new TemplatedId(null, null);
    }

    @Override
    public TemplatedId createTasksId(HubContext ctx) {
        return new TemplatedId(tasksId, null);
    }

    @Override
    public TemplatedId createUserId(String userId) {
        return new TemplatedId(null, null);
    }

    public String getDevicesId() {
        return devicesId;
    }

    public void setDevicesId(String devicesId) {
        this.devicesId = devicesId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getHubId() {
        return hubId;
    }

    public void setHubId(String hubId) {
        this.hubId = hubId;
    }

    public String getPresenceEntitiesId() {
        return presenceEntitiesId;
    }

    public void setPresenceEntitiesId(String presenceEntitiesId) {
        this.presenceEntitiesId = presenceEntitiesId;
    }

    public String getPresenceEntityId() {
        return presenceEntityId;
    }

    public void setPresenceEntityId(String presenceEntityId) {
        this.presenceEntityId = presenceEntityId;
    }

    public String getPresenceLocationId() {
        return presenceLocationId;
    }

    public void setPresenceLocationId(String presenceLocationId) {
        this.presenceLocationId = presenceLocationId;
    }

    public String getActionClassesId() {
        return actionClassesId;
    }

    public void setActionClassesId(String actionClassesId) {
        this.actionClassesId = actionClassesId;
    }

    public String getConditionClassesId() {
        return conditionClassesId;
    }

    public void setConditionClassesId(String conditionClassesId) {
        this.conditionClassesId = conditionClassesId;
    }

    public String getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(String configurationId) {
        this.configurationId = configurationId;
    }

    public String getConfigurationClassId() {
        return configurationClassId;
    }

    public void setConfigurationClassId(String configurationClassId) {
        this.configurationClassId = configurationClassId;
    }

    public String getLocalPluginsId() {
        return localPluginsId;
    }

    public void setLocalPluginsId(String localPluginsId) {
        this.localPluginsId = localPluginsId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getRemotePluginsId() {
        return remotePluginsId;
    }

    public void setRemotePluginId(String remotePluginId) {
        this.remotePluginId = remotePluginId;
    }

    public void setRemotePluginsId(String remotePluginsId) {
        this.remotePluginsId = remotePluginsId;
    }

    public String getTasksId() {
        return tasksId;
    }

    public void setTasksId(String tasksId) {
        this.tasksId = tasksId;
    }

    public void setDeviceVariablesId(String deviceVariablesId) {
        this.deviceVariablesId = deviceVariablesId;
    }

    public void setLocalPluginId(String localPluginId) {
        this.localPluginId = localPluginId;
    }

    public void setLocalPluginConfigurationClassId(String localPluginConfigurationClassId) {
        this.localPluginConfigurationClassId = localPluginConfigurationClassId;
    }

    public void setLocalPluginConfigurationId(String localPluginConfigurationId) {
        this.localPluginConfigurationId = localPluginConfigurationId;
    }

    public void setPropertyContainerId(String propertyContainerId) {
        this.propertyContainerId = propertyContainerId;
    }

    public void setPropertyContainerClassId(String propertyContainerClassId) {
        this.propertyContainerClassId = propertyContainerClassId;
    }
}
