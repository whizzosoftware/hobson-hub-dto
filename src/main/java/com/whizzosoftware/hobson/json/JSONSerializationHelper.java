/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.json;

import com.whizzosoftware.hobson.api.HobsonInvalidRequestException;
import com.whizzosoftware.hobson.api.HobsonRuntimeException;
import com.whizzosoftware.hobson.api.action.HobsonAction;
import com.whizzosoftware.hobson.api.action.meta.ActionMetaData;
import com.whizzosoftware.hobson.api.action.meta.ActionMetaDataEnumValue;
import com.whizzosoftware.hobson.api.activity.ActivityLogEntry;
import com.whizzosoftware.hobson.api.config.*;
import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.device.HobsonDevice;
import com.whizzosoftware.hobson.api.hub.HobsonHub;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.hub.HubLocation;
import com.whizzosoftware.hobson.api.hub.PasswordChange;
import com.whizzosoftware.hobson.api.image.ImageGroup;
import com.whizzosoftware.hobson.api.plugin.PluginDescriptor;
import com.whizzosoftware.hobson.api.presence.PresenceEntity;
import com.whizzosoftware.hobson.api.task.HobsonTask;
import com.whizzosoftware.hobson.api.user.HobsonUser;
import com.whizzosoftware.hobson.api.util.VersionUtil;
import com.whizzosoftware.hobson.api.variable.*;
import com.whizzosoftware.hobson.dto.HobsonDeviceDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Helper class for serializing API objects to/from JSON.
 *
 * @author Dan Noguerol
 */
public class JSONSerializationHelper {
    private static final Logger logger = LoggerFactory.getLogger(JSONSerializationHelper.class);

    public static final String LAST_UPDATE = "lastUpdate";

