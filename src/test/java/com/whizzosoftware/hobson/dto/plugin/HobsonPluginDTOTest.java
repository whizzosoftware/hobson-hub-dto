/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.plugin;

import com.whizzosoftware.hobson.api.plugin.PluginStatus;
import com.whizzosoftware.hobson.api.plugin.PluginType;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class HobsonPluginDTOTest {
    @Test
    public void testToJSON() {
        HobsonPluginDTO dto = new HobsonPluginDTO.Builder("pluginLink")
            .name("pluginName")
            .description("pluginDesc")
            .configurable(true)
            .status(PluginStatus.running())
            .type(PluginType.PLUGIN)
            .addLink("foo", "bar")
            .configuration(new PropertyContainerDTO.Builder("configLink").build())
            .configurationClass(new PropertyContainerClassDTO.Builder("cclassLink").build())
            .build();

        JSONObject json = dto.toJSON();
        assertEquals("pluginLink", json.getString("@id"));
        assertEquals("pluginName", json.getString("name"));
        assertEquals("pluginDesc", json.getString("description"));
        assertEquals("RUNNING", json.getJSONObject("status").getString("code"));
        assertEquals("PLUGIN", json.getString("type"));
        assertEquals(true, json.getBoolean("configurable"));
        assertEquals("bar", json.getJSONObject("links").getString("foo"));
        assertEquals("configLink", json.getJSONObject("configuration").getString("@id"));
        assertEquals("cclassLink", json.getJSONObject("configurationClass").getString("@id"));
    }
}
