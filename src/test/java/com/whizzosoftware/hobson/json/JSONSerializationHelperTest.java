package com.whizzosoftware.hobson.json;

import com.whizzosoftware.hobson.api.config.EmailConfiguration;
import com.whizzosoftware.hobson.api.hub.HubLocation;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JSONSerializationHelperTest {
    @Test
    public void testCreateHubLocation() {
        // test text only
        JSONObject json = new JSONObject();
        json.put("text", "my_address");
        HubLocation loc = com.whizzosoftware.hobson.json.JSONSerializationHelper.createHubLocation(json);
        assertEquals("my_address", loc.getText());
        assertNull(loc.getLatitude());
        assertNull(loc.getLongitude());

        // test lat/long only
        json = new JSONObject();
        json.put("latitude", "39.3722");
        json.put("longitude", "-104.8561");
        loc = JSONSerializationHelper.createHubLocation(json);
        assertNull(loc.getText());
        assertEquals(39.3722, loc.getLatitude(), 4);
        assertEquals(-104.8561, loc.getLongitude(), 4);
    }

    @Test
    public void testCreateEmailConfiguration() {
        JSONObject json = new JSONObject(new JSONTokener("{\"server\": null, \"secure\": null, \"username\": null, \"password\": null, \"senderAddress\": null}"));
        EmailConfiguration config = JSONSerializationHelper.createEmailConfiguration(json);
        assertNull(config.getServer());
        assertNull(config.getUsername());
        assertNull(config.getPassword());
        assertNull(config.getSenderAddress());
        assertNull(config.isSecure());
    }
}