    public static JSONObject createActionJSON(HobsonAction action, boolean details) {
        try {
            JSONObject json = new JSONObject();

            // add summary data
            json.put("name", action.getName());
            json.put("pluginId", action.getContext().getPluginId());

            // add detail data
            if (details) {
                JSONObject metas = new JSONObject();
                JSONArray metaOrder = new JSONArray();
                for (ActionMetaData ham : action.getMetaData()) {
                    JSONObject meta = new JSONObject();
                    metaOrder.put(ham.getId());
                    meta.put("name", ham.getName());
                    meta.put("description", ham.getDescription());
                    meta.put("type", ham.getType());
                    if (ham.getType() == ActionMetaData.Type.ENUMERATION) {
                        JSONObject enumValues = new JSONObject();
                        for (ActionMetaDataEnumValue eval : ham.getEnumValues()) {
                            JSONObject enumValue = new JSONObject();
                            enumValue.put("name", eval.getName());
                            if (eval.getParam() != null) {
                                JSONObject param = new JSONObject();
                                param.put("name", eval.getParam().getName());
                                param.put("description", eval.getParam().getDescription());
                                param.put("type", eval.getParam().getType());
                                enumValue.put("param", param);
                            }
                            if (eval.getRequiredDeviceVariable() != null) {
                                enumValue.put("requiredDeviceVariable", eval.getRequiredDeviceVariable());
                            }
                            enumValues.put(eval.getId(), enumValue);
                        }
                        meta.put("enumValues", enumValues);
                    }
                    metas.put(ham.getId(), meta);
                }
                json.put("meta", metas);
                json.put("metaOrder", metaOrder);
            }

            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static JSONObject createActivityEventJSON(ActivityLogEntry event) {
        JSONObject json = new JSONObject();
        json.put("timestamp", event.getTimestamp());
        json.put("name", event.getName());
        return json;
    }

    static public Configuration createConfiguration(JSONObject json) {
        try {
            Configuration config = new Configuration();
            JSONObject jsonProps = json.getJSONObject("properties");
            for (Object o : jsonProps.keySet()) {
                String configKey = o.toString();
                JSONObject configJson = jsonProps.getJSONObject(configKey);
                config.addProperty(new ConfigurationProperty(new ConfigurationPropertyMetaData(configKey), configJson.get("value")));
            }
            return config;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static JSONObject createConfigurationPropertyJSON(ConfigurationProperty cp) {
        try {
            JSONObject json = new JSONObject();
            json.put("name", cp.getName());
            json.put("description", cp.getDescription());
            json.put("type", cp.getType());
            if (cp.hasEnumValues()) {
                JSONObject vals = new JSONObject();
                for (ConfigurationEnumValue cev : cp.getEnumValues()) {
                    JSONObject jcev = new JSONObject();
                    jcev.put("name", cev.getName());
                    vals.put(cev.getId(), jcev);
                }
                json.put("enumValues", vals);
            }
            if (cp.hasValue()) {
                json.put("value", cp.getValue());
            }
            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static Map<String,Object> createConfigurationPropertyMap(JSONObject json) {
        try {
            Map<String,Object> results = new HashMap<>();
            for (Object o : json.keySet()) {
                String name = o.toString();
                JSONObject vo = json.getJSONObject(name);
                results.put(name, vo.get("value"));
            }
            return results;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

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

            // set the preferred variable if specified
            if (details) {
                if (telemetryEnabled != null) {
                    json.put("telemetryEnabled", telemetryEnabled);
                }
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

    public static HobsonDevice createDeviceDTO(HubContext ctx, JSONObject json) {
        try {
            HobsonDeviceDTO dto = new HobsonDeviceDTO.Builder(DeviceContext.create(ctx, json.getString("pluginId"), json.getString("id"))).
                setName(json.getString("name")).
                setType(DeviceType.valueOf(json.getString("type"))).
                build();
            return dto;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static JSONObject createDeviceConfigurationJSON(Configuration config) {
        try {
            JSONObject json = new JSONObject();
            for (ConfigurationProperty cp : config.getProperties()) {
                json.put(cp.getId(), createConfigurationPropertyJSON(cp));
            }
            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

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

    public static JSONObject createEmailConfigurationJSON(EmailConfiguration email) {
        try {
            JSONObject json = null;
            if (email != null && email.getServer() != null) {
                json = new JSONObject();
                json.put("server", email.getServer());
                json.put("secure", email.isSecure());
                json.put("senderAddress", email.getSenderAddress());
                json.put("username", email.getUsername());
            }
            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static JSONObject createErrorJSON(Throwable t) {
        return createErrorJSON(getErrorCode(t), t.getLocalizedMessage());
    }

    public static JSONObject createErrorJSON(String message) {
        return createErrorJSON(HobsonRuntimeException.CODE_INTERNAL_ERROR, message);
    }

    public static JSONObject createErrorJSON(int code, String message) {
        JSONObject json = new JSONObject();
        JSONArray errors = new JSONArray();
        json.put("errors", errors);
        JSONObject error = new JSONObject();
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
        try {
            return new HobsonHub.Builder(ctx).
                    name(getOptionalJSONString(json, "name", null)).
                    version(getOptionalJSONString(json, "version", null)).
                    location(createHubLocation(getOptionalJSONObject(json, "location"))).
                    email(createEmailConfiguration(getOptionalJSONObject(json, "email"))).
                    logLevel(getOptionalJSONString(json, "logLevel", null)).
                    setupComplete(getOptionalJSONBoolean(json, "setupComplete")).
                    cloudLinkUrl(getOptionalJSONString(json, "cloudLinkUrl", null)).
                    build();
        } catch (Exception e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static JSONObject createHubDetailsJSON(HobsonHub hub) {
        try {
            JSONObject json = new JSONObject();
            json.put("id", hub.getContext().getHubId());
            json.put("name", hub.getName());
            json.put("version", hub.getVersion());
            json.put("email", createEmailConfigurationJSON(hub.getEmail()));
            json.put("location", createHubLocationJSON(hub.getLocation()));
            json.put("logLevel", hub.getLogLevel());
            json.put("setupComplete", hub.isSetupComplete());
            json.put("cloudLinkUrl", hub.getCloudLinkUrl());
            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static HubLocation createHubLocation(JSONObject json) {
        if (json != null) {
            return new HubLocation.Builder().
                    text(getOptionalJSONString(json, "text", null)).
                    latitude(getOptionalJSONDouble(json, "latitude", null)).
                    longitude(getOptionalJSONDouble(json, "longitude", null)).
                    build();
        } else {
            return null;
        }
    }

    public static JSONObject createHubLocationJSON(HubLocation loc) {
        try {
            JSONObject json = null;
            if (loc != null && (loc.getText() != null || loc.getLatitude() != null || loc.getLongitude() != null)) {
                json = new JSONObject();
                json.put("text", loc.getText());
                json.put("latitude", loc.getLatitude());
                json.put("longitude", loc.getLongitude());
            }
            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

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

    public static PasswordChange createPasswordChange(JSONObject json) {
        try {
            return new PasswordChange(json.getString("currentPassword"), json.getString("newPassword"));
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    static public JSONObject createPluginConfigPropertiesJSON(String pluginId, Configuration config) {
        try {
            JSONObject json = new JSONObject();
            JSONObject configProps = new JSONObject();
            json.put("properties", configProps);
            for (ConfigurationProperty pcp : config.getProperties()) {
                JSONObject j = createConfigurationPropertyJSON(pcp);
                configProps.put(pcp.getId(), j);
            }
            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
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
                    String currentVersionString = pd.getCurrentVersionString();
                    String latestVersionString = pd.getLatestVersionString();

                    boolean hasCurrentVersion = (currentVersionString != null);
                    boolean hasNewerVersion = (VersionUtil.versionCompare(latestVersionString, currentVersionString) == 1);

                    if (pd.getDescription() != null) {
                        json.put("description", pd.getDescription());
                    }

                    if (hasCurrentVersion) {
                        json.put("currentVersion", currentVersionString);
                    }
                    if (hasNewerVersion) {
                        json.put("latestVersion", latestVersionString);
                    }
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

    public static JSONObject createTaskJSON(HobsonTask task, boolean details, boolean properties) {
        try {
            JSONObject json = new JSONObject();
            json.put("id", task.getContext().getTaskId());
            json.put("name", task.getName());
            json.put("type", task.getType().toString());

            if (details) {
                json.put("pluginId", task.getContext().getPluginId());
                json.put("conditions", task.getConditions());
                json.put("actions", task.getActions());
            }

            if (properties) {
                Properties p = task.getProperties();
                if (p != null && p.size() > 0) {
                    JSONObject props = new JSONObject();
                    for (Object o : p.keySet()) {
                        String key = o.toString();
                        props.put(o.toString(), p.get(key));
                    }
                    json.put("properties", props);
                }
            }

            return json;
        } catch (JSONException e) {
            throw new HobsonInvalidRequestException(e.getMessage());
        }
    }

    public static JSONObject createUserJSON(HobsonUser user) {
        try {
            JSONObject json = new JSONObject();
            json.put("firstName", user.getFirstName());
            json.put("lastName", user.getLastName());
            json.put("email", user.getEmail());
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
            return obj.getJSONObject(name);
        } else {
            return null;
        }
    }

    protected static Boolean getOptionalJSONBoolean(JSONObject obj, String name) {
        if (obj.has(name)) {
            return obj.getBoolean(name);
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
