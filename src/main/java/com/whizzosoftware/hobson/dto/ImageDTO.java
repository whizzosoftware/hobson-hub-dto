/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONObject;

public class ImageDTO extends ThingDTO {
    private String base64Data;

    public ImageDTO(String id) {
        super(id);
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    @Override
    public String getMediaType() {
        return "application/vnd.hobson.image";
    }

    @Override
    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = super.toJSON(links);
        if (base64Data != null) {
            json.put("data", base64Data);
        }
        return json;
    }
}
