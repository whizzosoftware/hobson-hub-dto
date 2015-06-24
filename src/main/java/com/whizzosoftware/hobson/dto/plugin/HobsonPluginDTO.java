/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.plugin;

import com.whizzosoftware.hobson.api.plugin.*;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.image.ImageDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.dto.property.PropertyContainerDTO;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class HobsonPluginDTO extends ThingDTO {
    private String version;
    private PluginType type;
    private PluginStatus status;
    private Boolean configurable;
    private PropertyContainerClassDTO configurationClass;
    private PropertyContainerDTO configuration;
    private ImageDTO image;

    private HobsonPluginDTO(String id) {
        super(id, null);
    }

    @Override
    public String getMediaType() {
        return MediaTypes.PLUGIN;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.VERSION, version != null ? version : null);
        json.put(JSONAttributes.TYPE, type != null ? type.toString() : null);
        json.put(JSONAttributes.CONFIGURABLE, configurable != null ? configurable : null);
        json.put(JSONAttributes.CONFIGURATION_CLASS, configurationClass != null ? configurationClass.toJSON() : null);
        json.put(JSONAttributes.CONFIGURATION, configuration != null ? configuration.toJSON() : null);
        json.put(JSONAttributes.IMAGE, image != null ? image.toJSON() : null);
        if (status != null) {
            JSONObject psjson = new JSONObject();
            psjson.put(JSONAttributes.CODE, status.getCode().toString());
            psjson.put(JSONAttributes.MESSAGE, status.getMessage());
            json.put(JSONAttributes.STATUS, psjson);
        }
        return json;
    }

    public static class Builder {
        private HobsonPluginDTO dto;

        public Builder(String id) {
            dto = new HobsonPluginDTO(id);
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder description(String description) {
            dto.setDescription(description);
            return this;
        }

        public Builder version(String version) {
            dto.version = version;
            return this;
        }

        public Builder type(PluginType type) {
            dto.type = type;
            return this;
        }

        public Builder status(PluginStatus status) {
            dto.status = status;
            return this;
        }

        public Builder configurable(boolean configurable) {
            dto.configurable = configurable;
            return this;
        }

        public Builder configuration(PropertyContainerDTO configuration) {
            dto.configuration = configuration;
            return this;
        }

        public Builder configurationClass(PropertyContainerClassDTO configurationClass) {
            dto.configurationClass = configurationClass;
            return this;
        }

        public Builder image(ImageDTO image) {
            dto.image = image;
            return this;
        }

        public Builder addLink(String rel, String url) {
            dto.addLink(rel, url);
            return this;
        }

        public HobsonPluginDTO build() {
            return dto;
        }
    }
}
