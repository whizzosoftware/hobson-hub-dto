/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.json;

import com.whizzosoftware.hobson.ExpansionFields;
import com.whizzosoftware.hobson.api.HobsonInvalidRequestException;
import com.whizzosoftware.hobson.api.HobsonRuntimeException;
import com.whizzosoftware.hobson.api.activity.ActivityLogEntry;
import com.whizzosoftware.hobson.api.config.*;
import com.whizzosoftware.hobson.api.device.HobsonDevice;
import com.whizzosoftware.hobson.api.hub.HobsonHub;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.hub.PasswordChange;
import com.whizzosoftware.hobson.api.image.ImageGroup;
import com.whizzosoftware.hobson.api.plugin.PluginDescriptor;
import com.whizzosoftware.hobson.api.presence.PresenceEntity;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.task.HobsonTask;
import com.whizzosoftware.hobson.api.task.TaskManager;
import com.whizzosoftware.hobson.api.telemetry.TemporalValue;
import com.whizzosoftware.hobson.api.user.HobsonUser;
import com.whizzosoftware.hobson.api.variable.*;
import com.whizzosoftware.hobson.dto.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Helper class for serializing API objects to/from JSON.
 *
 * @author Dan Noguerol
 */
public class JSONSerializationHelper {
    private static final Logger logger = LoggerFactory.getLogger(JSONSerializationHelper.class);

    public static final String ACTION_SET = "actionSet";
    public static final String CONDITION_SET = "conditionSet";
    public static final String CONDITION_CLASS = "conditionClass";
    public static final String LAST_UPDATE = "lastUpdate";

    public static JSONArray createTypedPropertiesJSON(List<TypedProperty> classProps) {
        JSONArray props = new JSONArray();
        for (TypedProperty p : classProps) {
            JSONObject jp = new JSONObject();
            jp.put("id", p.getId());
            jp.put("name", p.getName());
            jp.put("description", p.getDescription());
            if (p.getType() != null) {
                jp.put("type", p.getType().toString());
            }
            props.put(jp);
        }
        return props;
    }

//    public static List<TaskActionDTO> createActionList(HubContext hubContext, TaskManager manager, JSONArray actions) {
//        List<TaskActionDTO> results = new ArrayList<>();
//        for (int i=0; i < actions.length(); i++) {
//            JSONObject json = (JSONObject)actions.get(i);
//            results.add(createTaskAction(manager, json));
//        }
//        return results;
//    }

    public static JSONObject createActivityEventJSON(ActivityLogEntry event) {
        JSONObject json = new JSONObject();
        json.put("timestamp", event.getTimestamp());
        json.put("name", event.getName());
        return json;
    }

//    public static JSONObject createConditionClassJSON(TaskConditionClass c) {
//        JSONObject json = new JSONObject();
//        json.put("pluginId", c.getContext().getPluginContext().getPluginId());
//        json.put("id", c.getContext().getConditionClassId());
//        json.put("name", c.getName());
//        if (c.hasProperties()) {
//            json.put("properties", createTypedPropertiesJSON(c.getProperties()));
//        }
//        return json;
//    }

//    static public Configuration createConfiguration(JSONObject json) {
//        try {
//            Configuration config = new Configuration();
//            JSONObject jsonProps = (JSONObject)json.get("properties");
//            for (Object o : jsonProps.keySet()) {
//                String configKey = o.toString();
//                JSONObject configJson = (JSONObject)jsonProps.get(configKey);
//                config.addProperty(new ConfigurationProperty(new TypedProperty(configKey), configJson.get("value")));
//            }
//            return config;
//        } catch (JSONException e) {
//            throw new HobsonInvalidRequestException(e.getMessage());
//        }
//    }

//    static public JSONObject createConfigurationJSON(Configuration config) {
//        try {
//            JSONObject json = new JSONObject();
//            JSONObject configProps = new JSONObject();
//            json.put("properties", configProps);
//            for (ConfigurationProperty pcp : config.getProperties()) {
//                JSONObject j = createConfigurationPropertyJSON(pcp);
//                configProps.put(pcp.getId(), j);
//            }
//            return json;
//        } catch (JSONException e) {
//            throw new HobsonInvalidRequestException(e.getMessage());
//        }
//    }

//    public static JSONObject createConfigurationPropertyJSON(ConfigurationProperty cp) {
//        try {
//            JSONObject json = new JSONObject();
//            json.put("name", cp.getName());
//            json.put("description", cp.getDescription());
//            json.put("type", cp.getType());
//            if (cp.hasValue()) {
//                json.put("value", cp.getValue());
//            }
//            return json;
//        } catch (JSONException e) {
//            throw new HobsonInvalidRequestException(e.getMessage());
//        }
//    }

//    public static Map<String,Object> createPropertyMap(HubContext ctx, Map<String, TypedProperty> propMap, JSONObject json) {
//        try {
//            Map<String,Object> results = new HashMap<>();
//            for (Object o : json.keySet()) {
//                String id = o.toString();
//                JSONObject vo = (JSONObject)json.get(id);
//                if (propMap.containsKey(id)) {
//                    results.put(id, TypedPropertyValueSerializer.createValueObject(ctx, propMap.get(id).getType(), vo.get("value")));
//                } else {
//                    throw new HobsonInvalidRequestException("Invalid property key: " + id);
//                }
//            }
//            return results;
//        } catch (JSONException e) {
//            throw new HobsonInvalidRequestException(e.getMessage());
//        }
//    }

