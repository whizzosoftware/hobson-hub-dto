/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.json.event;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.device.HobsonDevice;
import com.whizzosoftware.hobson.api.event.HobsonEvent;
import com.whizzosoftware.hobson.api.event.StateSnapshotEvent;
import com.whizzosoftware.hobson.api.hub.HobsonHub;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.plugin.HobsonPlugin;
import com.whizzosoftware.hobson.api.plugin.PluginContext;
import com.whizzosoftware.hobson.api.plugin.PluginStatus;
import com.whizzosoftware.hobson.api.plugin.PluginType;
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.dto.HobsonPluginDTO;
import org.json.JSONObject;

public class StateSnapshotEventSerializer implements HobsonEventSerializer {
    @Override
    public boolean canMarshal(HobsonEvent event) {
        return (event instanceof StateSnapshotEvent);
    }

    @Override
    public JSONObject marshal(HobsonEvent event) {
        StateSnapshotEvent sse = (StateSnapshotEvent)event;

        JSONObject json = new JSONObject();
        json.put(PROP_EVENT, StateSnapshotEvent.ID);

        if (sse.hasHub()) {
            HobsonHub info = sse.getHub();
            JSONObject hubJson = new JSONObject();
            hubJson.put("userId", info.getContext().getUserId());
            hubJson.put("hubId", info.getContext().getHubId());
            hubJson.put("name", info.getName());
            hubJson.put("version", info.getVersion());
//            hubJson.put("logLevel", info.getLogLevel());
//            hubJson.put("location", info.getLocation() != null ? JSONSerializationHelper.createHubLocationJSON(info.getLocation()) : null);
//            hubJson.put("email", info.getEmail() != null ? JSONSerializationHelper.createEmailConfigurationJSON(info.getEmail()) : null);
//            hubJson.put("setupComplete", info.isSetupComplete());
            json.put("hub", hubJson);

            if (sse.hasPlugins()) {
                JSONObject pluginsJson = new JSONObject();
                hubJson.put("plugins", pluginsJson);
                for (HobsonPlugin plugin : sse.getPlugins()) {
                    String pluginId = plugin.getContext().getPluginId();
                    JSONObject pluginJson = new JSONObject();
                    pluginJson.put("name", plugin.getName());
                    pluginJson.put("version", plugin.getVersion());
                    pluginJson.put("type", plugin.getType().toString());
                    pluginJson.put("status", plugin.getStatus().getStatus().toString());
                    pluginsJson.put(pluginId, pluginJson);
                    if (sse.hasDevices(pluginId)) {
                        JSONObject devicesJson = new JSONObject();
                        pluginJson.put("devices", devicesJson);
                        for (HobsonDevice device : sse.getDevices(pluginId)) {
                            String deviceId = device.getContext().getDeviceId();
                            JSONObject deviceJson = new JSONObject();
                            deviceJson.put("name", device.getName());
                            if (device.getType() != null) {
                                deviceJson.put("type", device.getType().toString());
                            }
                            devicesJson.put(deviceId, deviceJson);
                            if (sse.hasVariables(pluginId, deviceId)) {
                                JSONObject variablesJson = new JSONObject();
                                deviceJson.put("variables", variablesJson);
                                for (HobsonVariable v : sse.getVariables(pluginId, deviceId)) {
                                    String varName = v.getName();
                                    JSONObject variableJson = new JSONObject();
                                    variableJson.put("value", v.getValue());
                                    variableJson.put("mask", v.getMask().toString());
                                    variableJson.put("lastUpdate", v.getLastUpdate());
                                    variablesJson.put(varName, variableJson);
                                }
                            }
                        }
                    }
                }
            }
        }

        return json;
    }

    @Override
    public boolean canUnmarshal(JSONObject json) {
        return (json.has(PROP_EVENT) && json.getString(PROP_EVENT).equals(StateSnapshotEvent.ID));
    }

    @Override
    public HobsonEvent unmarshal(JSONObject json) {
        StateSnapshotEvent sse = new StateSnapshotEvent(System.currentTimeMillis());

        if (json.has("hub")) {
            JSONObject hijson = (JSONObject)json.get("hub");
            HubContext hubContext = HubContext.create(hijson.getString("userId"), hijson.getString("hubId"));
            sse.setHub(
                new HobsonHub.Builder(hubContext).
                    name(hijson.getString("name")).
                    version(hijson.getString("version")).
//                    logLevel(hijson.getString("logLevel")).
//                    location(JSONSerializationHelper.createHubLocation(hijson.has("location") ? (JSONObject) hijson.get("location") : null)).
//                    email(JSONSerializationHelper.createEmailConfiguration(hijson.has("email") ? (JSONObject) hijson.get("email") : null)).
//                    setupComplete((Boolean) hijson.get("setupComplete"))
                    build()
            );
            if (hijson.has("plugins")) {
                JSONObject jplugins = (JSONObject)hijson.get("plugins");
                for (Object opid : jplugins.keySet()) {
                    String pluginId = (String)opid;
                    JSONObject jplugin = (JSONObject)jplugins.get(pluginId);
                    PluginContext pluginContext = PluginContext.create(hubContext, pluginId);
//                    sse.addPlugin(new HobsonPluginDTO.Builder(pluginContext).
//                        name(jplugin.has("name") ? jplugin.getString("name") : null).
//                        version(jplugin.has("version") ? jplugin.getString("version") : null).
//                        type(jplugin.has("type") ? PluginType.valueOf(jplugin.getString("type")) : null).
//                        status(jplugin.has("status") ? new PluginStatus(jplugin.getString("status"), null) : null).
//                        build()
//                    );
                    if (jplugin.has("devices")) {
                        JSONObject jdevs = (JSONObject)jplugin.get("devices");
                        for (Object odid : jdevs.keySet()) {
                            String deviceId = (String)odid;
                            JSONObject jdevice = (JSONObject)jdevs.get(deviceId);
                            DeviceType type = null;
                            if (jdevice.has("type")) {
                                type = DeviceType.valueOf(jdevice.getString("type"));
                            }
//                            sse.addDevice(new HobsonDeviceDTO.Builder(DeviceContext.create(pluginContext, deviceId)).
//                                name(jdevice.getString("name")).
//                                type(type).
//                                build()
//                            );
//                            if (jdevice.has("variables")) {
//                                JSONObject jvars = (JSONObject)jdevice.get("variables");
//                                for (Object ovname : jvars.keySet()) {
//                                    String varName = (String)ovname;
//                                    JSONObject jvar = (JSONObject)jvars.get(varName);
//                                    sse.addVariable(new HobsonVariableDTO.Builder(DeviceContext.createLocal(pluginId, deviceId)).
//                                        name(varName).
//                                        setValue(jvar.get("value")).
//                                        setMask(HobsonVariable.Mask.valueOf(jvar.getString("mask"))).
//                                        setLastUpdate(jvar.getLong("lastUpdate")).
//                                        build()
//                                    );
//                                }
//                            }
                        }
                    }
                }
            }
        }

        return sse;
    }
}
