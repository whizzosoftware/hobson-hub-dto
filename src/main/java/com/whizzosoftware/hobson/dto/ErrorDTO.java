/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.HobsonRuntimeException;
import com.whizzosoftware.hobson.json.JSONAttributes;
import com.whizzosoftware.hobson.json.JSONProducer;
import org.json.JSONObject;

public class ErrorDTO implements JSONProducer {
    private Integer code;
    private String message;

    public ErrorDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorDTO(Throwable t) {
        if (t instanceof HobsonRuntimeException) {
            this.code = ((HobsonRuntimeException)t).getCode();
        } else {
            this.code = HobsonRuntimeException.CODE_INTERNAL_ERROR;
        }
    }

    @Override
    public String getMediaType() {
        return MediaTypes.ERROR;
    }

    @Override
    public String getJSONMediaType() {
        return getMediaType() + "+json";
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put(JSONAttributes.CODE, code);
        json.put(JSONAttributes.MESSAGE, message);
        return json;
    }
}