    public static JSONObject createCurrentVersionJSON(String currentVersion) {
        try {
            JSONObject json = new JSONObject();
            json.put("currentVersion", currentVersion);
            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static JSONObject createDeviceJSON(HobsonDevice device) {
        try {
            JSONObject json = new JSONObject();
            json.put("id", device.getContext().getDeviceId());
            json.put("name", device.getName());
            json.put("pluginId", device.getContext().getPluginId());
            if (device.getType() != null) {
                json.put("type", device.getType().toString());
            }
            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static JSONObject createDeviceJSON(String userId, String hubId, HobsonDevice device, HobsonVariableCollection variables, Boolean telemetryEnabled, boolean details) {
        try {
            JSONObject json = createDeviceJSON(device);

            String pluginId = device.getContext().getPluginId();
            String deviceId = device.getContext().getDeviceId();

            if (details) {
                // set telemetry info
                JSONObject telem = new JSONObject();
                telem.put("capable", device.isTelemetryCapable());
                telem.put("enabled", (telemetryEnabled != null) ? telemetryEnabled: false);
                json.put("telemetry", telem);

                // set preferred variable if specified
                if (variables != null && device.getPreferredVariableName() != null) {
                    String pvName = device.getPreferredVariableName();
                    try {
                        HobsonVariable var = variables.get(pvName);
                        if (var != null) {
                            JSONObject vjson = createDeviceVariableJSON(device.getContext().getPluginId(), device.getContext().getDeviceId(), var, false);
                            vjson.put("name", pvName);
                            json.put("preferredVariable", vjson);
                        }
                    } catch (DeviceVariableNotFoundException e) {
                        logger.error("Error obtaining preferred variable for " + pluginId + "." + deviceId, e);
                    }
                }
            }

            // set all variables if specified
            if (variables != null) {
                JSONObject vars = new JSONObject();
                for (HobsonVariable v : variables.getCollection()) {
                    vars.put(v.getName(), createDeviceVariableJSON(pluginId, deviceId, v, false));
                }
                json.put("variables", vars);
            }

            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

//    public static HobsonDevice createDeviceDTO(HubContext ctx, JSONObject json) {
//        try {
//            HobsonDeviceDTO dto = new HobsonDeviceDTO.Builder(DeviceContext.create(ctx, json.getString("pluginId"), json.getString("id"))).
//                name(json.getString("name")).
//                type(DeviceType.valueOf(json.getString("type"))).
//                build();
//            return dto;
//        } catch (JSONException e) {
//            throw new HobsonInvalidRequestException(e.getMessage());
//        }
//    }

//    public static JSONObject createDeviceConfigurationJSON(Configuration config) {
//        try {
//            JSONObject json = new JSONObject();
//            for (ConfigurationProperty cp : config.getProperties()) {
//                json.put(cp.getId(), createConfigurationPropertyJSON(cp));
//            }
//            return json;
//        } catch (JSONException e) {
//            throw new HobsonInvalidRequestException(e.getMessage());
//        }
//    }

    public static JSONObject createDeviceVariableJSON(String pluginId, String deviceId, HobsonVariable v, boolean details) {
        try {
            JSONObject json = new JSONObject();

            if (v != null) {
                Object value = v.getValue();
                json.put("value", value);
                if (details) {
                    json.put("mask", v.getMask());
                    json.put(LAST_UPDATE, v.getLastUpdate());
                }
            }

            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static Object createDeviceVariableValue(JSONObject json) {
        try {
            return json.get("value");
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static EmailConfiguration createEmailConfiguration(JSONObject json) {
        try {
            if (json != null) {
                return new EmailConfiguration.Builder().
                    server((json.has("server") && !json.isNull("server")) ? json.getString("server") : null).
                    secure((json.has("server") && !json.isNull("server")) ? json.getBoolean("secure") : null).
                    username((json.has("server") && !json.isNull("server")) ? json.getString("username") : null).
                    password((json.has("password") && !json.isNull("password")) ? json.getString("password") : null).
                    senderAddress((json.has("server") && !json.isNull("server")) ? json.getString("senderAddress") : null).
                    build();
            } else {
                return null;
            }
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

//    public static JSONObject createEmailConfigurationJSON(EmailConfiguration email) {
//        try {
//            JSONObject json = null;
//            if (email != null && email.getServer() != null) {
//                json = new JSONObject();
//                json.put("server", email.getServer());
//                json.put("secure", email.isSecure());
//                json.put("senderAddress", email.getSenderAddress());
//                json.put("username", email.getUsername());
//            }
//            return json;
//        } catch (JSONException e) {
//            throw new HobsonInvalidRequestException(e.getMessage());
//        }
//    }

    public static JSONObject createErrorJSON(Throwable t) {
        return createErrorJSON(getErrorCode(t), t.getLocalizedMessage());
    }

    public static JSONObject createErrorJSON(String message) {
        return createErrorJSON(null, message);
    }

    public static JSONObject createErrorJSON(Integer code, String message) {
        JSONObject json = new JSONObject();
        JSONArray errors = new JSONArray();
        json.put("errors", errors);
        JSONObject error = new JSONObject();
        if (code == null) {
            code = HobsonRuntimeException.CODE_INTERNAL_ERROR;
        }
        error.put("code", code);
        error.put("message", message);
        errors.put(error);
        return json;
    }

    public static int getErrorCode(Throwable t) {
        if (t instanceof HobsonRuntimeException) {
            return ((HobsonRuntimeException)t).getCode();
        } else {
            return HobsonRuntimeException.CODE_INTERNAL_ERROR;
        }
    }

    public static JSONObject createGlobalVariableJSON(HobsonVariable v) {
        try {
            JSONObject json = new JSONObject();
            json.put("name", v.getName());
            json.put("value", v.getValue());
            json.put("mask", v.getMask());
            json.put(LAST_UPDATE, v.getLastUpdate());
            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static HobsonHub createHubDetails(HubContext ctx, JSONObject json) {
        return null;
//        try {
//            return new HobsonHub.Builder(ctx).
//                    name(getOptionalJSONString(json, "name", null)).
//                    version(getOptionalJSONString(json, "version", null)).
//                    location(createHubLocation(getOptionalJSONObject(json, "location"))).
//                    email(createEmailConfiguration(getOptionalJSONObject(json, "email"))).
//                    logLevel(getOptionalJSONString(json, "logLevel", null)).
//                    setupComplete(getOptionalJSONBoolean(json, "setupComplete")).
//                    build();
//        } catch (Exception e) {
//            throw new HobsonInvalidRequestException(e.getMessage());
//        }
    }

    public static JSONObject createHubDetailsJSON(HobsonHub hub) {
        return null;
//        try {
//            JSONObject json = new JSONObject();
//            json.put("id", hub.getContext().getHubId());
//            json.put("name", hub.getName());
//            json.put("version", hub.getVersion());
//            json.put("email", createEmailConfigurationJSON(hub.getEmail()));
//            json.put("location", createHubLocationJSON(hub.getLocation()));
//            json.put("logLevel", hub.getLogLevel());
//            json.put("setupComplete", hub.isSetupComplete());
//            return json;
//        } catch (JSONException e) {
//            throw new HobsonInvalidRequestException(e.getMessage());
//        }
    }

//    public static HubLocation createHubLocation(JSONObject json) {
//        if (json != null) {
//            return new HubLocation.Builder().
//                    text(getOptionalJSONString(json, "text", null)).
//                    latitude(getOptionalJSONDouble(json, "latitude", null)).
//                    longitude(getOptionalJSONDouble(json, "longitude", null)).
//                    build();
//        } else {
//            return null;
//        }
//    }
//
//    public static JSONObject createHubLocationJSON(HubLocation loc) {
//        try {
//            JSONObject json = null;
//            if (loc != null && (loc.getText() != null || loc.getLatitude() != null || loc.getLongitude() != null)) {
//                json = new JSONObject();
//                json.put("text", loc.getText());
//                json.put("latitude", loc.getLatitude());
//                json.put("longitude", loc.getLongitude());
//            }
//            return json;
//        } catch (JSONException e) {
//            throw new HobsonInvalidRequestException(e.getMessage());
//        }
//    }

    public static JSONObject createHubSummaryJSON(HobsonHub hub) {
        try {
            JSONObject json = new JSONObject();
            json.put("id", hub.getContext().getHubId());
            json.put("name", hub.getName());
            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static JSONObject createImageLibraryGroupJSON(ImageGroup group) {
        try {
            JSONObject json = new JSONObject();
            json.put("name", group.getName());
            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static JSONObject createImageLibraryImageJSON(String id) {
        return new JSONObject();
    }

    public static JSONObject createLoginResponseJSON(String token, HobsonUser hobsonUser) {
        JSONObject json = new JSONObject();
        json.put("token", token);
        json.put("user", createUserJSON(hobsonUser));
        return json;
    }

    public static PasswordChange createPasswordChange(JSONObject json) {
        try {
            return new PasswordChange(json.getString("currentPassword"), json.getString("newPassword"));
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static JSONObject createPluginJSON(String pluginId) {
        JSONObject json = new JSONObject();
        json.put("@pluginId", pluginId);
        return json;
    }

    public static JSONObject createPluginDescriptorJSON(PluginDescriptor pd, Boolean details) {
        try {
            if (pd != null && pd.getId() != null) {
                JSONObject json = new JSONObject();
                json.put("id", pd.getId());
                json.put("name", pd.getName());

                JSONObject status = new JSONObject();
                status.put("status", pd.getStatus().getStatus().toString());
                if (details != null && details && pd.getStatus().getMessage() != null) {
                    status.put("message", pd.getStatus().getMessage());
                }
                json.put("status", status);

                json.put("type", pd.getType().toString());

                if (details != null && details) {
                    // determine whether there are current and newer versions of the plugin
                    String currentVersionString = pd.getVersionString();
                    //String latestVersionString = pd.getLatestVersionString();

                    boolean hasCurrentVersion = (currentVersionString != null);
                    //boolean hasNewerVersion = (VersionUtil.versionCompare(latestVersionString, currentVersionString) == 1);

                    if (pd.getDescription() != null) {
                        json.put("description", pd.getDescription());
                    }

                    if (hasCurrentVersion) {
                        json.put("version", currentVersionString);
                    }
//                    if (hasNewerVersion) {
//                        json.put("latestVersion", latestVersionString);
//                    }
                }

                return json;
            } else {
                return null;
            }
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static JSONObject createPresenceEntityJSON(PresenceEntity entity, boolean details) {
        try {
            JSONObject json = new JSONObject();
            json.put("name", entity.getName());
            json.put("location", entity.getLocation());
            if (details) {
                json.put(LAST_UPDATE, entity.getLastUpdate());
            }
            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static HobsonTaskDTO createTask(HubContext ctx, TaskManager taskManager, JSONObject json) {
//        return new HobsonTaskDTO(json, null);
//        if (json.has("name")) {
//            if (json.has("triggerCondition")) {
//                if (json.has("actionSetId")) {
//                    return new HobsonTaskDTO(
//                        json.getString("name"),
//                        createTaskCondition(ctx, taskManager, json.getJSONObject("triggerCondition")),
//                        json.has("conditions") ? createTaskConditions(ctx, taskManager, json.getJSONArray("conditions")) : null,
//                        json.getString("actionSetId")
//                    );
//                } else if (json.has("actions") && json.getJSONArray("actions").length() > 0) {
//                    return new HobsonTaskDTO(
//                        json.getString("name"),
//                        createTaskCondition(ctx, taskManager, json.getJSONObject("triggerCondition")),
//                        json.has("conditions") ? createTaskConditions(ctx, taskManager, json.getJSONArray("conditions")) : null,
//                        createTaskActions(ctx, taskManager, json.getJSONArray("actions"))
//                    );
//                } else {
//                    throw new HobsonInvalidRequestException("Task must have at least one action");
//                }
//            } else {
//                throw new HobsonInvalidRequestException("Task must have a trigger condition");
//            }
//        } else {
//            throw new HobsonInvalidRequestException("Task name is required");
//        }
        return null;
    }

    public static JSONObject createTaskJSON(HubContext ctx, TaskManager taskManager, HobsonTask task, ExpansionFields expansions) {
        try {
            JSONObject json = new JSONObject();
            json.put("name", task.getName());

//            json.put(CONDITION_SET, createTaskConditionSetJSON(task.getConditionSet()));

//            JSONObject actionSet = new JSONObject();
//            json.put(ACTION_SET, createTaskActionSetJSON(ctx, taskManager, task.getActionSetId(), expansions));

            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

//    public static TaskActionDTO createTaskAction(TaskManager manager, JSONObject json) {
////        String pluginId = json.getString("pluginId");
////        String id = json.getString("id");
////        String actionClassId = json.getString("actionClassId");
////        Map<String,Object> values = new HashMap<>();
////        JSONObject va = (JSONObject)json.get("properties");
////        for (Object o : va.keySet()) {
////            String key = o.toString();
////            values.put(key, va.get(key));
////        }
//        return new TaskActionDTO(json, null);
//    }

    public static JSONObject createTaskActionJSON(PropertyContainer action) {
        JSONObject ja = new JSONObject();
        ja.put("plugin", createPluginJSON(action.getContainerClassContext().getPluginId()));
        ja.put("actionClass", createTaskActionClassJSON(action.getContainerClassContext().getContainerClassId()));
        if (action.hasPropertyValues()) {
            JSONObject po = new JSONObject();
            Map<String, Object> values = action.getPropertyValues();
            for (String key : values.keySet()) {
                po.put(key, values.get(key));
            }
            ja.put("properties", po);
        }
        return ja;
    }

//    public static List<TaskActionDTO> createTaskActions(HubContext ctx, TaskManager taskManager, JSONArray actions) {
//        List<TaskActionDTO> results = new ArrayList<>();
////        for (int i=0; i < actions.length(); i++) {
////            JSONObject action = (JSONObject)actions.get(i);
////            String actionClassId = action.getString("actionClassId");
////            TaskActionClassContext tacc = TaskActionClassContext.create(ctx, action.getString("pluginId"), actionClassId);
////            TaskActionClass tac = taskManager.getActionClass(tacc);
////            if (tac != null) {
////                results.add(
////                    new TaskActionDTO(tacc, null, createPropertyMap(ctx, createTypedPropertyMap(tac.getProperties()), (JSONObject) action.get("properties")))
////                );
////            } else {
////                throw new HobsonInvalidRequestException("Invalid action class: " + actionClassId);
////            }
////        }
//        return results;
//    }

    public static JSONObject createTaskActionClassJSON(String actionClassId) {
        JSONObject json = new JSONObject();
        json.put("@actionClassId", actionClassId);
        return json;
    }

//    public static JSONObject createTaskActionClassJSON(TaskActionClass actionClass) {
//        JSONObject json = new JSONObject();
//        json.put("plugin", createPluginJSON(actionClass.getContext().getPluginId()));
//        json.put("id", actionClass.getId());
//        json.put("name", actionClass.getName());
//        if (actionClass.hasProperties()) {
//            json.put("properties", createTypedPropertiesJSON(actionClass.getProperties()));
//        }
//        return json;
//    }

//    public static TaskActionSetDTO createTaskActionSet(HubContext hubContext, TaskManager manager, JSONObject json) {
//        String actionSetId = null;
//        if (json.has("actionSetId")) {
//            actionSetId = json.getString("actionSetId");
//        }
//        List<TaskActionDTO> actions = null;
//        if (json.has("actions")) {
//            JSONArray actionArray = (JSONArray)json.get("actions");
//            actions = new ArrayList<>();
//            for (int i=0; i < actionArray.length(); i++) {
//                actions.add(createTaskAction(manager, (JSONObject) actionArray.get(i)));
//            }
//        }
//        return new TaskActionSetDTO(json, null);
//    }

//    public static JSONObject createTaskActionSetJSON(HubContext ctx, TaskManager taskManager, String actionSetId, ExpansionFields expansionFields) {
//        JSONObject json = new JSONObject();
//        json.put("@actionSetId", actionSetId);
//        if (taskManager != null && expansionFields != null && expansionFields.has("actionSet")) {
//            populateTaskActionSetJSON(taskManager.getActionSet(ctx, actionSetId), json);
//        }
//        return json;
//    }

//    public JSONObject createTaskActionSetJSON(TaskActionSet actionSet) {
//        JSONObject json = new JSONObject();
//        populateTaskActionSetJSON(actionSet, json);
//        return json;
//    }

//    public static void populateTaskActionSetJSON(TaskActionSet actionSet, JSONObject json) {
//        if (actionSet != null) {
//            json.put("name", actionSet.getName());
//            JSONArray actions = new JSONArray();
//            for (TaskAction action : actionSet.getActions()) {
//                actions.put(createTaskActionJSON(action));
//            }
//            json.put("actions", actions);
//        }
//    }

//    public static TaskConditionDTO createTaskCondition(HubContext ctx, TaskManager taskManager, JSONObject condition) {
//        return new TaskConditionDTO(condition, null);
////        TaskConditionClassContext tccc = TaskConditionClassContext.create(ctx, condition.getString("pluginId"), condition.getString("conditionClassId"));
////        // TODO: pass this in?
////        TaskConditionClass tcc = taskManager.getConditionClass(tccc);
////        if (tcc != null) {
////            if (condition.has("properties")) {
////                return new TaskConditionDTO(tccc, createPropertyMap(ctx, createTypedPropertyMap(tcc.getProperties()), (JSONObject) condition.get("properties")));
////            } else {
////                throw new HobsonInvalidRequestException("Task condition does not contain any properties");
////            }
////        } else {
////            throw new HobsonInvalidRequestException("Invalid condition class: " + tccc);
////        }
//    }

    public static JSONObject createTaskConditionJSON(PropertyContainer tc) {
        JSONObject json = new JSONObject();
//        json.put("conditionClass", createTaskConditionClassJSON(tc.getContext().getConditionClassId()));
//        if (tc.hasProperties()) {
//            JSONObject jp = new JSONObject();
//            for (String key : tc.getPropertyValues().keySet()) {
//                jp.put(key, tc.getPropertyValues().get(key));
//            }
//            json.put("properties", jp);
//        }
        return json;
    }

    public static JSONObject createTaskConditionClassJSON(String conditionClassId) {
        JSONObject json = new JSONObject();
        json.put("@conditionClassId", conditionClassId);
        return json;
    }

//    public static List<TaskConditionDTO> createTaskConditions(HubContext ctx, TaskManager taskManager, JSONArray conditions) {
//        List<TaskConditionDTO> results = new ArrayList<>();
//        for (int i=0; i < conditions.length(); i++) {
//            JSONObject condition = (JSONObject)conditions.get(i);
//            results.add(createTaskCondition(ctx, taskManager, condition));
//        }
//        return results;
//    }

//    public static JSONObject createTaskConditionSetJSON(TaskConditionSet s) {
//        JSONObject json = new JSONObject();
//        json.put("trigger", createTaskConditionJSON(s.getTrigger()));
//        if (s.hasConditions()) {
//            JSONArray conditions = new JSONArray();
//            for (TaskCondition c : s.getConditions()) {
//                conditions.put(createTaskConditionJSON(c));
//            }
//            json.put("conditions", conditions);
//        }
//        return json;
//    }

    public static Map<String,TypedProperty> createTypedPropertyMap(List<TypedProperty> typedProps) {
        Map<String,TypedProperty> map = new HashMap<>();
        if (typedProps != null) {
            for (TypedProperty p : typedProps) {
                map.put(p.getId(), p);
            }
        }
        return map;
    }

    public static JSONObject createTelemetryJSON(boolean telemetryCapable, boolean telemetryEnabled, Map<String,Collection<TemporalValue>> telemetry) {
        JSONObject json = new JSONObject();

        json.put("capable", telemetryCapable);
        json.put("enabled", telemetryEnabled);

        if (telemetry != null) {
            JSONObject data = new JSONObject();
            boolean hasData = false;

            for (String varName : telemetry.keySet()) {
                Collection<TemporalValue> varTm = telemetry.get(varName);

                if (varTm.size() > 0) {
                    hasData = true;
                    JSONObject seriesJSON = new JSONObject();
                    data.put(varName, seriesJSON);

                    for (TemporalValue value : varTm) {
                        Double d = (Double) value.getValue();
                        if (d != null && !d.equals(Double.NaN)) {
                            seriesJSON.put(Long.toString(value.getTime()), d);
                        }
                    }
                }
            }

            if (hasData) {
                json.put("data", data);
            }
        }

        return json;
    }

    public static JSONObject createUserJSON(HobsonUser user) {
        try {
            JSONObject json = new JSONObject();
            json.put("id", user.getId());
            json.put("firstName", user.getFirstName());
            json.put("lastName", user.getLastName());
            json.put("email", user.getEmail());
            if (user.isRemote()) {
                JSONObject remote = new JSONObject();
                remote.put("hubCount", user.getRemoteInfo().getHubCount());
                json.put("remoteInfo", remote);
            }
            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static JSONArray createVariableEventIdJSON(Collection<String> deviceVariableEventIds) {
        try {
            JSONArray json = new JSONArray();
            for (String eventId : deviceVariableEventIds) {
                json.put(eventId);
            }
            return json;
        } catch (Exception e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static JSONObject createVariableUpdateJSON(VariableUpdate update) {
        try {
            JSONObject json = new JSONObject();
            json.put("plugin", update.getPluginId());
            if (update.getDeviceId() != null) {
                json.put("device", update.getDeviceId());
            }
            json.put("name", update.getName());
            json.put("value", update.getValue());
            json.put("timestamp", update.getTimestamp());
            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    protected static String getOptionalJSONString(JSONObject obj, String name, String defaultValue) {
        if (obj.has(name)) {
            return obj.getString(name);
        } else {
            return defaultValue;
        }
    }

    protected static JSONObject getOptionalJSONObject(JSONObject obj, String name) {
        if (obj.has(name)) {
            return (JSONObject)obj.get(name);
        } else {
            return null;
        }
    }

    protected static Boolean getOptionalJSONBoolean(JSONObject obj, String name) {
        if (obj.has(name)) {
            return (Boolean)obj.get(name);
        } else {
            return null;
        }
    }

    protected static Double getOptionalJSONDouble(JSONObject obj, String name, Double defaultValue) {
        if (obj.has(name)) {
            return obj.getDouble(name);
        } else {
            return defaultValue;
        }
    }
}
