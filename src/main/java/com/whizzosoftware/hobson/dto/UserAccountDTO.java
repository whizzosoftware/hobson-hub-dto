/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserAccountDTO {
    private long expiration;
    private boolean hasAvailableHubs;
    private Map<String,String> links;

    private UserAccountDTO(long expiration, boolean hasAvailableHubs) {
        this.expiration = expiration;
        this.hasAvailableHubs = hasAvailableHubs;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("expiration", expiration);
        json.put("hasAvailableHubs", hasAvailableHubs);
        if (links != null && links.size() > 0) {
            JSONObject l = new JSONObject();
            for (String rel : links.keySet()) {
                l.put(rel, links.get(rel));
            }
            json.put("links", l);
        }
        return json;
    }

    public static class Builder {
        private UserAccountDTO dto;

        public Builder(long expiration, boolean hasAvailableHubs) {
            dto = new UserAccountDTO(expiration, hasAvailableHubs);
        }

        public Builder link(String rel, String url) {
            if (dto.links == null) {
                dto.links = new HashMap<>();
            }
            dto.links.put(rel, url);
            return this;
        }

        public UserAccountDTO build() {
            return dto;
        }
    }
}
