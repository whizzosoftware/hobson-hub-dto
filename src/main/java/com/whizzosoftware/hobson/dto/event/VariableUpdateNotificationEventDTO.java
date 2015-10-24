package com.whizzosoftware.hobson.dto.event;

import com.whizzosoftware.hobson.api.event.HobsonEvent;
import com.whizzosoftware.hobson.api.event.VariableUpdateNotificationEvent;
import com.whizzosoftware.hobson.api.variable.VariableUpdate;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VariableUpdateNotificationEventDTO extends HobsonEventDTO {
    private List<JSONObject> updates;

    public VariableUpdateNotificationEventDTO(VariableUpdateNotificationEvent event) {
        super(event.getEventId(), event.getTimestamp());

        // create update JSON
        if (event.getUpdates() != null) {
            updates = new ArrayList<>();
            for (VariableUpdate update : event.getUpdates()) {
                updates.add(createUpdate(update));
            }
        }
    }

    public VariableUpdateNotificationEventDTO(JSONObject json) {
        super(json);

        // TODO
    }

    @Override
    protected JSONObject createProperties() {
        JSONObject json = new JSONObject();
        json.put("updates", updates);
        return json;
    }

    @Override
    protected void readProperties(JSONObject json) {

    }

    @Override
    public HobsonEvent createEvent() {
        List<VariableUpdate> updates = new ArrayList<>();
        return new VariableUpdateNotificationEvent(this.timestamp, updates);
    }

    protected JSONObject createUpdate(VariableUpdate update) {
        JSONObject json = new JSONObject();
        json.put("pluginId", update.getPluginId());
        json.put("deviceId", update.getDeviceId());
        json.put("name", update.getName());
        json.put("value", update.getValue());
        json.put("timestamp", update.getTimestamp());
        return json;
    }
}
