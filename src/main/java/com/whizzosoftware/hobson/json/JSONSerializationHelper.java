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
import com.whizzosoftware.hobson.api.activity.ActivityLogEntry;
import com.whizzosoftware.hobson.api.config.*;
import com.whizzosoftware.hobson.api.device.HobsonDevice;
import com.whizzosoftware.hobson.api.hub.HobsonHub;
import com.whizzosoftware.hobson.api.hub.PasswordChange;
import com.whizzosoftware.hobson.api.image.ImageGroup;
import com.whizzosoftware.hobson.api.plugin.PluginDescriptor;
import com.whizzosoftware.hobson.api.presence.PresenceEntity;
import com.whizzosoftware.hobson.api.telemetry.TemporalValue;
import com.whizzosoftware.hobson.api.variable.*;
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

    public static final String LAST_UPDATE = "lastUpdate";

    public static JSONObject createActivityEventJSON(ActivityLogEntry event) {
        JSONObject json = new JSONObject();
        json.put("timestamp", event.getTimestamp());
        json.put("name", event.getName());
        return json;
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

    public static JSONObject createErrorJSON(Throwable t) {
        return createErrorJSON(getErrorCode(t), t.getLocalizedMessage());
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
}
