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
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.api.plugin.PluginContext;
import com.whizzosoftware.hobson.api.presence.PresenceEntityContext;
import com.whizzosoftware.hobson.api.presence.PresenceLocationContext;
import com.whizzosoftware.hobson.api.property.PropertyContainerClass;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassContext;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassType;
import com.whizzosoftware.hobson.api.task.TaskContext;
import com.whizzosoftware.hobson.api.variable.VariableContext;

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
    private String variableId;

    @Override
    public String createTaskActionSetId(HubContext ctx, String id) {
        return null;
    }

    @Override
    public String createTaskActionSetsId(HubContext ctx) {
        return null;
    }

    @Override
    public String createActionId(HubContext ctx, String actionId) {
        return null;
    }

    @Override
    public String createActionSetId(HubContext ctx, String actionSetId) {
        return null;
    }

    @Override
    public String createActionSetActionsId(HubContext ctx, String actionSetId) {
        return null;
    }

    @Override
    public String createActionSetsId(HubContext ctx) {
        return null;
    }

    @Override
    public String createActionPropertiesId(HubContext ctx, String actionId) {
        return null;
    }

    @Override
    public String createActivityLogId(HubContext ctx) {
        return null;
    }

    @Override
    public String createDataStreamsId(String userId) {
        return null;
    }

    @Override
    public String createDataStreamId(String userId, String dataStreamId) {
        return null;
    }

    @Override
    public String createDataStreamDataId(String userId, String dataStreamId) {
        return null;
    }

    @Override
    public String createDataStreamVariablesId(String userId, String dataStreamId) {
        return null;
    }

    @Override
    public String createDevicesId(HubContext ctx) {
        return devicesId;
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
    public String createDeviceId(DeviceContext ctx) {
        return deviceId;
    }

    @Override
    public String createDevicePassportId(HubContext ctx, String deviceId) {
        return null;
    }

    @Override
    public String createDevicePassportsId(HubContext ctx) {
        return null;
    }

    @Override
    public String createDeviceConfigurationId(DeviceContext ctx) {
        return null;
    }

    @Override
    public String createDeviceConfigurationClassId(DeviceContext ctx) {
        return null;
    }

    @Override
    public String createTelemetryDatasetId(HubContext ctx, String dataSetId) {
        return null;
    }

    @Override
    public String createDeviceVariablesId(DeviceContext ctx) {
        return deviceVariablesId;
    }

    @Override
    public String createGlobalVariableId(HubContext ctx, String name) {
        return null;
    }

    @Override
    public String createGlobalVariablesId(HubContext ctx) {
        return null;
    }

    @Override
    public String createHubId(HubContext ctx) {
        return hubId;
    }

    @Override
    public String createHubConfigurationClassId(HubContext ctx) {
        return configurationClassId;
    }

    @Override
    public String createHubConfigurationId(HubContext ctx) {
        return configurationId;
    }

    @Override
    public String createHubLogId(HubContext ctx) {
        return logId;
    }

    @Override
    public String createUserHubsId(String userId) {
        return null;
    }

    @Override
    public String createUsersId() {
        return null;
    }

    @Override
    public VariableContext createVariableContext(String variableId) {
        return null;
    }

    @Override
    public String createVariableId(VariableContext ctx) {
        return variableId;
    }

    @Override
    public String createHubUploadCredentialsId(HubContext ctx) {
        return null;
    }

    @Override
    public String createLocalPluginConfigurationId(PluginContext ctx) {
        return localPluginConfigurationId;
    }

    @Override
    public String createLocalPluginConfigurationClassId(PluginContext ctx) {
        return localPluginConfigurationClassId;
    }

    @Override
    public String createLocalPluginId(PluginContext ctx) {
        return localPluginId;
    }

    @Override
    public String createLocalPluginIconId(PluginContext ctx) {
        return null;
    }

    @Override
    public String createLocalPluginReloadId(PluginContext ctx) {
        return null;
    }

    @Override
    public String createLocalPluginsId(HubContext ctx) {
        return localPluginsId;
    }

    @Override
    public String createPersonId(String userId) {
        return null;
    }

    @Override
    public PluginContext createPluginContext(String pluginId) {
        return null;
    }

    @Override
    public String createPluginDevicesId(PluginContext pctx) {
        return null;
    }

    @Override
    public String createPresenceEntitiesId(HubContext ctx) {
        return presenceEntitiesId;
    }

    @Override
    public PresenceEntityContext createPresenceEntityContext(String presenceEntityId) {
        return null;
    }

    @Override
    public String createPresenceEntityId(PresenceEntityContext ctx) {
        return presenceEntityId;
    }

    @Override
    public String createPresenceLocationsId(HubContext ctx) {
        return null;
    }

    @Override
    public PresenceLocationContext createPresenceLocationContext(String presenceLocationId) {
        return null;
    }

    @Override
    public String createPresenceLocationId(PresenceLocationContext ctx) {
        return presenceLocationId;
    }

    @Override
    public String createPropertyContainerId(PropertyContainerClass pcc) {
        return propertyContainerId;
    }

    @Override
    public String createPropertyContainerClassId(PropertyContainerClassContext pccc, PropertyContainerClassType type) {
        return propertyContainerClassId;
    }

    @Override
    public String createRemotePluginId(PluginContext ctx, String version) {
        return remotePluginId;
    }

    @Override
    public String createRemotePluginInstallId(PluginContext ctx, String version) {
        return null;
    }

    @Override
    public String createRemotePluginsId(HubContext ctx) {
        return remotePluginsId;
    }

    @Override
    public String createRepositoriesId(HubContext ctx) {
        return null;
    }

    @Override
    public String createRepositoryId(HubContext ctx, String uri) {
        return null;
    }

    @Override
    public String createShutdownId(HubContext ctx) {
        return null;
    }

    @Override
    public String createTaskActionClassesId(HubContext ctx) {
        return actionClassesId;
    }

    @Override
    public String createTaskActionClassId(PropertyContainerClassContext ctx) {
        return null;
    }

    @Override
    public String createTaskConditionClassesId(HubContext ctx) {
        return conditionClassesId;
    }

    @Override
    public String createTaskConditionClassId(PropertyContainerClassContext ctx) {
        return null;
    }

    @Override
    public String createTaskConditionId(TaskContext ctx, String propertyContainerId) {
        return null;
    }

    @Override
    public String createTaskConditionPropertiesId(TaskContext ctx, String propertyContainerId) {
        return null;
    }

    @Override
    public String createTaskConditionsId(TaskContext ctx) {
        return null;
    }

    @Override
    public String createTaskId(TaskContext ctx) {
        return null;
    }

    @Override
    public String createTaskPropertiesId(TaskContext ctx) {
        return null;
    }

    @Override
    public String createTasksId(HubContext ctx) {
        return tasksId;
    }

    @Override
    public String createUserId(String userId) {
        return null;
    }

    @Override
    public String createVariablesId(HubContext ctx) {
        return null;
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

    public void setVariableId(String variableId) {
        this.variableId = variableId;
    }
}
