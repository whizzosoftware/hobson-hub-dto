/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.plugin.*;
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
        return null;
    }

    @Override
    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = super.toJSON(links);
        json.put("version", version != null ? version : null);
        json.put("type", type != null ? type.toString() : null);
        json.put("configurable", configurable != null ? configurable : null);
        json.put("configurationClass", configurationClass != null ? configurationClass.toJSON(links) : null);
        json.put("configuration", configuration != null ? configuration.toJSON(links) : null);
        json.put("image", image != null ? image.toJSON(links) : null);
        if (status != null) {
            JSONObject psjson = new JSONObject();
            psjson.put("status", status.getStatus().toString());
            psjson.put("message", status.getMessage());
            json.put("status", psjson);
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
