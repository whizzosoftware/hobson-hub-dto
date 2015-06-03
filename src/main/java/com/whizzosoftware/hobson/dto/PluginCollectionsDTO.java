/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONObject;

public class PluginCollectionsDTO extends ThingDTO {
    private ItemListDTO local;
    private ItemListDTO remote;

    public PluginCollectionsDTO(String id, String localId, String remoteId) {
        setId(id);
        local = new ItemListDTO(localId);
        remote = new ItemListDTO(remoteId);
    }

    public ItemListDTO getLocal() {
        return local;
    }

    public void addLocal(PluginDTO dto) {
        local.add(new ListItemDTO(dto));
    }

    public ItemListDTO getRemote() {
        return remote;
    }

    public void addRemote(PluginDTO dto) {
        remote.add(new ListItemDTO(dto));
    }

    @Override
    public String getMediaType() {
        return "application/vnd.hobson.pluginCollection";
    }

    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = super.toJSON(links);
        json.put("local", local.toJSON(links));
        json.put("remote", remote.toJSON(links));
        return json;
    }
}
