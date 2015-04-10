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
import com.whizzosoftware.hobson.dto.HobsonDeviceDTO;
import com.whizzosoftware.hobson.dto.HobsonPluginDTO;
import com.whizzosoftware.hobson.dto.HobsonVariableDTO;
import com.whizzosoftware.hobson.json.JSONSerializationHelper;
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
            JSONObject hubJson = new JSONObject().
                put("userId", info.getContext().getUserId()).
                put("hubId", info.getContext().getHubId()).
                put("name", info.getName()).
                put("version", info.getVersion()).
                put("logLevel", info.getLogLevel()).
                put("location", info.getLocation() != null ? JSONSerializationHelper.createHubLocationJSON(info.getLocation()) : null).
                put("email", info.getEmail() != null ? JSONSerializationHelper.createEmailConfigurationJSON(info.getEmail()) : null).
                put("setupComplete", info.isSetupComplete());
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
            JSONObject hijson = json.getJSONObject("hub");
            HubContext hubContext = HubContext.create(hijson.getString("userId"), hijson.getString("hubId"));
            sse.setHub(
                new HobsonHub.Builder(hubContext).
                    name(hijson.getString("name")).
                    version(hijson.getString("version")).
                    logLevel(hijson.getString("logLevel")).
                    location(JSONSerializationHelper.createHubLocation(hijson.has("location") ? hijson.getJSONObject("location") : null)).
                    email(JSONSerializationHelper.createEmailConfiguration(hijson.has("email") ? hijson.getJSONObject("email") : null)).
                    setupComplete(hijson.getBoolean("setupComplete"))
                    .build()
            );
            if (hijson.has("plugins")) {
                JSONObject jplugins = hijson.getJSONObject("plugins");
                for (Object opid : jplugins.keySet()) {
                    String pluginId = (String)opid;
                    JSONObject jplugin = jplugins.getJSONObject(pluginId);
                    PluginContext pluginContext = PluginContext.create(hubContext, pluginId);
                    sse.addPlugin(new HobsonPluginDTO.Builder(pluginContext).
                        setName(jplugin.has("name") ? jplugin.getString("name") : null).
                        setVersion(jplugin.has("version") ? jplugin.getString("version") : null).
                        setType(jplugin.has("type") ? PluginType.valueOf(jplugin.getString("type")) : null).
                        setStatus(jplugin.has("status") ? new PluginStatus(jplugin.getString("status"), null) : null).
                        build()
                    );
                    if (jplugin.has("devices")) {
                        JSONObject jdevs = jplugin.getJSONObject("devices");
                        for (Object odid : jdevs.keySet()) {
                            String deviceId = (String)odid;
                            JSONObject jdevice = jdevs.getJSONObject(deviceId);
                            DeviceType type = null;
                            if (jdevice.has("type")) {
                                type = DeviceType.valueOf(jdevice.getString("type"));
                            }
                            sse.addDevice(new HobsonDeviceDTO.Builder(DeviceContext.create(pluginContext, deviceId)).
                                setName(jdevice.getString("name")).
                                setType(type).
                                build()
                            );
                            if (jdevice.has("variables")) {
                                JSONObject jvars = jdevice.getJSONObject("variables");
                                for (Object ovname : jvars.keySet()) {
                                    String varName = (String)ovname;
                                    JSONObject jvar = jvars.getJSONObject(varName);
                                    sse.addVariable(new HobsonVariableDTO.Builder(pluginId, deviceId).
                                        setName(varName).
                                        setValue(jvar.get("value")).
                                        setMask(HobsonVariable.Mask.valueOf(jvar.getString("mask"))).
                                        setLastUpdate(jvar.getLong("lastUpdate")).
                                        build()
                                    );
                                }
                            }
                        }
                    }
                }
            }
        }

        return sse;
    }
}
